package com.iharmolchan.meetingroomreservation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
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
import javax.validation.constraints.Min;
import java.util.List;


@JsonIgnoreProperties(value = {"floor", "reservations"})
@Entity
@Table(name = "meeting_rooms")
@NoArgsConstructor
@Getter
@Setter
public class MeetingRoom extends BaseEntity {

    @Min(value = 0, message = "Room capacity can not be less than 0")
    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    private int capacity;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    private boolean multimediaCapability;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Floor floor;

    @OneToMany
    @JoinColumn(name = "meeting_room_id")
    private List<Reservation> reservations;
}
