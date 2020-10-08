<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../OportunidadesEBS.wsdl"/>
      <rootElement name="oportunidadesNombreEtapa" namespace="http://com.imocom.intelcom.ws.ebs.interfaces/"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../ConsultaOportunidadesByNombreEtapaAdapter.wsdl"/>
      <rootElement name="ConsultaOportunidadesByNombreEtapaAdapterInput" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaOportunidadesByNombreEtapaAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 140919.1004.0161) AT [THU MAR 05 16:49:33 COT 2015]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/ServiciosEBS/Oportunidades/ConsultaOportunidadesByNombreEtapaAdapter"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
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
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaOportunidadesByNombreEtapaAdapter"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl soap tns xsd ns0 plt wsdl db xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <db:ConsultaOportunidadesByNombreEtapaAdapterInput>
      <xsl:choose>
        <xsl:when test='(/tns:oportunidadesNombreEtapa/nombre = "") or not(boolean(/tns:oportunidadesNombreEtapa/nombre))'>
          <db:v_nombre_oportunidad>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_nombre_oportunidad>
        </xsl:when>
        <xsl:otherwise>
          <db:v_nombre_oportunidad>
            <xsl:value-of select="concat(&quot;%&quot;,xp20:upper-case(translate(/tns:oportunidadesNombreEtapa/nombre,' ','%')),&quot;%&quot;)"/>
          </db:v_nombre_oportunidad>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='not(boolean(/tns:oportunidadesNombreEtapa/etapa)) or (/tns:oportunidadesNombreEtapa/etapa = "")'>
          <db:v_etapa>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_etapa>
        </xsl:when>
        <xsl:otherwise>
          <db:v_etapa>
            <xsl:value-of select="/tns:oportunidadesNombreEtapa/etapa"/>
          </db:v_etapa>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='not(boolean(/tns:oportunidadesNombreEtapa/idAsesor)) or (/tns:oportunidadesNombreEtapa/idAsesor = "")'>
          <db:v_id_asesor>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_id_asesor>
        </xsl:when>
        <xsl:otherwise>
          <db:v_id_asesor>
            <xsl:value-of select="/tns:oportunidadesNombreEtapa/idAsesor"/>
          </db:v_id_asesor>
        </xsl:otherwise>
      </xsl:choose>
    </db:ConsultaOportunidadesByNombreEtapaAdapterInput>
  </xsl:template>
</xsl:stylesheet>