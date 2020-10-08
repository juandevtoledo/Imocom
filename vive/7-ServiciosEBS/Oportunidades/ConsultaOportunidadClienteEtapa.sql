SELECT AS_LEADS_ALL.LEAD_ID,
  HZ_PARTIES.JGZZ_FISCAL_CODE AS NIT,
  HZ_PARTIES.PARTY_NAME Cliente,
  AS_LEADS_ALL.DESCRIPTION Descripcion,
  AS_SALES_STAGES_ALL_TL.DESCRIPTION etapa_oportunidad
FROM AS_LEADS_ALL,
  AS_SALES_LEADS,
  AS_SALES_LEAD_OPPORTUNITY,
  HZ_PARTIES,
  AS_SALES_STAGES_ALL_TL
WHERE AS_LEADS_ALL.CUSTOMER_ID = HZ_PARTIES.PARTY_ID
AND JGZZ_FISCAL_CODE LIKE '%'||&v_nit||'%'
AND AS_SALES_STAGES_ALL_TL.SALES_STAGE_ID = AS_LEADS_ALL.SALES_STAGE_ID
AND AS_SALES_STAGES_ALL_TL.LANGUAGE LIKE 'E'
AND AS_SALES_STAGES_ALL_TL.SOURCE_LANG like '%ESA%';
AND AS_LEADS_ALL.SALES_STAGE_ID LIKE '%'||&v_etapa||'%' 
AND AS_SALES_LEADS.SALES_LEAD_ID = AS_SALES_LEAD_OPPORTUNITY.SALES_LEAD_ID
AND AS_LEADS_ALL.LEAD_ID = AS_SALES_LEAD_OPPORTUNITY.OPPORTUNITY_ID
AND AS_SALES_LEADS.ASSIGN_TO_PERSON_ID like '%'||&v_id_asesor||'%'