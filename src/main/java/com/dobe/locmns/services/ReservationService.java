package com.dobe.locmns.services;

import com.dobe.locmns.dto.ReservationDto;

public interface ReservationService extends AbstractService<ReservationDto> {

    Integer validateReservation(ReservationDto dto);
    Integer invalidateReservation(Integer id);
    Integer createReservation(ReservationDto reservationDto);
}
