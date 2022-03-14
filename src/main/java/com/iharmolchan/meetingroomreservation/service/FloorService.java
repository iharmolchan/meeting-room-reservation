package com.iharmolchan.meetingroomreservation.service;

import com.iharmolchan.meetingroomreservation.model.Floor;

import java.util.List;

public interface FloorService {
    Floor save(Floor floor, Long buildingId);

    List<Floor> getAllByBuildingId(Long buildingId);

    Floor getById(Long id);

    void deleteById(Long id);
}
