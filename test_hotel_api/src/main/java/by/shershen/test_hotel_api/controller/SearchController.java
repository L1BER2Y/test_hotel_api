package by.shershen.test_hotel_api.controller;

import by.shershen.test_hotel_api.dto.request.HotelSearchCriteria;
import by.shershen.test_hotel_api.dto.response.HotelShortResponseDTO;
import by.shershen.test_hotel_api.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final HotelService hotelService;

    @GetMapping("/search")
    public List<HotelShortResponseDTO> search(HotelSearchCriteria criteria) {
        return hotelService.searchHotels(criteria);
    }
}
