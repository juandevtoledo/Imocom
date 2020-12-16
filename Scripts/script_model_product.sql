insert into recurso (ID_RECURSO,recurso_nombre) values (74,'/vive/pages/secured/mobile/productos/consultor-productos.jsf');
insert into ROL_RECURSO (ID_ROL_RECURSO, id_rol, id_recurso) values (100,1,74);
insert into menu (ID_MENU,MENU_NOMBRE,MENU_ACCION,MENU_ORDEN) VALUES (27,'Consulta Producto','outcome:productos-consulta',1);
INSERT INTO ROL_MENU (ID_ROL_MENU,ID_ROL,ID_MENU) VALUES (47,1,27);
--
insert into recurso (ID_RECURSO,recurso_nombre) values (75,'/vive/pages/secured/mobile/productos/crear_producto.jsf');
insert into ROL_RECURSO (ID_ROL_RECURSO, id_rol, id_recurso) values (101,1,75);
--
insert into recurso (ID_RECURSO,recurso_nombre) values (76,'/vive/pages/secured/mobile/modificacion-masiva/oportunida-cliente-editar.jsf');
insert into ROL_RECURSO (ID_ROL_RECURSO, id_rol, id_recurso) values (102,1,76);
insert into menu (ID_MENU,MENU_NOMBRE,MENU_ACCION,MENU_ORDEN) VALUES (28,'Modificacion Masiva','outcome:modificacion-masiva',1);
INSERT INTO ROL_MENU (ID_ROL_MENU,ID_ROL,ID_MENU) VALUES (48,1,28);
--
CREATE SEQUENCE  "SEQ_PRODUCTO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "SEQ_MODELO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "SEQ_MARCA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--
CREATE TABLE "MARCA" 
(
	"ID_MARCA" NUMBER NOT NULL ENABLE, 
	"NOMBRE" VARCHAR(100),
	"LINEA" VARCHAR2(100),
	 CONSTRAINT "PK_MARCA" PRIMARY KEY ("ID_MARCA")
);
--
CREATE TABLE "MODELO" 
(
	"ID_MODELO" NUMBER NOT NULL ENABLE, 
	"NOMBRE"   VARCHAR(100),
	"ID_MARCA" NUMBER NOT NULL,
	 CONSTRAINT "PK_MODELO" PRIMARY KEY ("ID_MODELO"),
	 CONSTRAINT "FK_MARCA" FOREIGN KEY ("ID_MARCA")
	  REFERENCES "MARCA" ("ID_MARCA")
);		
--
CREATE TABLE "MODELO" 
(
	"ID_MODELO" NUMBER NOT NULL ENABLE, 
	"NOMBRE"   VARCHAR(100),
	"ID_MARCA" NUMBER NOT NULL,
	 CONSTRAINT "PK_MODELO" PRIMARY KEY ("ID_MODELO"),
	 CONSTRAINT "FK_MARCA" FOREIGN KEY ("ID_MARCA")
	  REFERENCES "MARCA" ("ID_MARCA")
);	
--
CREATE TABLE "TIPO_PRODUCTO" 
(
	"ID_TIPO" NUMBER NOT NULL ENABLE, 
	"NOMBRE"   VARCHAR(100),
	CONSTRAINT "PK_TIPO_PRODUCTO" PRIMARY KEY ("ID_TIPO")
);
--	
CREATE TABLE "MONEDA" 
(
	"ID_MONEDA" NUMBER NOT NULL ENABLE, 
	"NOMBRE"   VARCHAR(100),
    CONSTRAINT "PK_MONEDA" PRIMARY KEY ("ID_MONEDA")
);
--
CREATE TABLE "PRODUCTO"
(
	"ID_PRODUCTO" NUMBER NOT NULL ENABLE, 
	"ID_TIPO" NUMBER NOT NULL ENABLE, 
	"CODIGO" VARCHAR2(100 BYTE), 
	"DESCRIPCION" VARCHAR2(200 BYTE), 
	"ID_MODELO" NUMBER NOT NULL ENABLE, 
	"ID_MONEDA" NUMBER NOT NULL ENABLE, 
	"PRECIO" NUMBER(16,4), 
	"ESTADO" VARCHAR2(10 BYTE), 
	"INCOTERM" VARCHAR2(200 BYTE),
	"COTIZABLE" NUMBER(*,0), 
	"CATALOGO" NUMBER(*,0), 
	"CENTRO_COSTOS" VARCHAR2(500 BYTE)
	CONSTRAINT "PK_PRODUCTO" PRIMARY KEY ("ID_PRODUCTO"),
	CONSTRAINT "FK_TIPO_PRODUCTO" FOREIGN KEY ("ID_TIPO") REFERENCES "TIPO_PRODUCTO" ("ID_TIPO"),
	CONSTRAINT "FK_ID_MODELO" FOREIGN KEY ("ID_MODELO") REFERENCES "MODELO" ("ID_MODELO"),
	CONSTRAINT "FK_ID_MONEDA" FOREIGN KEY ("ID_MONEDA") REFERENCES "MONEDA" ("ID_MONEDA")	
); 

