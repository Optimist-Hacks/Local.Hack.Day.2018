package ledmein;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class DatesTests {

    @Test
    public void dateParserTest() throws ParseException {
        String input = "2018-11-28T11:33:21Z";
        long date = Dates.formatDate(input);
        Assert.assertEquals(1543404801000L, date);
    }

}
