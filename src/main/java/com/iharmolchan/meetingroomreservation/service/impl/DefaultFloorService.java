package com.iharmolchan.meetingroomreservation.service.impl;

import com.iharmolchan.meetingroomreservation.exception.DbEntityNotFoundException;
import com.iharmolchan.meetingroomreservation.model.Building;
import com.iharmolchan.meetingroomreservation.model.Floor;
import com.iharmolchan.meetingroomreservation.repository.BuildingRepository;
import com.iharmolchan.meetingroomreservation.repository.FloorRepository;
import com.iharmolchan.meetingroomreservation.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultFloorService implements FloorService {
    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;

    @Override
    public Floor save(Floor floor, Long buildingId) {
        Building building = buildingRepository.getById(buildingId);
        floor.setBuilding(building);
        return floorRepository.save(floor);
    }

    @Override
    public List<Floor> getAllByBuildingId(Long buildingId) {
        return floorRepository.findAllByBuildingId(buildingId);
    }

    @Override
    public Floor getById(Long id) {
        return floorRepository.findById(id)
                .orElseThrow(() -> new DbEntityNotFoundException("Can't find floor floor with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        floorRepository.deleteById(id);
    }
}