insert into TIPO_PRODUCTO (ID_TIPO, NOMBRE) values (1,'MAQUINA');
insert into TIPO_PRODUCTO (ID_TIPO, NOMBRE) values (2,'ACCESORIO');
insert into TIPO_PRODUCTO (ID_TIPO, NOMBRE) values (3,'REPUESTO');
create  index producto_modelo
  on PRODUCTO(ID_MODELO);
create  index modelo_marca
  on MODELO(ID_MARCA);  
create  index modelo_marca
  on MODELO(ID_MARCA);  
create  index marca_linea
  on MARCA(LINEA);  
create  index producto_descripcion
  on PRODUCTO(descripcion);  
  --
 
 Insert into SERVICIOS_EBS (ID_SERVICIO,SERVICIO,DESCRIPCION,URL_WSDL,QNAME_SERVICIO,INTERFAZ_ENDPOINT,METODO,NAMESPACE,NUMERO_PARAMETROS,TIPO_INTERFAZ,ENTIDAD_PETICION,ESTADO) values ('40','EBS_WS_OPORTUNIDADESCONSULTAASESORMASIVO','Servicio que devuelve las oportunidades del asesor para actualización masiva','http://192.168.0.33:8001/soa-infra/services/default/Oportunidades/ConsultaOportunidadesEBS?WSDL','ConsultaOportunidadesEBS','com.imocom.intelcom.ws.ebs.interfaces.IOportunidadesEBS','oportunidadesConsultaAsesorMasivo','http://com.imocom.intelcom.ws.ebs.interfaces/','1','SERV',null,'A');
 
 
 
 Insert into SERVICIOS_EBS (ID_SERVICIO,SERVICIO,DESCRIPCION,URL_WSDL,QNAME_SERVICIO,INTERFAZ_ENDPOINT,METODO,NAMESPACE,NUMERO_PARAMETROS,TIPO_INTERFAZ,ENTIDAD_PETICION,ESTADO) values ('41','EBS_WS_OPORTUNIDADESASIGNARASESOR','Servicio que asigna asesor las oportunidades','http://192.168.0.33:8001/soa-infra/services/default/Oportunidades/ConsultaOportunidadesEBS?WSDL','ConsultaOportunidadesEBS','com.imocom.intelcom.ws.ebs.interfaces.IOportunidadesEBS','oportunidadesActualizarAsesor','http://com.imocom.intelcom.ws.ebs.interfaces/','2','SERV',null,'A');
 
  
  Insert into SERVICIOS_EBS (ID_SERVICIO,SERVICIO,DESCRIPCION,URL_WSDL,QNAME_SERVICIO,INTERFAZ_ENDPOINT,METODO,NAMESPACE,NUMERO_PARAMETROS,TIPO_INTERFAZ,ENTIDAD_PETICION,ESTADO) values ('42','EBS_WS_CLIENTEASIGNARASESOR','Servicio que asigna asesor a cliente','http://192.168.0.33:8001/soa-infra/services/default/Clientes/ClientesEBS?WSDL','ClientesEBS','com.imocom.intelcom.ws.ebs.interfaces.IClientesEBS','actualizarAsesorCliente','http://com.imocom.intelcom.ws.ebs.interfaces/','3','SERV',null,'A');
  
   
 Insert into SERVICIOS_EBS (ID_SERVICIO,SERVICIO,DESCRIPCION,URL_WSDL,QNAME_SERVICIO,INTERFAZ_ENDPOINT,METODO,NAMESPACE,NUMERO_PARAMETROS,TIPO_INTERFAZ,ENTIDAD_PETICION,ESTADO) values ('43','EBS_WS_OPORTUNIDADACTUALIZAR','Servicio que asigna asesor las oportunidades','http://192.168.0.33:8001/soa-infra/services/default/Oportunidades/ConsultaOportunidadesEBS?WSDL','ConsultaOportunidadesEBS','com.imocom.intelcom.ws.ebs.interfaces.IOportunidadesEBS','actualizarOportunidad','http://com.imocom.intelcom.ws.ebs.interfaces/','8','SERV',null,'A');