package by.shershen.test_hotel_api.controller;

import by.shershen.test_hotel_api.dto.request.HotelSearchCriteria;
import by.shershen.test_hotel_api.dto.response.HotelShortResponseDTO;
import by.shershen.test_hotel_api.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Search", description = "Hotels search with params")
public class SearchController {

    private final HotelService hotelService;

    @GetMapping("/search")
    @Operation(summary = "Hotels search", description = "All params are optional, case-insensitive, various amenities imply that the hotel must provide all of those")
    public List<HotelShortResponseDTO> search(@Parameter(description = "/search?city=minsk") HotelSearchCriteria criteria) {
        return hotelService.searchHotels(criteria);
    }
}
