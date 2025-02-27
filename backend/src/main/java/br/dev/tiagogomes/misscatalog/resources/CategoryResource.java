package br.dev.tiagogomes.misscatalog.resources;

import br.dev.tiagogomes.misscatalog.dto.CategoryDTO;
import br.dev.tiagogomes.misscatalog.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping (value = "/categories")
public class CategoryResource {
	
	private CategoryService categoryService;
	
	public CategoryResource (CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAll (
			@RequestParam (value = "page", defaultValue = "0") Integer page,
			@RequestParam (value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam (value = "direction", defaultValue = "ASC") String direction,
			@RequestParam (value = "orderBy", defaultValue = "name") String orderBy
	
	) {
		PageRequest pageRequest = PageRequest.of (page, linesPerPage, Sort.Direction.valueOf (direction), orderBy);
		Page<CategoryDTO> list = categoryService.findAllPaged (pageRequest);
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
	
	@PutMapping (value = "/{id}")
	public ResponseEntity<CategoryDTO> update (@PathVariable Long id, @RequestBody CategoryDTO dto) {
		dto = categoryService.update (id, dto);
		return ResponseEntity.ok ().body (dto);
	}
	
	@DeleteMapping (value = "/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id) {
		categoryService.delete (id);
		return ResponseEntity.noContent ().build ();
	}
}
