package br.com.bancofacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancofacil.model.ContaBancaria;
import br.com.bancofacil.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
	
	public List<Transacao> findByContaBancariaOrderByCriadoDesc(ContaBancaria contaBancaria);
	

}
