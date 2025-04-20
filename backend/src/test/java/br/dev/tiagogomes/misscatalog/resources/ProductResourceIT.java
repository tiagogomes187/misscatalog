package br.dev.tiagogomes.misscatalog.resources;

import br.dev.tiagogomes.misscatalog.dto.ProductDTO;
import br.dev.tiagogomes.misscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
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
	void findAllShouldReturnSortedPageWhenSortByName () throws Exception {
		ResultActions result =
				mockMvc.perform (get ("/products?page=0&size=12&sort=name,asc")
						.accept (MediaType.APPLICATION_JSON));
		
		result.andExpect (status ().isOk ());
		result.andExpect (jsonPath ("$.content").exists ());
		result.andExpect (jsonPath ("$.page.totalElements").exists ());
		result.andExpect (jsonPath ("$.content[0].name").value ("BIRKENMISS FIVELAS"));
		result.andExpect (jsonPath ("$.content[1].name").value ("BOTA CANO ALTO"));
		result.andExpect (jsonPath ("$.content[2].name").value ("BOTA CANO MÉDIO"));
		result.andExpect (jsonPath ("$.page.totalElements").value (countTotalProducts));
		
	}
	
	@Test
	void updateShouldReturnProductDTOWhenIdExists () throws Exception {
		ProductDTO productDTO = Factory.createProductDTO ();
		String jsonBody = objectMapper.writeValueAsString (productDTO);
		
		String expectedName = productDTO.name ();
		String expectedReference = productDTO.reference ();
		String expectedGtin = String.valueOf (productDTO.gtinCode ());
		
		ResultActions result =
				mockMvc.perform (put ("/products/{id}", existingId)
						.content (jsonBody)
						.contentType (MediaType.APPLICATION_JSON)
						.accept (MediaType.APPLICATION_JSON));
		
		result.andExpect (status ().isOk ());
		result.andExpect (jsonPath ("$.name").value (expectedName));
		result.andExpect (jsonPath ("$.id").value (existingId));
		result.andExpect (jsonPath ("$.reference").value (expectedReference));
		result.andExpect (jsonPath ("$.gtinCode").value (expectedGtin));
	}
	
	@Test
	void updateShouldReturnNotFoundWhenIdDoesNotExist () throws Exception {
		ProductDTO productDTO = Factory.createProductDTO ();
		String jsonBody = objectMapper.writeValueAsString (productDTO);
		
		ResultActions result =
				mockMvc.perform (put ("/products/{id}", nonExistingId)
						.content (jsonBody)
						.contentType (MediaType.APPLICATION_JSON)
						.accept (MediaType.APPLICATION_JSON));
		
		result.andExpect (status ().isNotFound ());
	}
}
