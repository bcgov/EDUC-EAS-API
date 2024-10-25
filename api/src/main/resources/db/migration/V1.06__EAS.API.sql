ALTER TABLE EAS_SAGA
ADD COLUMN ASSESSMENT_STUDENT_ID  UUID;

ALTER TABLE ASSESSMENT_STUDENT
ADD COLUMN ASSESSMENT_STUDENT_STATUS_CODE  VARCHAR(20)  NOT NULL;

ALTER TABLE ASSESSMENT_STUDENT_HISTORY
ADD COLUMN ASSESSMENT_STUDENT_STATUS_CODE  VARCHAR(20)  NOT NULL;

CREATE TABLE ASSESSMENT_STUDENT_STATUS_CODE
(
    ASSESSMENT_STUDENT_STATUS_CODE VARCHAR(20) NOT NULL,
    LABEL            VARCHAR(30)                         NOT NULL,
    DESCRIPTION      VARCHAR(255)                        NOT NULL,
    DISPLAY_ORDER    NUMERIC   DEFAULT 1                 NOT NULL,
    EFFECTIVE_DATE   TIMESTAMP                           NOT NULL,
    EXPIRY_DATE      TIMESTAMP                           NOT NULL,
    CREATE_USER      VARCHAR(32)                         NOT NULL,
    CREATE_DATE      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATE_USER      VARCHAR(32)                         NOT NULL,
    UPDATE_DATE      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT ASSESSMENT_STUDENT_STATUS_CODE_PK PRIMARY KEY (ASSESSMENT_STUDENT_STATUS_CODE)
);

ALTER TABLE ASSESSMENT_STUDENT
    ADD CONSTRAINT FK_ASSESSMENT_STUDENT_STATUS_CODE FOREIGN KEY (ASSESSMENT_STUDENT_STATUS_CODE)
        REFERENCES ASSESSMENT_STUDENT_STATUS_CODE (ASSESSMENT_STUDENT_STATUS_CODE);

INSERT INTO ASSESSMENT_STUDENT_STATUS_CODE (ASSESSMENT_STUDENT_STATUS_CODE, LABEL, DESCRIPTION, DISPLAY_ORDER, EFFECTIVE_DATE,
                                                       EXPIRY_DATE, CREATE_USER, CREATE_DATE, UPDATE_USER, UPDATE_DATE)
    VALUES ('LOADED', 'Loaded', 'Registration Loaded for student.', '1',
        to_date('2019-07-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        to_date('2099-12-31 00:00:00','YYYY-MM-DD HH24:MI:SS'),
        'EAS-API', CURRENT_TIMESTAMP,'EAS-API',CURRENT_TIMESTAMP);