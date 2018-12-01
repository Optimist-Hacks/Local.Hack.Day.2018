package ledmein.repository.eventsRepositiry;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.otopba.javarocketstart.RocketText;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ledmein.deserializers.TravisEventDeserializer;
import ledmein.model.Event;
import ledmein.model.EventType;
import ledmein.model.TravisEvent;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class TravisEventsRepository extends BaseEventsRepository {

    private static Logger logger = LoggerFactory.getLogger(TravisEventsRepository.class);

    int lastBuild;
    private OkHttpClient client = new OkHttpClient();

    public TravisEventsRepository() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(TravisEvent.class, new TravisEventDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public Observable<Event> onNextEvent(
            @NonNull String ownerUsername,
            @NonNull String repoName,
            long period,
            @NonNull TimeUnit unit
    ) {
        String path = "https://api.travis-ci.com/repos/" + ownerUsername + "/" + repoName;

        lastBuild = 0;

        Observable.interval(period, unit)
                .subscribeOn(Schedulers.io())
                .map(aLong -> getTravisEvent(path))
                .filter(travisEvent -> lastBuild < travisEvent.getBuildNumber())
                .map(travisEvent -> {
                    lastBuild = travisEvent.getBuildNumber();
                    boolean failed = RocketText.safeEqualsIgnoreCase(travisEvent.getBuildState(), "errored");
                    return new Event("build", failed ? EventType.BUILD_FAILED : EventType.BUILD_SUCCESS);
                })
                .doOnNext(onNextEvent::onNext)
                .subscribe();

        return onNextEvent;
    }

    private TravisEvent getTravisEvent(@NonNull String path) {
        logger.info("call getTravisEvent");

        Response response = null;

        try {
            Request request = new Request.Builder()
                    .url(path)
                    .header("Accept", "application/vnd.travis-ci.2.1+json")
                    .header("User-Agent", "MyClient/1.0.0")
                    .build();

            response = client.newCall(request).execute();
            return mapper.readValue(response.body().string(), TravisEvent.class);
        } catch (Exception ex) {
            logger.error("Can't  read build status", ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return new TravisEvent("", -1);
    }

}
