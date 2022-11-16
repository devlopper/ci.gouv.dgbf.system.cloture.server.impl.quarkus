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

-- Liste des activités
CREATE OR REPLACE VIEW VA_ACTIVITE AS
SELECT ads.ads_id AS "IDENTIFIANT",ads.ads_code AS "CODE",ads.ads_liblg AS "LIBELLE",localite.loc_id AS "SOUS_PREFECTURE",nature_depenses.ndep_id AS "NATURE_DEPENSE",action.adp_id AS "ACTION"
,unite_spec_bud.usb_id AS "UNITE_SPECIALISATION_BUDGET",section_budgetaire.secb_id AS "SECTION",unite_administrative.ua_id AS "UNITE_ADMINISTRATIVE",categorie_activite.catv_id AS "CATEGORIE_ACTIVITE"
,categorie_budget.uuid AS "CATEGORIE_BUDGET"
FROM activite_de_service ads
LEFT JOIN localite ON localite.loc_id = ads.loc_id
LEFT JOIN nature_depenses ON nature_depenses.ndep_id = ads.ndep_id
LEFT JOIN action ON action.adp_id = ads.adp_id
LEFT JOIN unite_spec_bud ON unite_spec_bud.usb_id = action.usb_id
LEFT JOIN section_budgetaire ON section_budgetaire.secb_id = unite_spec_bud.secb_id
LEFT JOIN unite_administrative ON unite_administrative.ua_id = ads.ua_id
LEFT JOIN categorie_activite ON categorie_activite.catv_id = ads.catv_id
LEFT JOIN categorie_budget ON categorie_budget.uuid = unite_spec_bud.cbud_id
ORDER BY ads.ads_code ASC;

-- Liste des natures économiques
CREATE OR REPLACE VIEW VA_NATURE_ECONOMIQUE AS
SELECT ne.nat_id AS "IDENTIFIANT",ne.nat_code AS "CODE",ne.nat_liblg AS "LIBELLE",ne.vref_num AS "REFERENTIEL",ne.vers_code AS "REFERENTIEL_CODE"
FROM nature_economique ne
WHERE LENGTH(ne.nat_code) >= 6
ORDER BY ne.nat_code ASC;

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

-- Liste des imputations
CREATE OR REPLACE VIEW VA_IMPUTATION AS
SELECT ld.exo_num||ld.ads_id||ld.nat_id AS "IDENTIFIANT",TO_CHAR(ld.exo_num) AS "EXERCICE",ld.exo_num AS "EXERCICE_ANNEE",ld.ads_id AS "ACTIVITE",ld.nat_id AS "NATURE_ECONOMIQUE"
FROM ligne_de_depenses ld;

-- Liste des verrous
CREATE OR REPLACE VIEW "VA_WORKFLOW_VERROU" AS
SELECT * FROM T_WORKFLOW_VERROU@dblink_bidf_exe;