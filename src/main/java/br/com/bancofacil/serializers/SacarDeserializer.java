package br.com.bancofacil.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.ObjectCodec;

import br.com.bancofacil.model.Sacar;

public class SacarDeserializer extends JsonDeserializer<Sacar> {

	@Override
	public Sacar deserialize(JsonParser jsonParse, DeserializationContext contexto)
			throws IOException, JsonProcessingException {

		ObjectCodec objectCodec = jsonParse.getCodec();
		JsonNode jsonNode = objectCodec.readTree(jsonParse);

		Sacar sacarDinheiro = new Sacar();

		Double valor = jsonNode.get("value").asDouble();
		sacarDinheiro.setValor(valor);

		return sacarDinheiro;

	}

}
