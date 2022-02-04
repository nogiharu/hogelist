drop table hogetable;

create table hogetable (
id serial primary key,
name varchar(10) not null,
age integer not null,
gender varchar(3) not null,
birthday date not null,
registration_date date not null,
eat integer not null,
vegetable_like text,
hobby text
);
insert into hogetable(name,age,gender,birthday,registration_date,eat,vegetable_like,hobby) values ('田中 明彦',33,'0','1970-10-10','2020-10-01',1,'Y','野菜を食べること');
insert into hogetable(name,age,gender,birthday,registration_date,eat,vegetable_like,hobby) values ('上田 松子',54,'1','1970-10-12','2020-10-02',0,'N','野菜を煮ること');
insert into hogetable(name,age,gender,birthday,registration_date,eat,vegetable_like,hobby) values ('林 康',45,'0','1970-10-14','2020-10-03',1,'Y','野菜を焼くこと');
insert into hogetable(name,age,gender,birthday,registration_date,eat,vegetable_like,hobby) values ('高橋 千代子',49,'1','1970-12-10','2020-10-04',0,'N','野菜を蒸すこと');
insert into hogetable(name,age,gender,birthday,registration_date,eat,vegetable_like,hobby) values ('佐々木 千代子',49,'3','1970-12-10','2020-10-04',0,'N','野菜を蒸すこと');

drop table hogemutter;

create table comments (
id serial primary key,
hoge_id integer not null,
name varchar(10) not null,
comment_date date not null,
comment text
);