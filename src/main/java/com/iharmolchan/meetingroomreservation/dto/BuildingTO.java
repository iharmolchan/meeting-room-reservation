package com.iharmolchan.meetingroomreservation.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuildingTO {
    @JsonView({DefaultView.UPDATE.class, DefaultView.GET.class})
    private Long id;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    private String address;
}
