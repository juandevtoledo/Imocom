SELECT DISTINCT party_id,
  hp.PARTY_NUMBER ,
  hp.party_name           AS NOMBRE,
  hp.creation_date        AS fecha_creacion,
  hp.jgzz_fiscal_code     AS NIT ,
  hp.address1             AS DIRECCION ,
  hp.city                 AS CIUDAD ,
  hp.state                AS DEPARTAMENTO,
  hp.url                  AS URL ,
  hp.email_address        AS CORREO ,
  hp.PRIMARY_PHONE_NUMBER AS TELEFONO,
  cod.CLASS_CODE          AS TIPO_CLIENTE
FROM ar.hz_parties hp,
  AS_ACCESSES_ALL acc,
  JTF_RS_RESOURCE_EXTNS rc,
  hz_code_assignments cod
WHERE party_type = 'ORGANIZATION'
AND hp.ATTRIBUTE16 LIKE '%C%'
AND acc.CUSTOMER_ID      = hp.PARTY_ID
AND acc.SALESFORCE_ID    = rc.RESOURCE_ID
AND cod.owner_table_name = 'HZ_PARTIES'
AND cod.class_category   = 'Estado del Cliente'
AND cod.owner_table_id   = hp.party_id
AND party_name LIKE #v_nombreEmpresa
AND jgzz_fiscal_code LIKE #v_nit
AND rc.USER_NAME LIKE #v_asesor
AND cod.CLASS_CODE LIKE #v_tipo_cliente