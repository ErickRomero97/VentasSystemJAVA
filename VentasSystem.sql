
-- Inserciones de Datos
insert into tipofactura(tipofactura)
values('Credito');
select * from cliente;
select * from proveedor; 
select * from tipofactura;
insert into sexo(sexo)
values('Masculino');

insert into proveedor(idproveedor,nombre,apellido,direccion,telefono,idsexo)
values('1709-1995-00000','Roger','Martinez','Nacaome','9933-1234',2);

select * from producto;

insert into producto(nombreproducto,unidadexistencia,existenciaminima,preciocompra,precioventa,idproveedor)
values('Manteca',20,5,100,130,'1709-1995-00000');

select * from usuario;
insert into usuario(nombre,contraseña,estado,idempleado)
values ('any','1234',1,'1709-1996-00038');

select * from factura;

insert into factura(fechafactura,idtipofactura,rtncliente,idusuario)
values ('2016-12-15',2,'1709-1995-000380',1);

select * from empleado;


-- Procemientos de la Relacion Cliente

-- Procedimiento de Listar Cliente
delimiter $$
create procedure sp_listarcliente()
	begin
		Select a.rtncliente,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo 
		from cliente a inner join sexo s on a.idsexo = s.idsexo;
    end $$
delimiter ;

-- Procedimiento de Busqueda de Cliente
delimiter $$
create procedure sp_buscarcliente1(in pfiltro varchar(50))
	begin
		Select a.rtncliente,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo 
		from cliente a inner join sexo s on a.idsexo = s.idsexo 
        where a.nombre Like concat('%',pfiltro,'%')  or a.rtncliente Like concat('%',pfiltro,'%') ; 
    end $$
delimiter ;

-- Procedimiento de Ingresar Cliente
delimiter $$
create procedure sp_ingresarcliente(in prtncliente varchar(16),in pnombre varchar(45),in papellido varchar(45)
	,in ptelefono char(9),in pdireccion varchar(300),in pidsexo int)
	begin
		Insert into cliente (rtncliente, nombre, apellido, telefono, direccion, idsexo)
        values (prtncliente,pnombre,papellido,ptelefono,pdireccion,pidsexo);
    end $$
delimiter ;

-- Procedimiento de Eliminar Cliente
delimiter $$
create procedure sp_eliminarcliente(in prtncliente varchar(16) )
	begin
		delete 
		from cliente 
        where rtncliente=prtncliente;
    end $$
delimiter ;

-- Procedimiento de Actualizar Cliente
delimiter $$
create procedure sp_actualizarcliente(in pnombre varchar(45),in papellido varchar(45)
	,in ptelefono char(9),in pdireccion varchar(300),in pidsexo int,in prtncliente varchar(16) )
	begin
		Update cliente set nombre=pnombre, apellido=papellido,telefono=ptelefono,direccion=pdireccion,idsexo=pidsexo 
        Where rtncliente =prtncliente;
    end $$
delimiter ;

-- Procedimiento de Listar Sexo
delimiter $$
create procedure sp_listarsexo()
	begin
		Select * from sexo;
    end $$
delimiter ;



-- Procemientos de la Relacion Empleado 

-- Procedimiento de Listar Empleado
delimiter $$
create procedure sp_listarempleado()
	begin
		Select a.idempleado,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo 
		from empleado a inner join sexo s on a.idsexo = s.idsexo;
    end $$
delimiter ;

-- Procedimiento de Busqueda de Empleado
delimiter $$
create procedure sp_buscarempleado(in pfiltro varchar(50))
	begin
		Select a.idempleado,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo 
		from empleado a inner join sexo s 
        on a.idsexo = s.idsexo 
        where a.nombre Like concat('%',pfiltro,'%')  or a.idempleado Like concat('%',pfiltro,'%') ; 
    end $$
delimiter ;

-- Procedimiento de Ingresar Empleado
delimiter $$
create procedure sp_ingresarempleado(in pidempleado varchar(15),in pnombre varchar(45),in papellido varchar(45)
	,in ptelefono char(9),in pdireccion varchar(300),in pidsexo int)
	begin
		Insert into empleado (idempleado, nombre, apellido, telefono, direccion, idsexo)
        values (pidempleado,pnombre,papellido,ptelefono,pdireccion,pidsexo);
    end $$
delimiter ;

-- Procedimiento de Actualizar Empleado
delimiter $$
create procedure sp_actualizarempleado(in pnombre varchar(45),in papellido varchar(45)
	,in ptelefono char(9),in pdireccion varchar(300),in pidsexo int,in pidempleado varchar(15) )
	begin
		Update empleado set nombre=pnombre, apellido=papellido,telefono=ptelefono,direccion=pdireccion,idsexo=pidsexo 
        Where idempleado =pidempleado;
    end $$
