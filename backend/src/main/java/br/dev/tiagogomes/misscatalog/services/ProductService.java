package br.dev.tiagogomes.misscatalog.services;

import br.dev.tiagogomes.misscatalog.dto.CategoryDTO;
import br.dev.tiagogomes.misscatalog.dto.ProductDTO;
import br.dev.tiagogomes.misscatalog.entities.Category;
import br.dev.tiagogomes.misscatalog.entities.Product;
import br.dev.tiagogomes.misscatalog.repositories.CategoryRepository;
import br.dev.tiagogomes.misscatalog.repositories.ProductRepository;
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
public class ProductService {
	
	private ProductRepository productRepository;
	
	private CategoryRepository categoryRepository;
	
	public ProductService (ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@Transactional (readOnly = true)
	public Page<ProductDTO> findAllPaged (Pageable pageable) {
		Page<Product> page = productRepository.findAll (pageable);
		return page.map (entity -> ProductDTO.fromEntity (entity));
	}
	
	@Transactional (readOnly = true)
	public ProductDTO findById (Long id) {
		Optional<Product> obj = productRepository.findById (id);
		Product entity = obj.orElseThrow (() -> new ResourceNotFoundException ("Entity with id " + id + " not found"));
		return ProductDTO.fromEntity (entity);
	}
	
	@Transactional
	public ProductDTO insert (ProductDTO dto) {
		Product entity = new Product ();
		copyDtoToEntity (dto, entity);
		entity = productRepository.save (entity);
		return ProductDTO.fromEntity (entity);
	}
	
	@Transactional
	public ProductDTO update (Long id, ProductDTO dto) {
		try {
			Product entity = productRepository.getReferenceById (id);
			copyDtoToEntity (dto, entity);
			entity = productRepository.save (entity);
			return ProductDTO.fromEntity (entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException ("Id " + id + " not found");
		}
	}
	
	@Transactional (propagation = Propagation.SUPPORTS)
	public void delete (Long id) {
		if (!productRepository.existsById (id)) {
			throw new ResourceNotFoundException ("Resource note found");
		}
		try {
			productRepository.deleteById (id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException ("Referential integrity failure");
		}
	}
	
	private void copyDtoToEntity (ProductDTO dto, Product entity) {
		entity.setGtin_code (dto.gtinCode ());
		entity.setReference (dto.reference ());
		entity.setColor (dto.color ());
		entity.setName (dto.name ());
		entity.setAdditionalCodes (dto.additionalCodes ());
		entity.setItemsIncluded (dto.itemsIncluded ());
		entity.setImages (dto.images ());
		entity.setBrand (dto.brand ());
		entity.setGrossWeight (dto.grossWeight ());
		entity.setNetWeight (dto.netWeight ());
		entity.setNetContent (dto.netContent ());
		entity.setHeight (dto.height ());
		entity.setWidth (dto.width ());
		entity.setDepth (dto.depth ());
		entity.setNcm (dto.ncm ());
		entity.setCest (dto.cest ());
		entity.setGpc (dto.gpc ());
		entity.setReleaseDate (dto.releaseDate ());
		entity.setType (dto.type ());
		entity.getCategories ().clear ();
		for (CategoryDTO catDto : dto.categories ()) {
			Category category = categoryRepository.getReferenceById (catDto.id ());
			entity.getCategories ().add (category);
		}
	}
	
}
