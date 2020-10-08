<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../ConsultaClientesByFiltrosEBSAdapter.wsdl"/>
      <rootElement name="ConsultaClientesByFiltrosEBSAdapterOutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaClientesByFiltrosEBSAdapter"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../ClientesEBS.wsdl"/>
      <rootElement name="consultaClienteNitResponse" namespace="http://com.imocom.intelcom.ws.ebs.interfaces/"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 150407.0554.0203) AT [FRI MAY 22 11:08:23 COT 2015]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
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
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaClientesByFiltrosEBSAdapter"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:ns0="http://com.imocom.intelcom.ws.ebs.interfaces/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/ServiciosEBS/Clientes/ConsultaClientesByFiltrosEBSAdapter"
                exclude-result-prefixes="xsi xsl plt wsdl db xsd tns soap ns0 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <ns0:consultaClienteNitResponse>
      <return>
        <xsl:for-each select="/db:ConsultaClientesByFiltrosEBSAdapterOutputCollection/db:ConsultaClientesByFiltrosEBSAdapterOutput">
          <clientes>
            <partyId>
              <xsl:value-of select="db:PARTY_ID"/>
            </partyId>
            <nombreCliente>
              <xsl:value-of select="db:PARTY_NAME"/>
            </nombreCliente>
            <fechaCreacion>
              <xsl:value-of select="db:FECHA_CREACION"/>
            </fechaCreacion>
            <nitCliente>
              <xsl:value-of select="db:NIT"/>
            </nitCliente>
            <ciudad>
              <xsl:value-of select="db:CIUDAD"/>
            </ciudad>
            <departamento>
              <xsl:value-of select="db:DEPARTAMENTO"/>
            </departamento>
            <urlCliente>
              <xsl:value-of select="db:URL"/>
            </urlCliente>
            <correoCliente>
              <xsl:value-of select="db:CORREO"/>
            </correoCliente>
            <direccionPpal>
              <xsl:value-of select="db:DIRECCION"/>
            </direccionPpal>
            <telefonoPpal>
              <xsl:value-of select="/db:ConsultaClientesByFiltrosEBSAdapterOutputCollection/db:ConsultaClientesByFiltrosEBSAdapterOutput/db:PRIMARY_PHONE_NUMBER"/>
            </telefonoPpal>
            <tipoCliente>
              <xsl:value-of select="db:TIPO_CLIENTE"/>
            </tipoCliente>
            <xsl:choose>
              <xsl:when test='not(boolean(db:ACCESS_USER)) or (db:ACCESS_USER = "")'>
                <diasSinVisita>
                  <xsl:text disable-output-escaping="no">-1</xsl:text>
                </diasSinVisita>
              </xsl:when>
              <xsl:otherwise>
                <diasSinVisita>
                  <xsl:value-of select="db:DIAS_SIN_VISITA"/>
                </diasSinVisita>
              </xsl:otherwise>
            </xsl:choose>
            <pais>
              <xsl:value-of select="db:PAIS"/>
            </pais>
            <semaforo>
              <xsl:value-of select="xp20:upper-case(db:SEMAFORO)"/>
            </semaforo>
          </clientes>
        </xsl:for-each>
      </return>
    </ns0:consultaClienteNitResponse>
  </xsl:template>
</xsl:stylesheet>