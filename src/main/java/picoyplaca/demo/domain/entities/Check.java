package picoyplaca.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "checks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private LocalDateTime checkDateTime;
    private boolean canCirculate;
    private String reason;
}