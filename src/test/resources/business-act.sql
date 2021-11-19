DROP ALIAS IF EXISTS PA_VERROUILLER_ACTE;
CREATE ALIAS PA_VERROUILLER_ACTE FOR "ci.gouv.dgbf.system.cloture.server.impl.Procedures.PA_VERROUILLER_ACTE";

DROP ALIAS IF EXISTS PA_DEVERROUILLER_ACTE;
CREATE ALIAS PA_DEVERROUILLER_ACTE FOR "ci.gouv.dgbf.system.cloture.server.impl.Procedures.PA_DEVERROUILLER_ACTE";

INSERT INTO VA_ACTE (IDENTIFIANT,CODE,LIBELLE) VALUES ('not_yet_operated_01','not_yet_operated_01','1');
INSERT INTO VA_ACTE (IDENTIFIANT,CODE,LIBELLE) VALUES ('not_yet_operated_02','not_yet_operated_02','1');
INSERT INTO VA_ACTE (IDENTIFIANT,CODE,LIBELLE,OPERATION,OPERATION_DATE,DECLENCHEUR) VALUES ('locked01','locked01','1','VERROUILLAGE',DATE '2000-1-1','test');
INSERT INTO VA_ACTE (IDENTIFIANT,CODE,LIBELLE,OPERATION,OPERATION_DATE,DECLENCHEUR) VALUES ('locked02','locked02','1','VERROUILLAGE',DATE '2000-1-1','test');
INSERT INTO VA_ACTE (IDENTIFIANT,CODE,LIBELLE,OPERATION,OPERATION_DATE,DECLENCHEUR) VALUES ('unlocked01','unlocked01','1','DEVERROUILLAGE',DATE '2000-1-1','test');
INSERT INTO VA_ACTE (IDENTIFIANT,CODE,LIBELLE,OPERATION,OPERATION_DATE,DECLENCHEUR) VALUES ('unlocked02','unlocked02','1','DEVERROUILLAGE',DATE '2000-1-1','test');
