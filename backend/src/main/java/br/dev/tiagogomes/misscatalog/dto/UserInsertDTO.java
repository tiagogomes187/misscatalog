package br.dev.tiagogomes.misscatalog.dto;

import br.dev.tiagogomes.misscatalog.entities.User;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Record que representa um DTO para inserção de novos usuários.
 * Inclui todos os campos de UserDTO mais um campo de senha.
 */
public record UserInsertDTO(Long id, String firstName, String lastName, String email, String password,
                            Set<RoleDTO> roles) {
	
	/**
	 * Converte uma entidade User em um UserInsertDTO.
	 *
	 * @param entity   A entidade User a ser convertida.
	 * @param password A senha do usuário para inserção.
	 * @return Um UserInsertDTO com os dados do usuário, senha e seus papéis.
	 * @throws IllegalArgumentException Se a entidade, seus papéis ou a senha forem nulos.
	 */
	public static UserInsertDTO dtoFromEntity (User entity, String password) {
		if (entity == null || entity.getRoles () == null || password == null) {
			throw new IllegalArgumentException ("User entity, roles, or password cannot be null");
		}
		
		Set<RoleDTO> roleDTOS = entity.getRoles ()
				.stream ()
				.map (role -> new RoleDTO (role.getId (), role.getAuthority ()))
				.collect (Collectors.toSet ());
		
		return new UserInsertDTO (
				entity.getId (),
				entity.getFirstName (),
				entity.getLastName (),
				entity.getEmail (),
				password,
				Collections.unmodifiableSet (roleDTOS)
		);
	}
	
	/**
	 * Converte este UserInsertDTO em um UserDTO, removendo o campo password.
	 *
	 * @return Um UserDTO com os dados do usuário, exceto a senha.
	 */
	public UserDTO toUserDTO () {
		return new UserDTO (
				this.id (),
				this.firstName (),
				this.lastName (),
				this.email (),
				this.roles ()
		);
	}
}