package br.dev.tiagogomes.misscatalog.services;

import br.dev.tiagogomes.misscatalog.entities.Category;
import br.dev.tiagogomes.misscatalog.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	
	
	public CategoryService (CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<Category> findAll(){
		return categoryRepository.findAll ();
	}
}
