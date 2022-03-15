package com.iharmolchan.meetingroomreservation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@JsonIgnoreProperties(value = {"meetingRoom"})
@NoArgsConstructor
@Getter
@Setter
@ToString
@ReservationDatesAreValid
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    @NotBlank
    private String meetingDescription;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    @FutureOrPresent(message = "It's not possible to reserve a meeting room in the past")
    @NotNull
    private LocalDateTime reservationStart;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    @Future(message = "It's not possible to reserve a meeting room in the past")
    @NotNull
    private LocalDateTime reservationFinish;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MeetingRoom meetingRoom;
}
