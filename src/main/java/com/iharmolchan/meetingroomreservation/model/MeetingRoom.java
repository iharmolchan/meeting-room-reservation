package com.iharmolchan.meetingroomreservation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@JsonIgnoreProperties(value = { "floor" })
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Floor floor;

    @OneToMany
    private List<Reservation> reservations;
}
