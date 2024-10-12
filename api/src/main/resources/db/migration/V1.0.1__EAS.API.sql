ALTER TABLE SESSION RENAME TO ASSESSMENT_SESSION;

ALTER TABLE COURSE_STATUS_CODE RENAME TO STUDENT_STATUS_CODE;
ALTER TABLE STUDENT_STATUS_CODE RENAME COLUMN  COURSE_STATUS_CODE TO STUDENT_STATUS_CODE;

CREATE TABLE ASSESSMENT
(
    ASSESSMENT_ID                       UUID                                NOT NULL,
    SESSION_ID                          UUID                                NOT NULL,
    ASSESSMENT_TYPE_CODE                VARCHAR(10)                         NOT NULL,
    CREATE_USER                         VARCHAR(100)                        NOT NULL,
    CREATE_DATE                         TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATE_USER                         VARCHAR(100)                        NOT NULL,
    UPDATE_DATE                         TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT ASSESSMENT_ID_PK PRIMARY KEY (ASSESSMENT_ID),
    CONSTRAINT FK_ASSESSMENT_TYPE_CODE FOREIGN KEY (ASSESSMENT_TYPE_CODE) REFERENCES ASSESSMENT_TYPE_CODE(ASSESSMENT_TYPE_CODE),
    CONSTRAINT FK_ASSESSMENT_SESSION FOREIGN KEY (SESSION_ID) REFERENCES ASSESSMENT_SESSION(SESSION_ID)
);

ALTER TABLE ASSESSMENT_STUDENT DROP CONSTRAINT FK_ASSESSMENT_TYPE_CODE;
ALTER TABLE ASSESSMENT_STUDENT DROP CONSTRAINT FK_SESSION_ID;
ALTER TABLE ASSESSMENT_STUDENT DROP COLUMN SESSION_ID;
ALTER TABLE ASSESSMENT_STUDENT DROP COLUMN ASSESSMENT_TYPE_CODE;

ALTER TABLE ASSESSMENT_STUDENT ADD COLUMN ASSESSMENT_ID UUID NOT NULL;
ALTER TABLE ASSESSMENT_STUDENT ADD CONSTRAINT FK_ASSESSMENT_ID FOREIGN KEY (ASSESSMENT_ID) REFERENCES ASSESSMENT(ASSESSMENT_ID);

CREATE TABLE EAS_SHEDLOCK
(
    NAME       VARCHAR(64),
    LOCK_UNTIL TIMESTAMP(3) NULL,
    LOCKED_AT  TIMESTAMP(3) NULL,
    LOCKED_BY  VARCHAR(255),
    CONSTRAINT EAS_SHEDLOCK_PK PRIMARY KEY (NAME)
);

COMMENT ON TABLE EAS_SHEDLOCK IS 'This table is used to achieve distributed lock between pods, for schedulers.';
