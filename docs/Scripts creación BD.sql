CREATE SCHEMA weatherBoard;
USE weatherBoard;

CREATE TABLE users (
	id INT(2) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(80) NOT NULL
);

CREATE TABLE cities(
	id INT(1) unsigned auto_increment PRIMARY KEY,
    woeid char(6) not null,
    name varchar(80) NOT NULL,
    humidity int(3) unsigned null,
    pressure decimal(6,2) unsigned null,
    temp decimal(4,2) null,
    text varchar(50) null
);

create table boards(
	id int(1) unsigned auto_increment primary key,
    iduser INT(2) UNSIGNED not null,
    FOREIGN KEY (iduser) REFERENCES users(id)
);

create table boards_cities(
	idboard int(1) unsigned not null,
    idcity INT(1) unsigned not null,
    FOREIGN KEY (idboard) REFERENCES boards(id),
    FOREIGN KEY (idcity) REFERENCES cities(id),
    primary key(idboard, idcity)
);

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_add_cities_to_board` $$
CREATE PROCEDURE `sp_add_cities_to_board`(
  IN pIdBoard int(1),
  IN pIdCity int(1)
)
BEGIN
	DECLARE vExists bit;
    
    select count(*) into vExists
	from boards_cities 
	where  	idBoard = pIdBoard
		and idCity = pIdCity;
        
	IF vExists = 0 THEN
		insert into boards_cities values(
			pIdBoard,
			pIdCity
		);
	END IF;
END $$

DELIMITER ;

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_remove_cities_from_board` $$
CREATE PROCEDURE `sp_remove_cities_from_board`(
  IN pIdBoard int(1),
  IN pIdCity int(1)
)
BEGIN
	delete from boards_cities 
    where 	idBoard = pIdBoard
		and idCity = pIdCity;
END $$

DELIMITER ;


DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_add_user` $$
CREATE PROCEDURE `sp_add_user`(
  IN pName varchar(80)
)
BEGIN
	DECLARE vExists bit;
    
    select count(*) into vExists
	from users
	where  	upper(name)= pName;
        
	IF vExists = 0 THEN
		insert into users(name) values(pName);
        insert into boards (iduser) values(LAST_INSERT_ID());
	END IF;
END $$

DELIMITER ;


DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_add_board` $$
CREATE PROCEDURE `sp_add_board`(
  IN pName varchar(80)
)
BEGIN

	insert into boards (iduser) 
	values((select id from users where upper(name)= pName));

END $$

DELIMITER ;



insert into cities (woeid, name) values(468739, 'Ciudad autónoma de Buenos Aires');
insert into cities (woeid, name) values(466861, 'Córdoba');
insert into cities (woeid, name) values(466862, 'Rosario');
insert into cities (woeid, name) values(332469, 'La Plata');
insert into cities (woeid, name) values(465663, 'Lanús');
insert into cities (woeid, name) values(332471, 'Mendoza');
insert into cities (woeid, name) values(466882, 'Posadas');
insert into cities (woeid, name) values(332474, 'Santiago del Estero');
insert into cities (woeid, name) values(467039, 'Rawson');
insert into cities (woeid, name) values(466869, 'Paraná');
commit;
insert into users (name, nickname, password) values ('Maximiliano Pozzi', 'maxi', 'pass');
insert into boards (iduser) values (1);
insert into boards_cities values (1, 1);




select * from boards;
select * from cities;
select * from boards_cities;
select * from users;
delete from boards where id > 1;
delete from boards_cities where idboard > 1;

