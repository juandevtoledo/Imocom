SELECT distinct CUSTOMER_NUMBER AS NIT,CUSTOMER_NAME AS NOMBRE_CLIENTE, TO_CHAR(CUPO_CREDITO, '$999,999,999.00') AS CUPO_CREDITO,(select cod.CLASS_CODE from hz_code_assignments cod WHERE cod.owner_table_id like substr(customer_id,1,9) AND cod.owner_table_name = 'HZ_PARTIES' AND cod.class_category   = 'Estado del Cliente') AS TIPOCLIE, 'Cupo Disponible'                        AS CUPO_DISPONIBLE,'Cartera vencida'                        AS CARTERA_VENCIDA, TIPO_DOC, NUMERO_DOC, TO_CHAR(FECHA_VENCIMIENTO, 'DD/MM/YYYY') FECHA_VENCIMIENTO, DIAS_VENCIDOS, TO_CHAR(VALOR_CARTERA, '$999,999,999.0')||' '||MONEDA VALOR_CARTERA, 'valor vencido' VALOR_VENCIDO, LINEA CENTRO_COSTOS FROM IMCM_CARTERA_VENCIDA_DETALLADA WHERE CUSTOMER_NUMBER LIKE ('%'|| #v_nit||'%')

;(SELECT cod.CLASS_CODE
FROM hz_code_assignments cod
WHERE cod.owner_table_id = '70514369'
AND cod.owner_table_name = 'HZ_PARTIES'
AND cod.class_category   = 'Estado del Cliente'
);


WHERE CUSTOMER_NUMBER LIKE ('%'||#v_nit||'%')
WHERE CUSTOMER_NUMBER LIKE ('%'||860350697||'%')


--SUAREZ LUIS FERNANDO
--2003
--860350697
;
SELECT * FROM
IMCM_CARTERA_EDADES_LINEAS_V2;