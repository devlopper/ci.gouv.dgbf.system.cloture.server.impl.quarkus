CREATE VIEW VA_ACTE_DERNIERE_OPERATION
AS SELECT acte
    ,MAX(identifiant) keep (dense_rank last order by operation_date) AS "IDENTIFIANT"
    ,MAX(operation) keep (dense_rank last order by operation_date) AS "OPERATION"
    ,MAX(operation_date) AS "OPERATION_DATE"
    ,MAX(declencheur) keep (dense_rank last order by operation_date) AS "DECLENCHEUR"
FROM  TA_ACTE_OPERATION
GROUP BY acte;
