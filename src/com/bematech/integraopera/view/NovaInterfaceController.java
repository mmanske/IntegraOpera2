package com.bematech.integraopera.view;

import org.controlsfx.dialog.ExceptionDialog;

import com.bematech.integraopera.model.InterfacesOpera;
import com.bematech.integraopera.util.IPAddressValidator;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NovaInterfaceController {

	@FXML
	private TextField enderecoField;
	@FXML
	private TextField portaField;
	@FXML
	private TextField nomeHotelField;
	private Stage dialogStage;
	private InterfacesOpera interfaceOpera;
	private boolean okClicked = false;

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setInterface(InterfacesOpera interfaceOpera) {
		this.interfaceOpera = interfaceOpera;
		enderecoField.setText(interfaceOpera.getEndereco());
		portaField.setText(interfaceOpera.getPorta());
		nomeHotelField.setText(interfaceOpera.getNomeHotel());
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			interfaceOpera.setEndereco(enderecoField.getText());
			interfaceOpera.setPorta(portaField.getText());
			interfaceOpera.setNomeHotel(nomeHotelField.getText());
			okClicked = true;
			dialogStage.close();
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";
		if (nomeHotelField.getText() == null || nomeHotelField.getText().length() == 0) {
			errorMessage += "Nome do Hotel não preenchido!\n";
		}
		if (enderecoField.getText() == null || enderecoField.getText().length() == 0 ||
				!new IPAddressValidator().validate(enderecoField.getText())) {
			errorMessage += "Endereço inválido ou não preenchido!\n";
		}

		if (portaField.getText() == null || portaField.getText().length() == 0 ||
				!new IPAddressValidator().validatePort(portaField.getText())) {
			errorMessage += "Porta inválida ou não preenchida!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			ExceptionDialog dialog = new ExceptionDialog(new Exception(errorMessage));
			dialog.show();
			return false;
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
