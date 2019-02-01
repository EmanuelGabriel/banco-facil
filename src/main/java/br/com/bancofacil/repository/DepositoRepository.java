package br.com.bancofacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancofacil.model.Deposito;

public interface DepositoRepository extends JpaRepository<Deposito, Long>{

}
