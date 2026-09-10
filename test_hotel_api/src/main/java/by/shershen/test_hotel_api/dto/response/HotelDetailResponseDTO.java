package by.shershen.test_hotel_api.dto.response;

import by.shershen.test_hotel_api.dto.AddressDTO;
import by.shershen.test_hotel_api.dto.ArrivalTimeDTO;
import by.shershen.test_hotel_api.dto.ContactsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDetailResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressDTO address;
    private ContactsDTO contacts;
    private ArrivalTimeDTO arrivalTime;
    private List<String> amenities;
}
