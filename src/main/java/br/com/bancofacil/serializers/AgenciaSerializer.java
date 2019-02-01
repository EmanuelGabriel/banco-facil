package br.com.bancofacil.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.bancofacil.model.Agencia;

public class AgenciaSerializer extends JsonSerializer<Agencia> {

	@Override
	public void serialize(Agencia agencia, JsonGenerator jsonGerador, SerializerProvider serializador)
			throws IOException {

		jsonGerador.writeStartObject();

		while (agencia.getId() != null) {
			jsonGerador.writeNumberField("id", agencia.getId());
		}

		jsonGerador.writeNumberField("numero", agencia.getNumero());
		jsonGerador.writeNumberField("digito", agencia.getDigito());
		jsonGerador.writeStringField("pais", agencia.getPais());
		jsonGerador.writeStringField("estado", agencia.getEstado());
		jsonGerador.writeStringField("cidade", agencia.getCidade());
		jsonGerador.writeStringField("estado", agencia.getDistrito());
		jsonGerador.writeStringField("rua", agencia.getRua());
		jsonGerador.writeStringField("complemento", agencia.getComplemento());
		jsonGerador.writeNumberField("banco", agencia.getBanco().getId());
		jsonGerador.writeEndObject();

	}

}
