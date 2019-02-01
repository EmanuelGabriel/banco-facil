package br.com.bancofacil.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.bancofacil.model.Agencia;
import br.com.bancofacil.model.Banco;
import br.com.bancofacil.repository.AgenciaRepository;
import br.com.bancofacil.repository.BancoRepository;
import br.com.bancofacil.utils.AgenciaNotFoundException;
import br.com.bancofacil.utils.BancoNotFoundException;

@RestController
@RequestMapping("/api/v1/agencias")
public class AgenciaResource {

	@Autowired
	private BancoRepository bancoRepository;

	@Autowired
	private AgenciaRepository agenciaRepository;

	private void verificarSeJaExisteAgencia(Agencia agenciaValidacao) {

		Banco banco = agenciaValidacao.getBanco();

		for (Agencia verificar : banco.getAgencias()) {
			if (agenciaValidacao.getId().equals(verificar.getId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível registrar agência!");
			}
		}

	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Agencia> listar(@RequestParam(name = "banco", required = false) Long id) {

		if (id != null) {

			Optional<Banco> bancoSalvar = bancoRepository.findById(id);

			if (bancoSalvar.isPresent()) {
				return bancoSalvar.get().getAgencias();
			} else {
				throw new BancoNotFoundException();
			}
		}

		return agenciaRepository.findAll();

	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Agencia buscarPorId(@PathVariable Long id) {

		Optional<Agencia> agencia = agenciaRepository.findById(id);

		if (!agencia.isPresent()) {
			throw new AgenciaNotFoundException();
		}

		return agencia.get();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Agencia criarAgencia(@Valid @RequestBody Agencia agencia) {

		verificarSeJaExisteAgencia(agencia);
		return agenciaRepository.save(agencia);

	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Agencia atualziarAgencia(@Valid @RequestBody Agencia agencia, @PathVariable Long id) {

		Optional<Agencia> agenciaSalva = agenciaRepository.findById(id);

		if (!agenciaSalva.isPresent()) {
			throw new AgenciaNotFoundException();
		}

		// setando o Id da Agência
		agencia.setId(id);

		while (!agencia.getId().equals(agenciaSalva.get().getId())) {

			verificarSeJaExisteAgencia(agencia);
		}

		return agenciaRepository.save(agencia);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletar(@PathVariable Long id) {
		agenciaRepository.deleteById(id);
	}

}
