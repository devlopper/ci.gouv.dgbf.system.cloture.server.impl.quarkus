DELETE FROM AT_MV_SCRIPT;
DELETE FROM AT_MV;
DELETE FROM AT_TABLE_LOG;

-----------------------------------------------------------------------------------------------------------------------------------------------
-- AT_MV
-----------------------------------------------------------------------------------------------------------------------------------------------
Insert into AT_MV (NAME,ORDER_NUMBER,ENABLED,QUERY,DELETABLE_COLUMN,DELETABLE_MARKER) values ('VMA_EXERCICE',1,1,'SELECT * FROM UT_BIDF_TAMP.VA_EXERCICE@dblink_elabo_bidf t','CODE','DELETED');
Insert into AT_MV (NAME,ORDER_NUMBER,ENABLED,QUERY,DELETABLE_COLUMN,DELETABLE_MARKER) values ('VMA_TYPE_ACTE',1,1,'SELECT * FROM UT_BIDF_TAMP.VA_TYPE_ACTE@dblink_elabo_bidf t','CODE','DELETED');
Insert into AT_MV (NAME,ORDER_NUMBER,ENABLED,QUERY,DELETABLE_COLUMN,DELETABLE_MARKER) values ('VMA_ACTE',1,1,'SELECT * FROM UT_BIDF_TAMP.VA_ACTE@dblink_elabo_bidf t','CODE','DELETED');
Insert into AT_MV (NAME,ORDER_NUMBER,ENABLED,QUERY,DELETABLE_COLUMN,DELETABLE_MARKER) values ('VMA_ACTIVITE',1,1,'SELECT * FROM UT_BIDF_TAMP.VA_ACTIVITE@dblink_elabo_bidf t','CODE','DELETED');
Insert into AT_MV (NAME,ORDER_NUMBER,ENABLED,QUERY,DELETABLE_COLUMN,DELETABLE_MARKER) values ('VMA_NATURE_ECONOMIQUE',1,1,'SELECT * FROM UT_BIDF_TAMP.VA_NATURE_ECONOMIQUE@dblink_elabo_bidf t','CODE','DELETED');
Insert into AT_MV (NAME,ORDER_NUMBER,ENABLED,QUERY,DELETABLE_COLUMN,DELETABLE_MARKER) values ('VMA_IMPUTATION',1,1,'SELECT * FROM UT_BIDF_TAMP.VA_IMPUTATION@dblink_elabo_bidf t','EXERCICE','DELETED');

-----------------------------------------------------------------------------------------------------------------------------------------------
-- AT_MV_SCRIPT
-----------------------------------------------------------------------------------------------------------------------------------------------
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_EXERCICE','ALTER TABLE VMA_EXERCICE ADD CONSTRAINT VMA_EXERCICE_PK PRIMARY KEY (IDENTIFIANT)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_EXERCICE','CREATE INDEX VMA_EXERCICE_K_CODE ON VMA_EXERCICE (CODE ASC)',null,1);

Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_TYPE_ACTE','ALTER TABLE VMA_TYPE_ACTE ADD CONSTRAINT VMA_TYPE_ACTE_PK PRIMARY KEY (IDENTIFIANT)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_TYPE_ACTE','CREATE INDEX VMA_TYPE_ACTE_K_CODE ON VMA_TYPE_ACTE (CODE ASC)',null,1);

Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_ACTE','ALTER TABLE VMA_ACTE ADD CONSTRAINT VMA_ACTE_PK PRIMARY KEY (IDENTIFIANT)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_ACTE','CREATE INDEX VMA_ACTE_K_CODE ON VMA_ACTE (CODE ASC)',null,1);

Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_ACTIVITE','ALTER TABLE VMA_ACTIVITE ADD CONSTRAINT VMA_ACTIVITE_PK PRIMARY KEY (IDENTIFIANT)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_ACTIVITE','CREATE INDEX VMA_ACTIVITE_K_CODE ON VMA_ACTIVITE (CODE ASC)',null,1);

Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_NATURE_ECONOMIQUE','ALTER TABLE VMA_NATURE_ECONOMIQUE ADD CONSTRAINT VMA_NATURE_ECONOMIQUE_PK PRIMARY KEY (IDENTIFIANT)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_NATURE_ECONOMIQUE','CREATE INDEX VMA_NE_K_CODE ON VMA_NATURE_ECONOMIQUE (CODE ASC)',null,1);

Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_IMPUTATION','ALTER TABLE VMA_IMPUTATION ADD CONSTRAINT VMA_IMPUTATION_PK PRIMARY KEY (IDENTIFIANT)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_IMPUTATION','CREATE INDEX VMA_IMPUTATION_K_EXERCICE ON VMA_IMPUTATION (CODE ASC)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_IMPUTATION','CREATE INDEX VMA_IMPUTATION_K_ACTIVITE ON VMA_IMPUTATION (CODE ASC)',null,1);
Insert into AT_MV_SCRIPT (MV,SCRIPT,ORDER_NUMBER,ENABLED) values ('VMA_IMPUTATION','CREATE INDEX VMA_IMPUTATION_K_NE ON VMA_IMPUTATION (CODE ASC)',null,1);

