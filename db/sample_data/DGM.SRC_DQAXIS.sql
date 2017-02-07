SET DEFINE OFF;
--SQL Statement which produced this data:
--
--  SELECT * FROM DGM.SRC_DQAXIS;
--
Insert into SRC_DQAXIS
   (DQX_FUNCKEY, DQX_LABEL, DQX_DESCRIPTION, DQX_STATUS, DQX_PK, 
    DQX_WEIGHT)
 Values
   ('ACCY', 'Accuracy', 'Accuracy', NULL, 1, 
    2);
Insert into SRC_DQAXIS
   (DQX_FUNCKEY, DQX_LABEL, DQX_DESCRIPTION, DQX_STATUS, DQX_PK, 
    DQX_WEIGHT)
 Values
   ('COMPL', 'Completness', 'Completness', NULL, 2, 
    6);
Insert into SRC_DQAXIS
   (DQX_FUNCKEY, DQX_LABEL, DQX_DESCRIPTION, DQX_STATUS, DQX_PK, 
    DQX_WEIGHT)
 Values
   ('TIME', 'Timeless', 'Timeless', NULL, 3, 
    1);
Insert into SRC_DQAXIS
   (DQX_FUNCKEY, DQX_LABEL, DQX_DESCRIPTION, DQX_STATUS, DQX_PK, 
    DQX_WEIGHT)
 Values
   ('PREC', 'Precision', 'Precision', NULL, 4, 
    1);
Insert into SRC_DQAXIS
   (DQX_FUNCKEY, DQX_LABEL, DQX_DESCRIPTION, DQX_STATUS, DQX_PK, 
    DQX_WEIGHT)
 Values
   ('INTEG', 'Integrity', 'Integrity', NULL, 5, 
    3);
COMMIT;
