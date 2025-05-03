package br.dev.tiagogomes.misscatalog.dto;

import br.dev.tiagogomes.misscatalog.entities.User;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Record que representa um DTO para transferência de dados de um usuário.
 * Contém informações básicas do usuário e seus papéis.
 */
public record UserDTO(Long id, String firstName, String lastName, String email, Set<RoleDTO> roles) {
	
	/**
	 * Converte uma entidade User em um UserDTO.
	 *
	 * @param entity A entidade User a ser convertida.
	 * @return Um UserDTO com os dados do usuário e seus papéis.
	 * @throws IllegalArgumentException Se a entidade ou seus papéis forem nulos.
	 */
	public static UserDTO dtoFromEntity (User entity) {
		
		if (entity == null || entity.getRoles () == null) {
			throw new IllegalArgumentException ("User entity or roles cannot be null");
		}
		
		Set<RoleDTO> roles = entity.getRoles ()
				.stream ()
				.map (role -> new RoleDTO (role.getId (), role.getAuthority ()))
				.collect (Collectors.toSet ());
		
		return new UserDTO (
				entity.getId (),
				entity.getFirstName (),
				entity.getLastName (),
				entity.getEmail (),
				Collections.unmodifiableSet (roles)
		);
	}
}