package ledmein.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ledmein.deserializers.CommitDeserializer;
import ledmein.model.Commit;
import ledmein.model.Event;
import ledmein.model.EventType;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DefaultEventRepository implements EventRepository {

    private static Logger logger = LoggerFactory.getLogger(DefaultEventRepository.class);

    @Override
    public List<Event> getEvents(@NonNull String ownerUsername, @NonNull String repoName) {
        String uriPrefix = "https://api.github.com/repos/" + ownerUsername + "/" + repoName;


        List<EventWrapper> eventWrappers = new ArrayList<>();

        eventWrappers.addAll(getCommits(uriPrefix));
        eventWrappers.addAll(getPulls(uriPrefix));



        return eventWrappers.stream()
                .sorted((a, b) -> (int) (a.eventTime - b.eventTime))
                .map(eventWrapper -> eventWrapper.event)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public List<EventWrapper> getCommits(@NonNull String uriPrefix) {
        logger.info("Start read commits");
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Commit.class, new CommitDeserializer());
        mapper.registerModule(module);

        List<Commit> commits = mapper.readValue(new URL(uriPrefix + "/commits"), new TypeReference<List<Commit>>() {
        });

        logger.info("End read commits: " + commits);

        return commits.stream()
                .map(commit -> new EventWrapper(new Event("AUTHOR", EventType.COMMIT), commit.time))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public List<EventWrapper> getPulls(@NonNull String uriPrefix) {
        logger.info("Start read pulls");
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Commit.class, new PullsDeserializer());
        mapper.registerModule(module);

        List<Pulls> pulls = mapper.readValue(new URL(uriPrefix + "/pulls?status=all"), new TypeReference<List<Commit>>() {
        });

        logger.info("End read pulls: " + commits);

        return commits.stream()
                .map(commit -> new EventWrapper(new Event("AUTHOR", EventType.COMMIT), commit.time))
                .collect(Collectors.toList());
    }
}
