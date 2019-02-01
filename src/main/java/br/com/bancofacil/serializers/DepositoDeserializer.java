package br.com.bancofacil.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.bancofacil.model.Deposito;

public class DepositoDeserializer extends JsonDeserializer<Deposito> {

	@Override
	public Deposito deserialize(JsonParser jsonParse, DeserializationContext contexto)
			throws IOException, JsonProcessingException {

		ObjectCodec objectCodec = jsonParse.getCodec();
		JsonNode jsonNode = objectCodec.readTree(jsonParse);

		Deposito deposito = new Deposito();

		Double valor = jsonNode.get("value").asDouble();
		deposito.setValor(valor);

		return deposito;

	}

}
