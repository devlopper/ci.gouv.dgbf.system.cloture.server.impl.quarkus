------------------------------------------------------------------------------------------------
--- Ces vues permettent de d'accélerer l'accès aux données à travers le lien base de données ---
------------------------------------------------------------------------------------------------

-- Exercice
CREATE OR REPLACE VIEW VA_EXERCICE AS
SELECT DISTINCT TO_CHAR(e.id_exercice) AS "IDENTIFIANT",e.r_exercice AS "CODE",e.l_exercice AS "LIBELLE",e.ordre AS "ANNEE"
FROM t_exercice@dblink_bidf_exe e
JOIN t_budget@dblink_bidf_exe b ON b.id_exercice = e.id_exercice
JOIN t_ligne@dblink_bidf_exe l ON l.id_budget = b.id_budget
JOIN t_ligne_detail@dblink_bidf_exe ld ON ld.id_ligne = l.id_ligne
JOIN t_depense@dblink_bidf_exe d ON d.id_ligne_detail = ld.id_ligne_detail
JOIN t_engagement_detail@dblink_bidf_exe ed ON ed.id_depense = d.id_depense
JOIN t_engagement@dblink_bidf_exe e ON e.id_engagement = ed.id_engagement;

-- Liste des actes
CREATE OR REPLACE VIEW "VA_ACTE" AS
SELECT a.r_engagement AS "IDENTIFIANT",a.r_engagement AS "CODE",a.l_engagement AS "LIBELLE",TO_CHAR(a.id_engagement) AS "REFERENCE", 'ENGAGEMENT' AS "TYPE"
FROM t_engagement@dblink_bidf_exe a
UNION
SELECT a.r_liquidation AS "IDENTIFIANT",a.r_liquidation AS "CODE",a.l_liquidation AS "LIBELLE",TO_CHAR(a.id_liquidation) AS "REFERENCE", 'LIQUIDATION' AS "TYPE"
FROM t_liquidation@dblink_bidf_exe a
UNION
SELECT a.r_mandat AS "IDENTIFIANT",a.r_mandat AS "CODE",a.l_mandat AS "LIBELLE",TO_CHAR(a.id_mandat) AS "REFERENCE", 'MANDAT' AS "TYPE"
FROM t_mandat@dblink_bidf_exe a
UNION
SELECT a.r_regie_paiement AS "IDENTIFIANT",a.r_regie_paiement AS "CODE",a.l_regie_paiement AS "LIBELLE",TO_CHAR(a.id_regie_paiement) AS "REFERENCE", 'ORDRE_PAIEMENT' AS "TYPE"
FROM t_regie_paiement@dblink_bidf_exe a;

-- Liste des verrous
CREATE OR REPLACE VIEW "VA_WORKFLOW_VERROU" AS
SELECT * FROM T_WORKFLOW_VERROU@dblink_bidf_exe;