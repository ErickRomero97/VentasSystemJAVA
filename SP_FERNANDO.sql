select * from cliente

--Procedimiento de Listar Cliente
delimiter $$
create procedure sp_listarproveedor()
	begin
		Select p.idproveedor, p.nombre, p.apellido, p.direccion, p.telefono, s.sexo 
		from proveedor p inner join sexo s on p.idsexo = s.idsexo;
    end $$
delimiter ;


delimiter $$
create procedure sp_buscarcliente1(in pfiltro varchar(50))
	begin
		Select a.rtncliente,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo 
		from cliente a inner join sexo s 
        on a.idsexo = s.idsexo 
        where a.nombre Like concat('%',pfiltro,'%')  or a.rtncliente Like concat('%',pfiltro,'%') ; 
    end $$
delimiter ;



select * from proveedor

insert into proveedor(idproveedor,nombre,apellido,direccion,telefono,idsexo)
values('0601-1997-00841','Fernando','Sanchez','Barrio Valle', '9691-172',1)
--Procedimiento de Actualizar Cliente
delimiter $$
create procedure sp_ingresarcliente(in prtncliente varchar(16),in pnombre varchar(45),in papellido varchar(45)
	,in ptelefono char(9),in pdireccion varchar(300),in pidsexo int)
	begin
		Insert into cliente (rtncliente, nombre, apellido, telefono, direccion, idsexo)
        values (prtncliente,pnombre,papellido,ptelefono,pdireccion,pidsexo);
    end $$
delimiter ;

--Procedimiento de Eliminar Cliente
delimiter $$
create procedure sp_eliminarcliente(in prtncliente varchar(16) )
	begin
		delete 
		from cliente 
        where rtncliente=prtncliente;
    end $$
delimiter ;

--Procedimiento de Actualizar Cliente
delimiter $$
create procedure sp_actualizarcliente(in pnombre varchar(45),in papellido varchar(45)
	,in ptelefono char(9),in pdireccion varchar(300),in pidsexo int,in prtncliente varchar(16) )
	begin
		Update cliente set nombre=pnombre, apellido=papellido,telefono=ptelefono,direccion=pdireccion,idsexo=pidsexo 
        Where rtncliente =prtncliente;
    end $$
delimiter ;

insert into sexo(sexo)
values('Femenino')



--Procedimiento de Proveedor
--Listar Proveedor
delimiter $$
create procedure sp_listarproveedor()
	begin
		Select p.idproveedor, p.nombre, p.apellido, p.direccion, p.telefono, s.sexo 
		from proveedor p inner join sexo s on p.idsexo = s.idsexo;
    end $$
delimiter ;

--LlenarComboSexo
delimiter $$
create procedure sp_listarcomboboxsexo()
	begin
		Select sexo
		from sexo
        order by idsexo;
    end $$
delimiter ;

--Insertar proveedor
delimiter $$
create procedure sp_insertarproveedor(in pidproveedor varchar(15),in pnombre varchar(45),in papellido varchar(45)
	,in pdireccion varchar(300), in ptelefono char(9), in pidsexo int)
	begin
		Insert into proveedor (idproveedor, nombre, apellido, direccion, telefono, idsexo)
        values (pidproveedor,pnombre,papellido,pdireccion,ptelefono,pidsexo);
    end $$
delimiter ;

--Actualizar proveedor
delimiter $$
create procedure sp_actualizarproveedor(in pnombre varchar(45),in papellido varchar(45)
	,in pdireccion varchar(300), in ptelefono char(9), in pidsexo int, in pidproveedor varchar(15))
	begin
		Update proveedor set nombre=pnombre, apellido=papellido,direccion=pdireccion,telefono=ptelefono,idsexo=pidsexo 
        Where idproveedor =pidproveedor;
    end $$
delimiter ;

--EliminarProveedor
delimiter $$
create procedure sp_eliminarproveedor(in pidproveedor varchar(15))
	begin
		delete
        from proveedor
        where idproveedor = pidproveedor;
	end $$
delimiter $$

--Buscar Proveedor
delimiter $$
create procedure sp_buscarproveedor(in pfiltro varchar(50))
	begin
		Select p.idproveedor, p.nombre, p.apellido, p.direccion, p.telefono, s.sexo 
		from proveedor p inner join sexo s on p.idsexo = s.idsexo
        where p.nombre Like concat('%',pfiltro,'%')  or p.idproveedor Like concat('%',pfiltro,'%') ;
    end $$
delimiter ;

--Buscar Proveedor1
delimiter $$
create procedure sp_buscarproveedor1(in pfiltro varchar(50))
	begin
		Select p.idproveedor, concat(p.nombre ," ", p.apellido) as 'nombreproveedor', p.telefono, p.direccion 
		from proveedor p 
        where p.nombre Like concat('%',pfiltro,'%')  or p.idproveedor Like concat('%',pfiltro,'%') ;
    end $$
delimiter ;

delimiter $$
create procedure sp_listarproveedor1(in pfiltro varchar(50))
	begin
		Select p.idproveedor, concat(p.nombre ," ", p.apellido) as 'nombreproveedor', p.telefono
		from proveedor p inner join sexo s on p.idsexo = s.idsexo;
    end $$
delimiter ;

--Procedimientos de producto

--Listar Producto
delimiter $$
create procedure sp_listarproducto(in pfiltro varchar(100))
	begin
		Select p.idproducto, p.nombreproducto, p.unidadexistencia, p.existenciaminima, p.preciocompra, p.precioventa, p.productocol, pr.idproveedor, concat(pr.nombre ," ", pr.apellido) as 'NombreProveedor'
		from producto p inner join proveedor pr on p.idproveedor = pr.idproveedor
        where p.nombreproducto Like concat('%',pfiltro,'%') ;
    end $$
delimiter ;


--Insertar producto

delimiter $$
create procedure sp_insertarproducto(in pidproducto Int, in pnombreproducto varchar(90), in punidadexistencia decimal,
	in pexistenciaminima decimal(7,2), in ppreciocompra decimal(7,2), in pprecioventa decimal(7,2), in pproductocol varchar(45), in pidproveedor varchar(90)	)
		Begin
			insert into producto(idproducto, nombreproducto, unidadexistencia, existenciaminima, preciocompra, precioventa, productocol, idproveedor)
            values(pidproducto, pnombreproducto, punidadexistencia, pexistenciaminima, ppreciocompra, pprecioventa, pproductocol, pidproveedor);
        end $$
delimiter $$
        
--Investrigar IdProducto
delimiter $$
create procedure sp_autoincrementaridproducto()
		Begin
			Select max(LAST_INSERT_ID(idproducto)) + 1 as idciudad
            From producto;
		end $$
delimiter $$

--Actualizar Producto
delimiter $$
create procedure sp_actualizarproducto(in pnombreproducto varchar(90), in punidadexistencia decimal,
	in pexistenciaminima decimal(7,2), in ppreciocompra decimal(7,2), in pprecioventa decimal(7,2), in pproductocol varchar(45), in pidproveedor varchar(90),in pidproducto Int)
	begin
		Update producto set nombreproducto=pnombreproducto, unidadexistencia=punidadexistencia, existenciaminima=pexistenciaminima, preciocompra=ppreciocompra, precioventa=pprecioventa, productocol=pproductocol, idproveedor=pidproveedor
        Where idproducto =pidproducto;
    end $$
delimiter ;

--Eliminar Producto

delimiter $$
create procedure sp_eliminarproducto(in pidproducto int)
	begin
		delete
        from producto
        where idproducto = pidproducto;
	end $$
delimiter $$





select * from proveedor