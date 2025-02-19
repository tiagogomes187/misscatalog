package br.dev.tiagogomes.misscatalog.resources;

import br.dev.tiagogomes.misscatalog.entities.Category;
import br.dev.tiagogomes.misscatalog.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping (value = "/categories")
public class CategoryResource {
	
	private CategoryService categoryService;
	
	public CategoryResource (CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll () {
		List<Category> list = categoryService.findAll ();
		return ResponseEntity.ok ().body (list);
	}
}
