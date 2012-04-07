#Language, friend and words
# --- !Ups
 
CREATE TABLE Language (
	id 		int 		NOT NULL AUTO_INCREMENT,
	name 		varchar(512) 	NOT NULL,
	nameEnglish 	varchar(512) 	NOT NULL,
	code 		varchar(8) 	NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Friend (
	id	bigint		NOT NULL AUTO_INCREMENT,
	text	text,
	createdate datetime	NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE FriendWord (
	id		bigint		NOT NULL AUTO_INCREMENT,
	friendId	bigint		NOT NULL,
	languageId	bigint		NOT NULL,
	word		varchar(2048)	NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (friendId) REFERENCES Friend(id),
	FOREIGN KEY (languageId) REFERENCES Friend(id)
);
 
# --- !Downs
 
DROP TABLE FriendWord; 
DROP TABLE Friend; 
DROP TABLE Language; 
