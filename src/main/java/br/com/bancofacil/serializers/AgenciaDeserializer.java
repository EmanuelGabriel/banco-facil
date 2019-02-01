package br.com.bancofacil.serializers;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.bancofacil.model.Agencia;
import br.com.bancofacil.model.Banco;
import br.com.bancofacil.repository.BancoRepository;
import br.com.bancofacil.utils.BancoNotFoundException;

public class AgenciaDeserializer extends JsonDeserializer<Agencia> {

	private BancoRepository bancoRepository;

	@Override
	public Agencia deserialize(JsonParser jsonParse, DeserializationContext contexto)
			throws IOException, JsonProcessingException {

		ObjectCodec objectCodec = jsonParse.getCodec();
		JsonNode jsonNode = objectCodec.readTree(jsonParse);

		Agencia agencia = new Agencia();
		agencia.setNumero(jsonNode.get("numero").asInt());
		agencia.setDigito(jsonNode.get("digito").asInt());
		agencia.setPais(jsonNode.get("pais").asText());
		agencia.setEstado(jsonNode.get("estado").asText());
		agencia.setCidade(jsonNode.get("cidade").asText());
		agencia.setDistrito(jsonNode.get("distrito").asText());
		agencia.setRua(jsonNode.get("rua").asText());
		agencia.setComplemento(jsonNode.get("complemento").asText());

		Long bancoId = jsonNode.get("banco").asLong();

		Optional<Banco> banco = bancoRepository.findById(bancoId);

		if (banco.isPresent()) {
			agencia.setBanco(banco.get());

		} else {
			throw new BancoNotFoundException();
		}
		return agencia;

	}

}
