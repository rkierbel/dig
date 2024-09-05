package dig.common.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public interface DateTimeUtil {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    ZoneId ZONE_BRUSSELS = ZoneId.of("Europe/Brussels");

    static RuntimeException nullOrEmptyParseEx() {
        return new DateTimeParseException("Cannot parse null or empty String to instant", "", 0);
    }
}
