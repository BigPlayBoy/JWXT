CREATE TABLE `Grade` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`sid`	INTEGER,
	`kecheng`	TEXT,
	`xuefen`	REAL,
	`cehngji`	REAL,
	`jidian`	REAL,
	`shuxing`	TEXT,
	`time`	TEXT
);
CREATE TABLE `Login` (
	`id`	INTEGER AUTOINCREMENT,
	`sid` INTEGER,
	`password`	TEXT,
	PRIMARY KEY(id)
);
CREATE TABLE `Student` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`sid` INTEGER,
	`name`	TEXT,
	`sex`	TEXT,
	`xuezhi`	TEXT DEFAULT 4,
	`yuanxi`	TEXT DEFAULT '计信学院',
	`zhuanye`	TEXT DEFAULT '计算机科学与技术',
	`banji`	TEXT DEFAULT 1301,
	`ruxueriqi`	TEXT DEFAULT 201309,
	`gradeNumber`	INTEGER
);
--ALTER TABLE Login add CONSTRAINT fk_login_sid FOREIGN KEY (sid) references Student(sid);
--ALTER TABLE Grade add CONSTRAINT fk_Grade_sid FOREIGN KEY (sid) references Student(sid);