package br.dev.tiagogomes.misscatalog.repositories;

import br.dev.tiagogomes.misscatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
