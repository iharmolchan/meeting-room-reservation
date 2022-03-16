package com.iharmolchan.meetingroomreservation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.validation.ReservationDatesAreValid;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@JsonIgnoreProperties(value = {"meetingRoom"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"meetingRoom"})
@ReservationDatesAreValid
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
    @NotBlank
    private String meetingDescription;

    @FutureOrPresent(message = "It's not possible to reserve a meeting room in the past")
    @NotNull
    private LocalDateTime reservationStart;

    @Future(message = "It's not possible to reserve a meeting room in the past")
    @NotNull
    private LocalDateTime reservationFinish;

    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MeetingRoom meetingRoom;

    @OneToOne
    @JoinColumn(name = "parent_reservation_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Reservation parentReservation;
}
