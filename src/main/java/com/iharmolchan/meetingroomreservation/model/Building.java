package com.iharmolchan.meetingroomreservation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buildings")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Building extends BaseEntity {
    private String address;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id")
    private List<Floor> floors;
}
