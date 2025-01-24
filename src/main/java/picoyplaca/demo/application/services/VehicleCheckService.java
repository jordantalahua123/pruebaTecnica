package picoyplaca.demo.application.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picoyplaca.demo.application.dtos.VehicleCheckRequestDTO;
import picoyplaca.demo.application.dtos.VehicleCheckResponseDTO;
import picoyplaca.demo.domain.entities.Check;
import picoyplaca.demo.domain.repositories.CheckRepository;
import picoyplaca.demo.domain.repositories.VehicleRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Slf4j
public class VehicleCheckService {
    private final VehicleRepository vehicleRepository;
    private final CheckRepository checkRepository;

    @Autowired
    public VehicleCheckService(VehicleRepository vehicleRepository, CheckRepository checkRepository) {
        this.vehicleRepository = vehicleRepository;
        this.checkRepository = checkRepository;
    }

    public VehicleCheckResponseDTO checkVehicle(VehicleCheckRequestDTO request) {
        String licensePlate = request.getLicensePlate();
        LocalDateTime dateTime = request.getCheckDateTime();

        if (request.isHasDisabilityCard() || request.isHasSeniorCitizen()) {
            return createResponse(licensePlate, dateTime, true, "Excepción por discapacidad/tercera edad");
        }

        if (!isRestrictedTime(dateTime.toLocalTime())) {
            return createResponse(licensePlate, dateTime, true, "Fuera de horario de restricción");
        }

        int lastDigit = getLastDigit(licensePlate);
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();

        if (canCirculateOnDay(lastDigit, dayOfWeek)) {
            return createResponse(licensePlate, dateTime, true, "Placa permitida este día");
        }

        return createResponse(licensePlate, dateTime, false, "Restricción de circulación");
    }

    private boolean isRestrictedTime(LocalTime time) {
        return (time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(9, 30))) ||
                (time.isAfter(LocalTime.of(16, 0)) && time.isBefore(LocalTime.of(21, 0)));
    }

    private boolean canCirculateOnDay(int lastDigit, DayOfWeek day) {
        return switch (day) {
            case MONDAY -> !(lastDigit == 1 || lastDigit == 2);
            case TUESDAY -> !(lastDigit == 3 || lastDigit == 4);
            case WEDNESDAY -> !(lastDigit == 5 || lastDigit == 6);
            case THURSDAY -> !(lastDigit == 7 || lastDigit == 8);
            case FRIDAY -> !(lastDigit == 9 || lastDigit == 0);
            default -> true;
        };
    }

    private int getLastDigit(String licensePlate) {
        return Character.getNumericValue(licensePlate.charAt(licensePlate.length() - 1));
    }

    private VehicleCheckResponseDTO createResponse(String licensePlate, LocalDateTime dateTime,
                                                   boolean canCirculate, String reason) {
        Check check = new Check();
        check.setLicensePlate(licensePlate);
        check.setCheckDateTime(dateTime);
        check.setCanCirculate(canCirculate);
        check.setReason(reason);
        checkRepository.save(check);

        return VehicleCheckResponseDTO.builder()
                .licensePlate(licensePlate)
                .checkDateTime(dateTime)
                .canCirculate(canCirculate)
                .reason(reason)
                .build();
    }
}