CREATE SCHEMA weatherBoard;
USE weatherBoard;

delete from boards_cities;
delete from users;
delete from boards;
delete from cities;

drop table boards_cities;
drop table users;
drop table boards;
drop table cities;

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



select * from boards;
select * from cities;
select * from boards_cities;
select * from users;

