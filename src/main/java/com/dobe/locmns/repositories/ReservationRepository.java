package com.dobe.locmns.repositories;

import com.dobe.locmns.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{



}
