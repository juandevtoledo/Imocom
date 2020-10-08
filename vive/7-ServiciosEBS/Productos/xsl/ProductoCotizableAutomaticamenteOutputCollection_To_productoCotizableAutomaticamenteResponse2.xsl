<?xml version='1.0' encoding='UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/ProductoCotizableAutomaticamente" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/ServiciosEBS/Productos/ProductoCotizableAutomaticamente" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:ns0="http://com.imocom.intelcom.ws.ebs.interfaces/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" exclude-result-prefixes="xsi xsl db plt wsdl tns xsd soap ns0 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
   <xsl:template match="/">
      <ns0:productoCotizableAutomaticamenteResponse>
         <productos>
            <codigo>
               <xsl:value-of select="/db:ProductoCotizableAutomaticamenteOutputCollection/db:ProductoCotizableAutomaticamenteOutput/db:INVENTORY_ITEM_ID"/>
            </codigo>
            <marca>
               <xsl:value-of select="/db:ProductoCotizableAutomaticamenteOutputCollection/db:ProductoCotizableAutomaticamenteOutput/db:MARCA"/>
            </marca>
            <precioLista>
               <xsl:value-of select="/db:ProductoCotizableAutomaticamenteOutputCollection/db:ProductoCotizableAutomaticamenteOutput/db:PRECIO_LISTA_DDP"/>
            </precioLista>
            <moneda>
               <xsl:value-of select="/db:ProductoCotizableAutomaticamenteOutputCollection/db:ProductoCotizableAutomaticamenteOutput/db:MONEDA_ORIGEN"/>
            </moneda>
            <descripcion>
               <xsl:value-of select="/db:ProductoCotizableAutomaticamenteOutputCollection/db:ProductoCotizableAutomaticamenteOutput/db:DESCRIPTION"/>
            </descripcion>
            <modelo>
               <xsl:value-of select="/db:ProductoCotizableAutomaticamenteOutputCollection/db:ProductoCotizableAutomaticamenteOutput/db:MODELO"/>
            </modelo>
            <tipoProducto>
               <xsl:value-of select="/db:ProductoCotizableAutomaticamenteOutputCollection/db:ProductoCotizableAutomaticamenteOutput/db:TIPO_PRODUCTO"/>
            </tipoProducto>
            <unidad>
               <xsl:value-of select="/db:ProductoCotizableAutomaticamenteOutputCollection/db:ProductoCotizableAutomaticamenteOutput/db:UNIDAD"/>
            </unidad>
            <organizationId>
               <xsl:value-of select="/db:ProductoCotizableAutomaticamenteOutputCollection/db:ProductoCotizableAutomaticamenteOutput/db:COTIZABLE_AUTOMATICAMENTE"/>
            </organizationId>
         </productos>
      </ns0:productoCotizableAutomaticamenteResponse>
   </xsl:template>
</xsl:stylesheet>
