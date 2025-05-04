package br.dev.tiagogomes.misscatalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table (name = "tb_product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	@Column (name = "gtin", unique = true)
	private Long gtinCode;
	@Column (name = "referencia")
	private String reference;
	@Column (name = "cor")
	private String color;
	@Column (name = "nome")
	private String name;
	@Column (name = "codigos_adicionais")
	private String additionalCodes;
	@Column (name = "itens_contidos")
	private Long itemsIncluded;
	@Column (name = "imagens")
	private String images;
	@Column (name = "marcas")
	private String brand;
	@Column (name = "peso_bruto")
	private Double grossWeight;
	@Column (name = "peso_liquido")
	private Double netWeight;
	@Column (name = "conteudo_liquido")
	private Integer netContent;
	@Column (name = "altura")
	private Double height;
	@Column (name = "largura")
	private Double width;
	@Column (name = "profundidade")
	private Double depth;
	@Column (name = "ncm")
	private String ncm;
	@Column (name = "cest")
	private String cest;
	@Column (name = "gpc")
	private String gpc;
	@Column (name = "data_lancamento", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant releaseDate;
	@Column (name = "tipo")
	private String type;
	@Column (columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;
	@Column (columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;
	
	@ManyToMany
	@JoinTable (name = "tb_product_category", joinColumns = @JoinColumn (name = "product_id"), inverseJoinColumns = @JoinColumn (name = "category_id"))
	Set<Category> categories = new HashSet<> ();
	
	public Product () {
	}
	
	public Product (Long id, Long gtinCode, String reference, String color, String name, String additionalCodes, Long itemsIncluded, String images, String brand, Double grossWeight, Double netWeight, Integer netContent, Double height, Double width, Double depth, String ncm, String cest, String gpc, Instant releaseDate, String type, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.gtinCode = gtinCode;
		this.reference = reference;
		this.color = color;
		this.name = name;
		this.additionalCodes = additionalCodes;
		this.itemsIncluded = itemsIncluded;
		this.images = images;
		this.brand = brand;
		this.grossWeight = grossWeight;
		this.netWeight = netWeight;
		this.netContent = netContent;
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.ncm = ncm;
		this.cest = cest;
		this.gpc = gpc;
		this.releaseDate = releaseDate;
		this.type = type;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Long getId () {
		return id;
	}
	
	public Long getGtinCode () {
		return gtinCode;
	}
	
	public String getReference () {
		return reference;
	}
	
	public String getColor () {
		return color;
	}
	
	public String getName () {
		return name;
	}
	
	public String getAdditionalCodes () {
		return additionalCodes;
	}
	
	public Long getItemsIncluded () {
		return itemsIncluded;
	}
	
	public String getImages () {
		return images;
	}
	
	public String getBrand () {
		return brand;
	}
	
	public Double getGrossWeight () {
		return grossWeight;
	}
	
	public Double getNetWeight () {
		return netWeight;
	}
	
	public Integer getNetContent () {
		return netContent;
	}
	
	public Double getHeight () {
		return height;
	}
	
	public Double getWidth () {
		return width;
	}
	
	public Double getDepth () {
		return depth;
	}
	
	public String getNcm () {
		return ncm;
	}
	
	public String getCest () {
		return cest;
	}
	
	public String getGpc () {
		return gpc;
	}
	
	public Instant getReleaseDate () {
		return releaseDate;
	}
	
	public String getType () {
		return type;
	}
	
	public Instant getCreatedAt () {
		return createdAt;
	}
	
	public Instant getUpdatedAt () {
		return updatedAt;
	}
	
	public Set<Category> getCategories () {
		return categories;
	}
	
	public void setId (Long id) {
		this.id = id;
	}
	
	public void setGtin_code (Long gtin_code) {
		this.gtinCode = gtin_code;
	}
	
	public void setReference (String reference) {
		this.reference = reference;
	}
	
	public void setColor (String color) {
		this.color = color;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void setAdditionalCodes (String additionalCodes) {
		this.additionalCodes = additionalCodes;
	}
	
	public void setItemsIncluded (Long itemsIncluded) {
		this.itemsIncluded = itemsIncluded;
	}
	
	public void setImages (String images) {
		this.images = images;
	}
	
	public void setBrand (String brand) {
		this.brand = brand;
	}
	
	public void setGrossWeight (Double grossWeight) {
		this.grossWeight = grossWeight;
	}
	
	public void setNetWeight (Double netWeight) {
		this.netWeight = netWeight;
	}
	
	public void setNetContent (Integer netContent) {
		this.netContent = netContent;
	}
	
	public void setHeight (Double height) {
		this.height = height;
	}
	
	public void setWidth (Double width) {
		this.width = width;
	}
	
	public void setDepth (Double depth) {
		this.depth = depth;
	}
	
	public void setNcm (String ncm) {
		this.ncm = ncm;
	}
	
	public void setCest (String cest) {
		this.cest = cest;
	}
	
	public void setGpc (String gpc) {
		this.gpc = gpc;
	}
	
	public void setReleaseDate (Instant releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public void setType (String type) {
		this.type = type;
	}
	
	@PrePersist
	public void prePersist () {
		createdAt = Instant.now ();
	}
	
	@PreUpdate
	public void preUpdate () {
		updatedAt = Instant.now ();
	}
	
	@Override
	public boolean equals (Object o) {
		if (!(o instanceof Product product)) return false;
		return Objects.equals (getId (), product.getId ()) && Objects.equals (getGtinCode (), product.getGtinCode ());
	}
	
	@Override
	public int hashCode () {
		return Objects.hash (getId (), getGtinCode ());
	}
	
	
}
