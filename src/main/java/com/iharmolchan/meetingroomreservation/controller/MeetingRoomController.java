package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.model.Floor;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.service.MeetingRoomService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/meeting-rooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    @GetMapping
    public List<MeetingRoom> getAll(@RequestParam(required = false) Long floorId) {
        return floorId == null? meetingRoomService.getAll() : meetingRoomService.getAllByFloorId(floorId);
    }

    @GetMapping("/{id}")
    public MeetingRoom getById(@PathVariable Long id) {
        return meetingRoomService.getById(id);
    }


    @PostMapping
    public MeetingRoom createMeetingRoom(
            @RequestParam Long floorId,
            @JsonView(DefaultView.CREATE.class) @RequestBody MeetingRoom meetingRoom
    ) {
        return meetingRoomService.save(meetingRoom, floorId);
    }

    @PutMapping
    public MeetingRoom updateMeetingRoom(
            @RequestParam Long floorId,
            @JsonView(DefaultView.UPDATE.class) @RequestBody MeetingRoom meetingRoom
    ) {
        return meetingRoomService.save(meetingRoom, floorId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        meetingRoomService.deleteById(id);
    }
}
