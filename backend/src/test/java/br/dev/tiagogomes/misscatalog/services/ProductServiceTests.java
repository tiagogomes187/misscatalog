package br.dev.tiagogomes.misscatalog.services;

import br.dev.tiagogomes.misscatalog.dto.ProductDTO;
import br.dev.tiagogomes.misscatalog.entities.Product;
import br.dev.tiagogomes.misscatalog.repositories.ProductRepository;
import br.dev.tiagogomes.misscatalog.services.exceptions.DatabaseException;
import br.dev.tiagogomes.misscatalog.services.exceptions.ResourceNotFoundException;
import br.dev.tiagogomes.misscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith (SpringExtension.class)
class ProductServiceTests {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;
	
	@BeforeEach
	void setUp () {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = Factory.createProduct ();
		page = new PageImpl<> (List.of (product));
		
		when (productRepository.findAll ((Pageable) ArgumentMatchers.any ())).thenReturn (page);
		when (productRepository.save (ArgumentMatchers.any ())).thenReturn (product);
		when (productRepository.findById (existingId)).thenReturn (Optional.of (product));
		when (productRepository.findById (nonExistingId)).thenReturn (Optional.empty ());
		
		
		when (productRepository.existsById (existingId)).thenReturn (true);
		when (productRepository.existsById (nonExistingId)).thenReturn (false);
		when (productRepository.existsById (dependentId)).thenReturn (true);
		doNothing ().when (productRepository).deleteById (existingId);
		doThrow (DataIntegrityViolationException.class).when (productRepository).deleteById (dependentId);
	}
	
	@Test
	void findAllPagedShouldReturnPage () {
		Pageable pageable = PageRequest.of (0,10);
		Page<ProductDTO> result = productService.findAllPaged (pageable);
		Assertions.assertNotNull (result);
		verify (productRepository, times (1)).findAll (pageable);
	}
	
	@Test
	void deleteShouldDoNothingWhenIdExists () {
		Assertions.assertDoesNotThrow (() -> {
			productService.delete (existingId);
		});
	}
	
	@Test
	void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExist () {
		Assertions.assertThrows (ResourceNotFoundException.class, () -> {
			productService.delete (nonExistingId);
		});
	}
	
	@Test
	void deleteShouldThrowDatabaseExceptionWhenDependentId () {
		Assertions.assertThrows (DatabaseException.class, () -> {
			productService.delete (dependentId);
		});
	}
	
}
