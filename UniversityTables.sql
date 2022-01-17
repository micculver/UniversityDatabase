create database university;

use university;
 
CREATE TABLE department (
    did INT AUTO_INCREMENT,
    dname VARCHAR(40) UNIQUE,
    PRIMARY KEY(did)
);

CREATE TABLE staff (
    sid INT AUTO_INCREMENT,
    sname VARCHAR(40),
    deptid INT,
    FOREIGN KEY(deptid)
        REFERENCES department(did)
        ON UPDATE CASCADE,
    PRIMARY KEY(sid)
);

CREATE TABLE faculty (
    fid INT AUTO_INCREMENT,
    fname VARCHAR(40),
    deptid INT,
    FOREIGN KEY(deptid)
        REFERENCES department(did)
        ON UPDATE CASCADE,
    PRIMARY KEY(fid)
);

CREATE TABLE student (
    sid INT AUTO_INCREMENT,
    sname VARCHAR(40),
    major VARCHAR(20),
    s_level VARCHAR(9),
    age INT,
    PRIMARY KEY(sid)
);

CREATE TABLE courses (
    cid VARCHAR(6) PRIMARY KEY,
    cname VARCHAR(40),
    meets_at VARCHAR(20),
    room VARCHAR(5),
    fid INT,
    class_limit INT,
    FOREIGN KEY(fid)
        REFERENCES faculty(fid)
        ON UPDATE CASCADE
);

CREATE TABLE enrolled (
    sid INT,
    cid VARCHAR(6),
    exam1_score INT,
    exam2_score INT,
    final_score INT,
    PRIMARY KEY(sid, cid),
    FOREIGN KEY(sid)
        REFERENCES student(sid)
        ON UPDATE CASCADE,
    FOREIGN KEY(cid)
        REFERENCES courses(cid)
        ON UPDATE CASCADE
);

--rejects insert on enroll when class limit for course is reached
DELIMITER $$
CREATE TRIGGER insert_enrolled
BEFORE INSERT ON enrolled
FOR EACH ROW
BEGIN
    IF (SELECT COUNT(cid) FROM enrolled WHERE cid = NEW.cid) >= (SELECT class_limit FROM courses WHERE cid = NEW.cid)
    THEN
        SIGNAL SQLSTATE '45000';
    END IF;
END; $$
DELIMITER ;