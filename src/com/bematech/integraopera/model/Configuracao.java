package com.bematech.integraopera.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Configuracao {

	private String portaEscuta;
	private int posicaoInicialApto;
	private int mantemConexaoComInterfaceSempreAtiva;
	
	public int getPosicaoInicialApto() {
		return posicaoInicialApto;
	}

	public void setPosicaoInicialApto(int posicaoInicialApto) {
		this.posicaoInicialApto = posicaoInicialApto;
	}

	public int getPosicaoFinalApto() {
		return posicaoFinalApto;
	}

	public void setPosicaoFinalApto(int posicaoFinalApto) {
		this.posicaoFinalApto = posicaoFinalApto;
	}

	private int posicaoFinalApto;
	private List<InterfacesOpera> interfaces = new ArrayList<InterfacesOpera>();
	
	
	public String getPortaEscuta() {
		return portaEscuta;
	}

	public void setPortaEscuta(String portaEscuta) {
		this.portaEscuta = portaEscuta;
	}

	public List<InterfacesOpera> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<InterfacesOpera> interfaces) {
		this.interfaces = interfaces;
	}

	public Configuracao() {
		this(null,0,0,0);
	}
	
	public Configuracao(String portaEscuta, int posicaoInicialApto, int posicaoFinalApto, 
			int mantemConexaoComInterfaceSempreAtiva) {
		this.portaEscuta = portaEscuta;
		this.posicaoFinalApto = posicaoFinalApto;
		this.posicaoInicialApto = posicaoInicialApto;
		this.mantemConexaoComInterfaceSempreAtiva = mantemConexaoComInterfaceSempreAtiva;
	}

	public int getMantemConexaoComInterfaceSempreAtiva() {
		return mantemConexaoComInterfaceSempreAtiva;
	}

	public void setMantemConexaoComInterfaceSempreAtiva(int mantemConexaoComInterfaceSempreAtiva) {
		this.mantemConexaoComInterfaceSempreAtiva = mantemConexaoComInterfaceSempreAtiva;
	}
	
	@JsonIgnore
	public boolean isMantemConexaoSempreAtiva() {
		return this.mantemConexaoComInterfaceSempreAtiva == 1;
	}
}
