package com.iharmolchan.meetingroomreservation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "buildings")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Building extends BaseEntity {
    private String address;
}
