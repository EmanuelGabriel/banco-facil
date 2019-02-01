package br.com.bancofacil.seguranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.bancofacil.model.ContaBancaria;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private String usuario;

	@JsonIgnore
	private String senha;

	private Collection<? extends GrantedAuthority> autorizacoes;

	public CustomUserDetails(Long id, String usuario, String nome, String senha,
			Collection<? extends GrantedAuthority> autorizacoes) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.autorizacoes = autorizacoes;
	}

	public static CustomUserDetails criar(ContaBancaria contaBancaria) {

		List<GrantedAuthority> autorizacoes = new ArrayList<GrantedAuthority>();
		autorizacoes.add(new SimpleGrantedAuthority("CLIENTE"));
		return new CustomUserDetails(contaBancaria.getId(), contaBancaria.getUsuario(), contaBancaria.getProprietario(),
				contaBancaria.getSenha(), autorizacoes);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Collection<? extends GrantedAuthority> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(Collection<? extends GrantedAuthority> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return autorizacoes;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		CustomUserDetails that = (CustomUserDetails) obj;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String getUsername() {
		return usuario;
	}

	@Override
	public String getPassword() {
		return senha;
	}

}
