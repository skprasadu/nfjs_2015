create database languages;

create user languages@'%' identified by 'password';

grant all on languages.* to languages@'%';

flush privileges;
