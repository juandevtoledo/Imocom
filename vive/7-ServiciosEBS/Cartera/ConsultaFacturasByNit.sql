SELECT
 DISTINCT   
  VENDEDOR,
  CUSTOMER_NAME,
  CUSTOMER_NUMBER,
  TIPOCLIE,
  TIPO_DOC,
  NUMERO_DOC,
  TO_CHAR(FECHA_VENCIMIENTO, 'DD/MM/YYYY') FECHA_VENCIMIENTO,
  DIAS_VENCIDOS,
  TO_CHAR(VALOR_CARTERA, '$999,999,999.0') VALOR_CARTERA,
  TO_CHAR(CUPO_CREDITO, '$999,999,999.0') CUPO_CREDITO
FROM IMCM_CARTERA_EDADES_LINEAS_V2, PER_ALL_PEOPLE_F
WHERE CUSTOMER_NUMBER LIKE ('%'||#v_nit||'%')
AND vendedor like PER_ALL_PEOPLE_F.FULL_NAME
AND PER_ALL_PEOPLE_F.person_ID like '%'||#v_id_asesor||'%'

WHERE CUSTOMER_NUMBER LIKE ('%'||#v_nit||'%')
WHERE CUSTOMER_NUMBER LIKE ('%'||860350697||'%')