package br.dev.tiagogomes.misscatalog.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table (name = "tb_category")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String name;
	
	public Category () {
	}
	
	public Category (Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Long getId () {
		return id;
	}
	
	public void setId (Long id) {
		this.id = id;
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals (Object o) {
		if (!(o instanceof Category category)) return false;
		return Objects.equals (getId (), category.getId ());
	}
	
	@Override
	public int hashCode () {
		return Objects.hashCode (getId ());
	}
}
