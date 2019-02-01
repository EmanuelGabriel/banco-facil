package br.com.bancofacil.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancofacil.repository.ContaBancariaRepository;
import br.com.bancofacil.seguranca.JwtTokenProvider;
import br.com.bancofacil.utils.JwtAuthenticationResponse;
import br.com.bancofacil.utils.LoginRequest;


@RestController
@RequestMapping("/api/auth")
public class AutenticacaoResource {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	ContaBancariaRepository contaBancariaRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@PostMapping("/token")
	public ResponseEntity<?> authenticateBankAccount(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication autenticacao = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(autenticacao);

		String jwToken = jwtTokenProvider.gerarToken(autenticacao);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwToken));
		
	}
	
	
}
