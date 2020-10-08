<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../OportunidadesBPM.wsdl"/>
      <rootElement name="procesoActualizacionOportunidades" namespace="http://com.imocom.intelcom.ws.ebs.interfaces/"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../ActualizacionOportunidadEBSAdapter.wsdl"/>
      <rootElement name="InputParameters" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/sp/ActualizacionOportunidadEBSAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 140919.1004.0161) AT [MON JUL 06 15:04:04 COT 2015]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/sp/ActualizacionOportunidadEBSAdapter"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/ServiciosEBS/ProcesosBPM/ActualizacionOportunidadEBSAdapter"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:tns="http://com.imocom.intelcom.ws.ebs.interfaces/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl soap tns xsd db plt wsdl ns0 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <db:InputParameters>
      <db:L_USER_NAME>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/nombreUsuario"/>
      </db:L_USER_NAME>
      <db:I_LEAD_ID>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idOportunidad"/>
      </db:I_LEAD_ID>
      <db:L_DESCRIPTION>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/nombreOportunidad"/>
      </db:L_DESCRIPTION>
      <db:L_SALES_STAGE_ID>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idEtapaOportunidad"/>
      </db:L_SALES_STAGE_ID>
      <db:L_STATUS_CODE>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idEstadoOportunidad"/>
      </db:L_STATUS_CODE>
      <db:L_TOTAL_AMOUNT>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/monto"/>
      </db:L_TOTAL_AMOUNT>
      <db:L_CHANNEL_CODE>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idCanalEntrada"/>
      </db:L_CHANNEL_CODE>
      <db:L_DECISION_DATE>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/fechaEstimadaCierre"/>
      </db:L_DECISION_DATE>
      <db:L_CLOSE_REASON>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/motivoCierre"/>
      </db:L_CLOSE_REASON>
      <db:L_CURRENCY_CODE>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idTipoMoneda"/>
      </db:L_CURRENCY_CODE>
      <db:L_PROB_OPORCLIE>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/probabilidadProyecto"/>
      </db:L_PROB_OPORCLIE>
      <db:L_PROB_OPORIMO>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/probabilidadImocom"/>
      </db:L_PROB_OPORIMO>
      <db:L_ID_ARCHIVO_ORD_COMPRA>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idArchivo"/>
      </db:L_ID_ARCHIVO_ORD_COMPRA>
      <db:L_NOMBRE_ARCHIVO_ORD_COMPRA>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/nombreArchivo"/>
      </db:L_NOMBRE_ARCHIVO_ORD_COMPRA>
      <db:L_LINE_TBL_BPM>
        <xsl:for-each select="/tns:procesoActualizacionOportunidades/request/oportunidadProducto">
          <db:L_LINE_TBL_BPM_ITEM>
            <db:INVENTORY_ITEM_ID>
              <xsl:value-of select="codigo"/>
            </db:INVENTORY_ITEM_ID>
            <db:UOM_CODE>
              <xsl:value-of select="unidad"/>
            </db:UOM_CODE>
            <db:QUANTITY>
              <xsl:value-of select="cantidad"/>
            </db:QUANTITY>
            <db:TOTAL_AMOUNT>
              <xsl:value-of select="valorUnitario"/>
            </db:TOTAL_AMOUNT>
          </db:L_LINE_TBL_BPM_ITEM>
        </xsl:for-each>
      </db:L_LINE_TBL_BPM>
    </db:InputParameters>
  </xsl:template>
</xsl:stylesheet>
