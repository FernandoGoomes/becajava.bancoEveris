package br.bancoeveris.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.service.OperacaoService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/operacao")
public class OperacaoController {
	@Autowired
	private OperacaoService service;

	@PostMapping(path = "/depositos")
	public ResponseEntity depositar(@RequestBody OperacaoRequest operacaoRequest) {
		try {
			BaseResponse response = service.depositar(operacaoRequest);
			return ResponseEntity.status(response.statusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

	@PostMapping(path = "/saques")
	public ResponseEntity sacar(@RequestBody OperacaoRequest operacaoRequest) {
		try {
			BaseResponse response = service.sacar(operacaoRequest);
			return ResponseEntity.status(response.statusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

	@PostMapping(path = "/transferencia")
	public ResponseEntity transferir(@RequestBody TransferenciaRequest operacaoRequest) {
		try {
			BaseResponse response = service.transferir(operacaoRequest);
			return ResponseEntity.status(response.statusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

}
