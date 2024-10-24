package com.arcila.facturacion.almacen.converter;

import org.springframework.stereotype.Component;

import com.arcila.facturacion.almacen.dto.ClienteDto;
import com.arcila.facturacion.almacen.entity.Cliente;

@Component
public class ClienteConverter extends AbstractConverter<Cliente, ClienteDto>{
	@Override
	public ClienteDto fromEntity(Cliente entity) {
		if(entity==null) return null;
		return ClienteDto.builder()
				.id(entity.getId())
				.nombre(entity.getNombre())
				.build();
	}

	@Override
	public Cliente fromDTO(ClienteDto dto) {
		if(dto==null) return null;
		return Cliente.builder()
				.id((long) dto.getId())
				.nombre(dto.getNombre())
				.build();
	}

}
