package com.sopra.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sopra.springboot.app.models.dao.IClienteDao;
import com.sopra.springboot.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService
{
	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true) //Indica que en este metodo solo se realizan lecturas
	public List<Cliente> findAll() 
	{
		return (List<Cliente>) clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) 
	{
		return clienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) 
	{
		clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) 
	{
		clienteDao.delete(id);
	}

	@Override
	public Page<Cliente> findAll(Pageable pageable) 
	{
		return clienteDao.findAll(pageable);
	}
}
