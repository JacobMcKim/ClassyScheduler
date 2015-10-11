DROP TABLE  `classy`.`section`,`classy`.`course`, `classy`.`department`, `classy`.`semester`;

CREATE TABLE `classy`.`department` (
  `dID` INT NOT NULL COMMENT '',
  `dName` VARCHAR(3) NULL COMMENT '',
  PRIMARY KEY (`dID`)  COMMENT '');

insert into department values(1, 'CIS');

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
    ON UPDATE NO ACTION);

#TODO: populate descriptions
insert into course values (1, 162, 'Computer Science 1', null);
insert into course values (1, 163, 'Computer Science 2', null);
insert into course values (1, 263, 'Data Structures', null);
insert into course values (1, 350, 'Software Engineering', null);
insert into course values (1, 290, 'Internship Preperation', null);

CREATE TABLE `classy`.`semester` (
  `year` INT NOT NULL COMMENT '',
  `season` VARCHAR(3) NOT NULL COMMENT '',
  PRIMARY KEY (`year`, `season`)  COMMENT '');

insert into semester values(15,'fal');
insert into semester values(16,'win');
insert into semester values(16,'sum');

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
    ON UPDATE casade,
  CONSTRAINT `fk_section2`
    FOREIGN KEY (`semYear` , `semSeas`)
    REFERENCES `classy`.`semester` (`year` , `season`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

# Two sections of each course for the fall semeseter
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

#Two of each for winter
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
