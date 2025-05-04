package br.dev.tiagogomes.misscatalog.dto;

import br.dev.tiagogomes.misscatalog.entities.Role;
import jakarta.validation.constraints.NotBlank;

public record RoleDTO(
		Long id,
		@NotBlank(message = "Campo obrigtório.")
		String authority) {
	public RoleDTO (Role entity) {
		this (entity.getId (), entity.getAuthority ());
	}
}
