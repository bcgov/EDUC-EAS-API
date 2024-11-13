
INSERT INTO PROVINCIAL_SPECIAL_CASE_CODE (PROVINCIAL_SPECIAL_CASE_CODE,LABEL,DESCRIPTION,DISPLAY_ORDER,EFFECTIVE_DATE,EXPIRY_DATE,CREATE_USER,CREATE_DATE,UPDATE_USER,UPDATE_DATE)
VALUES ('A','Aegrotat','Aegrotat',5,
        to_date('2024-10-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        to_date('2099-12-31 00:00:00','YYYY-MM-DD HH24:MI:SS'),
        'EAS-API', CURRENT_TIMESTAMP,'EAS-API',CURRENT_TIMESTAMP);

UPDATE public.provincial_special_case_code
SET "label"='Disqualified', description='Disqualified', update_user='EAS-API', update_date=CURRENT_TIMESTAMP
WHERE provincial_special_case_code='D';