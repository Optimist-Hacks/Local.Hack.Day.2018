package ledmein.repository.eventsRepositiry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.reactivex.Completable;
import io.reactivex.Observable;
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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class LiveEventsRepository extends BaseEventsRepository {

    private static Logger logger = LoggerFactory.getLogger(LiveEventsRepository.class);


    public LiveEventsRepository() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(RepoEvent.class, new RepoEventDeserializer());
        mapper.registerModule(module);
    }

    long lastUpdatedTime;

    @Override
    public Observable<Event> onNextEvent(
            @NonNull String ownerUsername,
            @NonNull String repoName,
            long period,
            @NonNull TimeUnit unit
    ) {
        String uriPrefix = "https://api.github.com/repos/" + ownerUsername + "/" + repoName;


        Completable.fromAction(() -> {

            List<RepoEvent> events = getRepoEvents(uriPrefix);
            Optional<RepoEvent> optional = events.stream().max(Comparator.comparingLong(a -> a.eventTime));

            if (optional.isPresent()) {
                lastUpdatedTime = optional.get().eventTime;
            } else {
                lastUpdatedTime = events.get(events.size() - 1).eventTime;
            }

        }).andThen(Observable.interval(period, unit)
                .flatMap(intervalEvent ->
                        Observable.fromIterable(
                                getRepoEvents(uriPrefix)
                                        .stream()
                                        .sorted(Comparator.comparingLong(a -> a.eventTime))
                                        .collect(Collectors.toList())
                        )
                                .filter(repoEvent -> repoEvent.eventType != EventType.IGNORE)
//                                .doOnNext(repoEvent -> {
//                                    if (repoEvent.eventType == EventType.ISSUE) {
//                                        logger.info(repoEvent.toString());
//                                        logger.info("curr time: " + lastUpdatedTime);
//                                    }
//                                })
                                .filter(repoEvent -> repoEvent.eventTime > lastUpdatedTime)
//                                .doOnNext(repoEvent -> {
//                                    if (repoEvent.eventType == EventType.ISSUE) {
//                                        logger.info("AFTER ISSUE");
//                                    }
//                                })
                                .doOnNext(repoEvent -> lastUpdatedTime = Math.max(lastUpdatedTime, repoEvent.eventTime))
                                .map(repoEvent -> new Event(repoEvent.author, repoEvent.eventType))
                                .doOnNext(onNextEvent::onNext)
                                .doOnComplete(() -> {
                                    logger.info("update lastUpdatedTime");
                                })
                ))
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
