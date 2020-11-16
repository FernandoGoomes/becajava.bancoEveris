package br.bancoeveris.app.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;



@Service
public class OperacaoService {
	@Autowired
	private OperacaoRepository operacaoRepository;

	@Autowired
	private ContaRepository contaRepository;

	public BaseResponse depositar(OperacaoRequest operacaoRequest) {

		Conta conta = contaRepository.findByHash(operacaoRequest.getHash());

		Operacao op = new Operacao();
		BaseResponse base = new BaseResponse();
		base.statusCode = 400;

		if (operacaoRequest.getTipo() == "") {
			base.message = "Insira o tipo D (deposito)";
			return base;
		}

		if (operacaoRequest.getValor() <= 0) {

			base.message = "O Valor do cliente não foi preenchido.";

			return base;

		}

		op.setTipo(operacaoRequest.getTipo());
		op.setValor(operacaoRequest.getValor());
		op.setContaOrigem(conta);

		conta.setSaldo(conta.getSaldo() + operacaoRequest.getValor());

		contaRepository.save(conta);

		operacaoRepository.save(op);

		base.statusCode = 200;
		base.message = "Deposito realizado com sucesso.";
		return base;
	}

	public BaseResponse sacar(OperacaoRequest operacaoRequestSacar) {

		Conta conta = contaRepository.findByHash(operacaoRequestSacar.getHash());

		Operacao op = new Operacao();

		BaseResponse base = new BaseResponse();

		base.statusCode = 400;

		if (operacaoRequestSacar.getValor() > conta.getSaldo()) {

			base.message = "Saque nao pode ser efetuado  valor do saldo menor ";
			return base;

		}

		op.setValor(operacaoRequestSacar.getValor());
		op.setTipo(operacaoRequestSacar.getTipo());
		op.setContaOrigem(conta);

		conta.setSaldo(conta.getSaldo() - operacaoRequestSacar.getValor());

		contaRepository.save(conta);
		operacaoRepository.save(op);

		base.statusCode = 200;
		base.message = "Saque realizado com sucesso.";

		return base;
	}

	public BaseResponse transferir(TransferenciaRequest operacaoRequest) {

		Conta conta1 = contaRepository.findByHash(operacaoRequest.getHashOrigem());
		Conta conta2 = contaRepository.findByHash(operacaoRequest.getHashDestino());

		BaseResponse base = new BaseResponse();

		Operacao operacao = new Operacao();

		
		if (conta1 == null) {
			base.statusCode = 404;
			base.message = "Conta origem não foi encontrada tente novamente";
			return base;
		}
		if (conta2 == null) {
			base.statusCode = 404;
			base.message = "A conta  destino não foi  encontrada  tente novamente";
			return base;
		}
		
		base.statusCode = 400;
		
		if (operacaoRequest.getValor() == 0) {

			base.message = "O valor Esta abaixo do limite Tente novamente ";

			return base;

		}

		if (operacaoRequest.getValor() > conta1.getSaldo()) {

			base.message = "O valor Inserido esta Abaixo do seu Saldo Tente Novamente";

			return base;
		}

		conta1.setSaldo(conta1.getSaldo() - operacaoRequest.getValor());
		conta2.setSaldo(conta2.getSaldo() + operacaoRequest.getValor());

		operacao.setContaOrigem(conta1);
		operacao.setContaDestino(conta2);

		operacao.setValor(operacaoRequest.getValor());
		operacao.setTipo(operacaoRequest.getTipo());

		contaRepository.save(conta1);
		contaRepository.save(conta2);

		operacaoRepository.save(operacao);

		base.statusCode = 200;
		base.message = "Transferencia realizada com sucesso.";
		return base;
	}

	public double Saldo(Long contaId) {

		double saldo = 0;

		Conta contaOrigem = new Conta();
		contaOrigem.setId(contaId);

		Conta contaDestino = new Conta();
		contaDestino.setId(contaId);

		
		
		List<Operacao> lista = operacaoRepository.findOperacoesPorConta(contaId);

		for (Operacao o : lista) {
			switch (o.getTipo()) {
			case "D":
				saldo += o.getValor();
				break;
			case "S":
				saldo -= o.getValor();
				break;
			case "T":
				
				if(o.getContaDestino().getId() == contaId ) {
					
				saldo -= o.getValor();
				}
				if(o.getContaOrigem().getId() == contaId) {
					saldo += o.getValor();
				}
					break;
			default:
				break;
			}
		}

		
		
		return saldo;
	}

}
