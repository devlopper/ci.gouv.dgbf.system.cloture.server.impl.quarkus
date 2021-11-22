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