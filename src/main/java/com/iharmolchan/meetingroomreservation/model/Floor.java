package com.iharmolchan.meetingroomreservation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "building_floors")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Floor extends BaseEntity {
    private Integer number;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "floor_id")
    private List<MeetingRoom> meetingRooms;
}
