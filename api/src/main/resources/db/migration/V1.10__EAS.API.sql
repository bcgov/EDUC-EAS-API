ALTER TABLE ASSESSMENT_STUDENT
ADD COLUMN SURNAME VARCHAR(25) NOT NULL;

ALTER TABLE ASSESSMENT_STUDENT
ADD COLUMN ASSESSMENT_CENTER_ID  UUID NULL;

ALTER TABLE ASSESSMENT_STUDENT
ADD COLUMN DISTRICT_ID  UUID NOT NULL;

ALTER TABLE ASSESSMENT_STUDENT_HISTORY
ADD COLUMN SURNAME  VARCHAR(25) NOT NULL;

ALTER TABLE ASSESSMENT_STUDENT_HISTORY
ADD COLUMN ASSESSMENT_CENTER_ID  UUID NULL;

ALTER TABLE ASSESSMENT_STUDENT_HISTORY
ADD COLUMN DISTRICT_ID  UUID NOT NULL;
