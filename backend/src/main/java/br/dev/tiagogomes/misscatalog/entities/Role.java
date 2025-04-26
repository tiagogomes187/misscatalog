package br.dev.tiagogomes.misscatalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table (name = "tb_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String authority;
	
	public Role () {
	}
	
	public Role (Long id, String authority) {
		this.id = id;
		this.authority = authority;
	}
	
	public Long getId () {
		return id;
	}
	
	public void setId (Long id) {
		this.id = id;
	}
	
	public String getAuthority () {
		return authority;
	}
	
	public void setAuthority (String authority) {
		this.authority = authority;
	}
	
	@Override
	public boolean equals (Object o) {
		if (!(o instanceof Role role)) return false;
		return Objects.equals (getId (), role.getId ());
	}
	
	@Override
	public int hashCode () {
		return Objects.hashCode (getId ());
	}
}
