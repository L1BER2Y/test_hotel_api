package by.shershen.test_hotel_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HotelSearchCriteria {

    private String name;
    private String brand;
    private String city;
    private String country;
    private List<String> amenities;
}
