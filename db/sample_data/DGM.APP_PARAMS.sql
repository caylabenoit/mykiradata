SET DEFINE OFF;
--SQL Statement which produced this data:
--
--  SELECT * FROM DGM.APP_PARAMS;
--
Insert into APP_PARAMS
   (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL, 
    PARAM_DISPLAY)
 Values
   ('emaildefault', 'admin@localhost.fr', 0, NULL, 'Email by default', 
    'Y');
COMMIT;
