package br.dev.tiagogomes.misscatalog.dto;

import br.dev.tiagogomes.misscatalog.entities.Product;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record ProductDTO(
		Long id,
		Long gtinCode,
		String reference,
		String color,
		String name,
		String additionalCodes,
		Long itemsIncluded,
		String images,
		String brand,
		Double grossWeight,
		Double netWeight,
		Integer netContent,
		Double height,
		Double width,
		Double depth,
		String ncm,
		String cest,
		String gpc,
		Instant releaseDate,
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
