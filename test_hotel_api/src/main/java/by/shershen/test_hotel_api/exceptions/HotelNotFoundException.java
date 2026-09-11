package by.shershen.test_hotel_api.exceptions;

public class HotelNotFoundException extends RuntimeException {

    public HotelNotFoundException(Long id) {
        super("Hotel not found with id: " + id);
    }
}
