package com.iharmolchan.meetingroomreservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@Slf4j
@SpringBootApplication
public class MeetingRoomReservationApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeetingRoomReservationApplication.class, args);
    }
}
