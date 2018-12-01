package ledmein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Dates {

    private static Logger logger = LoggerFactory.getLogger(Dates.class);

    public static long formatDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return dateFormat.parse(date).getTime();
        } catch (ParseException e) {
            logger.error("Can't parse date = " + date, e);
            return 0;
        }
    }

}
