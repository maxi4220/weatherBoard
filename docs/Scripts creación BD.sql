/*CREATE SCHEMA weatherBoard;*/
USE weatherBoard;

/*delete from boards_cities;
delete from cities;
delete from boards;
delete from users;

drop table boards_cities;
drop table cities;
drop table boards;
drop table users;
*/
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
    name varchar(15) not null default 'Board',
    FOREIGN KEY (iduser) REFERENCES users(id)
);

create table boards_cities(
	id_board int(1) unsigned not null,
    id_city INT(1) unsigned not null,
    FOREIGN KEY (id_board) REFERENCES boards(id),
    FOREIGN KEY (id_city) REFERENCES cities(id),
    primary key(id_board, id_city)
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



/*select * from boards;
select * from cities;
select * from boards_cities;
select * from users;
*/
