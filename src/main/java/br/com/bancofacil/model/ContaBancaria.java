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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
public class ContaBancaria implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	private String proprietario;

	private String username;

	@PositiveOrZero
	private Integer digito;

	@PositiveOrZero
	private Integer numero;

	@NotNull
	@NotBlank
	private String senha;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "agencia_id")
	private Agencia agencia;

	@OneToMany(mappedBy = "contaBancaria")
	private Set<Transacao> transacaos;

	public ContaBancaria() {
	}

	public ContaBancaria(String proprietario, Integer digito, Integer numero, String senha, Agencia agencia) {
		super();
		this.proprietario = proprietario;
		this.digito = digito;
		this.numero = numero;
		this.setSenha(senha);
		this.agencia = agencia;
		gerarUsuario();
	}

	/**
	 * Criando o método que irá gerar um Usuário
	 */
	public void gerarUsuario() {

		if (this.agencia != null && this.numero != null && this.digito != null) {

			String banco = this.agencia.getBanco().getId().toString();

			String agencia = this.agencia.getNumero().toString();
			agencia += "-" + this.agencia.getDigito().toString();

			String conta = this.numero.toString();
			conta += "-" + this.digito.toString();

			this.username = banco + "-" + agencia + "-" + conta;
		}
	}

	/**
	 * Método que verifica a senha do usuário
	 * 
	 * @param senha
	 * @return
	 */
	public Boolean verificarSenha(String senha) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		senha = passwordEncoder.encode(senha);
		return senha.equals(this.senha);
	}

	/**
	 * Método que pega o balanço
	 * 
	 * @return
	 */
	public Double getBalanco() {

		Double balanco = 0.0;
		if (transacaos != null) {
			for (Transacao transaction : transacaos) {
				balanco += transaction.getValor();
			}
		}
		return balanco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

	public String getUsuario() {
		return username;
	}

	public void setUsuario(String usuario) {
		this.username = usuario;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.senha = passwordEncoder.encode(senha);
		this.senha = senha;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Set<Transacao> getTransacaos() {
		return transacaos;
	}

	public void setTransacaos(Set<Transacao> transacaos) {
		this.transacaos = transacaos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaBancaria other = (ContaBancaria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
