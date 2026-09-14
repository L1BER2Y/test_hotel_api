package by.shershen.test_hotel_api.service;

import by.shershen.test_hotel_api.dto.request.HotelCreateRequestDTO;
import by.shershen.test_hotel_api.dto.request.HotelSearchCriteria;
import by.shershen.test_hotel_api.dto.response.HotelDetailResponseDTO;
import by.shershen.test_hotel_api.dto.response.HotelShortResponseDTO;

import java.util.List;

public interface HotelService {

    List<HotelShortResponseDTO> getAllHotels();

    HotelDetailResponseDTO getHotelById(Long id);

    List<HotelShortResponseDTO> searchHotels(HotelSearchCriteria criteria);

    HotelShortResponseDTO createHotel(HotelCreateRequestDTO requestDTO);

    List<String> addAmenities(Long id, List<String> amenities);
}
