SET DEFINE OFF;
--SQL Statement which produced this data:
--
--  SELECT * FROM DGM.DIM_ORIGINE;
--
Insert into DIM_ORIGINE
   (ORI_PK, ORI_NAME, ORI_DATETIME_LOAD, ORI_FUNCKEY)
 Values
   (2, 'Informatica Data Quality Profiles', TO_TIMESTAMP('02/12/2016 02:23:49.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'IDQPL');
Insert into DIM_ORIGINE
   (ORI_PK, ORI_NAME, ORI_DATETIME_LOAD, ORI_FUNCKEY)
 Values
   (0, 'Unknown', TO_TIMESTAMP('02/12/2016 02:23:49.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'UNKN');
Insert into DIM_ORIGINE
   (ORI_PK, ORI_NAME, ORI_DATETIME_LOAD, ORI_FUNCKEY)
 Values
   (3, 'External', TO_TIMESTAMP('02/12/2016 02:23:49.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'EXT');
Insert into DIM_ORIGINE
   (ORI_PK, ORI_NAME, ORI_DATETIME_LOAD, ORI_FUNCKEY)
 Values
   (1, 'Informatica Data Quality Scorecards', TO_TIMESTAMP('02/12/2016 02:23:49.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'IDQSC');
Insert into DIM_ORIGINE
   (ORI_PK, ORI_NAME, ORI_DATETIME_LOAD, ORI_FUNCKEY)
 Values
   (4, 'Informatica 10.1', TO_TIMESTAMP('26/01/2017 08:29:14.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'Informatica 10.1');
COMMIT;
