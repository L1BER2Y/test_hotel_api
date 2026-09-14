package by.shershen.test_hotel_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;
}
