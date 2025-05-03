package br.dev.tiagogomes.misscatalog.services;

import br.dev.tiagogomes.misscatalog.dto.RoleDTO;
import br.dev.tiagogomes.misscatalog.dto.UserDTO;
import br.dev.tiagogomes.misscatalog.dto.UserInsertDTO;
import br.dev.tiagogomes.misscatalog.entities.Role;
import br.dev.tiagogomes.misscatalog.entities.User;
import br.dev.tiagogomes.misscatalog.repositories.RoleRepository;
import br.dev.tiagogomes.misscatalog.repositories.UserRepository;
import br.dev.tiagogomes.misscatalog.services.exceptions.DatabaseException;
import br.dev.tiagogomes.misscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	private RoleRepository roleRepository;
	
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserService (UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional (readOnly = true)
	public Page<UserDTO> findAllPaged (Pageable pageable) {
		Page<User> page = userRepository.findAll (pageable);
		return page.map (entity -> UserDTO.dtoFromEntity (entity));
	}
	
	@Transactional (readOnly = true)
	public UserDTO findById (Long id) {
		Optional<User> obj = userRepository.findById (id);
		User entity = obj.orElseThrow (() -> new ResourceNotFoundException ("Entity with id " + id + " not found"));
		return UserDTO.dtoFromEntity (entity);
	}
	
	@Transactional
	public UserDTO insert (UserInsertDTO dto) {
		User entity = new User ();
		copyDtoToEntity (dto.toUserDTO (), entity);
		entity.setPassword (passwordEncoder.encode (dto.password ()));
		entity = userRepository.save (entity);
		return UserDTO.dtoFromEntity (entity);
	}
	
	@Transactional
	public UserDTO update (Long id, UserDTO dto) {
		try {
			User entity = userRepository.getReferenceById (id);
			copyDtoToEntity (dto, entity);
			entity = userRepository.save (entity);
			return UserDTO.dtoFromEntity (entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException ("Id " + id + " not found");
		}
	}
	
	@Transactional (propagation = Propagation.SUPPORTS)
	public void delete (Long id) {
		if (!userRepository.existsById (id)) {
			throw new ResourceNotFoundException ("Resource note found");
		}
		try {
			userRepository.deleteById (id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException ("Referential integrity failure");
		}
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setFirstName(dto.firstName());
		entity.setLastName(dto.lastName());
		entity.setEmail(dto.email());
		entity.getRoles().clear();
		for (RoleDTO roleDTO : dto.roles ()) {
			Role role = roleRepository.getReferenceById(roleDTO.id());
			entity.getRoles().add(role);
		}
	}
	
}
