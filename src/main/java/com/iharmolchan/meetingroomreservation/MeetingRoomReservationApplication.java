package com.iharmolchan.meetingroomreservation;

import com.iharmolchan.meetingroomreservation.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@Slf4j
@SpringBootApplication
public class MeetingRoomReservationApplication implements CommandLineRunner {
	private final BuildingRepository buildingRepository;

	public static void main(String[] args) {
		SpringApplication.run(MeetingRoomReservationApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info(buildingRepository.findById(10001L).get().toString());
	}
}
