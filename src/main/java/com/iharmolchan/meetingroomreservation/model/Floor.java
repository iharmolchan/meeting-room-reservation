package com.iharmolchan.meetingroomreservation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "building_floors")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Floor extends BaseEntity {
    private Integer number;
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
}
