package br.com.bancofacil.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancofacil.model.Banco;
import br.com.bancofacil.repository.BancoRepository;
import br.com.bancofacil.utils.BancoNotFoundException;

@RestController
@RequestMapping("/api/v1/banco")
public class BancoResource {

	@Autowired
	private BancoRepository bancoRepository;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Banco> listaDeBancos() {
		return bancoRepository.findAll();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Banco buscarPorId(@PathVariable Long id) {

		Optional<Banco> bancoOptional = bancoRepository.findById(id);

		if (!bancoOptional.isPresent())
			throw new BancoNotFoundException();

		return bancoOptional.get();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Banco create(@Valid @RequestBody Banco banco) {
		return bancoRepository.save(banco);
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Banco atualizarBanco(@Valid @RequestBody Banco banco, @PathVariable Long id) {

		Optional<Banco> bancoOptional = bancoRepository.findById(id);

		if (!bancoOptional.isPresent())
			throw new BancoNotFoundException();

		banco.setId(id);

		return bancoRepository.save(banco);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletarBanco(@PathVariable Long id) {
		bancoRepository.deleteById(id);
	}

}
