CREATE DATABASE itz;
USE itz;

CREATE TABLE  galeria(
    id int primary key,
    nombre varchar(30),
    descripcion varchar(50),
    img mediumblob
);

CREATE TABLE contacts (
    contact_id int PRIMARY KEY AUTO_INCREMENT,
    first_name varchar(45) DEFAULT NULL,
    last_name varchar(45) DEFAULT NULL,
    photo mediumblob
);