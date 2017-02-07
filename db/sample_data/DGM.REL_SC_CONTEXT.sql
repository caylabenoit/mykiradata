SET DEFINE OFF;
--SQL Statement which produced this data:
--
--  SELECT * FROM DGM.REL_SC_CONTEXT;
--
Insert into REL_SC_CONTEXT
   (SCO_NAME, CON_DESCRIPTION, SCX_DESCRIPTION, SCX_PK, CHT_FK)
 Values
   ('MDM_Hub_Scorecard', 'TEST 3', 'test 1', 1, NULL);
Insert into REL_SC_CONTEXT
   (SCO_NAME, CON_DESCRIPTION, SCX_DESCRIPTION, SCX_PK, CHT_FK)
 Values
   ('HIN_Individual_Before_and_After', 'TEST 1', 'toto', 2, NULL);
COMMIT;
