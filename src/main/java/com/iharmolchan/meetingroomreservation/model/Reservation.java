package com.iharmolchan.meetingroomreservation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reservation extends BaseEntity{
    private String meetingDescription;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationFinish;

    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MeetingRoom meetingRoom;
}
