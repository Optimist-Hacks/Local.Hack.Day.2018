package ledmein.repository;

import ledmein.model.Event;
import ledmein.model.EventType;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DefaultEventRepository implements EventRepository {

    OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();

    @Override
    public List<Event> getEvents(@NonNull String ownerUsername, @NonNull String repoName) {


        List<Event> events = new ArrayList<>();

        events.add(new Event("sanekyy", EventType.COMMIT));

        return events;
    }

    private void getCommits(@NonNull String repoUrl) {

        requestFactory.createRequest(
                URI.create("https://api.github.com/repos/square/okhttp/commits"),
                HttpMethod.GET
        );
    }
}
