package picoyplaca.demo.application.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCheckResponseDTO {
    private String licensePlate;
    private LocalDateTime checkDateTime;
    private boolean canCirculate;
    private String reason;
}