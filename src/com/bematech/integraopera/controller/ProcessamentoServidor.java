/*
 * Created on 30/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.bematech.integraopera.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.bematech.integraopera.model.Configuracao;
import com.bematech.integraopera.model.InterfacesOpera;
import com.bematech.integraopera.util.ConfiguracaoUtil;
import com.bematech.integraopera.util.LogUtil;

/**
 * @author desenv0032
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProcessamentoServidor implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */

	private int porta;
	private static char EOT = 0x04;
	private boolean terminaProcesso;
	// private String host;
	private Configuracao config;
	private List<ProcessamentoCliente> listaProcCliente;
	private int timeOut;
	private FilaProcessos filaRetorno;

	public boolean isTerminaProcesso() {
		return terminaProcesso;
	}

	public void setTerminaProcesso(boolean terminaProcesso) {
		this.terminaProcesso = terminaProcesso;
	}

	public ProcessamentoServidor(int porta, Configuracao config, int timeOut, FilaProcessos filaRetorno) {

		this.porta = porta;
		this.config = config;
		this.setTerminaProcesso(false);
		this.timeOut = timeOut;
		this.filaRetorno = filaRetorno;
		this.listaProcCliente = new ArrayList<ProcessamentoCliente>();
		;
	}

	private void processa(Socket socket, String str) {

		String codUH = extractUHFromMessage(str);
		if (codUH == null || codUH.trim().equals("")) {
			LogUtil.getInstance().logErro("Não foi possível extrair a UH da mensagem: " + str);
		} 

		ConfiguracaoUtil configUtil = new ConfiguracaoUtil(); 
		InterfacesOpera intfOpera = configUtil.findInterfaceByCodUH(config, codUH);
		if (intfOpera == null) {
			LogUtil.getInstance().logErro("UH " + codUH + " inválida ou não cadastrada!");
			return;
		}
		
		Configuracao config = configUtil.carregarConfiguracoes();
		
		ProcessamentoCliente procCliente = null;
		
		for (ProcessamentoCliente cliente : listaProcCliente) {
			if (cliente.getIntfOpera().isAptoInterface(codUH)) {
				procCliente = cliente;
				break;
			}
		}
		if (procCliente == null) {
			procCliente = new ProcessamentoCliente(timeOut, intfOpera, filaRetorno, config.isMantemConexaoSempreAtiva());
			listaProcCliente.add(procCliente);
			new Thread(procCliente).start();
		}
		procCliente.addProcessoToOpera(new Processo(socket, str));
		// filaProcessos.add(new Processo(socket, str));
	}

	private String extractUHFromMessage(String message) {
		int posFinal = Math.min(message.length(), config.getPosicaoFinalApto());
		String codUH = message.substring(config.getPosicaoInicialApto(), posFinal).trim();
		return codUH;
	}

	public void run() {
		ServerSocket srv = null;
		try {
			srv = new ServerSocket(porta);
		} catch (IOException e1) {
			e1.printStackTrace();
			LogUtil.getInstance().logErro("Erro ao escutar a porta " + porta + ".");
			LogUtil.getInstance().logErro("Texto do erro: " + e1.getMessage());
			LogUtil.getInstance().logErro("Corrija a configuração e reinicie a aplicação.");
			return;
		}
		while (!terminaProcesso) {
			try {
				LogUtil.getInstance().logMensagem("Aguardando nova requisição na porta " + porta);
				Socket socket = srv.accept();

				LogUtil.getInstance().logMensagem("Recebendo request de " + socket.getInetAddress().getHostAddress());
				BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				char[] cArray = new char[227];
				StringBuffer sb = new StringBuffer();
				// int i = rd.read(cArray);
				// LogUtil.getInstance().logMensagem("Bytes lidos: " + i);
				rd.read(cArray);

				for (int index = 0; index < cArray.length; index++) {
					char c = cArray[index];
					if (c == EOT) {
						sb.append(c);
						break;
					}
					sb.append(c);
				}

				// sb.append(cArray);

				LogUtil.getInstance().logMensagem("Texto do request: " + sb.toString().trim());
				processa(socket, sb.toString());
			} catch (IOException e) {
				LogUtil.getInstance().logErro(e.getMessage());
			}
		}
		try {
			srv.close();
		} catch (IOException e) {
			LogUtil.getInstance().logErro("Erro ao fechar a porta do Integrador: " + e.getMessage());
		}

	}

}
