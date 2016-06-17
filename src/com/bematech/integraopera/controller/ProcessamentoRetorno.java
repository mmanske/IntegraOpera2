package com.bematech.integraopera.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.bematech.integraopera.util.LogUtil;

public class ProcessamentoRetorno implements Runnable {

	private FilaProcessos filaRetorno;
	private boolean terminaProcesso = false;

	public ProcessamentoRetorno(FilaProcessos filaRetorno) {
		this.filaRetorno = filaRetorno;
	}

	public boolean isTerminaProcesso() {
		return terminaProcesso;
	}

	public void setTerminaProcesso(boolean terminaProcesso) {
		this.terminaProcesso = terminaProcesso;
	}

	@Override
	public void run() {

		while (!terminaProcesso) {
			Processo p = null;
			//synchronized (filaRetorno) {
				try {
					p = filaRetorno.get();
				} catch (InterruptedException e1) {
				}

				LogUtil.getInstance().logMensagem(
						p.getNomeHotel() + " - Enviando resposta para " + p.getSocket().getInetAddress().getHostAddress());

				BufferedWriter wrRetorno;
				try {
					wrRetorno = new BufferedWriter(new OutputStreamWriter(p.getSocket().getOutputStream()));
					wrRetorno.write(p.getResponseMessage());
					LogUtil.getInstance().logMensagem(p.getNomeHotel() + " - Resposta: " + p.getResponseMessage() + '\n');

					wrRetorno.flush();

					p.getSocket().close();
					
					LogUtil.getInstance().logMensagem(p.getNomeHotel() + " - Resposta para "
							+ p.getSocket().getInetAddress().getHostAddress() + " enviada.");
				} catch (IOException e) {
					LogUtil.getInstance().logErro(e.getMessage());
				}
				
			//}

		}

	}

}
