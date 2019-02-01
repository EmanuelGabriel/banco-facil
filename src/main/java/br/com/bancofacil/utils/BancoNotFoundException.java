package br.com.bancofacil.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class BancoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -236858111066082392L;

}
