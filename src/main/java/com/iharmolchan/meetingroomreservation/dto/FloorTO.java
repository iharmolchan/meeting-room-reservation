package com.iharmolchan.meetingroomreservation.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FloorTO {
    @JsonView({DefaultView.UPDATE.class, DefaultView.GET.class})
    private Long id;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    private Integer number;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    private Long buildingId;
}
