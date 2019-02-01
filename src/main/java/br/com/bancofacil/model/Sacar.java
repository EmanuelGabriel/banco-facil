package br.com.bancofacil.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import br.com.bancofacil.serializers.SacarDeserializer;

@Entity
@JsonDeserialize(using = SacarDeserializer.class)
public class Sacar extends Transacao {

	private static final long serialVersionUID = 1L;

	public Sacar() {
		this.descricao = "Saque";
	}

	public Sacar(@NotNull Double valor, ContaBancaria contaBancaria) {
		super();
		this.setValor(valor);
		this.setContaBancaria(contaBancaria);
	}

	@Override
	public void setValor(Double valor) {
		if (valor > 0) {
			valor *= -1;
		}
		super.setValor(valor);
	}

}
