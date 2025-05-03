package br.dev.tiagogomes.misscatalog.resources;

import br.dev.tiagogomes.misscatalog.dto.UserDTO;
import br.dev.tiagogomes.misscatalog.dto.UserInsertDTO;
import br.dev.tiagogomes.misscatalog.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping (value = "/users")
public class UserResource {
	
	private UserService userService;
	
	public UserResource (UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAll (Pageable pageable) {
		Page<UserDTO> list = userService.findAllPaged (pageable);
		return ResponseEntity.ok ().body (list);
	}
	
	@GetMapping (value = "/{id}")
	public ResponseEntity<UserDTO> findById (@PathVariable Long id) {
		UserDTO dto = userService.findById (id);
		return ResponseEntity.ok ().body (dto);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert (@RequestBody UserInsertDTO insertDto) {
		UserDTO newDto = userService.insert (insertDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri ().path ("/{id}")
				.buildAndExpand (newDto.id ()).toUri ();
		return ResponseEntity.created (uri).body (newDto);
	}
	
	@PutMapping (value = "/{id}")
	public ResponseEntity<UserDTO> update (@PathVariable Long id, @RequestBody UserDTO dto) {
		dto = userService.update (id, dto);
		return ResponseEntity.ok ().body (dto);
	}
	
	@DeleteMapping (value = "/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id) {
		userService.delete (id);
		return ResponseEntity.noContent ().build ();
	}
}
