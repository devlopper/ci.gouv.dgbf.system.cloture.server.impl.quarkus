CREATE OR REPLACE PROCEDURE PA_VERROUILLER_ACTE (IDENTIFIANT IN VARCHAR2 ) AS 
BEGIN
    --dbms_output.put_line('EXECUTION de procedure');
    -- sys.dbms_session.sleep(15); -- Pause for 1 second.
    APEX_UTIL.PAUSE(5);
END PA_DEVERROUILLER_ACTE;