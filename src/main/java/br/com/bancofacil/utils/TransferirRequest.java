package br.com.bancofacil.utils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferirRequest {

	@NotNull
	private Double valor;

	@Positive
	@NotNull
	private Long banco;

	@Positive
	@NotNull
	private Long agencia;

	@Positive
	@NotNull
	private Long digitoAgencia;

	@Positive
	@NotNull
	private Long conta;

	@Positive
	@NotNull
	private Long digitoConta;

	public String getUsername() {
		String bank = this.banco.toString();
		String agency = this.agencia.toString() + "-" + this.digitoAgencia.toString();
		String account = this.conta.toString() + "-" + this.digitoConta.toString();
		return bank + "-" + agency + "-" + account;
	}

	public Double getValue() {
		if (valor < 0) {
			valor *= -1;
		}
		return valor;
	}

	public void setValue(Double value) {
		this.valor = value;
	}

	public Long getBank() {
		return banco;
	}

	public void setBank(Long bank) {
		this.banco = bank;
	}

	public Long getAgency() {
		return agencia;
	}

	public void setAgency(Long agency) {
		this.agencia = agency;
	}

	public Long getAgencyDigit() {
		return digitoAgencia;
	}

	public void setAgencyDigit(Long agencyDigit) {
		this.digitoAgencia = agencyDigit;
	}

	public Long getAccount() {
		return conta;
	}

	public void setAccount(Long account) {
		this.conta = account;
	}

	public Long getAccountDigit() {
		return digitoConta;
	}

	public void setAccountDigit(Long accountDigit) {
		this.digitoConta = accountDigit;
	}

}
