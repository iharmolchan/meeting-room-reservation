package com.iharmolchan.meetingroomreservation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.validation.ReservationDatesAreValid;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Getter
@Setter
@ToString
@ReservationDatesAreValid
public class Reservation extends BaseEntity{

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    @NotBlank
    private String meetingDescription;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    @FutureOrPresent(message = "It's not possible to reserve a meeting room in the past")
    private LocalDateTime reservationStart;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    @Future(message = "It's not possible to reserve a meeting room in the past")
    private LocalDateTime reservationFinish;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MeetingRoom meetingRoom;
}
