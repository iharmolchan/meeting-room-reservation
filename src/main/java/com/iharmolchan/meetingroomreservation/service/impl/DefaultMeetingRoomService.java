package com.iharmolchan.meetingroomreservation.service.impl;

import com.iharmolchan.meetingroomreservation.DbEntityNotFoundException;
import com.iharmolchan.meetingroomreservation.model.Building;
import com.iharmolchan.meetingroomreservation.model.Floor;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.repository.BuildingRepository;
import com.iharmolchan.meetingroomreservation.repository.FloorRepository;
import com.iharmolchan.meetingroomreservation.repository.MeetingRoomRepository;
import com.iharmolchan.meetingroomreservation.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultMeetingRoomService implements MeetingRoomService {
    private final FloorRepository floorRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    @Override
    public MeetingRoom save(MeetingRoom meetingRoom, Long floorId) {
        Floor floor = floorRepository.getById(floorId);
        meetingRoom.setFloor(floor);
        return meetingRoomRepository.save(meetingRoom);
    }

    @Override
    public List<MeetingRoom> getAll() {
        return meetingRoomRepository.findAll();
    }

    @Override
    public List<MeetingRoom> getAllByFloorId(Long floorId) {
        return meetingRoomRepository.findAllByFloorId(floorId);
    }

    @Override
    public MeetingRoom getById(Long id) {
        return meetingRoomRepository.findById(id)
                .orElseThrow(() -> new DbEntityNotFoundException("Can't find meeting room floor with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        meetingRoomRepository.deleteById(id);
    }
}
