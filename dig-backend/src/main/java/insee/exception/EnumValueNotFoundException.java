package insee.exception;

public class EnumValueNotFoundException extends RuntimeException {

    public EnumValueNotFoundException(String message) {
        super(message);
    }

    public static EnumValueNotFoundException toRethrow(String name) {
        throw new EnumValueNotFoundException(name);
    }
}
