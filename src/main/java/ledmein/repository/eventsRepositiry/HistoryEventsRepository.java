package ledmein.repository.eventsRepositiry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ledmein.deserializers.CommitDeserializer;
import ledmein.deserializers.ForkDeserializer;
import ledmein.deserializers.PullDeserializer;
import ledmein.model.Event;
import ledmein.model.github.CommitEvent;
import ledmein.model.github.ForkEvent;
import ledmein.model.github.GitHubEvent;
import ledmein.model.github.PullEvent;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class HistoryEventsRepository extends BaseEventsRepository {

    private static Logger logger = LoggerFactory.getLogger(HistoryEventsRepository.class);

    private int currentEventIndex;

    @NonNull
    private List<Event> events;

    public HistoryEventsRepository() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CommitEvent.class, new CommitDeserializer());
        module.addDeserializer(PullEvent.class, new PullDeserializer());
        module.addDeserializer(ForkEvent.class, new ForkDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public Observable<Event> onNextEvent(
            @NonNull String ownerUsername,
            @NonNull String repoName,
            long period,
            @NonNull TimeUnit unit
    ) {
        return Completable.fromAction(() -> {
            String uriPrefix = "https://api.github.com/repos/" + ownerUsername + "/" + repoName;

            List<GitHubEvent> gitHubEvents = new ArrayList<>();

            gitHubEvents.addAll(getCommits(uriPrefix));
            gitHubEvents.addAll(getPulls(uriPrefix));
            gitHubEvents.addAll(getForks(uriPrefix));

            Observable.fromIterable(gitHubEvents)
                    .sorted(Comparator.comparingLong(o -> o.eventTime))
                    .map(gitHubEvent -> new Event(gitHubEvent.author, gitHubEvent.eventType))
                    .toList()
                    .flatMapObservable(events -> {
                        currentEventIndex = 0;
                        this.events = events;

                        return Observable.interval(period, unit)
                                .doOnNext(event -> {
                                    if (currentEventIndex >= this.events.size()) {
                                        onNextEvent.onComplete();
                                    } else {
                                        onNextEvent.onNext(this.events.get(currentEventIndex++));
                                    }
                                });
                    }).subscribe();
        }).subscribeOn(Schedulers.io())
                .andThen(onNextEvent);
    }

    @SneakyThrows
    private List<CommitEvent> getCommits(@NonNull String uriPrefix) {
        return readFromRemote(buildUrl(uriPrefix, "/commits?access_token=c9e7385407b091031a56de20d1187a39b6040f8c"), new TypeReference<List<CommitEvent>>() {
        });
    }

    @SneakyThrows
    private List<PullEvent> getPulls(@NonNull String uriPrefix) {
        return readFromRemote(buildUrl(uriPrefix, "/pulls?state=all&access_token=c9e7385407b091031a56de20d1187a39b6040f8c"), new TypeReference<List<PullEvent>>() {
        });
    }

    @SneakyThrows
    private List<ForkEvent> getForks(@NonNull String uriPrefix) {
        return readFromRemote(buildUrl(uriPrefix, "/forks?access_token=c9e7385407b091031a56de20d1187a39b6040f8c"), new TypeReference<List<ForkEvent>>() {
        });
    }
}
