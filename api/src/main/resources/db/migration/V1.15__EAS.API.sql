INSERT INTO PROVINCIAL_SPECIAL_CASE_CODE (PROVINCIAL_SPECIAL_CASE_CODE,LABEL,DESCRIPTION,DISPLAY_ORDER,EFFECTIVE_DATE,EXPIRY_DATE,CREATE_USER,CREATE_DATE,UPDATE_USER,UPDATE_DATE)
VALUES ('X','Not Completed','Not Completed',25,
        to_date('2024-10-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        to_date('2099-12-31 00:00:00','YYYY-MM-DD HH24:MI:SS'),
        'EAS-API', CURRENT_TIMESTAMP,'EAS-API',CURRENT_TIMESTAMP);

UPDATE PROVINCIAL_SPECIAL_CASE_CODE
SET PROVINCIAL_SPECIAL_CASE_CODE='Q', update_user='EAS-API', update_date=CURRENT_TIMESTAMP
WHERE label='Disqualified';

UPDATE PROVINCIAL_SPECIAL_CASE_CODE
SET "label"='Exempt', description='Exempt', update_user='EAS-API', update_date=CURRENT_TIMESTAMP
WHERE provincial_special_case_code='E';