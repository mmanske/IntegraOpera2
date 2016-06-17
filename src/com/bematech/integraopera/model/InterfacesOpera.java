package com.bematech.integraopera.model;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class InterfacesOpera {

	private StringProperty endereco;
	private StringProperty porta;
	private StringProperty nomeHotel;
	
	private List<String> apartamentos;
	
	public List<String> getApartamentos() {
		return apartamentos;
	}

	public void setApartamentos(List<String> apartamentos) {
		this.apartamentos = apartamentos;
	}

	public String getEndereco() {
		return endereco.get();
	}

	public String getNomeHotel() {
		return nomeHotel.get();
	}
	public void setEndereco(String endereco) {
		this.endereco.set(endereco);
	}

	public String getPorta() {
		return porta.get();
	}

	public void setPorta(String porta) {
		this.porta.set(porta);
	}
	public void setNomeHotel(String nomeHotel) {
		this.nomeHotel.set(nomeHotel);
	}

	public StringProperty enderecoProperty() {
		return endereco;
	}
	
	public StringProperty portaProperty() {
		return porta;
	}
	
	public StringProperty nomeHotelProperty() {
		return nomeHotel;
	}
	
	public InterfacesOpera() {
		this(null,null, null);
	}
	
	public InterfacesOpera(String endereco, String porta, String nomeHotel) {
		this.endereco = new SimpleStringProperty(endereco);
		this.porta = new SimpleStringProperty(porta);
		this.nomeHotel = new SimpleStringProperty(nomeHotel);
		this.apartamentos = new ArrayList<String>();
	}
	
	public void atualizaApartamentos(String aptos) {
		this.apartamentos.clear();
		if (aptos != null) {
			String[] listaAptos = aptos.split("\n");
			for (String apto : listaAptos) {
				this.apartamentos.add(apto);
			}
		}
	}
	public String carregaApartamentos() {
		StringBuilder sb = new StringBuilder();
		for (String apto : this.apartamentos) {
			sb.append(apto);
			sb.append("\n");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length()-1);	
		}
		
		return sb.toString();
	}
	public void addApto(String apto) {
		this.apartamentos.add(apto);
	}
	public void addAptos(String[] aptos) {
		for (String apto : aptos) {
			this.apartamentos.add(apto);	
		}
		
	}
	
	public boolean isAptoInterface(String codUH) {
		return this.apartamentos.indexOf(codUH) >= 0;
	}
	
}
