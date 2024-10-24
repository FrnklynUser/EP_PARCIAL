package com.arcila.facturacion.almacen.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.arcila.facturacion.almacen.entity.Cliente;
import com.arcila.facturacion.almacen.service.ClienteService;
import com.arcila.facturacion.almacen.util.WrapperResponse;
import com.arcila.facturacion.almacen.dto.ClienteDto;
import com.arcila.facturacion.almacen.converter.ClienteConverter;


@RestController
@RequestMapping("/v1/cliente")
//localhost:8090/v1/cliente versionado en la URI
public class ClienteController {
	@Autowired
	private ClienteService service;
	
	private final ClienteConverter converter = new ClienteConverter();
	
	@GetMapping
	public ResponseEntity<List<ClienteDto>> findAll(
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
			){
		Pageable page = PageRequest.of(pageNumber, pageSize);				
		List<ClienteDto> cliente = (List<ClienteDto>) converter.fromEntity(service.findAll());
		
		return new WrapperResponse(true, "success", cliente).createResponse(HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ClienteDto> create (@RequestBody ClienteDto cliente){
		Cliente clienteEntity=converter.fromDTO(cliente);
		ClienteDto registro = converter.fromEntity(service.save(clienteEntity));
		return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClienteDto> update(@PathVariable("id") int id,@RequestBody ClienteDto cliente){
		Cliente clienteEntity=converter.fromDTO(cliente);
		ClienteDto registro = converter.fromEntity(service.save(clienteEntity));
		return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id") int id){
		service.delete(id);
		return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> findById(@PathVariable("id") int id){
		ClienteDto registro=converter.fromEntity(service.findById(id));
		return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
	}
	
	
	
	
}
