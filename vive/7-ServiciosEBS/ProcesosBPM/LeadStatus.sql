SELECT lead_id,
  description,
  TO_CHAR(last_update_date, 'DD/MM/YYYY HH24:MM:SS') AS last_update_date,
  sales_stage_id,
  status,
  total_amount,
  decision_date,
  close_comment,
  close_reason,
  channel_code,
  currency_code,
  attribute3 AS probejec,
  attribute4 as probexito
FROM AS_LEADS_ALL WHERE lead_id = 108129
ORDER BY creation_date DESC;
--2015-01-30T09:00:00

SELECT status_code, lead_flag, opp_flag, opp_open_status_flag, forecast_rollup_flag, win_loss_indicator, description FROM as_statuses_vl where ENABLED_FLAG = 'Y' AND OPP_FLAG ='Y';

SELECT * FROM AS_LEADS_LOG where lead_id = 108129 order by creation_date desc;

select t.meaning, t.lookup_code, t.enabled_flag from applsys.fnd_lookup_values t where t.lookup_type = 'ASN_OPPTY_CLOSE_REASON' and t.language = 'ESA' and t.enabled_flag = 'Y';

SELECT * FROM ASO_ISALES_CHANNELS_V;
SELECT * FROM IMCM_PROB_OPORIMO; 

SELECT * FROM AS_LEAD_LINES_ALL WHERE LEAD_ID = 108154;


