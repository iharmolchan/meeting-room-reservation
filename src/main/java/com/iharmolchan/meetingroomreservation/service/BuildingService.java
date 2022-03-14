package com.iharmolchan.meetingroomreservation.service;

import com.iharmolchan.meetingroomreservation.model.Building;

import java.util.List;

public interface BuildingService {
    Building save(Building building);

    List<Building> getAll();

    Building getById(Long id);

    void deleteById(Long id);
}
