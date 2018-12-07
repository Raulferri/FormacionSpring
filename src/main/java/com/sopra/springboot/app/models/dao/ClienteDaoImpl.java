package com.sopra.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sopra.springboot.app.models.entity.Cliente;

@Repository
public class ClienteDaoImpl implements IClienteDao 
{

	@PersistenceContext
	private EntityManager em; //Gestor que se encarga de gestionar el trafico de datos con
							  //la base de datos. Existen algunas opciones para modificar
							  //las consultas sql que se realizan
	
	@SuppressWarnings("unchecked") //Este comando indica que este metodo dara problemas al realizar la migracion a Spring 5
	@Transactional(readOnly=true) //Indica que en este metodo solo se realizan lecturas
	@Override
	public List<Cliente> findAll() 
	{
		return em.createQuery("from Cliente").getResultList();//Ejecuta la consulta findAll, que es
															  //equivalente a un SELECT *
	}
	
	@Override
	@Transactional
	public void save(Cliente cliente) 
	{
		if(cliente.getId() != null && cliente.getId() > 0)
			em.merge(cliente);
		else
			em.persist(cliente);
	}
	
	@Override
	public Cliente findOne(Long id) 
	{
		return em.find(Cliente.class, id);
	}
}
