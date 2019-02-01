package br.com.bancofacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancofacil.model.Banco;

public interface BancoRepository extends JpaRepository<Banco, Long>{

}
