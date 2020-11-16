package br.bancoeveris.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.service.ContaService;
import br.bancoeveris.app.response.ContaList;
import br.bancoeveris.app.response.ContaResponse;



@RestController
@RequestMapping("/contas")
public class ContaController extends BaseController {
	@Autowired
	private ContaService service;

	@PostMapping
	public ResponseEntity inserir(@RequestBody ContaRequest contaRequest) { // Inserir Conta
		try {
			BaseResponse response = service.inserir(contaRequest);
			return ResponseEntity.status(response.statusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: HASH não está disponível! ");
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity obter(@PathVariable Long id) {  // 
		try {

			ContaResponse response = service.obter(id);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.statusCode).body(errorBase);
		}
	}

	@GetMapping
	public ResponseEntity listar() { //
		try {
			ContaList contas = service.listar();
			return ResponseEntity.status(HttpStatus.OK).body(contas);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.statusCode).body(errorBase);
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity atualizar(@RequestBody ContaRequest contaRequest, @PathVariable Long id) {
		try {
			BaseResponse response = service.atualizar(id, contaRequest);
			return ResponseEntity.status(response.statusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.statusCode).body(errorBase);
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity deletar(@PathVariable Long id) {
		try {
			BaseResponse response = service.deletar(id);
			return ResponseEntity.status(response.statusCode).build();
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.statusCode).body(errorBase);
		}
	}
	
	@GetMapping(path = "/saldo/{hash}")
	public ResponseEntity Saldo(@PathVariable String hash) {
		try {

			ContaResponse response = service.Saldo(hash);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.statusCode).body(errorBase);
		}
	
	}

}
