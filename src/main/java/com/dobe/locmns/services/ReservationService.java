package com.dobe.locmns.services;

import com.dobe.locmns.dto.ReservationDto;



public interface ReservationService extends AbstractService<ReservationDto> {

    Integer validateReservation(Integer id);

    Integer invalidateReservation(Integer id);
    Integer createReservation(ReservationDto reservationDto);
    Void delete(Integer id);
    Integer validateExtension(Integer id);
}
