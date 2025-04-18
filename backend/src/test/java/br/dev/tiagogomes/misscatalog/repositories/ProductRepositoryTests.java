package br.dev.tiagogomes.misscatalog.repositories;

import br.dev.tiagogomes.misscatalog.entities.Product;
import br.dev.tiagogomes.misscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository productRepository;
	
	private ProductRepositoryTests () {
	}
	
	public static ProductRepositoryTests createProductRepositoryTests () {
		return new ProductRepositoryTests ();
	}
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp () throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 18L;
	}
	
	@Test
	void deleteShouldDeleteObjectWhenIdExists () {
		productRepository.deleteById (existingId);
		Optional<Product> result = productRepository.findById (existingId);
		Assertions.assertFalse (result.isPresent ());
	}
	
	@Test
	void saveShouldPersistWithAutoincrementWhenIdIsNull () {
		Product product = Factory.createProduct ();
		product.setId (null);
		product = productRepository.save (product);
		Assertions.assertNotNull (product.getId ());
		Assertions.assertEquals (countTotalProducts + 1, product.getId ());
	}
	
	@Test
	void findByIdShouldReturnNonEmptyOptionalProductWhenIdExists (){
		Optional<Product> result = productRepository.findById (existingId);
		Assertions.assertTrue (result.isPresent ());
	}
	
	@Test
	void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists (){
		Optional<Product> result = productRepository.findById (nonExistingId);
		Assertions.assertTrue (result.isEmpty ());
	}
}
