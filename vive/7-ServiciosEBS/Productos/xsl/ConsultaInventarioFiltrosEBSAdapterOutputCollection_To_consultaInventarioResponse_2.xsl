<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../ConsultaInventarioFiltrosEBSAdapter.wsdl"/>
      <rootElement name="ConsultaInventarioFiltrosEBSAdapterOutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaInventarioFiltrosEBSAdapter"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../ProductosEBS.wsdl"/>
      <rootElement name="consultaInventarioResponse" namespace="http://com.imocom.intelcom.ws.ebs.interfaces/"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 140919.1004.0161) AT [WED FEB 11 13:55:20 COT 2015]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaInventarioFiltrosEBSAdapter"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/ServiciosEBS/Productos/ConsultaInventarioFiltrosEBSAdapter"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:ns0="http://com.imocom.intelcom.ws.ebs.interfaces/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl db plt wsdl tns xsd soap ns0 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <ns0:consultaInventarioResponse>
      <return>
        <xsl:if test="count(/db:ConsultaInventarioFiltrosEBSAdapterOutputCollection/db:ConsultaInventarioFiltrosEBSAdapterOutput) &lt;= 50.0">
          <xsl:for-each select="/db:ConsultaInventarioFiltrosEBSAdapterOutputCollection/db:ConsultaInventarioFiltrosEBSAdapterOutput">
            <productos>
              <codigo>
                <xsl:value-of select="db:INVENTORY_ITEM_ID"/>
              </codigo>
              <nombre>
                <xsl:value-of select="db:DESCRIPTION"/>
              </nombre>
              <division>
                <xsl:value-of select="db:BODEGA"/>
              </division>
              <linea>
                <xsl:value-of select="db:CENTRO_COSTOS_DESC"/>
              </linea>
              <familia>
                <xsl:value-of select="db:FAMILIA"/>
              </familia>
              <marca>
                <xsl:value-of select="db:MARCA"/>
              </marca>
              <cantidad>
                <xsl:value-of select="db:SALDO"/>
              </cantidad>
              <precioLista>
                <xsl:value-of select="db:PRECIO_LISTA"/>
              </precioLista>
              <descripcion>
                <xsl:value-of select="db:DESCRIPTION"/>
              </descripcion>
              <modelo>
                <xsl:value-of select="db:REFERENCIA"/>
              </modelo>
              <tipoProducto>
                <xsl:value-of select="db:TIPO_PRODUCTO"/>
              </tipoProducto>
              <bodega>
                <xsl:value-of select="db:BODEGA"/>
              </bodega>
              <unidad>
                <xsl:value-of select="db:UNIDAD"/>
              </unidad>
            </productos>
          </xsl:for-each>
        </xsl:if>
        <status>
          <xsl:value-of select="count(/db:ConsultaInventarioFiltrosEBSAdapterOutputCollection/db:ConsultaInventarioFiltrosEBSAdapterOutput) &lt;= 50.0"/>
        </status>
      </return>
    </ns0:consultaInventarioResponse>
  </xsl:template>
</xsl:stylesheet>
