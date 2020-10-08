SELECT UPPER(org.DESCRIPTION) AS description,
  UPPER(
  (SELECT Vtl.Description
  FROM Fnd_Flex_Value_Sets s ,
    Fnd_Flex_Values v ,
    Fnd_Flex_Values_Tl Vtl
  WHERE s.Flex_Value_Set_Id   = v.Flex_Value_Set_Id
  AND v.Flex_Value_Id         = Vtl.Flex_Value_Id
  AND Vtl.LANGUAGE            = 'ESA'
  AND s.Flex_Value_Set_Name   = 'IMO_LINEA'
  AND v.Parent_Flex_Value_Low = m.Segment1
  AND v.Flex_Value            = m.Segment2
  )) AS Centro_Costos_Desc ,
  UPPER(
  (SELECT DISTINCT vv.DESCRIPTION
  FROM fnd_flex_value_sets vs ,
    fnd_flex_values_vl vv
  WHERE vs.flex_value_set_name= 'IMO_TIPO_PRODUCTO'
  AND vs.flex_value_set_id    = vv.flex_value_set_id
  AND vv.flex_value           = SUBSTR(org.item,1,1)
  ))                                          AS Tipo_producto ,
  TO_CHAR(NVL(li.lista, 0),'$999,999,999.99') AS Precio_Lista ,
  UPPER(org.Unidad)                           AS Unidad ,
  UPPER(
  (SELECT DISTINCT vv.DESCRIPTION
  FROM fnd_flex_value_sets vs,
    fnd_flex_values_vl vv
  WHERE vs.flex_value_set_name= 'IMO_MARCA_PROV'
  AND vs.flex_value_set_id    = vv.flex_value_set_id
  AND vv.flex_value           = ORG.MARCA
  )) AS Marca ,
  (SELECT DISTINCT vv.DESCRIPTION
  FROM fnd_flex_value_sets vs,
    fnd_flex_values_vl vv
  WHERE vs.flex_value_set_name= 'IMO_FAMILIA'
  AND vs.flex_value_set_id    = vv.flex_value_set_id
  AND vv.flex_value           = ORG.segment1
  )                     AS FAMILIA,
  UPPER(org.referencia) AS referencia,
  ORG.INVENTORY_ITEM_ID ,
  org.on_hand Saldo ,
  UPPER(org.organization_name) Bodega
FROM
  (SELECT mp.inventory_item_id ,
    bo.organization_code organization_code ,
    bo.organization_name organization_name ,
    SUM(NVL(moq.primary_transaction_quantity,0)) on_hand ,
    mp.organization_id ,
    moq.subinventory_code ,
    mp.primary_uom_code Unidad ,
    moq.locator_id ,
    mp.DESCRIPTION ,
    mp.SEGMENT1,
    mp.SEGMENT1
    ||'.'
    ||MP.SEGMENT2
    ||'.'
    ||MP.SEGMENT3
    ||'.'
    ||MP.SEGMENT4
    ||'.'
    ||MP.SEGMENT5
    ||'.'
    ||MP.SEGMENT6 Item ,
    mp.segment4 Marca ,
    mp.segment6 Referencia
  FROM mtl_onhand_quantities_detail moq ,
    mtl_system_items_b mp ,
    org_organization_definitions bo
  WHERE moq.inventory_item_id(+)    = mp.Inventory_Item_Id
  AND mp.INVENTORY_ITEM_STATUS_CODE = 'ACTIVO'
  AND moq.owning_organization_id(+) = mp.organization_id
  AND bo.organization_id            = mp.ORGANIZATION_id
  GROUP BY mp.inventory_item_id ,
    bo.organization_code ,
    bo.organization_name ,
    mp.organization_id ,
    moq.subinventory_code ,
    mp.primary_uom_code ,
    moq.locator_id ,
    mp.description ,
    mp.segment1 ,
    mp.segment2 ,
    mp.segment3 ,
    mp.segment4 ,
    mp.segment5 ,
    mp.segment6 ,
    bo.operating_unit ,
    mp.segment4 ,
    mp.segment6,
    mp.attribute1
  ) Org,
  (SELECT mc.Segment1,
    mc.Segment2,
    mic.Inventory_Item_Id,
    Mcst.Category_Set_Name,
    mtp.organization_id
  FROM mtl_Parameters mtp ,
    mtl_Item_Categories mic ,
    mtl_Category_Sets_Tl mcst ,
    mtl_Category_Sets_b mcs ,
    mtl_categories_b mc
  WHERE mic.Organization_Id = mtp.Master_Organization_Id
  AND mic.Category_Set_Id   = mcs.Category_Set_Id
  AND mic.Category_Id       = mc.Category_Id
  AND mcst.Category_Set_Name= 'Inventory'
  AND mcst.LANGUAGE         = 'ESA'
  AND mcs.Category_Set_Id   = mcst.Category_Set_Id
  ) m ,
  (SELECT Qlh.CURRENCY_CODE,
    (Qll.Operand) Lista,
    qll.Product_Attr_Value inventory_item_id,
    qll.product_uom_code
  FROM Qp_List_Lines_v Qll,
    Qp_List_Headers_All Qlh
  WHERE Qlh.LIST_HEADER_ID = qll.list_header_id
  AND Qlh.LIST_HEADER_ID   = 4240963
  AND TRUNC(SYSDATE) BETWEEN Qll.Start_Date_Active AND NVL(Qll.End_Date_Active,TRUNC(SYSDATE))
  AND Qlh.Active_flag <> 'N'
  ) Li
WHERE org.organization_id(+) = m.organization_id
AND org.inventory_item_id(+) = m.Inventory_Item_Id
AND li.inventory_item_id(+)  = org.Inventory_Item_Id
AND li.product_uom_code(+)   = org.Unidad
AND org.item                IS NOT NULL
AND UPPER(
  (SELECT l.segment1
    ||l.segment2
    ||l.segment3
    ||l.segment4
  FROM mtl_item_locations L
  WHERE l.inventory_location_id=org.locator_id
  AND l.organization_id        =org.organization_id
  ))                          IS NOT NULL
AND UPPER(
  (SELECT DISTINCT vv.DESCRIPTION
  FROM fnd_flex_value_sets vs ,
    fnd_flex_values_vl vv
  WHERE vs.flex_value_set_name= 'IMO_TIPO_PRODUCTO'
  AND vs.flex_value_set_id    = vv.flex_value_set_id
  AND vv.flex_value           = SUBSTR(org.item,1,1)
  ))                          = UPPER('&v_tipo_producto')
AND UPPER(org.DESCRIPTION) LIKE '%'||UPPER('&v_marca') ||'%'
AND UPPER(org.DESCRIPTION) LIKE '&v_modelo'
AND UPPER(org.DESCRIPTION) LIKE '%'||'&v_descripcion'||'%'