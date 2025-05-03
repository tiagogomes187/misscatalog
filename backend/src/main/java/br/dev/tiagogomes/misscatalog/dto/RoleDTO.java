package br.dev.tiagogomes.misscatalog.dto;

import br.dev.tiagogomes.misscatalog.entities.Role;

public record RoleDTO(Long id, String authority) {
	public RoleDTO (Role entity) {
		this (entity.getId (), entity.getAuthority ());
	}
}
