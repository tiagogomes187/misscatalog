package br.dev.tiagogomes.misscatalog.tests;

import br.dev.tiagogomes.misscatalog.dto.ProductDTO;
import br.dev.tiagogomes.misscatalog.entities.Category;
import br.dev.tiagogomes.misscatalog.entities.Product;

import java.time.Instant;

public class Factory {
	
	public static Product createProduct () {
		Product product = new Product (1L, 17908839100477L, "10240", "AZUL COBALTO", "BIRKENMISS FIVELAS", "10240|AC40-41", 7908639100470L, "https://cnp30blob.blob.core.windows.net/cnp3files/58fc6a7d7ef78fc0097c313bcf3de05ccd22f69c3d6c326582ff7f0c6c7f36c9.png", "Miss-Miss", 8.238, 8.038, 12, 28.5, 39.5, 42.5, "6402.99.90", "28.059.00", "10001077", Instant.parse ("2023-10-12T10:30:00Z"), "GTIN-14", Instant.now (),Instant.now ());
		product.getCategories ().add (new Category (2L, "Infantis"));
		return product;
	}
	
	public static ProductDTO createProductDTO(){
		Product product = createProduct ();
		return ProductDTO.fromEntity (product);
	}
}
