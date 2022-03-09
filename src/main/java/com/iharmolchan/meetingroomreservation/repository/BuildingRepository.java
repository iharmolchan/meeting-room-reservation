package com.iharmolchan.meetingroomreservation.repository;

import com.iharmolchan.meetingroomreservation.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
