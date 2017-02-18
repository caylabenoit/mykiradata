SET DEFINE OFF;

Insert into APP_PLUGINS_TYPE (APT_PK, APT_NAME, APT_DESCRIPTION) Values (1, 'Informatica', 'Informatica workflow import process');
Insert into APP_PLUGINS_TYPE (APT_PK, APT_NAME, APT_DESCRIPTION) Values (2, 'File', 'Direct file import');
Insert into APP_PLUGINS (API_PK, API_CONTENT, APT_FK, API_DESCRIPTION, API_SUCCESS_WORDS) Values (1, 'C:\Informatica\10.1.0\server\bin\infacmd.bat wfs startWorkflow -dn Domain_infaw2k12 -sn DIS -un Administrator -pd Administrator -a App_DGM -wf wf_Landing -w true', 1, 'Informatica Data Quality', 'Completed');
Insert into APP_PARAMS (PARAM_NAME, PARAM_STRVALUE, PARAM_INTVALUE, PARAM_LASTUPDATE, PARAM_LABEL,  PARAM_DISPLAY) Values ('emaildefault', 'admin@localhost.fr', 0, NULL, 'Email by default',  'Y');

COMMIT;
