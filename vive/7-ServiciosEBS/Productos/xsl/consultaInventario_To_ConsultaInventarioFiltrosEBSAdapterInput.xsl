<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../ProductosEBS.wsdl"/>
      <rootElement name="consultaInventario" namespace="http://com.imocom.intelcom.ws.ebs.interfaces/"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../ConsultaInventarioFiltrosEBSAdapter.wsdl"/>
      <rootElement name="ConsultaInventarioFiltrosEBSAdapterInput" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/ConsultaInventarioFiltrosEBSAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 140919.1004.0161) AT [MON JUL 06 16:58:18 COT 2015]. -->
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
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/ServiciosEBS/Productos/ConsultaInventarioFiltrosEBSAdapter"
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
    <db:ConsultaInventarioFiltrosEBSAdapterInput>
      <xsl:choose>
        <xsl:when test='/tns:consultaInventario/tipoProducto = ""'>
          <db:v_tipo_producto>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_tipo_producto>
        </xsl:when>
        <xsl:when test="not(boolean(/tns:consultaInventario/tipoProducto))">
          <db:v_tipo_producto>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_tipo_producto>
        </xsl:when>
        <xsl:otherwise>
          <db:v_tipo_producto>
            <xsl:value-of select="/tns:consultaInventario/tipoProducto"/>
          </db:v_tipo_producto>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='not(boolean(/tns:consultaInventario/linea)) or (/tns:consultaInventario/linea = "")'>
          <db:v_linea>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_linea>
        </xsl:when>
        <xsl:otherwise>
          <db:v_linea>
            <xsl:value-of select="/tns:consultaInventario/linea"/>
          </db:v_linea>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='/tns:consultaInventario/marca = ""'>
          <db:v_marca>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_marca>
        </xsl:when>
        <xsl:when test="not(boolean(/tns:consultaInventario/marca))">
          <db:v_marca>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_marca>
        </xsl:when>
        <xsl:otherwise>
          <db:v_marca>
            <xsl:value-of select="/tns:consultaInventario/marca"/>
          </db:v_marca>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='/tns:consultaInventario/modelo = ""'>
          <db:vmodelo>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:vmodelo>
        </xsl:when>
        <xsl:when test="not(boolean(/tns:consultaInventario/modelo))">
          <db:vmodelo>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:vmodelo>
        </xsl:when>
        <xsl:otherwise>
          <db:vmodelo>
            <xsl:value-of select="/tns:consultaInventario/modelo"/>
          </db:vmodelo>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='/tns:consultaInventario/descripcion = ""'>
          <db:v_descripcion>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_descripcion>
        </xsl:when>
        <xsl:when test="not(boolean(/tns:consultaInventario/descripcion))">
          <db:v_descripcion>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_descripcion>
        </xsl:when>
        <xsl:otherwise>
          <db:v_descripcion>
            <xsl:value-of select='translate(xp20:upper-case(/tns:consultaInventario/descripcion)," ","%")'/>
          </db:v_descripcion>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='not(boolean(/tns:consultaInventario/organizationId)) or (/tns:consultaInventario/organizationId = "")'>
          <db:v_organization_id>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_organization_id>
        </xsl:when>
        <xsl:otherwise>
          <db:v_organization_id>
            <xsl:value-of select="/tns:consultaInventario/organizationId"/>
          </db:v_organization_id>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test='not(boolean(/tns:consultaInventario/subinventario)) or (/tns:consultaInventario/subinventario = "")'>
          <db:v_subinventario>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_subinventario>
        </xsl:when>
        <xsl:otherwise>
          <db:v_subinventario>
            <xsl:value-of select="/tns:consultaInventario/subinventario"/>
          </db:v_subinventario>
        </xsl:otherwise>
      </xsl:choose>
	  <xsl:choose>
			<xsl:when test='not(boolean(/tns:consultaInventario/cotizable)) or (/tns:consultaInventario/cotizable = "")'>
				<db:v_cotizable>
						<xsl:text disable-output-escaping="no">%</xsl:text>
				</db:v_cotizable>
			</xsl:when>	
			 <xsl:otherwise>
				<db:v_cotizable>
					<xsl:value-of select="/tns:consultaInventario/cotizable"/>
				</db:v_cotizable>
			</xsl:otherwise>		 
	  </xsl:choose>
      <xsl:choose>
        <xsl:when test='not(boolean(/tns:consultaInventario/catalogo)) or (/tns:consultaInventario/catalogo = "")'>
          <db:v_catalago>
            <xsl:text disable-output-escaping="no">%</xsl:text>
          </db:v_catalago>
        </xsl:when>
        <xsl:otherwise>
          <db:v_catalago>
            <xsl:value-of select="/tns:consultaInventario/catalogo"/>
          </db:v_catalago>
        </xsl:otherwise>
      </xsl:choose>
    </db:ConsultaInventarioFiltrosEBSAdapterInput>
  </xsl:template>
</xsl:stylesheet>
