<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../DetalleClienteNit.wsdl"/>
      <rootElement name="DetalleClienteNitOutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/DetalleClienteNit"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../ClientesEBS.wsdl"/>
      <rootElement name="detalleClienteNitResponse" namespace="http://com.imocom.intelcom.ws.ebs.interfaces/"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 150407.0554.0203) AT [MON JUN 01 19:56:20 COT 2015]. -->
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
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:tns="http://com.imocom.intelcom.ws.ebs.interfaces/"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/DetalleClienteNit"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl tns plt xsd wsdl db soap bpws xp20 mhdr bpel oraext dvm hwf med ids bpm xdk xref ora socket ldap">
  <xsl:template match="/">
    <tns:detalleClienteNitResponse>
      <return>
        <clientes>
          <partyId>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:PARTY_ID"/>
          </partyId>
          <partyNumber>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:PARTY_NUMBER"/>
          </partyNumber>
          <nombreCliente>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:NOMBRE"/>
          </nombreCliente>
          <fechaCreacion>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:fecha_creacion"/>
          </fechaCreacion>
          <nitCliente>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:NIT"/>
          </nitCliente>
          <ciudad>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:CIUDAD"/>
          </ciudad>
          <departamento>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:DEPARTAMENTO"/>
          </departamento>
          <urlCliente>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:URL"/>
          </urlCliente>
          <correoCliente>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:CORREO"/>
          </correoCliente>
          <direccionPpal>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:DIRECCION"/>
          </direccionPpal>
          <telefonoPpal>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:TELEFONO"/>
          </telefonoPpal>
          <tipoCliente>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:TIPO_CLIENTE"/>
          </tipoCliente>
          <pais>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:PAIS"/>
          </pais>
          <apellidoGerenteGeneral>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:APELLIDO_G_GRAL"/>
          </apellidoGerenteGeneral>
          <indicativoContacto>
            <xsl:value-of select="/db:DetalleClienteNitOutputCollection/db:DetalleClienteNitOutput/db:INDICATIVO"/>
          </indicativoContacto>
        </clientes>
      </return>
    </tns:detalleClienteNitResponse>
  </xsl:template>
</xsl:stylesheet>