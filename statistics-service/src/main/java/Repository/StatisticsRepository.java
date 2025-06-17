package Repository;

import Entity.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {
    List<Statistics> findByPeriodTypeAndTimestampBetween(String periodType, LocalDateTime start, LocalDateTime end);
}