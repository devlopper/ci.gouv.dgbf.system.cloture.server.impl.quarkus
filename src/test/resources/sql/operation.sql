INSERT INTO TA_TYPE_OPERATION(IDENTIFIANT,CODE,LIBELLE) VALUES ('DEVERROUILLAGE','DEVERROUILLAGE','DEVERROUILLAGE');
INSERT INTO TA_TYPE_OPERATION(IDENTIFIANT,CODE,LIBELLE) VALUES ('VERROUILLAGE','VERROUILLAGE','VERROUILLAGE');

INSERT INTO TA_OPERATION(IDENTIFIANT,CODE,LIBELLE,TYPE,MOTIF,AUDIT_IDENTIFIANT,AUDIT_FONCTIONNALITE,AUDIT_ACTION,AUDIT_ACTEUR,AUDIT_DATE) VALUES ('1','V1','Verrouillage des actes','DEVERROUILLAGE','Demande DG','1','1','1','1',DATE '2022-1-1');