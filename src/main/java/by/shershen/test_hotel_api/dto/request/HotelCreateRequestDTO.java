package by.shershen.test_hotel_api.dto.request;

import by.shershen.test_hotel_api.dto.AddressDTO;
import by.shershen.test_hotel_api.dto.ArrivalTimeDTO;
import by.shershen.test_hotel_api.dto.ContactsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelCreateRequestDTO {

    @NotBlank
    private String name;

    @Size(max = 4000)
    private String description;

    @NotBlank
    private String brand;

    @NotNull
    @Valid
    private AddressDTO address;

    @NotNull
    @Valid
    private ContactsDTO contacts;

    @NotNull
    @Valid
    private ArrivalTimeDTO arrivalTime;
}
