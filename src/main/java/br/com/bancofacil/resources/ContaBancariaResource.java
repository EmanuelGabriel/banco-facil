package br.com.bancofacil.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.bancofacil.model.ContaBancaria;
import br.com.bancofacil.model.Deposito;
import br.com.bancofacil.model.Transacao;
import br.com.bancofacil.model.Transferencia;
import br.com.bancofacil.repository.ContaBancariaRepository;
import br.com.bancofacil.repository.DepositoRepository;
import br.com.bancofacil.repository.TransacaoRepository;
import br.com.bancofacil.repository.TransferenciaRepository;
import br.com.bancofacil.seguranca.CustomUserDetails;
import br.com.bancofacil.utils.TransferirRequest;

@RestController
@RequestMapping("/api/v1/conta-bancaria")
public class ContaBancariaResource {

	@Autowired
	private ContaBancariaRepository bancariaRepository;

	@Autowired
	private DepositoRepository depositoRepository;

	@Autowired
	private TransferenciaRepository transferenciaRepository;

	@Autowired
	private TransacaoRepository transacaoRepository;

	/**
	 * Método responsável em verificar se já existe uma conta bancária
	 * 
	 * @param contaBancaria
	 */
	private void verificarSeJaExisteContaBancaria(ContaBancaria contaBancaria) {

		Optional<ContaBancaria> contaBancariaJaExiste = bancariaRepository.findByUsername(contaBancaria.getUsuario());

		if (contaBancariaJaExiste.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Já existe um registro para este número de conta.");
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ContaBancaria criarContaBancaria(@Valid @RequestBody ContaBancaria contaBancaria) {

		verificarSeJaExisteContaBancaria(contaBancaria);
		return bancariaRepository.save(contaBancaria);

	}

	@PostMapping("/depositos")
	@ResponseStatus(HttpStatus.CREATED)
	public Deposito depositar(@Valid @RequestBody Deposito deposito) {

		Optional<ContaBancaria> contaBancariaOptional = obterContaBancariaRegistrada();

		if (contaBancariaOptional.isPresent()) {
			deposito.setContaBancaria(contaBancariaOptional.get());
			return depositoRepository.save(deposito);
		}

		throw new ResponseStatusException(HttpStatus.FORBIDDEN);

	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ContaBancaria getContaBancaria() {

		Optional<ContaBancaria> contaBancaria = obterContaBancariaRegistrada();

		if (contaBancaria.isPresent()) {
			return contaBancaria.get();

		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	private Optional<ContaBancaria> obterContaBancariaRegistrada() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = ((CustomUserDetails) auth.getPrincipal()).getUsername();
		return bancariaRepository.findByUsername(username);
	}

	@GetMapping("/transacoes")
	@ResponseStatus(HttpStatus.OK)
	public List<Transacao> listaDeTransacoes() {

		Optional<ContaBancaria> contaBancariaOptional = obterContaBancariaRegistrada();

		if (contaBancariaOptional.isPresent()) {
			return transacaoRepository.findByContaBancariaOrderByCriadoDesc(contaBancariaOptional.get());
		}

		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	@PostMapping("/transferir")
	@ResponseStatus(HttpStatus.OK)
	public Transferencia realizarTransferencia(@Valid @RequestBody TransferirRequest transferirRequest) {

		Optional<ContaBancaria> deContaBancariaOptional = obterContaBancariaRegistrada();

		if (deContaBancariaOptional.isPresent()) {

			ContaBancaria deContaBancaria = deContaBancariaOptional.get();

			if (deContaBancaria.getBalanco() < transferirRequest.getValue()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não tem saldo suficiente!");
			}

			Optional<ContaBancaria> paraContaBancariaOptional = bancariaRepository
					.findByUsername(transferirRequest.getUsername());

			if (paraContaBancariaOptional.isPresent()) {

				List<Transacao> transacoes = new ArrayList<Transacao>();

				Transacao deTransacao = new Transacao("Transferência");
				deTransacao.setValor(transferirRequest.getValue() * -1);
				deTransacao.setContaBancaria(deContaBancaria);

				Transacao paraTransacao = new Transacao("Transferência");
				paraTransacao.setValor(transferirRequest.getValue());
				paraTransacao.setContaBancaria(paraContaBancariaOptional.get());

				transacoes.add(transacaoRepository.save(deTransacao));
				transacoes.add(transacaoRepository.save(paraTransacao));

				Transferencia transferir = new Transferencia();

				transferir.setDe(deTransacao);
				transferir.setPara(paraTransacao);

				return transferenciaRepository.save(transferir);

			}

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta bancária de destino não existe.");

		}

		throw new ResponseStatusException(HttpStatus.FORBIDDEN);

	}

}
