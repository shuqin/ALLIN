##############################################
# how to exec sql file  
# mysql -u root -p < /your_path/course.sql
# Enter password: [input your root password]   
##############################################
# 或者使用 root 登录 mysql, 
# 然后复制所有语句在 mysql 中执行。
#############################################

######################################
# mysql statement:
######################################

CREATE USER 'course4'@'localhost' IDENTIFIED BY  'course4';
CREATE DATABASE course;
grant all on course.* to 'course4'@'localhost';

#####################################

USE course;

CREATE TABLE students ( 
      id INT NOT NULL AUTO_INCREMENT, 
      stuName varchar(12) NOT NULL,  
      PRIMARY KEY(id)
      ) ;
      
CREATE TABLE courses (
      id INT NOT NULL AUTO_INCREMENT, 
      cName varchar(15) NOT NULL, 
      cTime int NOT NULL, 
      note varchar(30),
      PRIMARY KEY(id)
      ) ;
        
CREATE table stus_cours ( 
      stu_id int NOT NULL,  
      cour_id int NOT NULL, 
      sch_term  varchar(5) NOT NULL,  
      PRIMARY KEY(stu_id, cour_id)
      );  

alter table stus_cours add constraint stuCons foreign key(stu_id) references students(id);
alter table stus_cours add constraint courCons foreign key(cour_id) references courses(id);

 desc courses;
 desc students;
 desc stus_cours;
 
 insert into students values(10001, '小华');
 insert into students values(10002, '小明');
 insert into courses values(1001, '高数', 54, '');
 insert into courses values(1002, '大学英语', 81, '');
 insert into stus_cours values(10001, 1001, '2nd');
 insert into stus_cours values(10002, 1001, '2nd');
 insert into stus_cours values(10001, 1002, '1st');
 insert into stus_cours values(10002, 1002, '1st');
 
 SELECT * from students;
 SELECT * from courses;
 SELECT * from stus_cours;
  
  SELECT stuName , cName, cTime , sch_term  
  FROM students, courses, stus_cours 
  WHERE students.id = stus_cours.stu_id 
  AND courses.id = stus_cours.cour_id; 




