package br.dev.tiagogomes.misscatalog.resources.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class FieldMessage implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private String fildName;
	private String message;
	
	public FieldMessage () {
	}
	
	public FieldMessage (String fildName, String message) {
		this.fildName = fildName;
		this.message = message;
	}
	
	public String getFildName () {
		return fildName;
	}
	
	public void setFildName (String fildName) {
		this.fildName = fildName;
	}
	
	public String getMessage () {
		return message;
	}
	
	public void setMessage (String message) {
		this.message = message;
	}
}
