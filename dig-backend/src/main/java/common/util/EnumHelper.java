package common.util;

import common.exception.EnumValueNotFoundException;

import java.util.Arrays;

public class EnumHelper {

    private static final EnumHelper instance = new EnumHelper();

    public static EnumHelper helper() {
        return instance;
    }

    public <T extends Enum<T>> T deSerEnum(String name, T[] enumValues) throws EnumValueNotFoundException {
        String upperCaseName = name.toUpperCase();

        return Arrays.stream(enumValues)
                .filter(value -> value.name().equals(upperCaseName))
                .findFirst()
                .orElseThrow(() -> EnumValueNotFoundException.toRethrow(name));
    }
}