COMMIT;

BEGIN
	AP_CREATE_ALL_MV();
END;
/

CREATE OR REPLACE PROCEDURE AP_ACTUALIZE_MV_CALD_FRM_JOB AUTHID CURRENT_USER AS
BEGIN
    AP_ACTUALIZE_MV('VMA_ACTE');
    AP_ACTUALIZE_MV('VMA_ACTIVITE');
	AP_ACTUALIZE_MV('VMA_EXERCICE');
	AP_ACTUALIZE_MV('VMA_TYPE_ACTE');
	AP_ACTUALIZE_MV('VMA_IMPUTATION');
	AP_ACTUALIZE_MV('VMA_NATURE_ECONOMIQUE');
END;
/

BEGIN
    DBMS_SCHEDULER.enable(name => '"AJ_ACTUALIZE_MV"');
END;
/

-- Liste des imputations à déverouiller et à déverser
CREATE OR REPLACE VIEW VA_IPTT_A_DVRL_A_DVS AS
SELECT i.exercice_annee AS "EXO_NUM",i.ldep_id AS "LDEP_ID",i.r_ligne AS "R_LIGNE",CAST (SYSDATE AS TIMESTAMP) AS "DATE_DEBUT_OUVERTURE_EXCEPTION",NULL AS "DATE_FIN_OUVERTURE_EXCEPTION"
,'NOUVO' AS "ETAT",o.motif AS "COMMENTAIRE_ETAT",CAST (SYSDATE AS TIMESTAMP) AS "DATE_ETAT",o.identifiant AS "OPERATION",o.type AS "TYPE_OPERATION",oi.identifiant AS "IMPUTATION"
FROM TA_OPERATION_IMPUTATION oi
JOIN TA_OPERATION o ON o.identifiant = oi.operation AND o.type = 'DEVERROUILLAGE'
LEFT JOIN VMA_IMPUTATION i ON i.identifiant = oi.imputation
WHERE (oi.traite IS NULL OR oi.traite = 0) AND i.exercice_annee||i.ldep_id NOT IN (SELECT los.exo_num||los.ldep_id FROM LIGNE_OUVERTURE_SPECIALE@dblink_elabo_bidf los);
COMMENT ON TABLE VA_IPTT_A_DVRL_A_DVS IS 'Liste des imputations à déverouiller et à déverser'; 

-- Liste des imputations à verouiller et à déverser
CREATE OR REPLACE VIEW VA_IPTT_A_VRL_A_DVS AS
SELECT i.exercice_annee AS "EXO_NUM",i.ldep_id AS "LDEP_ID",i.r_ligne AS "R_LIGNE",los.date_debut_ouverture_exception,CAST (SYSDATE AS TIMESTAMP) AS "DATE_FIN_OUVERTURE_EXCEPTION"
,'NOUVO' AS "ETAT",o.motif AS "COMMENTAIRE_ETAT",CAST (SYSDATE AS TIMESTAMP) AS "DATE_ETAT",o.identifiant AS "OPERATION",o.type AS "TYPE_OPERATION",oi.identifiant AS "IMPUTATION"
FROM TA_OPERATION_IMPUTATION oi
JOIN TA_OPERATION o ON o.identifiant = oi.operation AND o.type = 'VERROUILLAGE'
JOIN VMA_IMPUTATION i ON i.identifiant = oi.imputation
JOIN LIGNE_OUVERTURE_SPECIALE@dblink_elabo_bidf los ON los.exo_num = i.exercice_annee AND los.ldep_id = i.ldep_id AND los.etat = 'PRCH'
WHERE (oi.traite IS NULL OR oi.traite = 0);
COMMENT ON TABLE VA_IPTT_A_VRL_A_DVS IS 'Liste des imputations à verouiller et à déverser'; 

