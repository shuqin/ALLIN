## executed using root account
## mysql -uroot -p < /path/to/project.sql

DROP USER 'test'@'localhost';
drop database if exists test;

CREATE USER 'test'@'localhost' IDENTIFIED BY 'test';
create database test ;
grant all privileges on test.* to 'test'@'localhost' identified by 'test';

use test

drop table if exists creative;

create table creative (
	creative_id int(10) UNSIGNED not null primary key AUTO_INCREMENT,
	title varchar(32) not null,
	content varchar(50) not null,
	submitter varchar(32) not null,
	comment varchar(100) default '',
	gmt_create datetime DEFAULT CURRENT_TIMESTAMP,
  	gmt_modified datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

## Run StudentCoursesDataGenerator to generate datas for student_courses

drop table if exists student_courses;

create table student_courses (
    id int(10) UNSIGNED not null primary key AUTO_INCREMENT comment 'AUTO_INCREMENT ID',
	s_id varchar(64) not null comment 'student ID',
	t_id varchar(64) not null comment 'teacher ID',
	room varchar(64) not null comment 'room name',
	c_id varchar(32) not null comment 'course ID',
	c_time int(10) not null comment 'course time',
	extra varchar(256) default '' comment 'extra info',
	gmt_create datetime DEFAULT CURRENT_TIMESTAMP,
  	gmt_modified datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `student_courses` ADD INDEX stc_index ( `s_id`, `t_id`, `c_id` );
ALTER TABLE `student_courses` ADD INDEX tc_index ( `t_id`, `c_id` );