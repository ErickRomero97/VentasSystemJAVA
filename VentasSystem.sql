select * from cliente

--Procedimiento de Listar Cliente
delimiter $$
create procedure sp_listarcliente()
	begin
		Select a.rtncliente,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo 
		from cliente a inner join sexo s on a.idsexo = s.idsexo;
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



