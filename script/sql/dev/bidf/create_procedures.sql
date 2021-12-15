CREATE OR REPLACE PROCEDURE PA_DEVERROUILLER_ACTE (IDENTIFIANT IN VARCHAR2,TYPE_VERROU IN VARCHAR2,TABLE_CIBLE IN VARCHAR2) AS V_SQL VARCHAR2(4000);
BEGIN
    V_SQL := 'UPDATE t_workflow_verrou@dblink_bidf_exe SET date_fin = SYSDATE WHERE id_pk_table_cible IN (:1) AND id_workflow_verrou_type LIKE :2 AND id_table_cible = :3 AND date_fin IS NULL';
    EXECUTE IMMEDIATE V_SQL USING IDENTIFIANT,TYPE_VERROU,TABLE_CIBLE;
    COMMIT;
END PA_DEVERROUILLER_ACTE;

CREATE OR REPLACE PROCEDURE PA_VERROUILLER_ACTE (IDENTIFIANT IN VARCHAR2,TYPE_VERROU IN VARCHAR2,TABLE_CIBLE IN VARCHAR2) AS V_SQL VARCHAR2(4000);
BEGIN
    V_SQL := 'UPDATE t_workflow_verrou@dblink_bidf_exe SET date_fin = NULL WHERE id_pk_table_cible IN (:1) AND id_workflow_verrou_type LIKE :2 AND id_table_cible = :3 AND date_fin IS NOT NULL';
    EXECUTE IMMEDIATE V_SQL USING IDENTIFIANT,TYPE_VERROU,TABLE_CIBLE;
    COMMIT;
END PA_VERROUILLER_ACTE;