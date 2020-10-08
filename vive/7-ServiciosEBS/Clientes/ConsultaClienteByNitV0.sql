SELECT DISTINCT party_id ,
  party_number ,
  party_name           AS NOMBRE,
  creation_date        AS fecha_creacion,
  jgzz_fiscal_code     AS NIT ,
  address1             AS DIRECCION ,
  city                 AS CIUDAD ,
  state                AS DEPARTAMENTO,
  url                  AS URL ,
  email_address        AS CORREO ,
  PRIMARY_PHONE_NUMBER AS TELEFONO
FROM ar.hz_parties hp
WHERE party_type = 'ORGANIZATION'
AND party_name LIKE DECODE(DECODE(#v_nombreEmpresa_1, '', NULL, #v_nombreEmpresa_2), NULL, '%', TO_CHAR('%'|| UPPER(#v_nombreEmpresa_3) || '%'))
AND hp.ATTRIBUTE16 LIKE '%C%'
AND jgzz_fiscal_code LIKE DECODE(DECODE(#v_nit_1, '', NULL, #v_nit_2), NULL, '%', TO_CHAR('%' || #v_nit_3 || '%'))