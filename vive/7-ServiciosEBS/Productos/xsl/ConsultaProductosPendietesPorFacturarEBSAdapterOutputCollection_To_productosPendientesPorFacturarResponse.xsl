<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../ConsultaProductosPendietesPorFacturarEBSAdapter.wsdl"/>
      <rootElement name="ConsultaProductosPendietesPorFacturarEBSAdapterOutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaProductosPendietesPorFacturarEBSAdapter"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../ProductosEBS.wsdl"/>
      <rootElement name="productosPendientesPorFacturarResponse" namespace="http://com.imocom.intelcom.ws.ebs.interfaces/"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 140919.1004.0161) AT [WED APR 15 14:11:02 COT 2015]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
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
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaProductosPendietesPorFacturarEBSAdapter"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:ns0="http://com.imocom.intelcom.ws.ebs.interfaces/"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/ServiciosEBS/Productos/ConsultaProductosPendietesPorFacturarEBSAdapter"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl db plt xsd wsdl tns ns0 soap xp20 bpws mhdr bpel oraext dvm hwf med ids bpm xdk xref ora socket ldap">
  <xsl:template match="/">
    <ns0:productosPendientesPorFacturarResponse>
      <return>
        <xsl:for-each select="/db:ConsultaProductosPendietesPorFacturarEBSAdapterOutputCollection/db:ConsultaProductosPendietesPorFacturarEBSAdapterOutput">
          <productos>
            <codigo>
              <xsl:value-of select="db:ORDEN_NO"/>
            </codigo>
            <nombre>
              <xsl:value-of select="db:descripcion"/>
            </nombre>
            <linea>
              <xsl:value-of select="db:LINEA"/>
            </linea>
            <descripcion>
              <xsl:value-of select="db:descripcion"/>
            </descripcion>
            <fecha>
              <xsl:value-of select="db:FECHA"/>
            </fecha>
            <asesor>
              <xsl:value-of select="db:asesor"/>
            </asesor>
          </productos>
        </xsl:for-each>
      </return>
    </ns0:productosPendientesPorFacturarResponse>
  </xsl:template>
</xsl:stylesheet>