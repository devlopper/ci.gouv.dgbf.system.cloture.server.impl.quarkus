DROP ALIAS IF EXISTS PA_VERROUILLER_ACTE;
CREATE ALIAS PA_VERROUILLER_ACTE FOR "ci.gouv.dgbf.system.cloture.server.impl.Procedures.PA_VERROUILLER_ACTE";

DROP ALIAS IF EXISTS PA_DEVERROUILLER_ACTE;
CREATE ALIAS PA_DEVERROUILLER_ACTE FOR "ci.gouv.dgbf.system.cloture.server.impl.Procedures.PA_DEVERROUILLER_ACTE";

INSERT INTO VMA_ACTE (IDENTIFIANT,CODE,LIBELLE) VALUES ('not_yet_operated_01','not_yet_operated_01','1');
INSERT INTO VMA_ACTE (IDENTIFIANT,CODE,LIBELLE) VALUES ('not_yet_operated_02','not_yet_operated_02','1');
INSERT INTO VMA_ACTE (IDENTIFIANT,CODE,LIBELLE) VALUES ('locked01','locked01','1');
INSERT INTO VA_ACTE_DERNIERE_OPERATION (IDENTIFIANT,ACTE,OPERATION,OPERATION_DATE,DECLENCHEUR) VALUES ('1','locked01','VERROUILLAGE',DATE '2000-1-1','test');
INSERT INTO VMA_ACTE (IDENTIFIANT,CODE,LIBELLE) VALUES ('locked02','locked02','1');
INSERT INTO VA_ACTE_DERNIERE_OPERATION (IDENTIFIANT,ACTE,OPERATION,OPERATION_DATE,DECLENCHEUR) VALUES ('2','locked02','VERROUILLAGE',DATE '2000-1-1','test');
INSERT INTO VMA_ACTE (IDENTIFIANT,CODE,LIBELLE) VALUES ('unlocked01','unlocked01','1');
INSERT INTO VA_ACTE_DERNIERE_OPERATION (IDENTIFIANT,ACTE,OPERATION,OPERATION_DATE,DECLENCHEUR) VALUES ('3','unlocked01','DEVERROUILLAGE',DATE '2000-1-1','test');
INSERT INTO VMA_ACTE (IDENTIFIANT,CODE,LIBELLE) VALUES ('unlocked02','unlocked02','1');
INSERT INTO VA_ACTE_DERNIERE_OPERATION (IDENTIFIANT,ACTE,OPERATION,OPERATION_DATE,DECLENCHEUR) VALUES ('4','unlocked02','DEVERROUILLAGE',DATE '2000-1-1','test');