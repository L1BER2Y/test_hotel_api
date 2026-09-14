package by.shershen.test_hotel_api.mapper;

import by.shershen.test_hotel_api.dto.request.HotelCreateRequestDTO;
import by.shershen.test_hotel_api.dto.response.HotelDetailResponseDTO;
import by.shershen.test_hotel_api.dto.response.HotelShortResponseDTO;
import by.shershen.test_hotel_api.entity.Address;
import by.shershen.test_hotel_api.entity.Amenity;
import by.shershen.test_hotel_api.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    Hotel toEntity(HotelCreateRequestDTO hotelCreateRequest);

    @Mapping(target = "address", source = "address", qualifiedByName = "formatAddress")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelShortResponseDTO toShortResponseDTO(Hotel hotel);

    List<HotelShortResponseDTO> toShortResponseDTOList(List<Hotel> hotels);

    @Mapping(target = "amenities", source = "amenities", qualifiedByName = "amenitiesToNames")
    HotelDetailResponseDTO toDetailResponseDTO(Hotel hotel);

    @Named("formatAddress")
    default String formatAddress(Address address) {
        if (address == null) {
            return null;
        }
        return "%s %s, %s, %s, %s".formatted(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry()
        );
    }

    @Named("amenitiesToNames")
    default List<String> amenitiesToNames(Set<Amenity> amenities) {
        if (amenities == null) {
            return List.of();
        }
        return amenities.stream()
                .map(Amenity::getName)
                .sorted(Comparator.naturalOrder())
                .toList();
    }

}
