SELECT JTF_NOTES_TL.NOTES, JTF_NOTES_TL.NOTES_DETAIL, TO_CHAR(JTF_NOTES_TL.CREATION_DATE, 'DD/MM/YYYY') AS CREATION_DATE, JTF_RS_RESOURCE_EXTNS.SOURCE_NAME, 'OPORTUNIDAD'
FROM JTF_NOTES_B,
  JTF_NOTES_TL,
  JTF_RS_RESOURCE_EXTNS
WHERE JTF_NOTES_TL.JTF_NOTE_ID = JTF_NOTES_B.JTF_NOTE_ID
AND JTF_RS_RESOURCE_EXTNS.USER_ID = JTF_NOTES_TL.CREATED_BY
AND JTF_NOTES_TL.LANGUAGE like '%ESA%'
AND JTF_NOTES_B.SOURCE_OBJECT_ID like '%'||&v_lead_id||'%'