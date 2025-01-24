package picoyplaca.demo.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCheckRequestDTO {
    @NotNull
    @Pattern(regexp = "[A-Z]{3}-\\d{3,4}")
    private String licensePlate;
    @NotNull
    private LocalDateTime checkDateTime;
    private boolean hasDisabilityCard;
    private boolean hasSeniorCitizen;
}