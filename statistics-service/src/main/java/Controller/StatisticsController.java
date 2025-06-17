package Controller;

import Dto.EntityStatisticsDTO;
import Dto.GlobalStatisticsDTO;
import Service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/global")
    public GlobalStatisticsDTO getGlobalStatistics(
            @RequestParam String periodType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return statisticsService.getGlobalStatistics(periodType, from, to);
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public EntityStatisticsDTO getEntityStatistics(
            @PathVariable String entityType,
            @PathVariable String entityId,
            @RequestParam String periodType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return statisticsService.getEntityStatistics(entityId, entityType, periodType, from, to);
    }
}