SELECT role_acct.account_number,
  SUBSTR(party.person_first_name, 1, 40) contact_first_name,
  SUBSTR(party.person_middle_name, 1, 40) contact_middle_name,
  SUBSTR(party.person_last_name, 1, 50) contact_last_name,
  acct_role.status,
  org_cont.job_title contact_job_title,
  org_cont.job_title_code contact_job_title_code,
  rel_party.address1 contact_address1,
  rel_party.address2 contact_address2,
  rel_party.address3 contact_address3,
  rel_party.address4 contact_address4,
  rel_party.country contact_country,
  rel_party.state contact_state,
  rel_party.city contact_city,
  rel_party.county contact_county,
  rel_party.postal_code contact_postal_code,
  rel_party.email_address contact_email_address,
  rel_party.primary_phone_number contact_phone_number,
  (SELECT hzoc.PHONE_NUMBER
  FROM HZ_CONTACT_POINTS hzoc
  WHERE hzoc.OWNER_TABLE_ID = rel_party.PARTY_ID
  AND hzoc.CONTACT_POINT_TYPE LIKE '%PHONE%'
  AND hzoc.PHONE_LINE_TYPE LIKE '%MOBILE%'
  AND ROWNUM = 1
  ) AS mobile_phone_number,
  (SELECT hzoc.PHONE_NUMBER
  FROM HZ_CONTACT_POINTS hzoc
  WHERE hzoc.OWNER_TABLE_ID = rel_party.PARTY_ID
  AND hzoc.CONTACT_POINT_TYPE LIKE '%PHONE%'
  AND hzoc.PHONE_LINE_TYPE LIKE '%GEN%'
  AND ROWNUM = 1
  ) AS GEN_PHONE_NUMBER
FROM hz_contact_points cont_point,
  hz_cust_account_roles acct_role,
  hz_parties party,
  hz_parties rel_party,
  hz_relationships rel,
  hz_org_contacts org_cont,
  hz_cust_accounts role_acct,
  hz_contact_restrictions cont_res,
  hz_person_language per_lang,
  hz_cust_acct_sites_all hcasa
WHERE acct_role.party_id               = rel.party_id
AND acct_role.role_type                = 'CONTACT'
AND org_cont.party_relationship_id     = rel.relationship_id
AND rel.subject_id                     = party.party_id
AND rel_party.party_id                 = rel.party_id
AND cont_point.owner_table_id(+)       = rel_party.party_id
AND ( cont_point.contact_point_type(+) = 'EMAIL'
OR cont_point.contact_point_type(+)    = 'PHONE' )
AND cont_point.primary_flag(+)         = 'Y'
AND acct_role.cust_account_id          = role_acct.cust_account_id
AND role_acct.party_id                 = rel.object_id
AND party.party_id                     = per_lang.party_id(+)
AND per_lang.native_language(+)        = 'Y'
AND party.party_id                     = cont_res.subject_id(+)
AND cont_res.subject_table(+)          = 'HZ_PARTIES'
AND role_acct.cust_account_id          = hcasa.cust_account_id
AND hcasa.cust_acct_site_id            = acct_role.cust_acct_site_id
AND role_acct.account_number LIKE 800184269