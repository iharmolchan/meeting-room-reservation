package com.iharmolchan.meetingroomreservation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "meeting_rooms")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MeetingRoom extends BaseEntity {
    private int capacity;
    private boolean multimediaCapability;
}
