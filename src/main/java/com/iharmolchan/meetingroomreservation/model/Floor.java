package com.iharmolchan.meetingroomreservation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@JsonIgnoreProperties(value = { "building" })
@Entity
@Table(name = "building_floors", uniqueConstraints = { @UniqueConstraint(columnNames = { "number", "building_id" }) })
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Floor extends BaseEntity {
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "building_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building building;
}
