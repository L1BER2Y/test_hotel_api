package by.shershen.test_hotel_api.controller;

import by.shershen.test_hotel_api.dto.request.HotelCreateRequestDTO;
import by.shershen.test_hotel_api.dto.response.HotelDetailResponseDTO;
import by.shershen.test_hotel_api.dto.response.HotelShortResponseDTO;
import by.shershen.test_hotel_api.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public List<HotelShortResponseDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelDetailResponseDTO getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelShortResponseDTO createHotel(@Valid @RequestBody HotelCreateRequestDTO hotelCreateRequest) {
        return hotelService.createHotel(hotelCreateRequest);
    }

    @PostMapping("/{id}/amenities")
    public List<String> addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        return hotelService.addAmenities(id, amenities);
    }
}
