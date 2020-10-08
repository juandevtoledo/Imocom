<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../CotizacionesBPM.wsdl"/>
      <rootElement name="procesoCotizaciones" namespace="http://com.imocom.intelcom.ws.ebs.interfaces/"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="http://192.168.0.25:8001/soa-infra/services/default/comercialProject/CotizacionInterface.wsdl"/>
      <rootElement name="entradaCotizacion" namespace="http://www.imocom.com.co/soa/xsd/gestionCotizacion"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 140919.1004.0161) AT [THU JAN 28 15:18:04 COT 2016]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:inp1="http://www.imocom.com.co/soa/xsd/gestionCotizacion"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:ns3="http://www.imocom.com.co/soa/xsd/usuario"
                xmlns:ns0="http://oracle.com/sca/soapservice/ImocomBPM/comercialProject/CotizacionInterface"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:ns1="http://www.imocom.com.co/soa/xsd/generico"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:ns2="http://www.imocom.com.co/soa/xsd/producto"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:ns4="http://www.imocom.com.co/soa/xsd/cotizacion"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:tns="http://com.imocom.intelcom.ws.ebs.interfaces/"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl soap tns xsd inp1 ns3 ns0 wsdl ns1 ns2 ns4 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <inp1:entradaCotizacion>
      <inp1:nombreUsuario>
        <xsl:value-of select="/tns:procesoCotizaciones/request/nombreUsuario"/>
      </inp1:nombreUsuario>
      <inp1:cotizacion>
        <ns4:requiereAprobacion>
          <xsl:value-of select="/tns:procesoCotizaciones/request/requiereAprobacion"/>
        </ns4:requiereAprobacion>
        <ns4:codigoCotizacion>
          <xsl:value-of select="/tns:procesoCotizaciones/request/codigo"/>
        </ns4:codigoCotizacion>
        <ns4:idOportunidad>
          <xsl:value-of select="/tns:procesoCotizaciones/request/idOportunidad"/>
        </ns4:idOportunidad>
        <ns4:nombreOportunidad>
          <xsl:value-of select="/tns:procesoCotizaciones/request/nombreOportunidad"/>
        </ns4:nombreOportunidad>
        <ns4:idCliente>
          <xsl:value-of select="/tns:procesoCotizaciones/request/idCliente"/>
        </ns4:idCliente>
        <ns4:nombreCliente>
          <xsl:value-of select="/tns:procesoCotizaciones/request/nombreCliente"/>
        </ns4:nombreCliente>
        <xsl:for-each select="/tns:procesoCotizaciones/request/cotizacionProducto">
          <ns4:producto>
            <xsl:if test="idCotizacionProducto">
              <ns4:idCotizacionProducto>
                <xsl:value-of select="idCotizacionProducto"/>
              </ns4:idCotizacionProducto>
            </xsl:if>
            <xsl:if test="nombreProducto">
              <ns4:nombreProducto>
                <xsl:value-of select="nombreProducto"/>
              </ns4:nombreProducto>
            </xsl:if>
            <xsl:if test="idProducto">
              <ns4:idProducto>
                <xsl:value-of select="idProducto"/>
              </ns4:idProducto>
            </xsl:if>
            <xsl:if test="cantidad">
              <ns4:cantidad>
                <xsl:value-of select="cantidad"/>
              </ns4:cantidad>
            </xsl:if>
            <xsl:if test="valorUnitario">
              <ns4:valorUnitario>
                <xsl:value-of select="valorUnitario"/>
              </ns4:valorUnitario>
            </xsl:if>
            <xsl:if test="marca">
              <ns4:marca>
                <xsl:value-of select="marca"/>
              </ns4:marca>
            </xsl:if>
            <xsl:if test="modelo">
              <ns4:modelo>
                <xsl:value-of select="modelo"/>
              </ns4:modelo>
            </xsl:if>
            <xsl:if test="tipoProducto">
              <ns4:tipoProducto>
                <xsl:value-of select="tipoProducto"/>
              </ns4:tipoProducto>
            </xsl:if>
            <xsl:if test="centroCostos">
              <ns4:centroCostos>
                <xsl:value-of select="centroCostos"/>
              </ns4:centroCostos>
            </xsl:if>
            <xsl:if test="bodega">
              <ns4:bodega>
                <xsl:value-of select="bodega"/>
              </ns4:bodega>
            </xsl:if>
            <xsl:if test="unidad">
              <ns4:unidad>
                <xsl:value-of select="unidad"/>
              </ns4:unidad>
            </xsl:if>
          </ns4:producto>
        </xsl:for-each>
        <ns4:idIncoterm>
          <xsl:value-of select="/tns:procesoCotizaciones/request/idInconterm"/>
        </ns4:idIncoterm>
        <ns4:fechaLimiteGeneracion>
          <xsl:value-of select="/tns:procesoCotizaciones/request/fechLimiteGeneracion"/>
        </ns4:fechaLimiteGeneracion>
        <ns4:tipoOferta>
          <xsl:value-of select="/tns:procesoCotizaciones/request/tipoOferta"/>
        </ns4:tipoOferta>
        <ns4:linea>
          <xsl:value-of select="/tns:procesoCotizaciones/request/linea"/>
        </ns4:linea>
        <ns4:idEstadoCotizacion>
          <xsl:value-of select="/tns:procesoCotizaciones/request/idEstadoCotizacion"/>
        </ns4:idEstadoCotizacion>
        <ns4:ObservAsesor>
          <xsl:value-of select="/tns:procesoCotizaciones/request/observAsesor"/>
        </ns4:ObservAsesor>
        <ns4:idArchivoAdjSolicitud>
          <xsl:value-of select="/tns:procesoCotizaciones/request/idArchivoAdjSolicitud"/>
        </ns4:idArchivoAdjSolicitud>
        <ns4:nombreArchivoAdjSolicitud>
          <xsl:value-of select="/tns:procesoCotizaciones/request/nombreArchivoAdjSolicitud"/>
        </ns4:nombreArchivoAdjSolicitud>
        <ns4:urlArchivoAdjunto>
          <xsl:value-of select="/tns:procesoCotizaciones/request/urlContentArchivoAdjSolicitud"/>
        </ns4:urlArchivoAdjunto>
        <ns4:idTipoMoneda>
          <xsl:value-of select="/tns:procesoCotizaciones/request/idTipoMoneda"/>
        </ns4:idTipoMoneda>
        <ns4:valorTotal>
          <xsl:value-of select="/tns:procesoCotizaciones/request/valorTotal"/>
        </ns4:valorTotal>
        <ns4:fechaSolicitud>
          <xsl:value-of select="/tns:procesoCotizaciones/request/fechaSolicitud"/>
        </ns4:fechaSolicitud>
        <ns4:idVisitaGenerador>
          <xsl:value-of select="/tns:procesoCotizaciones/request/idVisitaGenerador"/>
        </ns4:idVisitaGenerador>
        <ns4:dirigida>
          <xsl:value-of select="/tns:procesoCotizaciones/request/dirigida"/>
        </ns4:dirigida>
        <ns4:telefono>
          <xsl:value-of select="/tns:procesoCotizaciones/request/telefono"/>
        </ns4:telefono>
        <ns4:direccion>
          <xsl:value-of select="/tns:procesoCotizaciones/request/direccion"/>
        </ns4:direccion>
        <ns4:correo>
          <xsl:value-of select="/tns:procesoCotizaciones/request/correo"/>
        </ns4:correo>
      </inp1:cotizacion>
    </inp1:entradaCotizacion>
  </xsl:template>
</xsl:stylesheet>