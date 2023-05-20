CREATE DATABASE IF NOT EXISTS abilitydb;
USE abilitydb;
    
    CREATE TABLE ADDRESS (
       ADDRESS_ID INTEGER NOT NULL AUTO_INCREMENT,
        ADDRESS_LINE_1 VARCHAR(128),
        ADDRESS_LINE_2 VARCHAR(128),
        CITY VARCHAR(128),
        LIVING_SINCE_YRS INTEGER,
        POSTAL_CODE INTEGER,
        STATE VARCHAR(48),
        TIME_CREATED BIGINT,
        TIME_UPDATED BIGINT,
        PRIMARY KEY (ADDRESS_ID)
    ) ENGINE=INNODB;

    
    CREATE TABLE EDUCATION (
       EDUCATION_ID BIGINT NOT NULL AUTO_INCREMENT,
        CITY VARCHAR(128),
        COUNTRY VARCHAR(128),
        DEGREE VARCHAR(128),
        DEGREE_CERT_PATH VARCHAR(128),
        LEVEL_COMPLETED VARCHAR(128),
        MAJOR VARCHAR(128),
        SCHOOL_NAME VARCHAR(128),
        SCHOOL_TYPE VARCHAR(36),
        STATE VARCHAR(48),
        TIME_CREATED BIGINT,
        TIME_UPDATED BIGINT,
        EMPLOYEE_ID BIGINT,
        PRIMARY KEY (EDUCATION_ID)
    ) ENGINE=INNODB;

    
    CREATE TABLE EMERGENCY_CONTACT (
       EMERGENCY_CONTACT_ID INTEGER NOT NULL AUTO_INCREMENT,
        ALT_PHONE_NUMBER VARCHAR(24),
        FIRST_NAME VARCHAR(128),
        LAST_NAME VARCHAR(128),
        MIDDLE_NAME VARCHAR(128),
        PHONE_NUMBER VARCHAR(24),
        RELATIONSHIP VARCHAR(36),
        TIME_CREATED BIGINT,
        TIME_UPDATED BIGINT,
        ADDRESS_ID INTEGER,
        EMPLOYEE_ID BIGINT,
        PRIMARY KEY (EMERGENCY_CONTACT_ID)
    ) ENGINE=INNODB;

    
    CREATE TABLE EMP_AVALIABILITY (
       AVAILABILITY_ID BIGINT NOT NULL AUTO_INCREMENT,
        AVAIL_START_DATE DATETIME,
        EMPLOYMENT_DESIRED VARCHAR(60),
        AUTHORIZED_TO_WORK VARCHAR(48),
        TIME_CREATED BIGINT,
        TIME_UPDATED BIGINT,
        TIMES_NOT_AVAIL_TO_WORK VARCHAR(60),
        WORK_HOURS_PER_WEEK VARCHAR(36),
        WORK_ON_NIGHTS VARCHAR(36),
        WORK_ON_WEEKENDS VARCHAR(36),
        EMPLOYEE_ID BIGINT,
        PRIMARY KEY (AVAILABILITY_ID)
    ) ENGINE=INNODB;
    
    CREATE TABLE EMP_TRANSPORT (
       TRANSPORT_ID BIGINT NOT NULL AUTO_INCREMENT,
        ALT_MODE_OF_TRANSPORT VARCHAR(48),
        HAVE_A_CAR VARCHAR(12),
        LIENSE_EXP_DATE DATETIME,
        LIENSE_NUMBER VARCHAR(36),
        TIME_CREATED BIGINT,
        TIME_UPDATED BIGINT,
        EMPLOYEE_ID BIGINT,
        PRIMARY KEY (TRANSPORT_ID)
    ) ENGINE=INNODB;

    

    
    CREATE TABLE EMPLOYEE (
       EMPLOYEE_ID BIGINT NOT NULL AUTO_INCREMENT,
        SSN VARCHAR(24),
        CONFIRM_SSN VARCHAR(24),
        EMAIL_ADDRESS VARCHAR(128),
        FIRST_NAME VARCHAR(128),
        GENDER VARCHAR(36),
        HAVE_MENTAL_ILLNESS VARCHAR(36),
        HAVE_YOU_CONVICTED_CRIME VARCHAR(36),
        HOW_DID_YOU_HEAR_ABOUTUS VARCHAR(48),
        LAST_NAME VARCHAR(128),
        MIDDLE_NAME VARCHAR(128),
        MOST_CHALLENGE_IN_WORK VARCHAR(60),
        MOST_LIKE_IN_WORK VARCHAR(60),
        PHONE_NUMBER VARCHAR(24),
        POSITION_APPLY_FOR VARCHAR(36),
        RESUME_UPLOADED_PATH VARCHAR(128),
        TIME_CREATED BIGINT,
        TIME_UPDATED BIGINT,
        WORK_PHONE_NUMBER VARCHAR(24),
        ADDRESS_ID INTEGER,
        PRIMARY KEY (EMPLOYEE_ID)
    ) ENGINE=INNODB;

    
    CREATE TABLE EMPLOYEE_SKILLSET (
       SKILLSET_ID BIGINT NOT NULL AUTO_INCREMENT,
        SKILLSET_GROUP VARCHAR(128),
        SKILLSET_NAME VARCHAR(128),
        SKILLSET_VALUE VARCHAR(36),
        EMPLOYEE_ID BIGINT,
        PRIMARY KEY (SKILLSET_ID)
    ) ENGINE=INNODB;


    
    CREATE TABLE EXPERIENCE (
       EXPERIENCE_ID BIGINT NOT NULL AUTO_INCREMENT,
        CAN_WE_CONTACT BIT,
        COMPANY VARCHAR(128),
        END_DATE DATETIME,
        IS_CURRENT BIT,
        JOB_TITLE VARCHAR(128),
        MANAGER_NAME VARCHAR(128),
        PHONE_NUMBER VARCHAR(24),
        REASON_LEFT VARCHAR(60),
        START_DATE DATETIME,
        TIME_CREATED BIGINT,
        TIME_UPDATED BIGINT,
        EMPLOYEE_ID BIGINT,
        PRIMARY KEY (EXPERIENCE_ID)
    ) ENGINE=INNODB;

    
