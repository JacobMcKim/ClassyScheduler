#Drop statement that will clear database for reintialization.
#DROP TABLE  `classy`.`section`,`classy`.`course`, `classy`.`department`,
#            `classy`.`semester`;

#~~ Create department table.
#~~~~ A table represendting all the departments at the institution.
#~ Atributes-
#~~~~ dID- Department ID- a numerical tag for how we will refer to departments
#~~~~ dName- Department Name- a 3 letter tag for the Department.
#TODO: Fix dName, add Dtag.
CREATE TABLE `classy`.`department` (
  `dID` INT NOT NULL COMMENT '',
  `dName` VARCHAR(3) NULL COMMENT '',
  PRIMARY KEY (`dID`)  COMMENT '');

#Populates Department
insert into department values(1, 'CIS');

#~~ Create course table.
#~~~~ A table represendting all the courses offered by an institution.
#~ Atributes-
#~~~~ dNum- Refers to the department that offers the course.
#~~~~ cID- Numerical tag used to in conjuntion wiht dNum to identify the
#          course.
#~~~~ title- Contains the title of the course.
#~~~~ desc- Contains a description of the course.
CREATE TABLE `classy`.`course` (
  `dNum` INT NOT NULL COMMENT '',
  `cID` INT NOT NULL COMMENT '',
  `title` VARCHAR(100) NULL COMMENT '',
  `desc` VARCHAR(300) null comment '',
  PRIMARY KEY (`dNum`, `cID`)  COMMENT '',
  CONSTRAINT `fk_course1`
    FOREIGN KEY (`dNum`)
    REFERENCES `classy`.`department` (`dID`)
    ON DELETE NO ACTION
    ON UPDATE cascade);

#Populate course
#TODO: populate descriptions
insert into course values (1, 162, 'Computer Science 1', null);
insert into course values (1, 163, 'Computer Science 2', null);
insert into course values (1, 263, 'Data Structures', null);
insert into course values (1, 350, 'Software Engineering', null);
insert into course values (1, 290, 'Internship Preperation', null);

#~~ Create semester table.
#~~~~ A table represendting the different semesters in which courses are
#     offered.
#~ Atributes-
#~~~~ year- Integer representing the year of the semester.
#~~~~ season- 3 letter tag refering to the season which the class was held
#             i.e. 'win', 'fal' or 'sum'.
CREATE TABLE `classy`.`semester` (
  `year` INT NOT NULL COMMENT '',
  `season` VARCHAR(3) NOT NULL COMMENT '',
  PRIMARY KEY (`year`, `season`)  COMMENT '');

#Populate semester.
insert into semester values(15,'fal');
insert into semester values(16,'win');
insert into semester values(16,'sum');

#~~ Create section table.
#~~~~ A table represendting all the sections offered by an institution. A
#     section is the instance of a class that the student signs up for to
#     complete a course.
#~ Atributes-
#~~~~ depNum- refers to the dNum of the course this secton satisfies.
#~~~~ cNum- refers to the cID of the course this section satisfies
#~~~~ sID- a numerical identifier for the section.
#~~~~ semYear- refers to the year of the semseter this section took place.
#~~~~ semSeas- refers to the season of the semester this section took place.
#~~~~ sTime- a time value representing what time the section meets.
#~~~~ eTime- a time value representing what time the section ends.
#~~~~ meet- a string representing what tdays of the week the section meets on.
#           i.e. 'MWF' for Monday, Wednesday, Friday etc.
CREATE TABLE `classy`.`section` (
  `depNum` INT NOT NULL COMMENT '',
  `cNum` INT NOT NULL COMMENT '',
  `sID` INT NOT NULL COMMENT '',
  `semYear` INT NOT NULL COMMENT '',
  `semSeas` VARCHAR(3) NOT NULL COMMENT '',
  `sTime` TIME NULL COMMENT '',
  `eTime` TIME NULL COMMENT '',
  `meet` VARCHAR(45) NULL COMMENT '',
  PRIMARY KEY (`depNum`, `cNum`, `sID`, `semYear`, `semSeas`)  COMMENT '',
  INDEX `fk_section2_idx` (`semYear` ASC, `semSeas` ASC)  COMMENT '',
  CONSTRAINT `fk_section1`
    FOREIGN KEY (`depNum` , `cNum`)
    REFERENCES `classy`.`course` (`dNum` , `cID`)
    ON DELETE NO ACTION
    ON UPDATE cascade,
  CONSTRAINT `fk_section2`
    FOREIGN KEY (`semYear` , `semSeas`)
    REFERENCES `classy`.`semester` (`year` , `season`)
    ON DELETE NO ACTION
    ON UPDATE cascade);

# Populates two sections of each course for the fall semeseter
insert into section values(1,162,1,15,'fal','08:00:00','08:50:00','MWF');
insert into section values(1,162,2,15,'fal','09:00:00','09:50:00','MWF');
insert into section values(1,163,1,15,'fal','08:00:00','08:50:00','MWF');
insert into section values(1,163,2,15,'fal','09:00:00','09:50:00','MWF');
insert into section values(1,263,1,15,'fal','12:00:00','12:50:00','MWF');
insert into section values(1,263,2,15,'fal','14:00:00','14:50:00','MWF');
insert into section values(1,350,1,15,'fal','10:00:00','10:50:00','MWF');
insert into section values(1,350,2,15,'fal','11:00:00','11:50:00','MWF');
insert into section values(1,290,1,15,'fal','12:00:00','12:50:00','Tr');
insert into section values(1,290,2,15,'fal','13:00:00','13:50:00','Tr');

# Populates two of each for winter
insert into section values(1,162,1,16,'win','08:00:00','08:50:00','MWF');
insert into section values(1,162,2,16,'win','09:00:00','09:50:00','MWF');
insert into section values(1,163,1,16,'win','08:00:00','08:50:00','MWF');
insert into section values(1,163,2,16,'win','09:00:00','09:50:00','MWF');
insert into section values(1,263,1,16,'win','12:00:00','12:50:00','MWF');
insert into section values(1,263,2,16,'win','14:00:00','14:50:00','MWF');
insert into section values(1,350,1,16,'win','10:00:00','10:50:00','MWF');
insert into section values(1,350,2,16,'win','11:00:00','11:50:00','MWF');
insert into section values(1,290,1,16,'win','12:00:00','12:50:00','Tr');
insert into section values(1,290,2,16,'win','13:00:00','13:50:00','Tr');
