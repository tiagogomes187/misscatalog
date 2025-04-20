package br.dev.tiagogomes.misscatalog.services;

import br.dev.tiagogomes.misscatalog.dto.ProductDTO;
import br.dev.tiagogomes.misscatalog.repositories.ProductRepository;
import br.dev.tiagogomes.misscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PrductServiceIT {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProducts;
	
	@BeforeEach
	void setUp () {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 18L;
	}
	
	@Test
	void deleteShouldDeleteResorceWhenIdExists () {
		productService.delete (existingId);
		Assertions.assertEquals (countTotalProducts - 1, productRepository.count ());
	}
	
	@Test
	void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExit () {
		Assertions.assertThrows (ResourceNotFoundException.class, () -> {
			productService.delete (nonExistingId);
		});
	}
	
	@Test
	void findAllPagedShouldReturnPageWhenPage0Size10 () {
		PageRequest pageRequest = PageRequest.of (0, 10);
		Page<ProductDTO> result = productService.findAllPaged (pageRequest);
		Assertions.assertFalse (result.isEmpty ());
		Assertions.assertEquals (0, result.getNumber ());
		Assertions.assertEquals (10, result.getSize ());
		Assertions.assertEquals (countTotalProducts, result.getTotalElements ());
	}
	
	@Test
	void findAllPagedShouldReturnEmptyPageDoesNotExist () {
		PageRequest pageRequest = PageRequest.of (50, 10);
		Page<ProductDTO> result = productService.findAllPaged (pageRequest);
		Assertions.assertTrue (result.isEmpty ());
	}
	
	@Test
	void findAllPagedShouldReturnSortedPageWhenSortByName () {
		PageRequest pageRequest = PageRequest.of (0, 10, Sort.by ("name"));
		Page<ProductDTO> result = productService.findAllPaged (pageRequest);
		Assertions.assertFalse (result.isEmpty ());
		Assertions.assertEquals ("BIRKENMISS FIVELAS", result.getContent ().get (0).name ());
		Assertions.assertEquals ("BOTA CANO ALTO", result.getContent ().get (1).name ());
		Assertions.assertEquals ("BOTA CANO MÉDIO", result.getContent ().get (2).name ());
	}
}
