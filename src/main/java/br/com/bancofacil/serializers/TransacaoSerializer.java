package br.com.bancofacil.serializers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.bancofacil.model.Transacao;

public class TransacaoSerializer extends JsonSerializer<Transacao> {

	@Override
	public void serialize(Transacao valor, JsonGenerator jsonGerador, SerializerProvider serializador)
			throws IOException {

		jsonGerador.writeStartObject();

		if (valor.getId() != null) {
			jsonGerador.writeNumberField("id", valor.getId());
		}

		jsonGerador.writeNumberField("valor", valor.getValor());
		jsonGerador.writeStringField("descricao", valor.getDescricao());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
		String criado = dateFormat.format(valor.getCriado());
		jsonGerador.writeStringField("criado", criado);

		jsonGerador.writeEndObject();

	}

}
