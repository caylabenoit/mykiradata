SET DEFINE OFF;
--SQL Statement which produced this data:
--
--  SELECT * FROM DGM.DIM_SCORECARD_GROUP;
--
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (2, 2, 'Address', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'HIN_Discovery_Scorecard|Address', 
    'HIN_Discovery_Scorecard|Address');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (3, 2, 'Contact', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'HIN_Discovery_Scorecard|Contact', 
    'HIN_Discovery_Scorecard|Contact');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (4, 1, 'Accuracy', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'Contract_Scorecard|Accuracy', 
    'Contract_Scorecard|Accuracy');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (5, 4, 'Accuracy', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'MDM_Hub_Scorecard|Accuracy', 
    'MDM_Hub_Scorecard|Accuracy');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (6, 1, 'Consistency', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'Contract_Scorecard|Consistency', 
    'Contract_Scorecard|Consistency');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (7, 3, 'Before', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'HIN_Individual_Before_and_After|Before', 
    'HIN_Individual_Before_and_After|Before');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (8, 1, 'Default', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'Contract_Scorecard|Default', 
    'Contract_Scorecard|Default');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (9, 4, 'Completeness', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'MDM_Hub_Scorecard|Completeness', 
    'MDM_Hub_Scorecard|Completeness');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (10, 1, 'Completeness', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'Contract_Scorecard|Completeness', 
    'Contract_Scorecard|Completeness');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (11, 2, 'Personal', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'HIN_Discovery_Scorecard|Personal', 
    'HIN_Discovery_Scorecard|Personal');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (12, 3, 'After', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'HIN_Individual_Before_and_After|After', 
    'HIN_Individual_Before_and_After|After');
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (0, 0, 'Unknown', TO_TIMESTAMP('02/12/2016 02:23:49.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'UNKN', 
    NULL);
Insert into DIM_SCORECARD_GROUP
   (SCG_PK, SCO_FK, SCG_NAME, SCG_DATETIME_LOAD, SCG_FUNCKEY, 
    SCG_PATH)
 Values
   (1, 1, 'Validity', TO_TIMESTAMP('26/01/2017 08:29:15.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'Contract_Scorecard|Validity', 
    'Contract_Scorecard|Validity');
COMMIT;
