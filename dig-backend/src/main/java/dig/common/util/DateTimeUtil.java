package dig.common.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public interface DateTimeUtil {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    ZoneId ZONE_BRUSSELS = ZoneId.of("Europe/Brussels");
}
