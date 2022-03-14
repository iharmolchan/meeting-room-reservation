package com.iharmolchan.meetingroomreservation.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @JsonView({DefaultView.UPDATE.class})
    @Id
    @GeneratedValue
    private Long id;
}