delimiter ;




-- Procedimientos Almacenados de la Relacion Factura

-- Procedimiento de  Ingresar Factura
delimiter $$
create procedure sp_ingresarfactura(in pfechafactura date,in pidtipofactura int,in prtncliente varchar(16) ,in pidusuario int)
	begin
		Insert into factura (fechafactura, idtipofactura, rtncliente, idusuario)
        values (pfechafactura,pidtipofactura,prtncliente,pidusuario);
    end $$
delimiter ;

-- Procedimiento de  Ingresar DetalleFactura
delimiter $$
create procedure sp_ingresardetallefactura(in pidproducto int,in pidfactura int,in pcantidad decimal(7,2)
	,in pprecioventa decimal(7,2))
	begin
		Insert into detallefactura (idproducto,idfactura,cantidad,precioventa)
        values (pidproducto,pidfactura,pcantidad, pprecioventa);
    end $$
delimiter ;

-- Procedimiento de  Restar Producto
delimiter $$
create procedure sp_restarproducto(in pcantidad decimal(7,2),in pidproducto int)
	begin
		Update producto set unidadexistencia = unidadexistencia - pcantidad 
        Where idproducto = pidproducto;
    end $$
delimiter ;

-- Procedimiento de  Sumar Producto
delimiter $$
create procedure sp_sumarproducto(in pcantidad decimal(7,2),in pidproducto int)
	begin
		Update producto set unidadexistencia = unidadexistencia + pcantidad 
        Where idproducto = pidproducto;
    end $$
delimiter ;

-- Procedimiento de  InvestigarCorrelativo
delimiter $$
create procedure sp_investigarcorrelativo()
	begin
		Select max(idfactura) + 1 as Cod From factura;
    end $$
delimiter ;

-- Procedimiento de  Mostrar Factura
delimiter $$
create procedure sp_mostrarfactura()
	begin
		Select  F.idfactura,C.rtncliente, C.nombre,C.apellido, TF.tipofactura, F.fechafactura 
             from factura F inner join cliente C on C.rtncliente = F.rtncliente 
							inner join tipofactura TF on TF.idtipofactura = F.idtipofactura;
    end $$
delimiter ;

-- Procedimiento de  Mostrar  Busqueda Factura
delimiter $$
create procedure sp_buscarfactura(in pfiltro varchar(50))
	begin
		Select  F.idfactura,C.rtncliente, C.nombre,C.apellido, TF.tipofactura, F.fechafactura 
             from factura F inner join cliente C on C.rtncliente = F.rtncliente 
							inner join tipofactura TF on TF.idtipofactura = F.idtipofactura 
        where C.rtncliente Like concat('%',pfiltro,'%') or C.nombre Like concat('%',pfiltro,'%')  or TF.tipofactura Like concat('%',pfiltro,'%') ; 
    end $$
delimiter ;

-- Procedimiento de  Mostrar DetalleFactura
delimiter $$
create procedure sp_mostrardetallefactura(in pfiltro varchar(50))
	begin
		Select  F.idfactura,p.idproducto,p.nombreproducto,df.cantidad,df.precioventa 
		from factura F inner join detallefactura df on f.idfactura=df.idfactura
					   inner join producto p on df.idproducto=p.idproducto
		where p.nombreproducto Like concat('%',pfiltro,'%') ;
    end $$
delimiter ;

-- Procedimiento de  Mostrar DetalleFactura
delimiter $$
create procedure sp_detallefactura()
	begin
		Select  F.idfactura,p.idproducto,p.nombreproducto,df.cantidad,df.precioventa 
		from factura F inner join detallefactura df on f.idfactura=df.idfactura
					   inner join producto p on df.idproducto=p.idproducto;
    end $$
delimiter ;

-- llamado de Procedimientos Alamacenados de Factura y Detalle Factura
call sp_buscarfactura('Walter A.');
call sp_mostrardetallefactura('Balde');
execute sp_detallefactura;
select * from detallefactura;
select * from producto;
insert into detallefactura(idproducto,idfactura,cantidad,precioventa)
values(2,2,22,22);


-- Procedimiento de  MostrarTipoFactura
delimiter $$
create procedure sp_mostrartipofactura()
	begin
		Select TipoFactura from tipofactura ;
    end $$
delimiter ;




-- Procedimiento de la Relación Proveedor

-- Listar Proveedor
delimiter $$
create procedure sp_listarproveedor()
	begin
		Select p.idproveedor, p.nombre, p.apellido, p.direccion, p.telefono, s.sexo 
		from proveedor p inner join sexo s on p.idsexo = s.idsexo;
    end $$
