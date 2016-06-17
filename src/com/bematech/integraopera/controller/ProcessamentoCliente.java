/*
 * Created on 30/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.bematech.integraopera.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.bematech.integraopera.model.InterfacesOpera;
import com.bematech.integraopera.util.LogUtil;

/**
 * @author desenv0032
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProcessamentoCliente implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	private int timeOut;

	private FilaProcessos filaProcessos;

	private Socket socket;

	private BufferedReader rd;

	private BufferedWriter wr;

	private boolean terminaProcesso;
	private InterfacesOpera intfOpera;
	private boolean mantemConexaoAtiva;
	public InterfacesOpera getIntfOpera() {
		return intfOpera;
	}

	private FilaProcessos filaRetorno;
	
	


	private void createSocket() throws IOException {

		if (socket == null || !socket.isConnected() || socket.isClosed()) {
			/*
			if (socket == null) {
				LogUtil.getInstance().logErro("socket == null");
			} else {
				if (!socket.isConnected()) {
					LogUtil.getInstance().logErro("!socket.isConnected()");
				} else {
					LogUtil.getInstance().logErro("socket.isClosed()");
				}
			} */
			InetAddress addr;
			SocketAddress sockaddr = null;
			try {
				addr = InetAddress.getByName(intfOpera.getEndereco());
				sockaddr = new InetSocketAddress(addr, Integer.parseInt(intfOpera.getPorta()));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			socket = new Socket();
			socket.connect(sockaddr, timeOut * 1000);
			rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		}
	}

	public void closeSocket() {
		//LogUtil.getInstance().logErro("closeSocket()");
		if (socket != null && socket.isConnected()) {
			//LogUtil.getInstance().logErro("socket.close()");
			try {
				rd.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				wr.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			socket = null;
		}
	}

	public ProcessamentoCliente(int timeOut, InterfacesOpera intfOpera, FilaProcessos filaRetorno, boolean mantemConexaoAtiva) {
		this.timeOut = timeOut;
		this.filaProcessos = new FilaProcessos();
		terminaProcesso = false;
		this.intfOpera = intfOpera;
		this.filaRetorno = filaRetorno;
		this.mantemConexaoAtiva = mantemConexaoAtiva;
	}

	public void addProcessoToOpera(Processo processo) {
		synchronized (filaProcessos) {
			filaProcessos.add(processo);
		}
	}
	

	//private void enviarMensagemRecebidaParaPDV(Socket socket, String nomeHotel, String msg) throws IOException {
	private void enviarMensagemRecebidaParaPDV(Processo processo, String msg, String nomeHotel) throws IOException {
		
		synchronized (filaRetorno) {
			processo.setResponseMessage(msg);
			processo.setNomeHotel(nomeHotel);
			filaRetorno.add(processo);
		}
		
		
		//LogUtil.getInstance()
			//	.logMensagem(nomeHotel + " - Enviando resposta para " + socket.getInetAddress().getHostAddress());
		/*
		BufferedWriter wrRetorno = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		wrRetorno.write(msg);
		LogUtil.getInstance().logMensagem(nomeHotel + " - Resposta: " + msg + '\n');

		wrRetorno.flush();
		*/
		//LogUtil.getInstance()
			//	.logMensagem(nomeHotel + " - Resposta para " + socket.getInetAddress().getHostAddress() + " enviada.");
	}

	/*
	private void enviarErroParaPDV(Socket socket, String msg) throws IOException {
		LogUtil.getInstance().logErro(msg);

		BufferedWriter wrRetorno = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		wrRetorno.write(msg);
		wrRetorno.flush();
	} */

	public void run() {
	//	ConfiguracaoUtil configUtil = new ConfiguracaoUtil();
//		Configuracao config = configUtil.carregarConfiguracoes();
		// List<InterfacesOpera> interfaces =
		// configUtil.carregarConfiguracoes().getInterfaces();
		while (!terminaProcesso) {
			try {
				Processo p = null;
				//System.out.println("Esperando novo processo");
				try {
					p = filaProcessos.get();
				} catch (InterruptedException e1) {
				}
				//System.out.println("Novo processo encontrado");
				if (p != null) {
					
					//System.out.println("Novo processo != null");
      					//String codUH = extractUHFromMessage(config, p.getRequestMessage());
      				//	System.out.println("UH da requisição: " + codUH);
      					/*
					if (codUH == null || codUH.trim().equals("")) {
						LogUtil.getInstance()
						.logErro("Não foi possível extrair a UH da mensagem: " + p.getRequestMessage());
						enviarErroParaPDV(p.getSocket(),
								"Não foi possível extrair a UH da mensagem: " + p.getRequestMessage());
						return;
					} */
					
					
					//InterfacesOpera intfOpera = p.getIntfOpera();
					
					/*
					InterfacesOpera intfOpera = configUtil.findInterfaceByCodUH(config, codUH);
					if (intfOpera == null) {
						LogUtil.getInstance()
						.logErro("UH " + codUH + " inválida ou não cadastrada!");
						enviarErroParaPDV(p.getSocket(), "UH " + codUH + " inválida ou não cadastrada!");
						return;
					} */
					
					
					LogUtil.getInstance().logMensagem(intfOpera.getNomeHotel() + " - Processando request de "
							+ p.getSocket().getInetAddress().getHostAddress());
					createSocket();

					
					LogUtil.getInstance()
							.logMensagem(intfOpera.getNomeHotel() + " - Enviando comando p/ Inteface Opera");

					wr.write(p.getRequestMessage());
					wr.flush();
					LogUtil.getInstance()
							.logMensagem(intfOpera.getNomeHotel() + " - Comando p/ Inteface Opera enviado");

					char[] cArray = new char[255];
					StringBuffer sb = new StringBuffer();

					LogUtil.getInstance().logMensagem(intfOpera.getNomeHotel() + " - Lendo retorno da Inteface");
					rd.read(cArray);
					sb.append(cArray);

					LogUtil.getInstance().logMensagem(intfOpera.getNomeHotel() + " - Retorno recebido");
					enviarMensagemRecebidaParaPDV(p, sb.toString(), intfOpera.getNomeHotel());

					//p.getSocket().close();
					if (!this.mantemConexaoAtiva) {
						closeSocket();	
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.getInstance().logErro("O correu o seguinte erro: " + e.getMessage());
				LogUtil.getInstance()
						.logErro("A conexão com a Interface Opera será finalizada e reiniciada em seguida");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
				}
				closeSocket();
			}
		}
		closeSocket();

	}

	public void setTerminaProcesso(boolean terminaProcesso) {
		this.terminaProcesso = terminaProcesso;
	}

}
