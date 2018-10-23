drop schema if exists jobfinder;
create schema jobfinder;
use jobfinder;

drop table if exists credential;
drop table if exists hibernate_sequence;
drop table if exists job;
drop table if exists jobapplication;
drop table if exists jobcategory;
drop table if exists recruiter;
drop table if exists userprofile;

create table credential (username varchar(255) not null, password varchar(255), userType varchar(255), primary key (username)) engine=InnoDB;
create table hibernate_sequence (next_val bigint) engine=InnoDB;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

create table job (jobID integer not null, currency varchar(255), employmentType varchar(255), jobdescription varchar(255), jobstatus bit, jobTitle varchar(255), location varchar(255), plusCommission bit, rangeamt integer, term varchar(255), jobCategoryID integer, recruiterID integer, primary key (jobID)) engine=InnoDB;
create table jobapplication (applicationID integer not null auto_increment, applicationDate date, decision bit not null, decisionDate date, decisionMade bit not null, jobID integer, userid integer, primary key (applicationID)) engine=InnoDB;
create table jobcategory (jobCategoryID integer not null, category varchar(255), primary key (jobCategoryID)) engine=InnoDB;
create table recruiter (recruiterID integer not null, company varchar(255), email varchar(255), jobtitle varchar(255), name varchar(255), phone varchar(255), credential_username varchar(255), primary key (recruiterID)) engine=InnoDB;
create table userprofile (userid integer not null auto_increment, country varchar(255), email varchar(255), facebookurl varchar(255), file varchar(255), headline varchar(255), linkedinurl varchar(255), name varchar(255), phone varchar(255), postalcode varchar(255), twitterurl varchar(255), credential_username varchar(255), jobCategoryID integer, primary key (userid)) engine=InnoDB;

alter table recruiter add constraint UK_drmlth7vhdbyxldwmw51wmnk0 unique (email);
alter table userprofile add constraint UK_ib68cmideb8wp9dm9krrncem3 unique (email);
alter table job add constraint FKnhdnxgrcxy21r9jfcy787jdsy foreign key (jobCategoryID) references jobcategory (jobCategoryID);
alter table job add constraint FKqi8ntu8iv7qgyphf6due38owy foreign key (recruiterID) references recruiter (recruiterID);
alter table jobapplication add constraint FK276mbu0j5phgkoy0j6hr4i5p9 foreign key (jobID) references job (jobID);
alter table jobapplication add constraint FKm3qywismu3xciklfu80pkhdje foreign key (userid) references userprofile (userid);
alter table recruiter add constraint FK967pqyvpdkkkc0nf8t61w4cwm foreign key (credential_username) references credential (username);
alter table userprofile add constraint FK8p2jxwt2uebu915r840p8qx6e foreign key (credential_username) references credential (username);
alter table userprofile add constraint FK6grhxsk1yxv8hvdc9eu9kv4l3 foreign key (jobCategoryID) references jobcategory (jobCategoryID);

insert into jobcategory (jobcategoryid, category)
values (1, 'Accounting'), (2, 'Banking'), (3, 'Engineering'), (4, 'Finance'), (5, 'Management');

drop user if exists 'jfuser'@'localhost';
create user 'jfuser'@'localhost' identified by 'jfpass';
grant all privileges on *.* to 'jfuser'@'localhost';
