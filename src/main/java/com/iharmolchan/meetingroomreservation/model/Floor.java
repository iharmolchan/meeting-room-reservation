package com.iharmolchan.meetingroomreservation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
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
import javax.persistence.UniqueConstraint;

@JsonIgnoreProperties(value = { "building" })
@Entity
@Table(name = "building_floors", uniqueConstraints = { @UniqueConstraint(columnNames = { "number", "building_id" }) })
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Floor extends BaseEntity {
    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "building_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building building;
}
