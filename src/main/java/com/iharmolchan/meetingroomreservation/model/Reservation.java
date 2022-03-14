package com.iharmolchan.meetingroomreservation.model;

import com.iharmolchan.meetingroomreservation.validation.DateIsAfterAnother;
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
@DateIsAfterAnother(baseDateField = "reservationStart", dateAfterField = "reservationFinish")
public class Reservation extends BaseEntity{
    @NotBlank
    private String meetingDescription;
    @FutureOrPresent(message = "It's not possible to reserve a meeting room in the past")
    private LocalDateTime reservationStart;
    @Future(message = "It's not possible to reserve a meeting room in the past")
    private LocalDateTime reservationFinish;

    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MeetingRoom meetingRoom;
}
