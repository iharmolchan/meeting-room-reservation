package com.iharmolchan.meetingroomreservation.service;

import com.iharmolchan.meetingroomreservation.DbEntityNotFoundException;
import com.iharmolchan.meetingroomreservation.model.Building;
import com.iharmolchan.meetingroomreservation.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public Building save(Building building) {
        return buildingRepository.save(building);
    }

    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    public Building getById(Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new DbEntityNotFoundException("Can't find building with id: " + id));
    }

    public void deleteById(Long id) {
        buildingRepository.deleteById(id);
    }
}
