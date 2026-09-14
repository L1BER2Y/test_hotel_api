package by.shershen.test_hotel_api.histogram;

import by.shershen.test_hotel_api.exceptions.InvalidHistogramParamException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistogramServiceTest {

    @Mock
    HistogramStrategy brandStrategy;

    @Test
    void calculate_withKnownParam_delegatesToMatchingStrategy() {
        HistogramService histogramService = new HistogramService(Map.of("brand", brandStrategy));
        when(brandStrategy.calculate()).thenReturn(Map.of("Hilton", 2L));

        Map<String, Long> result = histogramService.calculate("brand");

        assertThat(result).containsEntry("Hilton", 2L);
    }

    @Test
    void calculate_isCaseInsensitiveOnParamName() {
        HistogramService histogramService = new HistogramService(Map.of("brand", brandStrategy));
        when(brandStrategy.calculate()).thenReturn(Map.of("Hilton", 1L));

        Map<String, Long> result = histogramService.calculate("BRAND");

        assertThat(result).containsEntry("Hilton", 1L);
    }

    @Test
    void calculate_withUnknownParam_throwsInvalidHistogramParamException() {
        HistogramService histogramService = new HistogramService(Map.of("brand", brandStrategy));

        assertThatThrownBy(() -> histogramService.calculate("rating"))
                .isInstanceOf(InvalidHistogramParamException.class);
    }
}
