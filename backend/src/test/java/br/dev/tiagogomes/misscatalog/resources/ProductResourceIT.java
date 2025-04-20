package br.dev.tiagogomes.misscatalog.resources;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
	
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
	
}
