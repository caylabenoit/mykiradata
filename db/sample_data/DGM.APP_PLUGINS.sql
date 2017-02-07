SET DEFINE OFF;
--SQL Statement which produced this data:
--
--  SELECT * FROM DGM.APP_PLUGINS;
--
Insert into APP_PLUGINS
   (API_PK, API_CONTENT, APT_FK, API_DESCRIPTION, API_SUCCESS_WORDS)
 Values
   (1, 'C:\Informatica\10.1.0\server\bin\infacmd.bat wfs startWorkflow -dn Domain_infaw2k12 -sn DIS -un Administrator -pd Administrator -a App_DGM -wf wf_Landing -w true', 1, 'Informatica Data Quality', 'Completed');
COMMIT;
