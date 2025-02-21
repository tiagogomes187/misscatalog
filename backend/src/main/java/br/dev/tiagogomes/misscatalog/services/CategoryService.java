package br.dev.tiagogomes.misscatalog.services;

import br.dev.tiagogomes.misscatalog.dto.CategoryDTO;
import br.dev.tiagogomes.misscatalog.entities.Category;
import br.dev.tiagogomes.misscatalog.repositories.CategoryRepository;
import br.dev.tiagogomes.misscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	
	public CategoryService (CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Transactional (readOnly = true)
	public List<CategoryDTO> findAll () {
		List<Category> list = categoryRepository.findAll ();
		return list.stream ().map (CategoryDTO :: fromEntity).collect (Collectors.toList ());
		
	}
	
	@Transactional (readOnly = true)
	public CategoryDTO findById (Long id) {
		Optional<Category> obj = categoryRepository.findById (id);
		Category entity = obj.orElseThrow (() -> new ResourceNotFoundException ("Entity with id " + id + " not found"));
		return CategoryDTO.fromEntity (entity);
	}
}
