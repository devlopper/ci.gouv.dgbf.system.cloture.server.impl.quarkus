-- Act
CREATE OR REPLACE VIEW "VA_ACTE" AS
SELECT a.r_engagement AS "IDENTIFIANT",a.r_engagement AS "CODE",a.l_engagement AS "LIBELLE"
FROM vx_engagement@dblink_elabo_bidf a
UNION
SELECT a.r_liquidation AS "IDENTIFIANT",a.r_liquidation AS "CODE",a.l_liquidation AS "LIBELLE"
FROM vx_liquidation@dblink_elabo_bidf a
UNION
SELECT a.r_mandat AS "IDENTIFIANT",a.r_mandat AS "CODE",a.l_mandat AS "LIBELLE"
FROM vx_mandat@dblink_elabo_bidf a
UNION
SELECT a.r_regie_paiement AS "IDENTIFIANT",a.r_regie_paiement AS "CODE",a.l_regie_paiement AS "LIBELLE"
FROM vx_ordre_paiement@dblink_elabo_bidf a

-- Acte : Dernière opération
CREATE OR REPLACE VIEW "VA_ACTE_DERNIERE_OPERATION" AS
SELECT acte
    ,MAX(identifiant) keep (dense_rank last order by operation_date) AS "IDENTIFIANT"
    ,MAX(operation) keep (dense_rank last order by operation_date) AS "OPERATION"
    ,MAX(operation_date) AS "OPERATION_DATE"
    ,MAX(declencheur) keep (dense_rank last order by operation_date) AS "DECLENCHEUR"
FROM  TA_ACTE_OPERATION
GROUP BY acte

-- Liste des actes clos sur BIDF
CREATE OR REPLACE VIEW VA_ACTE_CLOS_REFERENCE
AS SELECT a.r_engagement AS "REFERENCE"
FROM t_workflow_verrou@dblink_bidf_exe v
INNER JOIN t_engagement@dblink_bidf_exe a ON (v.id_pk_table_cible = a.id_engagement AND v.id_table_cible = 'T_ENGAGEMENT')
WHERE v.date_fin IS NOT NULL AND v.id_workflow_verrou_type LIKE 'CLOTURE_%'
UNION
-- Liste des liquidations ayant un verrou de type cloture
SELECT a.r_liquidation AS "REFERENCE"
FROM t_workflow_verrou@dblink_bidf_exe v
INNER JOIN t_liquidation@dblink_bidf_exe a ON (v.id_pk_table_cible = a.id_liquidation AND v.id_table_cible = 'T_LIQUIDATION')
WHERE v.date_fin IS NOT NULL AND v.id_workflow_verrou_type LIKE 'CLOTURE_%'
UNION
-- Liste des mandats ayant un verrou de type cloture
SELECT a.r_mandat AS "REFERENCE"
FROM t_workflow_verrou@dblink_bidf_exe v
INNER JOIN t_mandat@dblink_bidf_exe a ON (v.id_pk_table_cible = a.id_mandat AND v.id_table_cible = 'T_MANDAT')
WHERE v.date_fin IS NOT NULL AND v.id_workflow_verrou_type LIKE 'CLOTURE_%'
UNION
-- Liste des liquidations ayant un verrou de type cloture
SELECT a.r_regie_paiement AS "REFERENCE"
FROM t_workflow_verrou@dblink_bidf_exe v
INNER JOIN t_regie_paiement@dblink_bidf_exe a ON (v.id_pk_table_cible = a.id_regie_paiement AND v.id_table_cible = 'T_REGIE_PAIEMENT')
WHERE v.date_fin IS NOT NULL AND v.id_workflow_verrou_type LIKE 'CLOTURE_%'
UNION
-- Liste des avances ayant un verrou de type cloture
SELECT a.r_avance AS "REFERENCE"
FROM t_workflow_verrou@dblink_bidf_exe v
INNER JOIN t_avance@dblink_bidf_exe a ON (v.id_pk_table_cible = a.id_avance AND v.id_table_cible = 'T_AVANCE')
WHERE v.date_fin IS NOT NULL AND v.id_workflow_verrou_type LIKE 'CLOTURE_%';
