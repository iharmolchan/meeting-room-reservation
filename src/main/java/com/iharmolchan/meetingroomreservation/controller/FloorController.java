package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.dto.FloorTO;
import com.iharmolchan.meetingroomreservation.model.Floor;
import com.iharmolchan.meetingroomreservation.service.FloorService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/buildings/{buildingId}/floors")
public class FloorController {
    private final ModelMapper modelMapper;
    private final FloorService floorService;

    @JsonView(DefaultView.GET.class)
    @GetMapping
    public List<FloorTO> getAllByBuilding(@PathVariable Long buildingId) {
        return floorService.getAllByBuildingId(buildingId).stream().map(this::convertToDto).toList();
    }

    @JsonView(DefaultView.GET.class)
    @GetMapping("/{id}")
    public FloorTO getById(@PathVariable Long id) {
        return convertToDto(floorService.getById(id));
    }


    @PostMapping
    public FloorTO createFloor(
            @PathVariable Long buildingId,
            @JsonView(DefaultView.CREATE.class) @RequestBody FloorTO floorTo
    ) {
        Floor floor = convertToEntity(floorTo);
        return convertToDto(floorService.save(floor, buildingId));
    }

    @PutMapping
    public FloorTO updateFloor(
            @PathVariable Long buildingId,
            @JsonView(DefaultView.UPDATE.class) @RequestBody FloorTO floorTo
    ) {
        Floor floor = convertToEntity(floorTo);
        return convertToDto(floorService.save(floor, buildingId));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        floorService.deleteById(id);
    }

    private FloorTO convertToDto(Floor floor) {
        return modelMapper.map(floor, FloorTO.class);
    }

    private Floor convertToEntity(FloorTO floorTO) {
        return modelMapper.map(floorTO, Floor.class);
    }

}
