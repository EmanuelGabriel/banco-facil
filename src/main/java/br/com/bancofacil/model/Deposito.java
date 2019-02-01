package br.com.bancofacil.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Deposito extends Transacao {

	private static final long serialVersionUID = 5318493360983251704L;

	public Deposito() {
		this.descricao = "Deposito";
	}

	public Deposito(@NotNull Double valor, ContaBancaria contaBancaria) {
		super();
		this.setValor(valor);
		this.setContaBancaria(contaBancaria);
	}

	@Override
	public void setValor(Double valor) {
		if (valor < 0) {
			valor *= -1;
		}

		super.setValor(valor);
	}

}
