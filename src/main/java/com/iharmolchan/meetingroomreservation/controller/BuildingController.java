package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.model.Building;
import com.iharmolchan.meetingroomreservation.service.BuildingService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping
    public List<Building> getAll() {
        return buildingService.getAll();
    }

    @GetMapping("/{id}")
    public Building getById(@PathVariable Long id) {
        return buildingService.getById(id);
    }

    @JsonView(DefaultView.CREATE.class)
    @PostMapping
    public Building createBuilding(@RequestBody Building building) {
        return buildingService.save(building);
    }

    @JsonView(DefaultView.UPDATE.class)
    @PutMapping
    public Building updateBuilding(@RequestBody Building building) {
        return buildingService.save(building);
    }
}
