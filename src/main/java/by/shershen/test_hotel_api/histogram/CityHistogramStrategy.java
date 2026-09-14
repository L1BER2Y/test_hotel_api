package by.shershen.test_hotel_api.histogram;

import by.shershen.test_hotel_api.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component("city")
@RequiredArgsConstructor
public class CityHistogramStrategy implements HistogramStrategy {

    private final HotelRepository hotelRepository;

    @Override
    public Map<String, Long> calculate() {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Object[] row : hotelRepository.countByCity()) {
            result.put((String) row[0], (Long) row[1]);
        }
        return result;
    }
}
