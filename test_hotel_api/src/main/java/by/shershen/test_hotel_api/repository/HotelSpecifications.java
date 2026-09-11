package by.shershen.test_hotel_api.repository;

import by.shershen.test_hotel_api.dto.request.HotelSearchCriteria;
import by.shershen.test_hotel_api.entity.Amenity;
import by.shershen.test_hotel_api.entity.Hotel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class HotelSpecifications {

    private HotelSpecifications() {

    }

    public static Specification<Hotel> build(HotelSearchCriteria criteria) {
        Specification<Hotel> specification = Specification.unrestricted();

        if (StringUtils.hasText(criteria.getName())) {
            specification = specification.and(hasName(criteria.getName()));
        }

        if (StringUtils.hasText(criteria.getBrand())) {
            specification = specification.and(hasBrand(criteria.getBrand()));
        }

        if (StringUtils.hasText(criteria.getCity())) {
            specification = specification.and(hasCity(criteria.getCity()));
        }

        if (StringUtils.hasText(criteria.getCountry())) {
            specification = specification.and(hasCountry(criteria.getCountry()));
        }

        if (criteria.getAmenities() != null && !criteria.getAmenities().isEmpty()) {
            for (String amenity : criteria.getAmenities()) {
                if (StringUtils.hasText(amenity)) {
                    specification = specification.and(hasAmenity(amenity));
                }
            }
        }
        return specification;
    }

    public static Specification<Hotel> hasName(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
    }

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("address").get("city")), "%" + city.toLowerCase() + "%");
    }

    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("address").get("country")), "%" + country.toLowerCase() + "%");
    }

    public static Specification<Hotel> hasAmenity(String amenityName) {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Hotel> subRoot = subquery.correlate(root);
            Join<Hotel, Amenity> amenityJoin = subRoot.join("amenities");

            subquery.select(amenityJoin.get("id"))
                    .where(cb.equal(cb.lower(amenityJoin.get("name")), amenityName.toLowerCase()));

            return cb.exists(subquery);
        };
    }
}
