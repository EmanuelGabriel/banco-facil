package br.com.bancofacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancofacil.model.Agencia;

public interface AgenciaRepository extends JpaRepository<Agencia, Long>{
	
	

}
