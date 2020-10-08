<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../updateOp.wsdl"/>
      <rootElement name="InputParameters" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/sp/updateOp"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../updateOp.wsdl"/>
      <rootElement name="InputParameters" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/sp/updateOp"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 140919.1004.0161) AT [TUE JUN 16 13:37:22 COT 2015]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/ServiciosEBS/ProcesosBPM/updateOp"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/sp/updateOp"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl tns plt xsd db wsdl bpws xp20 mhdr bpel oraext dvm hwf med ids bpm xdk xref bpmn ora socket ldap">
  <xsl:template match="/">
    <db:InputParameters>
      <db:L_USER_NAME>
        <xsl:attribute name="xsi:nil">
          <xsl:value-of select="/db:InputParameters/db:L_USER_NAME/@xsi:nil"/>
        </xsl:attribute>
      </db:L_USER_NAME>
      <db:I_LEAD_ID>
        <xsl:attribute name="xsi:nil">
          <xsl:value-of select="/db:InputParameters/db:I_LEAD_ID/@xsi:nil"/>
        </xsl:attribute>
      </db:I_LEAD_ID>
      <db:INVENTORY_ITEM_ID>
        <xsl:attribute name="xsi:nil">
          <xsl:value-of select="/db:InputParameters/db:INVENTORY_ITEM_ID/@xsi:nil"/>
        </xsl:attribute>
      </db:INVENTORY_ITEM_ID>
      <db:L_LINE_TBL_BPM>
        <xsl:attribute name="xsi:nil">
          <xsl:value-of select="/db:InputParameters/db:L_LINE_TBL_BPM/@xsi:nil"/>
        </xsl:attribute>
        <db:L_LINE_TBL_BPM_ITEM>
          <db:LEAD_LINE_ID>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="/db:InputParameters/db:L_LINE_TBL_BPM/db:L_LINE_TBL_BPM_ITEM/db:LEAD_LINE_ID/@xsi:nil"/>
            </xsl:attribute>
          </db:LEAD_LINE_ID>
          <db:RETURN_STATUS>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="/db:InputParameters/db:L_LINE_TBL_BPM/db:L_LINE_TBL_BPM_ITEM/db:RETURN_STATUS/@xsi:nil"/>
            </xsl:attribute>
          </db:RETURN_STATUS>
          <db:INVENTORY_ITEM_ID>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="/db:InputParameters/db:L_LINE_TBL_BPM/db:L_LINE_TBL_BPM_ITEM/db:INVENTORY_ITEM_ID/@xsi:nil"/>
            </xsl:attribute>
          </db:INVENTORY_ITEM_ID>
          <db:UOM_CODE>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="/db:InputParameters/db:L_LINE_TBL_BPM/db:L_LINE_TBL_BPM_ITEM/db:UOM_CODE/@xsi:nil"/>
            </xsl:attribute>
          </db:UOM_CODE>
          <db:QUANTITY>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="/db:InputParameters/db:L_LINE_TBL_BPM/db:L_LINE_TBL_BPM_ITEM/db:QUANTITY/@xsi:nil"/>
            </xsl:attribute>
          </db:QUANTITY>
          <db:TOTAL_AMOUNT>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="/db:InputParameters/db:L_LINE_TBL_BPM/db:L_LINE_TBL_BPM_ITEM/db:TOTAL_AMOUNT/@xsi:nil"/>
            </xsl:attribute>
          </db:TOTAL_AMOUNT>
        </db:L_LINE_TBL_BPM_ITEM>
      </db:L_LINE_TBL_BPM>
    </db:InputParameters>
  </xsl:template>
</xsl:stylesheet>