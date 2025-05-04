package br.dev.tiagogomes.misscatalog.dto;

import br.dev.tiagogomes.misscatalog.entities.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
		Long id,
		@NotBlank(message = "Categoria obrigatória.")
		String name
) {
	
	public static CategoryDTO fromEntity(Category category) {
		return new CategoryDTO(category.getId(), category.getName());
	}
}