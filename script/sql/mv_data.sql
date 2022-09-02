DELETE FROM AT_MV_SCRIPT;
DELETE FROM AT_MV;
DELETE FROM AT_TABLE_LOG;

-----------------------------------------------------------------------------------------------------------------------------------------------
-- AT_MV
-----------------------------------------------------------------------------------------------------------------------------------------------
Insert into AT_MV (NAME,ORDER_NUMBER,ENABLED,QUERY,DELETABLE_COLUMN,DELETABLE_MARKER) values ('VMA_EXERCICE',1,1,'SELECT * FROM UT_BIDF_TAMP.VA_EXERCICE@dblink_elabo_bidf t','CODE','DELETED');
Insert into AT_MV (NAME,ORDER_NUMBER,ENABLED,QUERY,DELETABLE_COLUMN,DELETABLE_MARKER) values ('VMA_TYPE_ACTE',1,1,'SELECT * FROM UT_BIDF_TAMP.VA_TYPE_ACTE@dblink_elabo_bidf t','CODE','DELETED');
Insert into AT_MV (NAME,ORDER_NUMBER,ENABLED,QUERY,DELETABLE_COLUMN,DELETABLE_MARKER) values ('VMA_ACTE',1,1,'SELECT * FROM UT_BIDF_TAMP.VA_ACTE@dblink_elabo_bidf t','CODE','DELETED');

-----------------------------------------------------------------------------------------------------------------------------------------------
-- AT_MV_SCRIPT
-----------------------------------------------------------------------------------------------------------------------------------------------
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_EXERCICE','ALTER TABLE VMA_EXERCICE ADD CONSTRAINT VMA_EXERCICE_PK PRIMARY KEY (IDENTIFIANT)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_EXERCICE','CREATE INDEX VMA_EXERCICE_K_CODE ON VMA_EXERCICE (CODE ASC)',null,1);

Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_TYPE_ACTE','ALTER TABLE VMA_TYPE_ACTE ADD CONSTRAINT VMA_TYPE_ACTE_PK PRIMARY KEY (IDENTIFIANT)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_TYPE_ACTE','CREATE INDEX VMA_TYPE_ACTE_K_CODE ON VMA_TYPE_ACTE (CODE ASC)',null,1);

Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_ACTE','ALTER TABLE VMA_ACTE ADD CONSTRAINT VMA_ACTE_PK PRIMARY KEY (IDENTIFIANT)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_ACTE','CREATE INDEX VMA_ACTE_K_CODE ON VMA_ACTE (CODE ASC)',null,1);

COMMIT;

BEGIN
	AP_CREATE_ALL_MV();
END;
/

CREATE OR REPLACE PROCEDURE AP_ACTUALIZE_MV_CALD_FRM_JOB AUTHID CURRENT_USER AS
BEGIN
    AP_ACTUALIZE_MV('VMA_ACTE');
	AP_ACTUALIZE_MV('VMA_EXERCICE');
	AP_ACTUALIZE_MV('VMA_TYPE_ACTE');
END;
/

BEGIN
    DBMS_SCHEDULER.enable(name => '"AJ_ACTUALIZE_MV"');
END;
/

CREATE OR REPLACE PROCEDURE PA_VERROUILLER(identifiants IN VARCHAR2) AUTHID CURRENT_USER AS
BEGIN
    FOR t IN (SELECT REGEXP_SUBSTR(identifiants, '[^,]+', 1, level) AS parts FROM dual CONNECT BY REGEXP_SUBSTR(identifiants, '[^,]+', 1, level) IS NOT NULL)
    	LOOP
    	    DBMS_OUTPUT.PUT_LINE('VERROUILLER '||t.parts);
    	END LOOP;
END;
/

CREATE OR REPLACE PROCEDURE PA_DEVERROUILLER(identifiants IN VARCHAR2) AUTHID CURRENT_USER AS
BEGIN
    FOR t IN (SELECT REGEXP_SUBSTR(identifiants, '[^,]+', 1, level) AS parts FROM dual CONNECT BY REGEXP_SUBSTR(identifiants, '[^,]+', 1, level) IS NOT NULL)
    	LOOP
    	    DBMS_OUTPUT.PUT_LINE('DEVERROUILLER '||t.parts);
    	END LOOP;
END;
/