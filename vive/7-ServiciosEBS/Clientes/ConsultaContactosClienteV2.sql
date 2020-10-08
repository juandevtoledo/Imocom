SELECT hz1.party_id,
  UPPER(SUBSTR(hz1.party_name,1,instr(hz1.party_name,'-')-1)) AS NOMBRE ,
  hz1.EMAIL_ADDRESS ,
  hz1.PRIMARY_PHONE_NUMBER ,
  (SELECT hp.attribute1
  FROM hz_relationships hp
  WHERE hp.party_id   = hz1.party_id
  AND hp.object_type  ='PERSON'
  AND hp.subject_type = 'ORGANIZATION'
  ) AS ROLE ,
  (SELECT hzoc.PHONE_NUMBER
  FROM HZ_CONTACT_POINTS hzoc
  WHERE hzoc.OWNER_TABLE_ID = HZ1.PARTY_ID
  AND hzoc.CONTACT_POINT_TYPE LIKE '%PHONE%'
  AND hzoc.PHONE_LINE_TYPE LIKE '%MOBILE%'
  AND ROWNUM = 1
  ) AS CELULAR
FROM apps.hz_parties hz1,
  apps.hz_relationships hr
WHERE hz1.party_id     = hr.party_id
AND hr.subject_type(+) = 'PERSON'
AND hr.object_id       =
  (SELECT hp.party_id
  FROM HZ_PARTIES hp
  WHERE hp.JGZZ_FISCAL_CODE LIKE '860001779'
  AND hp.ATTRIBUTE16 = 'C'
  )
AND hr.object_type       = 'ORGANIZATION'
AND hr.relationship_type = 'CONTACT'
AND SYSDATE BETWEEN hr.start_date AND NVL (hr.end_date, SYSDATE + 1)
AND hr.status = 'A'