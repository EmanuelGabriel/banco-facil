package br.com.bancofacil.seguranca;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bancofacil.model.ContaBancaria;
import br.com.bancofacil.repository.ContaBancariaRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	ContaBancariaRepository contaBancariaRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		ContaBancaria contaBancaria = contaBancariaRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Não foi possível encontrar sua Conta bancária."));
		return CustomUserDetails.criar(contaBancaria);
	}

	@Transactional
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {

		ContaBancaria contaBancaria = contaBancariaRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("Conta bancária não encontrada."));

		return CustomUserDetails.criar(contaBancaria);
	}
	
	

}
