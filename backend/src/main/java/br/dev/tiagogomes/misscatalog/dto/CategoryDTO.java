package br.dev.tiagogomes.misscatalog.dto;

import br.dev.tiagogomes.misscatalog.entities.Category;

public record CategoryDTO(Long id, String name) {
	
	public static CategoryDTO from(Category category) {
		return new CategoryDTO(category.getId(), category.getName());
	}
	
}
