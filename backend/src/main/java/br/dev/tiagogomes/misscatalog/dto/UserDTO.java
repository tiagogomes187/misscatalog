package br.dev.tiagogomes.misscatalog.dto;

import br.dev.tiagogomes.misscatalog.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Record que representa um DTO para transferência de dados de um usuário.
 * Contém informações básicas do usuário e seus papéis.
 */
public record UserDTO(
		@Schema(description = "ID único do usuário (pode ser null)", example = "null")
		Long id,
		@Schema (description = "Primeiro nome do usuário", example = "João")
		@NotBlank(message = "Primeiro nome não pode estar em branco")
		@Size (min = 2, max = 50, message = "Primeiro nome deve ter entre 2 e 50 caracteres")
		String firstName,
		@Schema(description = "Sobrenome do usuário", example = "Silva")
		@NotBlank(message = "Sobrenome não pode estar em branco")
		@Size(min = 2, max = 50, message = "Sobrenome deve ter entre 2 e 50 caracteres")
		String lastName,
		@NotBlank (message = "O e-mail é obrigatório!")
		@Email (message = "Formato de e-mail inválido!")
		String email,
		Set<RoleDTO> roles) {
	
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