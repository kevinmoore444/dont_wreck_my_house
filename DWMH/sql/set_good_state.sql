delimiter //
create procedure set_known_good_state()
begin
	delete from reservation;
	delete from location;
    delete from `state`;
	delete from `user`;
	alter table `user` auto_increment=1;
    alter table `state` auto_increment=1;
	alter table location auto_increment=1;
	alter table reservation auto_increment=1;
    
    insert into `user` (first_name, last_name, email, phone)
		values
		('FirstName1', 'LastName1', 'user1@gmail.com', '(234)567-8912'),
        ('FirstName2', 'LastName2', 'user2@gmail.com', '(123)456-7890');
        
	insert into state (`name`, usps_code)
		values
        ('Alabama', 'AL'),
        ('Alaska', 'AK');
    
	insert into location (user_id, address, city, postal_code, state_id, standard_rate, weekend_rate) values
		(1, '123 Fake St.', 'Fake City', 11111, 1, 100.00, 200.00),
		(2, '456 Fake St.', 'Fake City1', 22222, 2, 150.00, 250.00);
        
	insert into reservation(location_id, guest_user_id, start_date, end_date, total) values
		(1, 2, '2024-04-01', '2024-04-05', 300.0),
        (2, 1, '2024-05-01', '2024-05-05', 500.0);
end//
delimiter ;