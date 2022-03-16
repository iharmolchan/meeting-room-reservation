package com.iharmolchan.meetingroomreservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationTO {

    @JsonView({DefaultView.UPDATE.class, DefaultView.GET.class})
    private Long id;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    private String meetingDescription;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationStart;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationFinish;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    private Long meetingRoomId;

    @JsonView({DefaultView.CREATE.class, DefaultView.UPDATE.class, DefaultView.GET.class})
    private Long parentReservationId;
}
