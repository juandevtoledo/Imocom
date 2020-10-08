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
      <schema location="http://192.168.0.25:8001/soa-infra/services/default/comercialProject/actualizarOportunidadBPM.wsdl"/>
      <rootElement name="actualizarOportunidad" namespace="http://www.imocom.com.co/soa/xsd/gestionOportunidad"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.7.0(build 140919.1004.0161) AT [MON FEB 22 14:28:55 COT 2016]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:ns1="http://www.imocom.com.co/soa/xsd/oportunidad"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:ns0="http://oracle.com/sca/soapservice/ImocomBPM/comercialProject/actualizarOportunidadBPM"
                xmlns:ns2="http://www.imocom.com.co/soa/xsd/generico"
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
                xmlns:inp1="http://www.imocom.com.co/soa/xsd/gestionOportunidad"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl soap tns xsd ns1 wsdl ns0 ns2 inp1 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <inp1:actualizarOportunidad>
      <ns1:nombreUsuario>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/nombreUsuario"/>
      </ns1:nombreUsuario>
      <ns1:idOportunidad>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idOportunidad"/>
      </ns1:idOportunidad>
      <ns1:nombreOportunidad>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/nombreOportunidad"/>
      </ns1:nombreOportunidad>
      <ns1:idEtapaOportunidad>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idEtapaOportunidad"/>
      </ns1:idEtapaOportunidad>
      <ns1:codigoEstadoOportunidad>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idEstadoOportunidad"/>
      </ns1:codigoEstadoOportunidad>
      <ns1:fechaEstimada>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/fechaEstimadaCierre"/>
      </ns1:fechaEstimada>
      <ns1:razonDeCierre>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/motivoCierre"/>
      </ns1:razonDeCierre>
      <ns1:channelCode>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idCanalEntrada"/>
      </ns1:channelCode>
      <ns1:valorTipoMoneda>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idTipoMoneda"/>
      </ns1:valorTipoMoneda>
      <ns1:monto>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/monto"/>
      </ns1:monto>
      <ns1:PROB_OPORCLIE>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/probabilidadProyecto"/>
      </ns1:PROB_OPORCLIE>
      <ns1:PROB_OPORIMO>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/probabilidadImocom"/>
      </ns1:PROB_OPORIMO>
      <ns1:id_archivo_ord_compra>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idArchivo"/>
      </ns1:id_archivo_ord_compra>
      <ns1:nombre_archivo_ord_compra>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/nombreArchivo"/>
      </ns1:nombre_archivo_ord_compra>
      <ns1:id_archivo_ord_pedido>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/idArchivoPedido"/>
      </ns1:id_archivo_ord_pedido>
      <ns1:nombre_archivo_ord_pedido>
        <xsl:value-of select="/tns:procesoActualizacionOportunidades/request/nombreArchivoPedido"/>
      </ns1:nombre_archivo_ord_pedido>
    </inp1:actualizarOportunidad>
  </xsl:template>
</xsl:stylesheet>
