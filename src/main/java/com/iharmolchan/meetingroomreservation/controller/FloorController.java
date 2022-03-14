package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.model.Floor;
import com.iharmolchan.meetingroomreservation.service.FloorService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/buildings/{buildingId}/floors")
public class FloorController {
    private final FloorService floorService;

    @GetMapping
    public List<Floor> getAllByBuilding(@PathVariable Long buildingId) {
        return floorService.getAllByBuildingId(buildingId);
    }

    @GetMapping("/{id}")
    public Floor getById(@PathVariable Long id) {
        return floorService.getById(id);
    }


    @PostMapping
    public Floor createFloor(
            @PathVariable Long buildingId,
            @JsonView(DefaultView.CREATE.class) @RequestBody Floor floor
    ) {
        return floorService.save(floor, buildingId);
    }

    @PutMapping
    public Floor updateBuilding(
            @PathVariable Long buildingId,
            @JsonView(DefaultView.UPDATE.class) @RequestBody Floor floor
    ) {
        return floorService.save(floor, buildingId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        floorService.deleteById(id);
    }
}
