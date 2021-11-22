-- Act
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