package br.bancoeveris.app.service;

import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;

public interface OperacaoService {
	
	BaseResponse depositar(OperacaoRequest operacaoRequest);
	
	BaseResponse sacar(OperacaoRequest operacaoRequestSacar);
	
	BaseResponse transferir(TransferenciaRequest operacaoRequest);
	
	public double Saldo(Long contaId);


}
