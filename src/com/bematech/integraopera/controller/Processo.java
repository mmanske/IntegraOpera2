/*
 * Created on 30/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.bematech.integraopera.controller;

import java.net.Socket;

/**
 * @author desenv0032
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Processo {
	
	private String requestMessage;
	//private Configuracao config;
	//private InterfacesOpera intfOpera;
	private Socket socket;
	private String responseMessage;
	private String nomeHotel;
	

/*
	private String extractUHFromMessage(String message) {
		int posFinal = Math.min(message.length(), config.getPosicaoFinalApto());
		String codUH = message.substring(config.getPosicaoInicialApto(), posFinal).trim();
		return codUH;
	}
	*/
	

	
	public String getNomeHotel() {
		return nomeHotel;
	}

	public void setNomeHotel(String nomeHotel) {
		this.nomeHotel = nomeHotel;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Processo(Socket socket, String requestMessage) {
		this.socket = socket;
		this.requestMessage = requestMessage;	
	}

	/**
	 * @return
	 */
	public String getRequestMessage() {
		return requestMessage;
	}

	/**
	 * @return
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param string
	 */
	public void setRequestMessage(String string) {
		requestMessage = string;
	}

	/**
	 * @param socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
