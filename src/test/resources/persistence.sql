DROP ALIAS IF EXISTS PA_EXECUTER_PROCEDURE;
CREATE ALIAS PA_EXECUTER_PROCEDURE FOR "ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationPersistenceTest.dbmsProcedure";

INSERT INTO TA_GROUPE_OPERATION(IDENTIFIANT,CODE,LIBELLE) VALUES ('E','E','Engagement');
INSERT INTO TA_GROUPE_OPERATION(IDENTIFIANT,CODE,LIBELLE) VALUES ('L','L','Liquidation');
INSERT INTO TA_GROUPE_OPERATION(IDENTIFIANT,CODE,LIBELLE) VALUES ('O','O','Ordonnancement');
INSERT INTO TA_GROUPE_OPERATION(IDENTIFIANT,CODE,LIBELLE) VALUES ('PC','PC','Prise en charge');

INSERT INTO TA_OPERATION(IDENTIFIANT,CODE,LIBELLE,GROUPE,DATE_FIN) VALUES ('V01','V01','Verrouillage des engagements par bon de commande dont l''ordonnateur n''a pas fait la 1ere validation','E',DATE '2021-11-1');
INSERT INTO TA_OPERATION(IDENTIFIANT,CODE,LIBELLE,GROUPE,DATE_FIN) VALUES ('V02','V02','Verrouillage des engagements par bon de commande dont l''ordonnateur n''a pas fait la 2eme validation','E',DATE '2021-11-1');
INSERT INTO TA_OPERATION(IDENTIFIANT,CODE,LIBELLE,GROUPE,DATE_FIN) VALUES ('V03','V03','Verrouillage des engagements direct hors Personnel, Abonnement et Dette non validés une première fois par l’Ordonnateur','E',DATE '2021-11-1');