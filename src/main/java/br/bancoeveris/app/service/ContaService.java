package br.bancoeveris.app.service;

import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.response.ContaList;
import br.bancoeveris.app.response.ContaResponse;

public interface ContaService {
	
	BaseResponse inserir(ContaRequest contaRequest);
	
	ContaResponse obter(Long id);
	
	ContaList listar();
	
	BaseResponse atualizar(Long id, ContaRequest contaRequest);
	
	BaseResponse deletar(Long id);
	
	ContaResponse Saldo(String hash);

}
