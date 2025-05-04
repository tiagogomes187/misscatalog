package br.dev.tiagogomes.misscatalog.dto;

import br.dev.tiagogomes.misscatalog.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record ProductDTO(
		Long id,
		@NotNull(message = "Campo obrigatório.")
		Long gtinCode,
		@NotBlank(message = "Campo obrigatório.")
		String reference,
		@NotBlank(message = "Campo obrigatório.")
		String color,
		@NotBlank(message = "Campo obrigatório.")
		@Size(min = 5, max = 60, message = "O nome do produto deve ter no mínimo 5 caracteres e máximo 60.")
		String name,
		@NotBlank(message = "Campo obrigatório.")
		String additionalCodes,
		@NotNull(message = "Campo obrigatório.")
		Long itemsIncluded,
		@NotBlank(message = "Campo obrigatório.")
		String images,
		@NotBlank(message = "Campo obrigatório.")
		String brand,
		@NotNull(message = "Campo obrigatório.")
		Double grossWeight,
		@NotNull(message = "Campo obrigatório.")
		Double netWeight,
		@NotNull(message = "Campo obrigatório.")
		Integer netContent,
		@NotNull(message = "Campo obrigatório.")
		Double height,
		@NotNull(message = "Campo obrigatório.")
		Double width,
		@NotNull(message = "Campo obrigatório.")
		Double depth,
		@NotBlank(message = "Campo obrigatório.")
		String ncm,
		@NotBlank(message = "Campo obrigatório.")
		String cest,
		@NotBlank(message = "Campo obrigatório.")
		String gpc,
		@NotNull(message = "Campo obrigatório.")
		@PastOrPresent(message = "A data não pode ser futura")
		Instant releaseDate,
		@NotBlank(message = "Campo obrigatório.")
		String type,
		Set<CategoryDTO> categories
) {
	
	public static ProductDTO fromEntity (Product entity) {
		Set<CategoryDTO> categoryDTOs = entity.getCategories ().stream ()
				.map (category -> new CategoryDTO (category.getId (), category.getName ()))
				.collect (Collectors.toSet ());
		return new ProductDTO (
				entity.getId (),
				entity.getGtinCode (),
				entity.getReference (),
				entity.getColor (),
				entity.getName (),
				entity.getAdditionalCodes (),
				entity.getItemsIncluded (),
				entity.getImages (),
				entity.getBrand (),
				entity.getGrossWeight (),
				entity.getNetWeight (),
				entity.getNetContent (),
				entity.getHeight (),
				entity.getWidth (),
				entity.getDepth (),
				entity.getNcm (),
				entity.getCest (),
				entity.getGpc (),
				entity.getReleaseDate (),
				entity.getType (),
				Collections.unmodifiableSet (categoryDTOs)
		);
	}
	
}