-- Liste des imputations à déverser
CREATE OR REPLACE VIEW VA_IMPUTATION_A_DEVERSER AS
SELECT * FROM VA_IPTT_A_DVRL_A_DVS iad
UNION
SELECT * FROM VA_IPTT_A_VRL_A_DVS iav;
COMMENT ON TABLE VA_IMPUTATION_A_DEVERSER IS 'Liste des imputations à déverser'; 

CREATE OR REPLACE PROCEDURE PA_EXECUTER_OPERATION(identifiant IN VARCHAR2) AUTHID CURRENT_USER AS
BEGIN
	DBMS_OUTPUT.PUT_LINE('Exécution de l''opération <<'||identifiant||'>> en cours');
    FOR t IN (SELECT exo_num,ldep_id,r_ligne,date_debut_ouverture_exception,date_fin_ouverture_exception,etat,commentaire_etat,date_etat,type_operation,imputation FROM VA_IMPUTATION_A_DEVERSER WHERE operation = identifiant)
    	LOOP
    		IF t.type_operation = 'DEVERROUILLAGE' THEN
    			INSERT INTO ligne_ouverture_speciale@dblink_elabo_bidf(exo_num,ldep_id,r_ligne,date_debut_ouverture_exception,date_fin_ouverture_exception,etat,commentaire_etat,date_etat) VALUES(t.exo_num,t.ldep_id,t.r_ligne,t.date_debut_ouverture_exception,t.date_fin_ouverture_exception,t.etat,t.commentaire_etat,t.date_etat);
    			--DBMS_OUTPUT.PUT_LINE('INSERT de <<'||t.imputation||'>> dans la table ligne_ouverture_speciale du BIDF');
    		ELSE
    			UPDATE ligne_ouverture_speciale@dblink_elabo_bidf SET date_fin_ouverture_exception = t.date_fin_ouverture_exception WHERE exo_num = t.exo_num AND ldep_id = t.ldep_id;
    			--DBMS_OUTPUT.PUT_LINE('UPDATE de <<'||t.imputation||'>> dans la table ligne_ouverture_speciale du BIDF');
    		END IF;
    	    -- dbms_lock.SLEEP(1);
    	END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('L''opération <<'||identifiant||'>> a été exécutée');
END;
/

CREATE OR REPLACE PROCEDURE PA_DEVERROUILLER(actes_identifiants IN VARCHAR2,imputation_identifiants IN VARCHAR2) AUTHID CURRENT_USER AS
BEGIN
    FOR t IN (SELECT REGEXP_SUBSTR(actes_identifiants, '[^,]+', 1, level) AS parts FROM dual CONNECT BY REGEXP_SUBSTR(actes_identifiants, '[^,]+', 1, level) IS NOT NULL)
    	LOOP
    	    DBMS_OUTPUT.PUT_LINE('DEVERROUILLER '||t.parts);
    	    -- dbms_lock.SLEEP(1);
    	END LOOP;
END;
/

CREATE OR REPLACE PROCEDURE PA_VERROUILLER(actes_identifiants IN VARCHAR2,imputation_identifiants IN VARCHAR2) AUTHID CURRENT_USER AS
BEGIN
    FOR t IN (SELECT REGEXP_SUBSTR(actes_identifiants, '[^,]+', 1, level) AS parts FROM dual CONNECT BY REGEXP_SUBSTR(actes_identifiants, '[^,]+', 1, level) IS NOT NULL)
    	LOOP
    	    DBMS_OUTPUT.PUT_LINE('VERROUILLER '||t.parts);
    	    -- dbms_lock.SLEEP(1);
    	END LOOP;
END;
/