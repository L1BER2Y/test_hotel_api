package by.shershen.test_hotel_api.histogram;

import by.shershen.test_hotel_api.exceptions.InvalidHistogramParamException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HistogramService {

    private final Map<String, HistogramStrategy> strategies;

    public Map<String, Long> calculate(String param) {
        HistogramStrategy strategy = strategies.get(param.toLowerCase());
        if (strategy == null) {
            throw new InvalidHistogramParamException(param);
        }
        return strategy.calculate();
    }
}
