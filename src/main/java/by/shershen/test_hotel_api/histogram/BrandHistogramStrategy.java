package by.shershen.test_hotel_api.histogram;

import by.shershen.test_hotel_api.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("brand")
@RequiredArgsConstructor
public class BrandHistogramStrategy implements HistogramStrategy {

    private final HotelRepository hotelRepository;

    @Override
    public Map<String, Long> calculate() {
        Map<String, Long> result = new HashMap<>();
        for (Object[] row : hotelRepository.countByBrand()) {
            result.put((String) row[0], (Long) row[1]);
        }
        return result;
    }
}
