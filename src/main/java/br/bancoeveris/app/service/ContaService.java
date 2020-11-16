package br.bancoeveris.app.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.bancoeveris.app.model.*;
import br.bancoeveris.app.repository.*;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.response.ContaList;
import br.bancoeveris.app.response.ContaResponse;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private OperacaoService operacaoService;
	

	public BaseResponse inserir(ContaRequest contaRequest) {
		Conta conta = new Conta();
		ContaResponse response = new ContaResponse();
		response.statusCode = 400;

		if (contaRequest.getNome() == "" || contaRequest.getNome() == null ) {
			response.message = "O nome do cliente não foi preenchido.";
			return response;
		}

		if (contaRequest.getCpf() == "" || contaRequest.getCpf() == null) {
			response.message = "O CPF do cliente não foi preenchido.";
			return response;
		}

		conta.setSaldo(0.0);

		conta.setNome(contaRequest.getNome());
		conta.setCpf(contaRequest.getCpf());
		
		// Criar HASH random
		UUID uuid = UUID.randomUUID();
		conta.setHash(uuid.toString());

		contaRepository.save(conta);
		response.statusCode = 201;
		response.message = "Cliente inserida com sucesso.";
		return response;
	}

	public ContaResponse obter(Long id) {
		Optional<Conta> cliente = contaRepository.findById(id);

		ContaResponse response = new ContaResponse();

		if (cliente.isEmpty()) {
			response.message = "Cliente não encontrado";
			response.statusCode = 404;
			return response;
			
		}
		response.setHash(cliente.get().getHash());
		response.setSaldo(cliente.get().getSaldo());
		response.message = "Cliente obtido com sucesso";
		response.statusCode = 200;
		return response;
	}

	public ContaList listar() {

		List<Conta> lista = contaRepository.findAll();

		ContaList response = new ContaList();
		response.setContas(lista);
		response.statusCode = 200;
		response.message = "Clientes obtidos com sucesso.";

		return response;
	}

	public BaseResponse atualizar(Long id, ContaRequest contaRequest) {
		Conta conta = new Conta();
		BaseResponse base = new BaseResponse();
		base.statusCode = 400;

		if (contaRequest.getNome() == "" || contaRequest.getNome() == null) {
			base.message = "O nome do cliente não foi preenchido.";
			return base;
		}

		if (contaRequest.getCpf() == "" || contaRequest.getCpf() == null) {
			base.message = "O CPF do cliente não foi preenchido.";
			return base;
		}

		if (contaRequest.getHash() == "" || contaRequest.getHash() == null) {
			base.message = "O Hash do cliente não foi preenchido.";
			return base;
		}

		conta.setId(id);
		conta.setNome(contaRequest.getNome());
		conta.setCpf(contaRequest.getCpf());
		conta.setHash(contaRequest.getHash());

		contaRepository.save(conta);
		base.statusCode = 200;
		base.message = "Cliente atualizado com sucesso.";
		return base;
	}

	public BaseResponse deletar(Long id) {
		BaseResponse response = new BaseResponse();

		if (id == null || id == 0) {
			response.statusCode = 400;
			return response;
		}

		contaRepository.deleteById(id);
		response.statusCode = 200;
		return response;
	}
	
	public ContaResponse Saldo(String hash) {
		ContaResponse response = new ContaResponse();
		response.statusCode = 400;
		
		Conta conta = contaRepository.findByHash(hash);
		
		if (conta == null) {
		response.message = "Conta não encontrada";
		return response;
	}
	
	Double saldo = operacaoService.Saldo(conta.getId());
	
	response.setSaldo(saldo);
	response.setNome(conta.getNome());
	response.setHash(conta.getHash());
	response.statusCode = 200;
	return response;	
	
	}
}