delimiter ;

-- LlenarComboSexo
delimiter $$
create procedure sp_listarcomboboxsexo()
	begin
		Select sexo
		from sexo
        order by idsexo;
    end $$
delimiter ;

-- Insertar proveedor
delimiter $$
create procedure sp_insertarproveedor(in pidproveedor varchar(15),in pnombre varchar(45),in papellido varchar(45)
	,in pdireccion varchar(300), in ptelefono char(9), in pidsexo int)
	begin
		Insert into proveedor (idproveedor, nombre, apellido, direccion, telefono, idsexo)
        values (pidproveedor,pnombre,papellido,pdireccion,ptelefono,pidsexo);
    end $$
delimiter ;

-- Actualizar proveedor
delimiter $$
create procedure sp_actualizarproveedor(in pnombre varchar(45),in papellido varchar(45)
	,in pdireccion varchar(300), in ptelefono char(9), in pidsexo int, in pidproveedor varchar(15))
	begin
		Update proveedor set nombre=pnombre, apellido=papellido,direccion=pdireccion,telefono=ptelefono,idsexo=pidsexo 
        Where idproveedor =pidproveedor;
    end $$
delimiter ;

-- EliminarProveedor
delimiter $$
create procedure sp_eliminarproveedor(in pidproveedor varchar(15))
	begin
		delete
        from proveedor
        where idproveedor = pidproveedor;
	end $$
delimiter $$

-- Buscar Proveedor
delimiter $$
create procedure sp_buscarproveedor(in pfiltro varchar(50))
	begin
		Select p.idproveedor, p.nombre, p.apellido, p.direccion, p.telefono, s.sexo 
		from proveedor p inner join sexo s on p.idsexo = s.idsexo
        where p.nombre Like concat('%',pfiltro,'%')  or p.idproveedor Like concat('%',pfiltro,'%') ;
    end $$
delimiter ;




-- Procedimientos de la Relación Producto

-- Listar Producto
delimiter $$
create procedure sp_listarproducto(in pfiltro varchar(100))
	begin
		Select p.idproducto, p.nombreproducto, p.unidadexistencia, p.existenciaminima, p.preciocompra, p.precioventa, pr.idproveedor, concat(pr.nombre ," ", pr.apellido) as 'nombreproveedor'
		from producto p inner join proveedor pr on p.idproveedor = pr.idproveedor
        where p.nombreproducto Like concat('%',pfiltro,'%') ;
    end $$
delimiter ;


-- Insertar producto
delimiter $$
create procedure sp_insertarproducto(in pidproducto Int, in pnombreproducto varchar(90), in punidadexistencia decimal,
	in pexistenciaminima decimal(7,2), in ppreciocompra decimal(7,2), in pprecioventa decimal(7,2), in pidproveedor varchar(90)	)
		Begin
			insert into producto(idproducto, nombreproducto, unidadexistencia, existenciaminima, preciocompra, precioventa, idproveedor)
            values(pidproducto, pnombreproducto, punidadexistencia, pexistenciaminima, ppreciocompra, pprecioventa, pidproveedor);
        end $$
delimiter $$
       
       
-- Investrigar IdProducto
delimiter $$
create procedure sp_autoincrementaridproducto()
		Begin
			Select max(LAST_INSERT_ID(idproducto)) + 1 as idciudad
            From producto;
		end $$
delimiter $$

-- Actualizar Producto
delimiter $$
create  procedure sp_actualizarproducto(in pnombreproducto varchar(90), in punidadexistencia decimal,
	in pexistenciaminima decimal(7,2), in ppreciocompra decimal(7,2), in pprecioventa decimal(7,2), in pidproveedor varchar(90),in pidproducto Int)
	begin
		Update producto set nombreproducto=pnombreproducto, unidadexistencia=punidadexistencia, existenciaminima=pexistenciaminima, preciocompra=ppreciocompra, precioventa=pprecioventa, idproveedor=pidproveedor
        Where idproducto =pidproducto;
    end $$
delimiter ;

-- Eliminar Producto
delimiter $$
create procedure sp_eliminarproducto(in pidproducto int)
	begin
		delete
        from producto
        where idproducto = pidproducto;
	end $$
delimiter $$
 
 
 
 
-- Procedimiento Almacenados de la Relación Usuario

-- Procedimiento de Listar Usuario
delimiter $$
create procedure sp_listarusuario()
	begin
		Select u.idusuario,u.nombre,u.contraseña,u.estado,e.idempleado,concat(e.nombre,' ',e.apellido) as 'Nombre Empleado'
		from usuario u inner join empleado e on u.idempleado = e.idempleado;
    end $$
