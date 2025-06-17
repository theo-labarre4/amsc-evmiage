package Controller;

import Dto.EntityStatisticsDTO;
import Dto.GlobalStatisticsDTO;
import Service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/global")
    public ResponseEntity<GlobalStatisticsDTO> getGlobalStatistics() {
        GlobalStatisticsDTO dto = statisticsService.getGlobalStatistics();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<String> getTest() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<EntityStatisticsDTO> getEntityStatistics(
            @PathVariable String entityType,
            @PathVariable String entityId,
            @RequestParam String periodType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        EntityStatisticsDTO dto = statisticsService.getEntityStatistics(entityId, entityType, periodType, from, to);
        return ResponseEntity.ok(dto);
    }
}