package br.dev.tiagogomes.misscatalog.services.validation;

import br.dev.tiagogomes.misscatalog.dto.UserInsertDTO;
import br.dev.tiagogomes.misscatalog.entities.User;
import br.dev.tiagogomes.misscatalog.repositories.UserRepository;
import br.dev.tiagogomes.misscatalog.resources.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	public UserInsertValidator (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private UserRepository userRepository;
	
	@Override
	public void initialize (UserInsertValid ann) {
	}
	
	@Override
	public boolean isValid (UserInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<> ();
		
		User user = userRepository.findByEmail (dto.email ());
		
		if (user != null) {
			list.add (new FieldMessage ("email", "Email já existe"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation ();
			context.buildConstraintViolationWithTemplate (e.getMessage ()).addPropertyNode (e.getFildName ())
					.addConstraintViolation ();
		}
		return list.isEmpty ();
	}
}