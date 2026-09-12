package by.shershen.test_hotel_api.controller;

import by.shershen.test_hotel_api.histogram.HistogramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/histogram")
@RequiredArgsConstructor
@Tag(name = "Histogram", description = "Number of hotels grouped by param")
public class HistogramController {

    private final HistogramService histogramService;

    @GetMapping("/{param}")
    @Operation(summary = "Get histogram by param", description = "Valid param values: brand, city, country, amenities")
    @ApiResponse(responseCode = "400", description = "Unknown param")
    public Map<String, Long> getHistogram(@Parameter(description = "brand | city | country | amenities") @PathVariable String param) {
        return histogramService.calculate(param);
    }
}
