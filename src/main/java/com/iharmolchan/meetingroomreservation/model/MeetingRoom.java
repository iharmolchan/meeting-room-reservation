package com.iharmolchan.meetingroomreservation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "meeting_rooms")
@NoArgsConstructor
@Getter
@Setter
public class MeetingRoom extends BaseEntity {
    private int capacity;
    private boolean multimediaCapability;
    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;
    @OneToMany
    private List<Reservation> reservations;
}
