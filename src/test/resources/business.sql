DROP ALIAS IF EXISTS PA_EXECUTER_PROCEDURE;
CREATE ALIAS PA_EXECUTER_PROCEDURE FOR "ci.gouv.dgbf.system.cloture.server.impl.Procedures.PA_EXECUTER_PROCEDURE";

INSERT INTO TA_GROUPE_OPERATION(IDENTIFIANT,CODE,LIBELLE) VALUES ('E','E','Engagement');

-- passed blocking
INSERT INTO TA_OPERATION(IDENTIFIANT,CODE,LIBELLE,GROUPE,DATE_DEBUT,PROCEDURE_LIBELLE) VALUES ('OPassedBlocking','OPassedBlocking','Verrouiller','E',DATE '2021-1-1','P');
-- passed non blocking
INSERT INTO TA_OPERATION(IDENTIFIANT,CODE,LIBELLE,GROUPE,DATE_DEBUT,PROCEDURE_LIBELLE) VALUES ('OPassedNonBlocking','OPassedNonBlocking','Verrouiller','E',DATE '2021-1-1','P');
-- future
INSERT INTO TA_OPERATION(IDENTIFIANT,CODE,LIBELLE,GROUPE,DATE_DEBUT,PROCEDURE_LIBELLE) VALUES ('OFuture','OFuture','Verrouiller','E',DATE '2099-1-1','P');