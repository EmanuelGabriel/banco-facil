package br.com.bancofacil.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.bancofacil.serializers.AgenciaDeserializer;
import br.com.bancofacil.serializers.AgenciaSerializer;

@Entity
@JsonSerialize(using = AgenciaSerializer.class)
@JsonDeserialize(using = AgenciaDeserializer.class)
public class Agencia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@PositiveOrZero
	private Integer digito;

	@NotNull
	@PositiveOrZero
	private Integer numero;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "banco_id")
	private Banco banco;

	@OneToMany(mappedBy = "agencia")
	private Set<ContaBancaria> contaBancarias;

	@NotBlank
	private String pais;

	@Length(min = 2, max = 2)
	private String estado;

	@NotBlank
	private String cidade;

	@NotBlank
	private String distrito;

	@NotBlank
	private String rua;

	private String complemento;

	public Agencia() {
	}

	public Agencia(Integer digito, Integer numero, Banco banco, String pais, String estado, String cidade,
			String distrito, String rua) {
		super();
		this.digito = digito;
		this.numero = numero;
		this.banco = banco;
		this.pais = pais;
		this.estado = estado;
		this.cidade = cidade;
		this.distrito = distrito;
		this.rua = rua;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDigito() {
		return digito;
	}

	public void setDigito(Integer digito) {
		this.digito = digito;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Set<ContaBancaria> getContaBancarias() {
		return contaBancarias;
	}

	public void setContaBancarias(Set<ContaBancaria> contaBancarias) {
		this.contaBancarias = contaBancarias;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
