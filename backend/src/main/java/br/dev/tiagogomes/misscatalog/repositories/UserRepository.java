package br.dev.tiagogomes.misscatalog.repositories;

import br.dev.tiagogomes.misscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
