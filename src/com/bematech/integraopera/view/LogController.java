package com.bematech.integraopera.view;

import com.bematech.integraopera.util.LogUtilInterface;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class LogController implements LogUtilInterface {

	@FXML
	private TextArea errosTextArea;
	@FXML
	private TextArea  mensagensTextArea;
	
	public void logarMensagem(String msg) {
		mensagensTextArea.appendText("\n" + msg);
	}

	public void logarErro(String msg) {
		errosTextArea.appendText("\n" + msg);
	}
	
	@FXML
	private void handleLimparMensagem() {
		mensagensTextArea.clear();
	}
	@FXML
	private void handleLimparErro() {
		errosTextArea.clear();
	}
	

}
