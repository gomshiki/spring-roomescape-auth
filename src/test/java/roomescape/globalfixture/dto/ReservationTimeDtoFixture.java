package roomescape.globalfixture.dto;

import roomescape.reservationtime.dto.ReservationTimeRequestDto;

public class ReservationTimeDtoFixture {

    public static ReservationTimeRequestDto createReservationTimeRequestDto(){
        return new ReservationTimeRequestDto(1L, "10:00");
    }
}