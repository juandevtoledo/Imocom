SELECT hp.party_id ,
  hp.party_number ,
  hp.party_name           AS NOMBRE,
  hp.creation_date        AS fecha_creacion,
  hp.jgzz_fiscal_code     AS NIT ,
  hp.address1             AS DIRECCION ,
  hp.city                 AS CIUDAD ,
  hp.state                AS DEPARTAMENTO,
  hp.url                  AS URL ,
  hp.email_address        AS CORREO ,
  hp.PRIMARY_PHONE_NUMBER AS TELEFONO,
  hp.COUNTRY              AS PAIS,
  cod.CLASS_CODE          AS TIPO_CLIENTE
FROM ar.hz_parties hp,
  hz_code_assignments cod
WHERE hp.party_type      = 'ORGANIZATION'
AND hp.ATTRIBUTE16       = 'C'
AND cod.owner_table_name = 'HZ_PARTIES'
AND cod.class_category   = 'Estado del Cliente'
AND cod.owner_table_id   = hp.party_id
AND hp.jgzz_fiscal_code  = #v_nit