/* Buildings */
INSERT INTO buildings (id, address)
VALUES (10001, '230021 Hrodna, Limozh st. 21');
INSERT INTO buildings (id, address)
VALUES (10002, '1233456 Minsk, Bahdanovicha st. 153b');
INSERT INTO buildings (id, address)
VALUES (10003, '654321 Minsk, Filimonova st. 25b');

/* Buildings floors */
INSERT INTO building_floors (id, number, building_id)
VALUES (10001, 1, 10001);
INSERT INTO building_floors (id, number, building_id)
VALUES (10002, 2, 10001);
INSERT INTO building_floors (id, number, building_id)
VALUES (10003, 3, 10001);
INSERT INTO building_floors (id, number, building_id)
VALUES (10004, 1, 10002);
INSERT INTO building_floors (id, number, building_id)
VALUES (10005, 1, 10003);
INSERT INTO building_floors (id, number, building_id)
VALUES (10006, 2, 10003);

/* Meeting rooms */
INSERT INTO meeting_rooms (id, capacity, multimedia_capability, floor_id)
VALUES (10001, 5, true, 10001);
INSERT INTO meeting_rooms (id, capacity, multimedia_capability, floor_id)
VALUES (10002, 10, false, 10001);
INSERT INTO meeting_rooms (id, capacity, multimedia_capability, floor_id)
VALUES (10003, 5, false, 10002);
INSERT INTO meeting_rooms (id, capacity, multimedia_capability, floor_id)
VALUES (10004, 3, true, 10003);
INSERT INTO meeting_rooms (id, capacity, multimedia_capability, floor_id)
VALUES (10005, 4, false, 10003);

/* Reservations */
INSERT INTO reservations (id, meeting_description, reservation_start, reservation_finish, meeting_room_id)
VALUES ( 10001, 'Some meeting', parsedatetime('18-03-2022 16:30', 'dd-MM-yyyy hh:mm'), parsedatetime('18-03-2022 18:00', 'dd-MM-yyyy hh:mm'), 10002);
INSERT INTO reservations (id, meeting_description, reservation_start, reservation_finish, meeting_room_id, parent_reservation_id)
VALUES ( 10002, 'Cleaning for: Some meeting', parsedatetime('18-03-2022 18:00', 'dd-MM-yyyy hh:mm'), parsedatetime('18-03-2022 18:15', 'dd-MM-yyyy hh:mm'), 10002, 10001);

INSERT INTO reservations (id, meeting_description, reservation_start, reservation_finish, meeting_room_id)
VALUES ( 10003, 'Clients negotiations', parsedatetime('19-03-2022 14:30', 'dd-MM-yyyy hh:mm'), parsedatetime('19-03-2022 16:00', 'dd-MM-yyyy hh:mm'), 10001);
INSERT INTO reservations (id, meeting_description, reservation_start, reservation_finish, meeting_room_id, parent_reservation_id)
VALUES ( 10004, 'Cleaning for: Clients negotiations', parsedatetime('19-03-2022 16:00', 'dd-MM-yyyy hh:mm'), parsedatetime('19-03-2022 16:10', 'dd-MM-yyyy hh:mm'), 10001, 10003);

INSERT INTO reservations (id, meeting_description, reservation_start, reservation_finish, meeting_room_id)
VALUES ( 10005, 'Project presentation', parsedatetime('19-03-2022 11:20', 'dd-MM-yyyy hh:mm'), parsedatetime('19-03-2022 12:30', 'dd-MM-yyyy hh:mm'), 10001);
INSERT INTO reservations (id, meeting_description, reservation_start, reservation_finish, meeting_room_id, parent_reservation_id)
VALUES ( 10006, 'Cleaning for: Project presentation', parsedatetime('19-03-2022 12:30', 'dd-MM-yyyy hh:mm'), parsedatetime('19-03-2022 12:40', 'dd-MM-yyyy hh:mm'), 10001, 10005);


