package roomescape.reservation;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservationTime.ReservationTimeResponseDto;

import java.util.List;

@RestController
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        final List<ReservationResponseDto> reservationResponseDtos = reservationService.findAll();
        return ResponseEntity.ok().body(reservationResponseDtos);

    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(@Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        final ReservationResponseDto responseDto = reservationService.save(reservationRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/times/available")
    public ResponseEntity<List<ReservationTimeResponseDto>> getAvaliableTimes(@RequestParam String date, @RequestParam Long themeId) {
        final List<ReservationTimeResponseDto> reservationTimeResponseDtos = reservationService.findAvaliableTimes(date, themeId);
        return ResponseEntity.ok().body(reservationTimeResponseDtos);
    }
}
