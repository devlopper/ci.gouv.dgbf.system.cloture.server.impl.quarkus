-- Liste des verrous - un verrou actif est un verrou où la date de fin est nulle
CREATE OR REPLACE VIEW VA_VERROU AS
SELECT v.id_workflow_verrou AS "IDENTIFIANT",a.identifiant AS "ACTE_IDENTIFIANT",v.id_pk_table_cible AS "ACTE_REFERENCE",a.type AS "ACTE_TYPE",v.id_workflow_verrou_type AS "TYPE"
,v.id_workflow_verrou_type AS "MOTIF",CASE WHEN v.date_fin IS NULL THEN 1 ELSE 0 END AS "ACTIF"
FROM t_workflow_verrou@dblink_elabo_bidf v
JOIN VMA_ACTE a ON a.reference = v.id_pk_table_cible AND 'T_'||a.type = v.id_table_cible;

CREATE OR REPLACE VIEW VA_ACTE_DERNIERE_OPERATION
AS SELECT acte
    ,MAX(identifiant) keep (dense_rank last order by operation_date) AS "IDENTIFIANT"
    ,MAX(operation) keep (dense_rank last order by operation_date) AS "OPERATION"
    ,MAX(operation_date) AS "OPERATION_DATE"
    ,MAX(declencheur) keep (dense_rank last order by operation_date) AS "DECLENCHEUR"
FROM  TA_ACTE_OPERATION
GROUP BY acte;

-- Liste des actes et leurs verrous
CREATE OR REPLACE VIEW VA_ACTE_STATUT AS
SELECT a.identifiant AS "IDENTIFIANT",COUNT(v.id_workflow_verrou) AS "NOMBRE_VERROU",SUM(CASE WHEN v.date_fin IS NULL THEN 1 ELSE 0 END) AS "NOMBRE_VERROU_ACTIF"
FROM VA_ACTE@dblink_elabo_bidf a
LEFT JOIN T_WORKFLOW_VERROU@dblink_elabo_bidf v ON v.id_pk_table_cible = a.reference AND v.id_table_cible = 'T_'||a.type
WHERE (v.id_workflow_verrou_type LIKE 'CLOTURE_%' OR v.id_workflow_verrou_type LIKE 'CHANGEMENT_%')
GROUP BY a.identifiant
ORDER BY a.identifiant;