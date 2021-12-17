-- Liste des actes
CREATE OR REPLACE VIEW "VA_ACTE" AS
SELECT a.r_engagement AS "IDENTIFIANT",a.r_engagement AS "CODE",a.l_engagement AS "LIBELLE",TO_CHAR(a.id_engagement) AS "REFERENCE", 'ENGAGEMENT' AS "TYPE"
FROM vx_engagement a
UNION
SELECT a.r_liquidation AS "IDENTIFIANT",a.r_liquidation AS "CODE",a.l_liquidation AS "LIBELLE",TO_CHAR(a.id_liquidation) AS "REFERENCE", 'LIQUIDATION' AS "TYPE"
FROM vx_liquidation a
UNION
SELECT a.r_mandat AS "IDENTIFIANT",a.r_mandat AS "CODE",a.l_mandat AS "LIBELLE",TO_CHAR(a.id_mandat) AS "REFERENCE", 'MANDAT' AS "TYPE"
FROM vx_mandat a
UNION
SELECT a.r_regie_paiement AS "IDENTIFIANT",a.r_regie_paiement AS "CODE",a.l_regie_paiement AS "LIBELLE",TO_CHAR(a.id_regie_paiement) AS "REFERENCE", 'REGIE_PAIEMENT' AS "TYPE"
FROM vx_ordre_paiement a;

-- Liste des verrous
CREATE OR REPLACE VIEW "VA_WORKFLOW_VERROU" AS
SELECT * FROM T_WORKFLOW_VERROU@dblink_bidf_exe;