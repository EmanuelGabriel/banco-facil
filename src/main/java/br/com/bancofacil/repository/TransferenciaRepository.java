package br.com.bancofacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancofacil.model.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

}
