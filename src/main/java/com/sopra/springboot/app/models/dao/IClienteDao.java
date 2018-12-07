package com.sopra.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.sopra.springboot.app.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> //Clase que implementa todos los metodos de una interfaz(findAll, findOne, delete...)
{
	
}
