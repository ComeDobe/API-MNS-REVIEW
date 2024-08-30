package com.dobe.locmns.repositories;

import com.dobe.locmns.models.Pannes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PannesRepository extends JpaRepository<Pannes, Integer> {

    List<Pannes> findByMaterielId(Integer materielId);
}
