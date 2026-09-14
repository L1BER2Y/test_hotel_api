package by.shershen.test_hotel_api.controller;

import by.shershen.test_hotel_api.dto.request.HotelCreateRequestDTO;
import by.shershen.test_hotel_api.dto.response.HotelDetailResponseDTO;
import by.shershen.test_hotel_api.dto.response.HotelShortResponseDTO;
import by.shershen.test_hotel_api.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Hotels", description = "CRUD operations for hotels and management tools")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    @Operation(summary = "Get all hotels list")
    public List<HotelShortResponseDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get detailed hotel info")
    @ApiResponse(responseCode = "404", description = "Hotel found with this id was not found")
    public HotelDetailResponseDTO getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new hotel")
    @ApiResponse(responseCode = "400", description = "Validation error")
    public HotelShortResponseDTO createHotel(@Valid @RequestBody HotelCreateRequestDTO hotelCreateRequest) {
        return hotelService.createHotel(hotelCreateRequest);
    }

    @PostMapping("/{id}/amenities")
    @Operation(summary = "Add hotel amenities", description = "Respond with updated hotel amenities list")
    @ApiResponse(responseCode = "404", description = "Hotel with this id was not found")
    public List<String> addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        return hotelService.addAmenities(id, amenities);
    }
}
