CREATE OR REPLACE PROCEDURE ofg_ouverture_speciale_ligne AUTHID CURRENT_USER AS
BEGIN
	DBMS_OUTPUT.PUT_LINE('Prise en charge des imputations en cours');
    FOR t IN (SELECT exo_num,ldep_id,r_ligne,date_debut_ouverture_exception,date_fin_ouverture_exception,etat,commentaire_etat,date_etat FROM ligne_ouverture_speciale WHERE etat = 'NOUV')
    	LOOP
    		UPDATE ligne_ouverture_speciale SET etat = 'PRCH',date_etat = CAST(SYSDATE AS TIMESTAMP);
    		-- dbms_lock.SLEEP(1);
    	END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Imputations prises en charges');
END;
/