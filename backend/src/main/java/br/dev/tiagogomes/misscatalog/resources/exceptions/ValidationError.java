package br.dev.tiagogomes.misscatalog.resources.exceptions;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StardardError {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<> ();
	
	public List<FieldMessage> getErrors () {
		return errors;
	}
	
	public void addError (String fieldName, String message) {
		errors.add (new FieldMessage (fieldName, message));
	}
}
