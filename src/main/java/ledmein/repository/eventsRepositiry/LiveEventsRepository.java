package ledmein.repository.eventsRepositiry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ledmein.Token;
import ledmein.deserializers.RepoEventDeserializer;
import ledmein.model.Event;
import ledmein.model.EventType;
import ledmein.model.github.RepoEvent;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class LiveEventsRepository extends BaseEventsRepository {

    private static Logger logger = LoggerFactory.getLogger(LiveEventsRepository.class);

    long lastUpdatedTime;

    public LiveEventsRepository() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(RepoEvent.class, new RepoEventDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public Observable<Event> onNextEvent(
            @NonNull String ownerUsername,
            @NonNull String repoName,
            long period,
            @NonNull TimeUnit unit
    ) {
        String uriPrefix = "https://api.github.com/repos/" + ownerUsername + "/" + repoName;

        lastUpdatedTime = System.currentTimeMillis();

        Observable.interval(period, unit)
                .subscribeOn(Schedulers.io())
                .flatMap(intervalEvent ->
                        Observable.fromIterable(getRepoEvents(uriPrefix))
                                .filter(repoEvent -> repoEvent.eventType != EventType.IGNORE)
                                .filter(repoEvent -> repoEvent.eventTime > lastUpdatedTime)
                                .map(repoEvent -> new Event(repoEvent.author, repoEvent.eventType))
                                .doOnNext(onNextEvent::onNext)
                                .doOnComplete(() -> {
                                    logger.info("update lastUpdatedTime");
                                    lastUpdatedTime = System.currentTimeMillis();
                                })
                )
                .subscribe();

        return onNextEvent;
    }

    @SneakyThrows
    private List<RepoEvent> getRepoEvents(@NonNull String uriPrefix) {
        logger.info("call getRepoEvents");
        return readFromRemote(buildUrl(uriPrefix, "/events?access_token=" + Token.token), new TypeReference<List<RepoEvent>>() {
        });
    }

}
