package ledmein.repository.eventRepositiry;

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
public class DefaultEventRepository implements EventRepository {

    private static Logger logger = LoggerFactory.getLogger(DefaultEventRepository.class);
    private final ObjectMapper mapper;

    public DefaultEventRepository() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CommitEvent.class, new CommitDeserializer());
        module.addDeserializer(PullEvent.class, new PullDeserializer());
        module.addDeserializer(ForkEvent.class, new ForkDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public List<Event> getEvents(@NonNull String ownerUsername, @NonNull String repoName) {
        String uriPrefix = "https://api.github.com/repos/" + ownerUsername + "/" + repoName;

        List<GitHubEvent> gitHubEvents = new ArrayList<>();

        gitHubEvents.addAll(getCommits(uriPrefix));
        gitHubEvents.addAll(getPulls(uriPrefix));
        gitHubEvents.addAll(getForks(uriPrefix));

        return Observable.fromIterable(gitHubEvents)
                .sorted(Comparator.comparingLong(o -> o.eventTime))
                .map(gitHubEvent -> new Event(gitHubEvent.author, gitHubEvent.eventType))
                .toList()
                .blockingGet();
    }

    @SneakyThrows
    private List<CommitEvent> getCommits(@NonNull String uriPrefix) {
        return readFromRemote(buildUrl(uriPrefix, "/commits"), new TypeReference<List<CommitEvent>>() {
        });
    }

    @SneakyThrows
    private List<PullEvent> getPulls(@NonNull String uriPrefix) {
        return readFromRemote(buildUrl(uriPrefix, "/pulls?state=all"), new TypeReference<List<PullEvent>>() {
        });
    }

    @SneakyThrows
    private List<ForkEvent> getForks(@NonNull String uriPrefix) {
        return readFromRemote(buildUrl(uriPrefix, "/forks"), new TypeReference<List<ForkEvent>>() {
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
