package br.dev.tiagogomes.misscatalog.services;

import br.dev.tiagogomes.misscatalog.dto.CategoryDTO;
import br.dev.tiagogomes.misscatalog.entities.Category;
import br.dev.tiagogomes.misscatalog.repositories.CategoryRepository;
import br.dev.tiagogomes.misscatalog.services.exceptions.DatabaseException;
import br.dev.tiagogomes.misscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	
	public CategoryService (CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Transactional (readOnly = true)
	public Page<CategoryDTO> findAllPaged (Pageable pageable) {
		Page<Category> page = categoryRepository.findAll (pageable);
		return page.map (CategoryDTO :: fromEntity);
	}
	
	@Transactional (readOnly = true)
	public CategoryDTO findById (Long id) {
		Optional<Category> obj = categoryRepository.findById (id);
		Category entity = obj.orElseThrow (() -> new ResourceNotFoundException ("Entity with id " + id + " not found"));
		return CategoryDTO.fromEntity (entity);
	}
	
	@Transactional
	public CategoryDTO insert (CategoryDTO dto) {
		Category entity = new Category ();
		entity.setName (dto.name ());
		entity = categoryRepository.save (entity);
		return CategoryDTO.fromEntity (entity);
	}
	
	@Transactional
	public CategoryDTO update (Long id, CategoryDTO dto) {
		try {
			Category entity = categoryRepository.getReferenceById (id);
			entity.setName (dto.name ());
			entity = categoryRepository.save (entity);
			return CategoryDTO.fromEntity (entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException ("Id " + id + " not found");
		}
	}
	
	@Transactional (propagation = Propagation.SUPPORTS)
	public void delete (Long id) {
		if (!categoryRepository.existsById (id)) {
			throw new ResourceNotFoundException ("Resource not found");
		}
		try {
			categoryRepository.deleteById (id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException ("Referential integrity failure");
		}
	}
	
}
