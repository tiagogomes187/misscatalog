package br.dev.tiagogomes.misscatalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table (name = "tb_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank (message = "O e-mail é obrigatório!")
	@Email (message = "Formato de e-mail inválido!")
	private String email;
	@NotBlank
	private String password;
	
	@ManyToMany
	@JoinTable (name = "tb_user_role",
			joinColumns = @JoinColumn (name = "user_id"),
			inverseJoinColumns = @JoinColumn (name = "role_id"))
	private Set<Role> roles = new HashSet<> ();
	
	public User () {
	}
	
	public User (Long id, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	public Long getId () {
		return id;
	}
	
	public void setId (Long id) {
		this.id = id;
	}
	
	public String getFirstName () {
		return firstName;
	}
	
	public void setFirstName (String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName () {
		return lastName;
	}
	
	public void setLastName (String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail () {
		return email;
	}
	
	public void setEmail (String email) {
		this.email = email;
	}
	
	public String getPassword () {
		return password;
	}
	
	public void setPassword (String password) {
		this.password = password;
	}
	
	public Set<Role> getRoles () {
		return roles;
	}
	
	@Override
	public boolean equals (Object o) {
		if (!(o instanceof User user)) return false;
		return Objects.equals (getId (), user.getId ()) && Objects.equals (getEmail (), user.getEmail ());
	}
	
	@Override
	public int hashCode () {
		return Objects.hash (getId (), getEmail ());
	}
}
