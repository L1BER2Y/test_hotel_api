package by.shershen.test_hotel_api.service;

import by.shershen.test_hotel_api.dto.response.HotelDetailResponseDTO;
import by.shershen.test_hotel_api.entity.Amenity;
import by.shershen.test_hotel_api.entity.Hotel;
import by.shershen.test_hotel_api.exceptions.HotelNotFoundException;
import by.shershen.test_hotel_api.mapper.HotelMapper;
import by.shershen.test_hotel_api.repository.AmenityRepository;
import by.shershen.test_hotel_api.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private AmenityRepository amenityRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void getHotelById_whenNotFound_throwsHotelNotFoundException() {
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.getHotelById(99L))
                .isInstanceOf(HotelNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getHotelById_whenFound_returnsMappedDetailResponse() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        HotelDetailResponseDTO expected = HotelDetailResponseDTO.builder().id(1L).build();

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toDetailResponseDTO(hotel)).thenReturn(expected);

        HotelDetailResponseDTO actual = hotelService.getHotelById(1L);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addAmenities_whenAmenityAlreadyExists_reusesIt_doesNotCreateDuplicate() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setAmenities(new HashSet<>());

        Amenity existing = new Amenity();
        existing.setId(10L);
        existing.setName("Free WiFi");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByNameIgnoreCase("Free WiFi")).thenReturn(Optional.of(existing));
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        when(hotelMapper.amenitiesToNames(hotel.getAmenities())).thenReturn(List.of("Free WiFi"));

        hotelService.addAmenities(1L, List.of("Free WiFi"));

        verify(amenityRepository, never()).save(any());
        assertThat(hotel.getAmenities()).contains(existing);
    }

    @Test
    void addAmenities_whenAmenityDoesNotExist_createsIt() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setAmenities(new HashSet<>());

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByNameIgnoreCase("Sauna")).thenReturn(Optional.empty());
        when(amenityRepository.save(any(Amenity.class))).thenAnswer(invocation -> {
            Amenity saved = invocation.getArgument(0);
            saved.setId(20L);
            return saved;
        });
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        when(hotelMapper.amenitiesToNames(any())).thenReturn(List.of("Sauna"));

        hotelService.addAmenities(1L, List.of("Sauna"));

        verify(amenityRepository).save(any(Amenity.class));
    }

    @Test
    void addAmenities_whenHotelNotFound_throwsBeforeTouchingAmenityRepository() {
        when(hotelRepository.findById(404L)).thenReturn(Optional.empty());
        List<String> amenities = List.of("Free WiFi");

        assertThatThrownBy(() -> hotelService.addAmenities(404L, amenities))
                .isInstanceOf(HotelNotFoundException.class);

        verify(amenityRepository, never()).findByNameIgnoreCase(any());
    }
}
