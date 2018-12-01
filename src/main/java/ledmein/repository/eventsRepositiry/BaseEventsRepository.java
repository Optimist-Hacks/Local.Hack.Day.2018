package ledmein.repository.eventsRepositiry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.subjects.PublishSubject;
import ledmein.model.Event;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

public abstract class BaseEventsRepository implements EventsRepository {

    private static Logger logger = LoggerFactory.getLogger(BaseEventsRepository.class);

    @NonNull
    final ObjectMapper mapper = new ObjectMapper();

    @NonNull
    final PublishSubject<Event> onNextEvent = PublishSubject.create();

    @SneakyThrows
    <T> List<T> readFromRemote(URL url, TypeReference<List<T>> typeReference) {
        logger.info("Start read " + url);
        List<T> list = mapper.readValue(url, typeReference);
        logger.info("End read " + url);
        return list;
    }

    @SneakyThrows
    URL buildUrl(String uriPrefix, String address) {
        return new URL(uriPrefix + address);
    }
}
