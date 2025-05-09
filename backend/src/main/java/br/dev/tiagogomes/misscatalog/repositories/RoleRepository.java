package br.dev.tiagogomes.misscatalog.repositories;

import br.dev.tiagogomes.misscatalog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
