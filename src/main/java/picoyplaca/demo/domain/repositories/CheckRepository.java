package picoyplaca.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import picoyplaca.demo.domain.entities.Check;
import java.util.List;

@Repository
public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findByLicensePlateOrderByCheckDateTimeDesc(String licensePlate);
}