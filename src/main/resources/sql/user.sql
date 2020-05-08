create table user (
    id bigint(15) auto_increment,
    name varchar(50),
    age  int(5),
    sex  tinyint(1),
    password varchar(20),
    role varchar(100),
    head_photo varchar(20),
    create_time datetime,
    create_timestamp bigint,
    zone_create_time TIMESTAMP,
     PRIMARY KEY(id)
);