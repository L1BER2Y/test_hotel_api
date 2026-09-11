package by.shershen.test_hotel_api.service;

import by.shershen.test_hotel_api.dto.request.HotelCreateRequestDTO;
import by.shershen.test_hotel_api.dto.request.HotelSearchCriteria;
import by.shershen.test_hotel_api.dto.response.HotelDetailResponseDTO;
import by.shershen.test_hotel_api.dto.response.HotelShortResponseDTO;
import by.shershen.test_hotel_api.entity.Amenity;
import by.shershen.test_hotel_api.entity.Hotel;
import by.shershen.test_hotel_api.exceptions.HotelNotFoundException;
import by.shershen.test_hotel_api.mapper.HotelMapper;
import by.shershen.test_hotel_api.repository.AmenityRepository;
import by.shershen.test_hotel_api.repository.HotelRepository;
import by.shershen.test_hotel_api.repository.HotelSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final HotelMapper hotelMapper;

    @Override
    public List<HotelShortResponseDTO> getAllHotels() {
        return hotelMapper.toShortResponseDTOList(hotelRepository.findAll());
    }

    @Override
    public HotelDetailResponseDTO getHotelById(Long id) {
        Hotel hotel = findHotelOrThrow(id);
        return hotelMapper.toDetailResponseDTO(hotel);
    }

    @Override
    public List<HotelShortResponseDTO> searchHotels(HotelSearchCriteria criteria) {
        Specification<Hotel> specification = HotelSpecifications.build(criteria);
        return hotelMapper.toShortResponseDTOList(hotelRepository.findAll(specification));
    }

    @Override
    @Transactional
    public HotelShortResponseDTO createHotel(HotelCreateRequestDTO requestDTO) {
        Hotel hotel = hotelMapper.toEntity(requestDTO);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toShortResponseDTO(savedHotel);
    }

    @Override
    @Transactional
    public List<String> addAmenities(Long id, List<String> amenities) {
        Hotel hotel = findHotelOrThrow(id);

        for (String amenityName : amenities) {
            Amenity amenity = findOrCreateAmenity(amenityName);
            hotel.getAmenities().add(amenity);
        }
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.amenitiesToNames(savedHotel.getAmenities());
    }

    private Hotel findHotelOrThrow(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    private Amenity findOrCreateAmenity(String name) {
        return amenityRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Amenity newAmenity = new Amenity();
                    newAmenity.setName(name);
                    return amenityRepository.save(newAmenity);
                });
    }
}
