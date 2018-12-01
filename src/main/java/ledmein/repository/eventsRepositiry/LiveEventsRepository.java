package ledmein.repository.eventsRepositiry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.reactivex.Observable;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class LiveEventsRepository extends BaseEventsRepository {

    private static Logger logger = LoggerFactory.getLogger(LiveEventsRepository.class);
    private final ObjectMapper mapper;

    public LiveEventsRepository() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CommitEvent.class, new CommitDeserializer());
        module.addDeserializer(PullEvent.class, new PullDeserializer());
        module.addDeserializer(ForkEvent.class, new ForkDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public Observable<Event> onNextEvent(@NonNull String ownerUsername, @NonNull String repoName) {
        String uriPrefix = "https://api.github.com/repos/" + ownerUsername + "/" + repoName;


        return onNextEvent;

//        return Observable.fromIterable(gitHubEvents)
//                .sorted(Comparator.comparingLong(o -> o.eventTime))
//                .map(gitHubEvent -> new Event(gitHubEvent.author, gitHubEvent.eventType))
//                .toList()
//                .blockingGet();
    }

    @SneakyThrows
    private List<CommitEvent> getRepoEvents(@NonNull String uriPrefix) {
        return readFromRemote(buildUrl(uriPrefix, "/events"), new TypeReference<List<CommitEvent>>() {
        });
    }

    @SneakyThrows
    private <T> List<T> readFromRemote(URL url, TypeReference<List<T>> typeReference) {
        logger.info("Start read " + url);
        List<T> list = mapper.readValue(url, typeReference);
        logger.info("End read " + url);
        return list;
    }

    @SneakyThrows
    private URL buildUrl(String uriPrefix, String address) {
        return new URL(uriPrefix + address);
    }

}
