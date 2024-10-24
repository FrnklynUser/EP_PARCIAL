package com.arcila.facturacion.almacen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arcila.facturacion.almacen.entity.Cliente;
import com.arcila.facturacion.almacen.entity.Permiso;
import com.arcila.facturacion.almacen.exception.GeneralException;
import com.arcila.facturacion.almacen.exception.NoDataFoundException;
import com.arcila.facturacion.almacen.exception.ValidateException;
import com.arcila.facturacion.almacen.repository.ClienteRepository;
import com.arcila.facturacion.almacen.repository.PermisoRepository;
import com.arcila.facturacion.almacen.service.ClienteService;
import com.arcila.facturacion.almacen.service.PermisoService;
import com.arcila.facturacion.almacen.validator.ClienteValidator;
import com.arcila.facturacion.almacen.validator.PermisoValidator;

@Service
public class ClienteServiceImpl implements ClienteService{
	@Autowired
	private ClienteRepository repository;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll(Pageable page) {
		try {
			List<Cliente> registros=repository.findAll(page).toList();
			return registros;
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> finByNombre(String nombre, Pageable page) {
		try {
			List<Cliente> registros=repository.findByNombreContaining(nombre,page);
			return registros;
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(int id) {
		try {
			Cliente registro=repository.findById(id).
					orElseThrow(()->new NoDataFoundException("No existe un registro con ese ID"));
			return registro;
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		try {
			
			ClienteValidator.save(cliente);
			//Nuevo registro
			if (cliente.getId()==0) {
				Cliente nuevo=repository.save(cliente);
				return nuevo;				
			}
			//editar registro
			Cliente registro=repository.findById(cliente.getId()).orElseThrow(()->new NoDataFoundException("No existe un registro con ese ID"));
			registro.setNombre(cliente.getNombre());
			repository.save(registro);
			return registro;
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		try {
			Cliente registro=repository.findById(id).orElseThrow(()->new NoDataFoundException("No existe un registro con ese ID"));
			repository.delete(registro);
		} catch (ValidateException | NoDataFoundException e) {
			throw e;
		}
		catch (GeneralException e) {
			throw new GeneralException("Error del servidor");
		}
		
	}

	@Override
	public Cliente findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