delimiter ;

-- Procedimiento de Busqueda de Usuario
delimiter $$
create procedure sp_buscarusuario(in pfiltro varchar(50))
	begin
		Select u.idusuario,u.nombre,u.contraseña,u.estado,e.idempleado,concat(e.nombre,' ',e.apellido) as 'Nombre Empleado'
		from usuario u inner join empleado e  on u.idempleado = e.idempleado 
        where u.nombre Like concat('%',pfiltro,'%')  or e.nombre Like concat('%',pfiltro,'%') or u.estado Like concat('%',pfiltro,'%') ; 
    end $$
delimiter ;

-- Procedimiento de Ingresar Usuario
delimiter $$
create procedure sp_ingresarusuario(in pidusuario int,in pnombre varchar(45),in pcontrasenia varchar(45)
	,in estado boolean,in pidempleado varchar(45))
	begin
		Insert into usuario (idusuario,nombre,contraseña,estado,idempleado)
        values (pidusuario,pnombre,pcontrasenia,estado,pidempleado);
    end $$
delimiter ;

-- Procedimiento de Actualizar Usuario
delimiter $$
create procedure sp_actualizarusuario(in pidusuario int,in pnombre varchar(45),in pcontrasenia varchar(45)
	,in estado boolean,in pidempleado varchar(45))
	begin
		update usuario set idusuario=pidusuario,nombre=pnombre,contraseña=pcontrasenia,estado=estado,idempleado=pidempleado
        where idusuario=pidusuario;
    end $$
delimiter ;

-- Procedimiento de  InvestigarCorrelativo
delimiter $$
create procedure sp_investigarcorrelativo1()
	begin
		Select max(idusuario) + 1 as Cod From usuario;
    end $$
delimiter ;




-- Procedimiento Almacenado de login

delimiter $$
create procedure sp_login(in pnombreusuario varchar(50),in pcontrasenia varchar(50))
	begin
		Select u.idusuario,u.nombre,u.contraseña,u.estado,e.nombre
		from usuario u inner join empleado e  on u.idempleado = e.idempleado 
        where u.nombre=pnombreusuario  and u.contraseña=pcontrasenia and u.estado=1 ; 
    end $$
delimiter ;

call sp_login('any','1234');


-- Procedimientos Almacenados de los Reportes

-- Procedimiento de  Mostrar Factura Reporte Contado
delimiter $$
create procedure sp_facturareportecontado()
	begin
		Select  F.idfactura,C.rtncliente, C.nombre,C.apellido, TF.tipofactura, F.fechafactura 
             from factura F inner join cliente C on C.rtncliente = F.rtncliente 
							inner join tipofactura TF on TF.idtipofactura = F.idtipofactura
			 where TF.idtipofactura =1;
    end $$
delimiter ;

-- Procedimiento de  Mostrar Factura Reporte Credito
delimiter $$
create procedure sp_facturareportecredito()
	begin
		Select  F.idfactura,C.rtncliente, C.nombre,C.apellido, TF.tipofactura, F.fechafactura 
             from factura F inner join cliente C on C.rtncliente = F.rtncliente 
							inner join tipofactura TF on TF.idtipofactura = F.idtipofactura
			 where TF.idtipofactura =2;
    end $$
delimiter ;

delimiter $$
create procedure sp_facturaId(in pidfactura int)
	begin
		Select  F.idfactura,F.fechafactura,tf.tipofactura,c.rtncliente,concat(c.nombre,' ',c.apellido) as 'Nombre Cliente',p.idproducto,p.nombreproducto,df.cantidad,df.precioventa 
        ,df.precioventa * df.cantidad as 'Subtotal',sum(df.precioventa * df.cantidad) as 'SubtotalFinal',((sum(df.precioventa * df.cantidad))*0.15) as 'ISV',((sum(df.precioventa * df.cantidad))+((sum(df.precioventa * df.cantidad))*0.15)) as 'Total'
		from factura F inner join detallefactura df on F.idfactura=df.idfactura
					   inner join producto p on df.idproducto=p.idproducto
					   inner join tipofactura tf on F.idtipofactura=tf.idtipofactura
                       inner join cliente c on F.rtncliente=c.rtncliente
		Where F.idfactura= pidfactura
        group by F.idfactura,F.fechafactura,tf.tipofactura,c.rtncliente,c.nombre,c.apellido,p.idproducto,p.nombreproducto,df.cantidad,df.precioventa,'SubtotalFinal';
    end $$
delimiter ;

call sp_facturaId(2)





