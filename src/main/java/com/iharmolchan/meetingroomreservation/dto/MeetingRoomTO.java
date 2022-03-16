package com.iharmolchan.meetingroomreservation.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MeetingRoomTO {
    @JsonView({DefaultView.UPDATE.class, DefaultView.GET.class})
    private Long id;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    private int capacity;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    private boolean multimediaCapability;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    private Long floorId;

    @JsonView({DefaultView.GET.class})
    private List<ReservationTO> reservations;
}
