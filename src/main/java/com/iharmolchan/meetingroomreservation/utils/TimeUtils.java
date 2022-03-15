package com.iharmolchan.meetingroomreservation.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtils {
    public static boolean isBetween(LocalTime start, LocalTime finish, LocalTime match) {
        return (match.isAfter(start) || match.equals(start)) && (match.isBefore(finish) || match.equals(finish));
    }
}
