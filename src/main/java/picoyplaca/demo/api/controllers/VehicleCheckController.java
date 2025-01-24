package picoyplaca.demo.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import picoyplaca.demo.application.dtos.VehicleCheckRequestDTO;
import picoyplaca.demo.application.dtos.VehicleCheckResponseDTO;
import picoyplaca.demo.application.services.VehicleCheckService;
import picoyplaca.demo.domain.entities.Check;
import picoyplaca.demo.domain.repositories.CheckRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle-checks")
@Validated
public class VehicleCheckController {
    private final VehicleCheckService vehicleCheckService;
    private final CheckRepository checkRepository;

    @Autowired
    public VehicleCheckController(VehicleCheckService vehicleCheckService, CheckRepository checkRepository) {
        this.vehicleCheckService = vehicleCheckService;
        this.checkRepository = checkRepository;
    }

    @PostMapping
    public ResponseEntity<VehicleCheckResponseDTO> checkVehicle(
            @Valid @RequestBody VehicleCheckRequestDTO request) {
        VehicleCheckResponseDTO response = vehicleCheckService.checkVehicle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<List<Check>> getVehicleHistory(@PathVariable String licensePlate) {
        return ResponseEntity.ok(checkRepository.findByLicensePlateOrderByCheckDateTimeDesc(licensePlate));
    }
}