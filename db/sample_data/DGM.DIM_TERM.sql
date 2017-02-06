SET DEFINE OFF;
--SQL Statement which produced this data:
--
--  SELECT * FROM DGM.DIM_TERM;
--
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (2, 'Contract Initial Period', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0ic5BEeWu4gbligZhTg', 
    ' The time period of a contract when the Initial Interest Rate applies. Once the initial period has expired, a contract interest rate reverts from the Initial Rate to the Standard Rate.', ' Retail & Commercial Banking : Used to define the time frame in which the initial interest rate applies.IT : A technical indicator(flag)  in the database that can be used to determine if a specific contract is in the initial or standard period.', NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0ic5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (3, 'Credit Card Number', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0ks5BEeWu4gbligZhTg', 
    ' Credit Card Number of the customer', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0ks5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (4, 'Credit Default Risk', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQNGs5BEeWu4gbligZhTg', 
    ' The risk of loss arising from a debtor being unlikely to pay its loan obligations in full or the debtor is more than 90 days past due on any material credit obligation; default risk may impact all credit-sensitive transactions, including loans, securities and derivatives', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQNGs5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (5, 'Email', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0mM5BEeWu4gbligZhTg', 
    ' The email address of a customer', ' name@domain.com (.net. etc..)', ' customer@yourbank.com', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0mM5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (6, 'First Name', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0ls5BEeWu4gbligZhTg', 
    ' First Name of a customer', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0ls5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (7, 'Initial Rate', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0es5BEeWu4gbligZhTg', 
    ' The rate at which interest is paid by borrowers (debtors) for the use of money that they borrow from lenders (creditors) throughout an introductory time period.The Initial Rate is a rate used to attract borrowers. It is always lower than a standard interest rate. The initial rate is offered for a fixed period of time and upon expiration, the Standard Rate is used to calculate interest.', NULL, ' 4.99', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0es5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (8, 'Interest Rate', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0g85BEeWu4gbligZhTg', 
    ' The rate at which interest is paid by borrowers (debtors) for the use of money that they borrow from lenders (creditors)', NULL, ' 9.99', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0g85BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (9, 'Jupiter', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yRT9WM5BEeWu4gbligZhTg', 
    ' Risk Reporting / Limits Management Application', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 3, 
    'U:yRT9WM5BEeWu4gbligZhTg', 1);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (10, 'Net Stable Funding Ratio', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQNJs5BEeWu4gbligZhTg', 
    NULL, NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQNJs5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (11, 'Account Balance', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0ec5BEeWu4gbligZhTg', 
    ' Financial result of all transactions for a specific account. Balances can be negative, positive or zero.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0ec5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (12, 'Account Identifier', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPibqs5BEeWu4gbligZhTg', 
    ' Uniquely identifies an account. Starts at 50000 and sequentially increases by 1. New Accounts are assigned the next unique number in the sequence.', NULL, ' 50000', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPibqs5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (13, 'Account Status', 'ACTIVE', TO_TIMESTAMP('24/01/2017 08:20:48.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0cs5BEeWu4gbligZhTg', 
    ' The status of an Account. Specific events lead to changes in Account Status. For example a customer purchasing $100 of merchandise with $50 in their account would lead to an ''Insufficient Funds'' Account Status.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0cs5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (14, 'Customer Address', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0gc5BEeWu4gbligZhTg', 
    ' The address of a customer', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0gc5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (15, 'Liquidity Risk', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQ0Q85BEeWu4gbligZhTg', 
    ' The risk that a counterparty (or participant in a settlement system) will not settle an obligation for full value when due. Liquidity risk does not imply that a counterparty or participant is insolvent since it may be able to settle the required debit obligations at some unspecified time thereafter.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQ0Q85BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (16, 'Payment Term', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0is5BEeWu4gbligZhTg', 
    ' Unit of measure for the payment term.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0is5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (17, 'Title', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPibq85BEeWu4gbligZhTg', 
    ' Represents the title of a customer', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPibq85BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (0, 'Unknown', NULL, TO_TIMESTAMP('02/12/2016 02:23:49.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'UNKN', 
    NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, 0, NULL, 
    NULL, NULL);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (1, 'Contract', 'ACTIVE', TO_TIMESTAMP('06/01/2017 07:44:06.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQNFs5BEeWu4gbligZhTg', 
    ' An agreement entered into voluntarily by the Bank and a borrower creating one or more legal obligations between them. The elements of a contract are ', 'This contract is used in any case ...', 'Contract renewal', 'PUBLISHED', 'a.omar', 
    NULL, 'a.omar@infa-server.com', NULL, NULL, 6, 
    'U:yQQNFs5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (18, 'Alpha Scorer', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yRT9Xs5BEeWu4gbligZhTg', 
    ' Credit Risk Engine to calculated Counterparty Risk; supports use in real-time mode for credit scoring and also "batch" mode for portfolio level risk calculation', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 3, 
    'U:yRT9Xs5BEeWu4gbligZhTg', 1);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (19, 'Arrangement Fee', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0lc5BEeWu4gbligZhTg', 
    ' Any fee imposed on the borrower in the process of initiating a Contract.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0lc5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (20, 'Contract Open Date', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0kc5BEeWu4gbligZhTg', 
    ' The date a contract was executed between the bank and a party.', NULL, ' YYYY-MM-DD HH24:MI:SS 2010-08-09 00:00:00', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0kc5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (21, 'Funding Liquidity Risk', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQ0Os5BEeWu4gbligZhTg', 
    ' Funding liquidity risk is driven by the possibility that, over a specific horizon, the bank will become unable to settle obligations when due', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQ0Os5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (22, 'Last Name', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0hc5BEeWu4gbligZhTg', 
    ' The last name of a customer', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0hc5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (23, 'Loss Given Default', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0fs5BEeWu4gbligZhTg', 
    ' LGD is the share of an asset that is lost when a borrower defaults. Loss Given Default is facility-specific because such losses are generally understood to be influenced by key transaction characteristics such as the presence of collateral and the degree of subordination.', NULL, ' If the client defaults, with an outstanding debt of 200,000 (exposure (EAD)) and the bank or insurance is able to sell the security (e.g. condo) for a net price of 160,000 (including costs related to the repurchase), then 40,000, or 20%, of the EAD are lost - the LGD is 20%.', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0fs5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (24, 'Probability of Default', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQ0Oc5BEeWu4gbligZhTg', 
    ' The term describes the likelihood of a default over a particular time horizon. It provides an estimate of the likelihood that a borrower will be unable to meet its debt obligations. Under Basel II, it is a key parameter used in the calculation of economic capital or regulatory capital for a banking institution.PD is the risk that the borrower will be unable or unwilling to repay its debt in full or on time. The risk of default is derived by analyzing the obligor’s capacity to repay the debt in accordance with contractual terms. PD is generally associated with financial characteristics such as inadequate cash flow to service debt, declining revenues or operating margins, high leverage, declining or marginal liquidity, and the inability to successfully implement a business plan. In addition to these quantifiable factors, the borrower’s willingness to repay also must be evaluated.— [Office of the Comptroller of the Currency]', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQ0Oc5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (25, 'Account Currency', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0kM5BEeWu4gbligZhTg', 
    ' The base currency of a financial product the account refers to.', NULL, ' USD', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0kM5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (26, 'Contract Initial End Date', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0h85BEeWu4gbligZhTg', 
    ' The date a contract will expire assuming regular payments are made consistently throughout the life of the contract.Other factors often influence the actual end date such as customers accelerating payments into the principal, refinancing the contract or executing a modification agreement to lower their payments.', NULL, ' YYYY-MM-DD HH24:MI:SS2010-04-01 00:00:00', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0h85BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (27, 'Country Code', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0gs5BEeWu4gbligZhTg', 
    ' Represents the Country Code of a customer.', ' 2 Character ISO code', ' US', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0gs5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (28, 'Exposure Completeness', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yRuMX85BEeWu4gbligZhTg', 
    'Ensure all attributes of Exposure are complete to support the calculations / aggregation processes within the respective application or systems. ', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, 2, 4, 
    'U:yRuMX85BEeWu4gbligZhTg', 4);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (29, 'Market Liquidity Risk', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQNGM5BEeWu4gbligZhTg', 
    ' Market Liquidity Risk is asset illiquidity. This is an inability to easily exit a position.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQNGM5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (30, 'Risk Capital Basel 3', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQ0PM5BEeWu4gbligZhTg', 
    ' Capital on hand necessary as required by the Basel III standard.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQ0PM5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (31, 'Risk Capital Basel II', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0ds5BEeWu4gbligZhTg', 
    ' Capital on-hand necessary to conform to Basel II requirements and guard against the financial and operational risks implicit in entering a contract with a borrower.Designed to ensure that the bank has adequate capital for the risk the bank exposes itself to through its lending and investment practices. Generally speaking, these rules mean that the greater risk to which the bank is exposed, the greater the amount of capital the bank needs to hold to safeguard its solvency and overall economic stability.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0ds5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (32, 'Value at Risk', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQ0Pc5BEeWu4gbligZhTg', 
    ' A statistical technique used to measure and quantify the level of financial risk within a firm or investment portfolio over a specific time frame. Value at risk is used by risk managers in order to measure and control the level of risk which the firm undertakes. The risk manager''s job is to ensure that risks are not taken beyond the level at which the firm can absorb the losses of a probable worst outcome.', NULL, 'Value at Risk is measured in three variables: the amount of potential loss, the probability of that amount of loss, and the time frame. For example, a financial firm may determine that it has a 5% one month value at risk of $100 million. This means that there is a 5% chance that the firm could lose more than $100 million in any given month. Therefore, a $100 million loss should be expected to occur once every 20 months.', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQ0Pc5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (33, 'Account', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQNHc5BEeWu4gbligZhTg', 
    ' A financial relationship between a bank customer and a financial institution.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQNHc5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (34, 'Collateral Required', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0jM5BEeWu4gbligZhTg', 
    ' Indicator as to whether or not a contract requires collateral as security.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0jM5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (35, 'Contract Currency', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0hs5BEeWu4gbligZhTg', 
    ' The currency in which a contract was originally executed.The default currency based upon the Currency from a customer account. A customer might have multiple accounts based in different countries. Each account would have it''s own currency and any contracts executed under that account would default to the account currency.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0hs5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (36, 'Contract ID', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0f85BEeWu4gbligZhTg', 
    ' The internal contract identifier. This is not meant to be external and is used within IT only as a database identifier.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0f85BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (37, 'Death Date', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0k85BEeWu4gbligZhTg', 
    ' Death Date of a customer', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0k85BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (38, 'Delta', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQ0O85BEeWu4gbligZhTg', 
    ' A risk measure representing the rate of change between the option''s price and the underlying asset''s price - in other words, price sensitivity.', NULL, ' For example, with respect to call options, a delta of 0.7 means that for every $1 the underlying stock increases, the call option will increase by $0.70.  Put option deltas, on the other hand, will be negative, because as the underlying security increases, the value of the option will decrease. So a put option with a delta of -0.7 will decrease by $0.70 for every $1 the underlying increases in price. As an in-the-money call option nears expiration, it will approach a delta of 1.00, and as an in-the-money put option nears expiration, it will approach a delta of -1.00.', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQ0O85BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (39, 'Operational Risk', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQNHs5BEeWu4gbligZhTg', 
    ' The risk that deficiencies in information systems or internal controls could result in unexpected losses.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQNHs5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (40, 'Account Branch Code', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0e85BEeWu4gbligZhTg', 
    ' Code for the retail branch where an account was opened.', NULL, ' 123', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0e85BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (41, 'Account Open Date', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0iM5BEeWu4gbligZhTg', 
    ' The date an account was opened.', NULL, ' Jan 1, 2000 12:00:00 AM', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0iM5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (42, 'Exposure', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQ0Rs5BEeWu4gbligZhTg', 
    ' The amount of funds or percentage of a portfolio an investor can lose from the risks related to one of the categories of risk: Credit, Market, etc.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQ0Rs5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (43, 'Payment Due Day', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0dc5BEeWu4gbligZhTg', 
    ' The day of the month when a payment is due for the contract.', NULL, ' 2 indicates that the regular payment is due on the 2nd day of every month for the duration of the contract.', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0dc5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (44, 'Term', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0fM5BEeWu4gbligZhTg', 
    ' Period of time in which a contract is in effect. Term is measured from the point of contract origination.', NULL, ' 24', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0fM5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (45, 'Zipcode', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPibrM5BEeWu4gbligZhTg', 
    ' Represents the zipcode or postal code of a customer', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPibrM5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (46, 'Account Name', 'ACTIVE', TO_TIMESTAMP('24/01/2017 10:18:47.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0d85BEeWu4gbligZhTg', 
    ' Name of an account. Corporate standard isÂ Â Account', NULL, ' Mersche Account - 30 Year Fixed Mortgage', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0d85BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (47, 'Account Number', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0j85BEeWu4gbligZhTg', 
    ' A unique, external facing identifier. The Account Number is used as the account identifier when processing payments and processing deposits or withdrawals.We are adopting the European Union International Bank Account Number (IBAN) structure for Account Numbers.', NULL, ' GB19AIBK895912141142', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0j85BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (48, 'Apollo', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yRT9W85BEeWu4gbligZhTg', 
    ' Credit Risk System used by Wealth Management', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 3, 
    'U:yRT9W85BEeWu4gbligZhTg', 1);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (49, 'Birth Date', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0dM5BEeWu4gbligZhTg', 
    ' Birth Date of a Customer', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0dM5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (50, 'Branch Code', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0l85BEeWu4gbligZhTg', 
    ' A specific retail bank branch', ' 4 digit numeric code', ' 1234', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0l85BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (51, 'Contract Face Value', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPibrs5BEeWu4gbligZhTg', 
    ' Original value from the Bank perspective of the contract.', ' Mortgage : Total amount borrowed at contract originationBond : Redemption Value upon reaching maturityStock : Par value of the stock', NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPibrs5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (52, 'Credit Risk', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQNEs5BEeWu4gbligZhTg', 
    ' Credit risk refers to the risk that a borrower will default on any type of debt by failing to make payments which it is obligated to do. The risk is primarily that of the lender and include lost principal and interest, disruption to cash flows, and increased collection costs.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQNEs5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (53, 'Risk Weighted Asset', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQNJM5BEeWu4gbligZhTg', 
    ' Risk-weighted asset is a bank''s assets or off-balance-sheet exposures, weighted according to risk.[1] This sort of asset calculation is used in determining the capital requirement or Capital Adequacy Ratio (CAR) for a financial institution. In the Basel I accord published by the Basel Committee on Banking Supervision, the Committee explains why using a risk-weight approach is the preferred methodology which banks should adopt for capital calculation.it provides an easier approach to compare banks across different geographiesoff-balance-sheet exposures can be easily included in capital adequacy calculationsbanks are not deterred from carrying low risk liquid assets in their booksUsually, different classes of assets have different risk weights associated with them. The calculation of risk weights is dependent on whether the bank has adopted the standardized or IRB approach under the Basel II framework.[3]Some assets, such as debentures, are assigned a higher risk than others, such as cash or government securities/bonds. Since different types of assets have different risk profiles, weighting assets according to their level of risk primarily adjusts for assets that are less risky by allowing banks to discount lower-risk assets. In the most basic application, government debt is allowed a 0% "risk weighting" - that is, they are subtracted from total assets for purposes of calculating the CAR.A document was written in 1988 by the Basel Committee on Banking Supervision which recommends certain standards and regulations for banks. This was called Basel I, and the Committee came out with a revised framework known as Basel II. More recently, the committee has published another revised framework known as Basel III.  The main recommendation of this document is that banks should hold enough capital to equal at least 8% of its risk-weighted assets.[6] The calculation of the amount of risk-weighted assets depends on which revision of the Basel Accord is being followed by the financial institution. Most countries have implemented some version of this regulation.', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQNJM5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (54, 'Standard Rate', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0hM5BEeWu4gbligZhTg', 
    ' The rate at which interest is paid by borrowers (debtors) for the use of money that they borrow from lenders (creditors) after the introductory time period has expired.The Standard Rate is a rate used once the Initial Rate has expired. It is always higher than the initial interest rate.', NULL, ' 9.99', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0hM5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (55, 'Contract Number', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0eM5BEeWu4gbligZhTg', 
    ' Unique integer identifying a contract between the bank and a customer. This is an external that customers will use as reference when contacting the bank regarding their contract.', NULL, ' 95129', 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0eM5BEeWu4gbligZhTg', 3);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (56, 'Market Risk', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yQQNGc5BEeWu4gbligZhTg', 
    ' The risk of losses in on - and off - balance sheet positions arising   from movements in market prices', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 6, 
    'U:yQQNGc5BEeWu4gbligZhTg', 2);
Insert into DIM_TERM
   (TRM_PK, TRM_NAME, TRM_STATUS, TRM_DATETIME_LOAD, TRM_FUNCKEY, 
    TRM_DESCRIPTION, TRM_USAGE, TRM_EXAMPLE, TRM_PHASE, TRM_OWNER, 
    TRM_STEWARD, TRM_OWNER_EMAIL, TRM_STEWARD_EMAIL, CAT_FK, GLO_FK, 
    OBJECT_ID, TRT_FK)
 Values
   (57, 'Telephone Number', 'ACTIVE', TO_TIMESTAMP('02/01/2016 02:24:33.000000','DD/MM/YYYY HH24:MI:SS.FF'), 'U:yPh0gM5BEeWu4gbligZhTg', 
    ' Primary telephone number for a customer', NULL, NULL, 'PUBLISHED', NULL, 
    NULL, NULL, NULL, NULL, 1, 
    'U:yPh0gM5BEeWu4gbligZhTg', 3);
COMMIT;
