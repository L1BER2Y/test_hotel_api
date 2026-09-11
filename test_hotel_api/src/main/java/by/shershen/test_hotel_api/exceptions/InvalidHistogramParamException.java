package by.shershen.test_hotel_api.exceptions;

import java.util.Set;

public class InvalidHistogramParamException extends RuntimeException {

    private static final Set<String> ALLOWED_PARAMS = Set.of("brand", "city", "country", "amenities");

    public InvalidHistogramParamException(String param) {
        super("Invalid histogram parameter: '%s'. Allowed values: %s".formatted(param, ALLOWED_PARAMS));
    }
}