CREATE TABLE REFERENCE (
       REFERENCE_ID BIGINT not null auto_increment,
        FIRST_NAME varchar(128),
        LAST_NAME varchar(128),
        MIDDLE_NAME varchar(128),
        PHONE_NUMBER varchar(24),
        RELATIONSHIP varchar(36),
        TIME_CREATED BIGINT,
        TIME_UPDATED BIGINT,
        YEARS_KNOWN integer,
        EMPLOYEE_ID BIGINT,
        primary key (REFERENCE_ID)
    ) engine=InnoDB;
    
    ALTER TABLE REFERENCE 
       add constraint FKBEIYA16GNI6C828PAXEKPLHX4 
       foreign key (EMPLOYEE_ID) 
       references EMPLOYEE (EMPLOYEE_ID);

    
    ALTER TABLE EDUCATION 
       ADD CONSTRAINT FKRPHG8GBX569XVJ1TXKKT91UY4 
       FOREIGN KEY (EMPLOYEE_ID) 
       REFERENCES EMPLOYEE (EMPLOYEE_ID);

    
    ALTER TABLE EMERGENCY_CONTACT 
       ADD CONSTRAINT FK55YJNWA8XB7R1E3T6W34CH5P2 
       FOREIGN KEY (ADDRESS_ID) 
       REFERENCES ADDRESS (ADDRESS_ID);

    
    ALTER TABLE EMERGENCY_CONTACT 
       ADD CONSTRAINT FKTOYEY9U8X1MC48K4LABHFHEW7 
       FOREIGN KEY (EMPLOYEE_ID) 
       REFERENCES EMPLOYEE (EMPLOYEE_ID);

    
    ALTER TABLE EMP_AVALIABILITY 
       ADD CONSTRAINT FK2J732UQHS6PKUTPC6GSWNKH93 
       FOREIGN KEY (EMPLOYEE_ID) 
       REFERENCES EMPLOYEE (EMPLOYEE_ID);

    
    ALTER TABLE EMP_TRANSPORT 
       ADD CONSTRAINT FKMCEQCQ8EB49TH4NBV3553X9IJ 
       FOREIGN KEY (EMPLOYEE_ID) 
       REFERENCES EMPLOYEE (EMPLOYEE_ID);

    
    ALTER TABLE EMPLOYEE 
       ADD CONSTRAINT FKGA73HDTPB67TWLR9C1I337TYT 
       FOREIGN KEY (ADDRESS_ID) 
       REFERENCES ADDRESS (ADDRESS_ID);

    
    ALTER TABLE EMPLOYEE_SKILLSET 
       ADD CONSTRAINT FK85I222XIANGV2S8N54FI89F82 
       FOREIGN KEY (EMPLOYEE_ID) 
       REFERENCES EMPLOYEE (EMPLOYEE_ID);

    
    ALTER TABLE EXPERIENCE 
       ADD CONSTRAINT FKL2JNEVDMIBIL233ECOGBVOFF4 
       FOREIGN KEY (EMPLOYEE_ID) 
       REFERENCES EMPLOYEE (EMPLOYEE_ID);
	   
ALTER TABLE EMPLOYEE ADD COLUMN IS_VISITED VARCHAR(10) NULL;

ALTER TABLE EMPLOYEE ADD COLUMN IS_CONSENT_SIGNED VARCHAR(10) NULL;

ALTER TABLE EMPLOYEE ADD COLUMN DOB DATETIME NULL;

ALTER TABLE EMPLOYEE ADD COLUMN ACTIVE_TAB_INDEX VARCHAR(48) NULL;

ALTER TABLE EMPLOYEE ADD COLUMN SIGNATURE BLOB NULL;

ALTER TABLE EDUCATION ADD COLUMN ACTIVE CHAR  NULL;

ALTER TABLE EXPERIENCE ADD COLUMN ACTIVE CHAR  NULL;

ALTER TABLE REFERENCE ADD COLUMN ACTIVE CHAR  NULL;

ALTER TABLE EMPLOYEE ADD COLUMN CRIME_REASON VARCHAR(256)  NULL;

ALTER TABLE ADDRESS MODIFY LIVING_SINCE_YRS VARCHAR(64) ;

ALTER TABLE REFERENCE MODIFY YEARS_KNOWN VARCHAR(64) ;

CREATE INDEX emailAddr ON EMPLOYEE (EMAIL_ADDRESS);



CREATE TABLE ONE_TIME_PASS_CODE (
		ONE_TIME_PASS_CODE_ID BIGINT not null auto_increment,
        EMPLOYEE_ID BIGINT,
        OTP_CODE VARCHAR(15),
        EXPIRARY_TIME BIGINT,
		IS_EXPIRED varchar(2),
        primary key (ONE_TIME_PASS_CODE_ID)
    ) engine=InnoDB;
	
CREATE INDEX otpEmployeeIdIdx ON ONE_TIME_PASS_CODE (EMPLOYEE_ID);

ALTER TABLE ONE_TIME_PASS_CODE ADD COLUMN GENERATED_TIME BIGINT  NULL;
