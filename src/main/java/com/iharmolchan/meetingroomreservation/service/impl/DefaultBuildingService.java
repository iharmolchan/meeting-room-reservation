package com.iharmolchan.meetingroomreservation.service.impl;

import com.iharmolchan.meetingroomreservation.exception.DbEntityNotFoundException;
import com.iharmolchan.meetingroomreservation.model.Building;
import com.iharmolchan.meetingroomreservation.repository.BuildingRepository;
import com.iharmolchan.meetingroomreservation.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultBuildingService implements BuildingService {
    private final BuildingRepository buildingRepository;

    @Override
    public Building save(Building building) {
        return buildingRepository.save(building);
    }

    @Override
    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    @Override
    public Building getById(Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new DbEntityNotFoundException("Can't find building with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        buildingRepository.deleteById(id);
    }
}
