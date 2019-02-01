package br.com.bancofacil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bancofacil.model.ContaBancaria;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {

	Optional<ContaBancaria> findByUsername(String usuario);

}
