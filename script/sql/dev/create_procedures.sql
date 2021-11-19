CREATE OR REPLACE PROCEDURE PA_EXECUTER_PROCEDURE (NOM_PROCEDURE IN VARCHAR2 ) AS 
BEGIN
    --dbms_output.put_line('EXECUTION de procedure');
    -- sys.dbms_session.sleep(15); -- Pause for 1 second.
    APEX_UTIL.PAUSE(5);
END PA_EXECUTER_PROCEDURE;

CREATE OR REPLACE PROCEDURE PA_VERROUILLER_ACTE (IDENTIFIANT IN VARCHAR2 ) AS 
BEGIN
    --dbms_output.put_line('EXECUTION de procedure');
    -- sys.dbms_session.sleep(15); -- Pause for 1 second.
    APEX_UTIL.PAUSE(5);
END PA_VERROUILLER_ACTE;

CREATE OR REPLACE PROCEDURE PA_DEVERROUILLER_ACTE (IDENTIFIANT IN VARCHAR2 ) AS 
BEGIN
    --dbms_output.put_line('EXECUTION de procedure');
    -- sys.dbms_session.sleep(15); -- Pause for 1 second.
    APEX_UTIL.PAUSE(5);
END PA_DEVERROUILLER_ACTE;