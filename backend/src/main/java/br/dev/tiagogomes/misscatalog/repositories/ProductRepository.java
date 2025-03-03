package br.dev.tiagogomes.misscatalog.repositories;

import br.dev.tiagogomes.misscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
