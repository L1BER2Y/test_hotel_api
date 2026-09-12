package by.shershen.test_hotel_api.repository;

import by.shershen.test_hotel_api.dto.request.HotelSearchCriteria;
import by.shershen.test_hotel_api.entity.Address;
import by.shershen.test_hotel_api.entity.Amenity;
import by.shershen.test_hotel_api.entity.ArrivalTime;
import by.shershen.test_hotel_api.entity.Contacts;
import by.shershen.test_hotel_api.entity.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HotelSpecificationsTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HotelRepository hotelRepository;

    private Hotel minskHotel;
    private Hotel moscowHotel;

    @BeforeEach
    void setUp() {
        minskHotel = persistHotel("DoubleTree", "Hilton", "Minsk", "Belarus",
                Set.of("Free WiFi", "Free parking"));
        moscowHotel = persistHotel("Ritz Carlton Moscow", "Ritz", "Moscow", "Russia",
                Set.of("Free WiFi"));
        entityManager.flush();
    }

    void hasCity_matchesCaseInsensitivePartial() {
        List<Hotel> result = hotelRepository.findAll(HotelSpecifications.hasCity("minsk"));

        assertThat(result).containsExactly(minskHotel);
    }

    @Test
    void hasBrand_matchesPartial() {
        List<Hotel> result = hotelRepository.findAll(HotelSpecifications.hasBrand("hilt"));

        assertThat(result).containsExactly(minskHotel);
    }

    @Test
    void hasAmenity_combinedWithAnd_requiresAllAmenities() {
        Specification<Hotel> spec = Specification.<Hotel>unrestricted()
                .and(HotelSpecifications.hasAmenity("Free WiFi"))
                .and(HotelSpecifications.hasAmenity("Free parking"));

        List<Hotel> result = hotelRepository.findAll(spec);

        assertThat(result).containsExactly(minskHotel);
    }

    @Test
    void build_withEmptyCriteria_returnsAllHotels() {
        HotelSearchCriteria criteria = HotelSearchCriteria.builder().build();

        List<Hotel> result = hotelRepository.findAll(HotelSpecifications.build(criteria));

        assertThat(result).containsExactlyInAnyOrder(minskHotel, moscowHotel);
    }

    @Test
    void build_withCityAndCountry_appliesBothFilters() {
        HotelSearchCriteria criteria = HotelSearchCriteria.builder()
                .city("Moscow")
                .country("Russia")
                .build();

        List<Hotel> result = hotelRepository.findAll(HotelSpecifications.build(criteria));

        assertThat(result).containsExactly(moscowHotel);
    }


    private Hotel persistHotel(String name, String brand, String city, String country, Set<String> amenityNames) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setBrand(brand);
        hotel.setAddress(new Address("1", "Main St", city, country, "00000"));
        hotel.setContacts(new Contacts("+375000000", "test@example.com"));
        hotel.setArrivalTime(new ArrivalTime(LocalTime.of(14, 0), LocalTime.of(12, 0)));

        Set<Amenity> amenities = new HashSet<>();
        for (String amenityName : amenityNames) {
            amenities.add(findOrCreateAmenity(amenityName));
        }
        hotel.setAmenities(amenities);

        return entityManager.persist(hotel);
    }

    private Amenity findOrCreateAmenity(String name) {
        List<Amenity> existing = entityManager.getEntityManager()
                .createQuery("SELECT a FROM Amenity a WHERE a.name = :name", Amenity.class)
                .setParameter("name", name)
                .getResultList();

        if (!existing.isEmpty()) {
            return existing.getFirst();
        }

        Amenity amenity = new Amenity();
        amenity.setName(name);
        return entityManager.persist(amenity);
    }
}
