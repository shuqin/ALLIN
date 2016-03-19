## executed using root account
## mysql -uroot -p < /path/to/project.sql

drop database if exists goodcode;

create database goodcode ;
grant all privileges on goodcode.* to 'test'@'localhost' identified by 'test';

use goodcode

drop table if exists creative;

create table creative (
	creative_id int(10) UNSIGNED not null primary key AUTO_INCREMENT,
	title varchar(32) not null,
	content varchar(50) not null,
	submitter varchar(32) not null,
	comment varchar(100) default '',
	gmt_create datetime DEFAULT NULL,
  	gmt_modified datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
