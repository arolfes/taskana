-- this script updates the TASKANA database schema from version 5.2.0 to version 5.10.0.

INSERT INTO TASKANA_SCHEMA_VERSION (VERSION, CREATED) VALUES ('5.10.0', CURRENT_TIMESTAMP);

ALTER TABLE TASK ALTER COLUMN NAME SET DATA TYPE VARCHAR(255 CODEUNITS32);