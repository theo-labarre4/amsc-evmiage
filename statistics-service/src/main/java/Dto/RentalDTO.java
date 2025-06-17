package Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {
    private String id;
    private String userId;
    private String startTime;
    private String endTime;
    /* TODO distance existe pas sur une rental faudrait le faire */
    private int distance = 100;
}
