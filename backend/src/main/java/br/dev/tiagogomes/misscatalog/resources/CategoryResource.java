package br.dev.tiagogomes.misscatalog.resources;

import br.dev.tiagogomes.misscatalog.dto.CategoryDTO;
import br.dev.tiagogomes.misscatalog.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping (value = "/categories")
public class CategoryResource {
	
	private CategoryService categoryService;
	
	public CategoryResource (CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll () {
		List<CategoryDTO> list = categoryService.findAll ();
		return ResponseEntity.ok ().body (list);
	}
	
	@GetMapping (value = "/{id}")
	public ResponseEntity<CategoryDTO> findById (@PathVariable Long id) {
		CategoryDTO dto = categoryService.findById (id);
		return ResponseEntity.ok ().body (dto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert (@RequestBody CategoryDTO dto) {
		dto = categoryService.insert (dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri ().path ("/{id}")
				.buildAndExpand (dto.id ()).toUri ();
		return ResponseEntity.created (uri).body (dto);
	}
}
