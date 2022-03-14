package com.iharmolchan.meetingroomreservation.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
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
    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class})
    private String address;
}
