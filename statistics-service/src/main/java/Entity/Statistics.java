package Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "statistics")
public class Statistics {
    @Id
    private String id;
    private LocalDateTime timestamp;
    private String periodType; // HOUR, DAY, WEEK, MONTH, YEAR
    private Map<String, Object> globalStats;
    private Map<String, Object> entityStats; // keyed by entity type/id
}