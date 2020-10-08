<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../OportunidadesEBS.wsdl"/>
      <rootElement name="oportunidadesClienteFiltros" namespace="http://com.imocom.intelcom.ws.ebs.interfaces/"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../ConsultaOportunidadesFiltrosEBSAdapter.wsdl"/>
      <rootElement name="ConsultaOportunidadesFiltrosEBSAdapterInput" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaOportunidadesFiltrosEBSAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 140919.1004.0161) AT [MON APR 13 10:00:21 COT 2015]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/ServiciosEBS/Oportunidades/ConsultaOportunidadesFiltrosEBSAdapter"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaOportunidadesFiltrosEBSAdapter"
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
                exclude-result-prefixes="xsi xsl soap tns xsd ns0 plt wsdl db bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <db:ConsultaOportunidadesFiltrosEBSAdapterInput>
      <xsl:choose>
        <xsl:when test='/tns:oportunidadesClienteFiltros/nitCliente = ""'>
          <db:v_nit>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_nit>
        </xsl:when>
        <xsl:when test="not(boolean(/tns:oportunidadesClienteFiltros/nitCliente))">
          <db:v_nit>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_nit>
        </xsl:when>
        <xsl:otherwise>
          <db:v_nit>
            <xsl:value-of select="/tns:oportunidadesClienteFiltros/nitCliente"/>
          </db:v_nit>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='/tns:oportunidadesClienteFiltros/probabilidadExito = ""'>
          <db:v_probabilidad_exito_1>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_probabilidad_exito_1>
        </xsl:when>
        <xsl:when test="not(boolean(/tns:oportunidadesClienteFiltros/probabilidadExito))">
          <db:v_probabilidad_exito_1>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_probabilidad_exito_1>
        </xsl:when>
        <xsl:otherwise>
          <db:v_probabilidad_exito_1>
            <xsl:value-of select="/tns:oportunidadesClienteFiltros/probabilidadExito"/>
          </db:v_probabilidad_exito_1>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='/tns:oportunidadesClienteFiltros/probabilidadEjecucion = ""'>
          <db:v_probabilidad_ejecucion>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_probabilidad_ejecucion>
        </xsl:when>
        <xsl:when test="not(boolean(/tns:oportunidadesClienteFiltros/probabilidadEjecucion))">
          <db:v_probabilidad_ejecucion>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_probabilidad_ejecucion>
        </xsl:when>
        <xsl:otherwise>
          <db:v_probabilidad_ejecucion>
            <xsl:value-of select="/tns:oportunidadesClienteFiltros/probabilidadEjecucion"/>
          </db:v_probabilidad_ejecucion>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='/tns:oportunidadesClienteFiltros/idEtapaOportunidad = ""'>
          <db:v_etapa>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_etapa>
        </xsl:when>
        <xsl:when test="not(boolean(/tns:oportunidadesClienteFiltros/idEtapaOportunidad))">
          <db:v_etapa>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_etapa>
        </xsl:when>
        <xsl:otherwise>
          <db:v_etapa>
            <xsl:value-of select="/tns:oportunidadesClienteFiltros/idEtapaOportunidad"/>
          </db:v_etapa>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='"" = /tns:oportunidadesClienteFiltros/idAsesor'>
          <db:v_id_asesor>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_id_asesor>
        </xsl:when>
        <xsl:when test="not(boolean(/tns:oportunidadesClienteFiltros/idAsesor))">
          <db:v_id_asesor>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_id_asesor>
        </xsl:when>
        <xsl:otherwise>
          <db:v_id_asesor>
            <xsl:value-of select="/tns:oportunidadesClienteFiltros/idAsesor"/>
          </db:v_id_asesor>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="not(boolean(/tns:oportunidadesClienteFiltros/idEstadoOportunidad)) or (/tns:oportunidadesClienteFiltros/idEstadoOportunidad = '')">
          <db:v_estado>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_estado>
        </xsl:when>
        <xsl:otherwise>
          <db:v_estado>
            <xsl:value-of select="/tns:oportunidadesClienteFiltros/idEstadoOportunidad"/>
          </db:v_estado>
        </xsl:otherwise>
      </xsl:choose>
    </db:ConsultaOportunidadesFiltrosEBSAdapterInput>
  </xsl:template>
</xsl:stylesheet>
