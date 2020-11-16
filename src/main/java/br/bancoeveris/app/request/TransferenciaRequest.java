package br.bancoeveris.app.request;

public class TransferenciaRequest {
	
	private String hashOrigem;
	private String hashDestino;
	private String tipo;
	private Double valor;
	
	public String getHashOrigem() {
		return hashOrigem;
	}
	public void setHashOrigem(String hashOrigem) {
		this.hashOrigem = hashOrigem;
	}
	public String getHashDestino() {
		return hashDestino;
	}
	public void setHashDestino(String hashDestino) {
		this.hashDestino = hashDestino;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}
