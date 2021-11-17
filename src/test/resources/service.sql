INSERT INTO VA_SECTION(IDENTIFIANT,CODE,LIBELLE) VALUES ('101','101','Assemblée nationale');
INSERT INTO VA_SECTION(IDENTIFIANT,CODE,LIBELLE) VALUES ('323','323','Intérieur');
INSERT INTO VA_SECTION(IDENTIFIANT,CODE,LIBELLE) VALUES ('327','327','Budget');

INSERT INTO TA_ACTE(IDENTIFIANT,CODE,LIBELLE) VALUES ('1','1','1');
INSERT INTO TA_VERSION_ACTE(IDENTIFIANT,CODE,LIBELLE,ACTE,NUMERO) VALUES ('1','1','1','1',1);
INSERT INTO TA_LIGNE_DEPENSE(IDENTIFIANT,ACTIVITE,NATURE_ECONOMIQUE,SOURCE_FINANCEMENT,BAILLEUR,VERSION_ACTE,AJUSTEMENT_AE,AJUSTEMENT_CP) VALUES ('1','1','1','1','1','1',0,0);
INSERT INTO TA_LIGNE_DEPENSE(IDENTIFIANT,ACTIVITE,NATURE_ECONOMIQUE,SOURCE_FINANCEMENT,BAILLEUR,VERSION_ACTE,AJUSTEMENT_AE,AJUSTEMENT_CP) VALUES ('2','1','1','1','1','1',3,0);