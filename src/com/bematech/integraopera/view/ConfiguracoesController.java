package com.bematech.integraopera.view;

import java.io.File;
import java.io.IOException;

import org.controlsfx.dialog.ExceptionDialog;

import com.bematech.integraopera.model.Configuracao;
import com.bematech.integraopera.model.InterfacesOpera;
import com.bematech.integraopera.util.ConfiguracaoUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfiguracoesController {

	@FXML
    private TableView<InterfacesOpera> interfacesTable;
    @FXML
    private TableColumn<InterfacesOpera, String> enderecoColumn;
    @FXML
    private TableColumn<InterfacesOpera, String> portaColumn;
    @FXML
    private TableColumn<InterfacesOpera, String> nomeHotelColumn;
    @FXML
    private TextArea listaApartamentos;
    @FXML
    private TextField portaEscuta;
    @FXML
    private TextField posicaoInicialApto;
    @FXML
    private TextField posicaoFinalApto;
    @FXML
    private CheckBox mantemConexaoComInterfaceAtiva;
    
    private Stage thisStage;
    
    public void setThisStage(Stage thisStage) {
		this.thisStage = thisStage;
	}

	private ObservableList<InterfacesOpera> interfacesData = FXCollections.observableArrayList();
    
    public ConfiguracoesController() {
    	
    	
    }
    
    private void fillTestInterfaceList() {
    	/*
		InterfacesOpera intf = new InterfacesOpera("192.168.0.1", "3000", "Hotel 1");
		intf.addAptos(new String[]{"101", "102", "103", "104"});
		interfacesData.add(intf);
		intf = new InterfacesOpera("192.168.0.2", "3000", "Hotel 2");
		intf.addAptos(new String[]{"201", "202", "203", "204"});
		interfacesData.add(intf);
		intf = new InterfacesOpera("192.168.0.3", "3000", "Hotel 3");
		intf.addAptos(new String[]{"301", "302", "303", "304"});
		interfacesData.add(intf);
		intf = new InterfacesOpera("192.168.0.4", "3000", "Hotel 4");
		intf.addAptos(new String[]{"401", "402", "403", "404"});
		interfacesData.add(intf);
		intf = new InterfacesOpera("192.168.0.5", "3000", "Hotel 5");
		intf.addAptos(new String[]{"501", "502", "503", "504"});
		interfacesData.add(intf); */
	}
    
    
    private void loadConfig() {
    	interfacesData.clear();
    	File f = new File(ConfiguracaoUtil.CONFIG_FILENAME);
    	if (!f.exists()) {
    		fillTestInterfaceList();	
    	} else {
    		Configuracao config = new ConfiguracaoUtil().carregarConfiguracoes();
    		interfacesData.addAll(config.getInterfaces());
    		portaEscuta.setText(config.getPortaEscuta());
    		posicaoInicialApto.setText(String.valueOf(config.getPosicaoInicialApto()));
    		posicaoFinalApto.setText(String.valueOf(config.getPosicaoFinalApto()));
    		mantemConexaoComInterfaceAtiva.setSelected(config.getMantemConexaoComInterfaceSempreAtiva()==1);
    	}
    	interfacesTable.setItems(interfacesData);
    	
    }
    
    @FXML
    private void handleDeleteInterface() {
        int selectedIndex = interfacesTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	interfacesTable.getItems().remove(selectedIndex);	
        } else {
        	ExceptionDialog dialog = new ExceptionDialog(new Exception("Selecione uma interface !"));
        	dialog.show();
        }
        
    }
    
    @FXML
    private void handleSalvarConfig() {
    	if (portaEscuta.getText().trim().equals("")) {
        	ExceptionDialog dialog = new ExceptionDialog(new Exception("Indique uma porta de escuta !"));
        	dialog.show();
    		portaEscuta.requestFocus();
    		return;
    	}
    	if (posicaoInicialApto.getText().trim().equals("")) {
        	ExceptionDialog dialog = new ExceptionDialog(new Exception("Indique a posição inicial do número do Apto !"));
        	dialog.show();
        	posicaoInicialApto.requestFocus();
    		return;
    	}
    	if (posicaoFinalApto.getText().trim().equals("")) {
        	ExceptionDialog dialog = new ExceptionDialog(new Exception("Indique a posição final do número do Apto !"));
        	dialog.show();
        	posicaoFinalApto.requestFocus();
    		return;
    	}
    	
    	Configuracao config = new Configuracao(portaEscuta.getText(), 
    			Integer.parseInt(posicaoInicialApto.getText().trim()),
    			Integer.parseInt(posicaoFinalApto.getText().trim()),
    			mantemConexaoComInterfaceAtiva.isSelected()?1:0);
    	InterfacesOpera intf = interfacesTable.getSelectionModel().getSelectedItem();
    	if (intf != null) {
    		intf.atualizaApartamentos(listaApartamentos.getText());	
    	}
    	config.getInterfaces().addAll(interfacesData);
    	new ConfiguracaoUtil().salvarConfiguracoes(config);
    }

    @FXML
    private void handleReloadConfig() {
    	loadConfig();
    }

    
    @FXML
    private void handleNewInterface() {
        InterfacesOpera intf = new InterfacesOpera();
        boolean okClicked = showInterfaceDialog(intf);
        if (okClicked) {
        	interfacesData.add(intf);
        }
    }
    
    @FXML
    private void handleEditInterface() {
    	InterfacesOpera intf = interfacesTable.getSelectionModel().getSelectedItem();
    	if (intf != null) {
    		showInterfaceDialog(intf);
    	} else {
        	ExceptionDialog dialog = new ExceptionDialog(new Exception("Selecione uma interface !"));
        	dialog.show();
    	}
    }
	
    @FXML
    private void initialize() {
    	enderecoColumn.setCellValueFactory(cellData -> cellData.getValue().enderecoProperty());
    	portaColumn.setCellValueFactory(cellData -> cellData.getValue().portaProperty());
    	nomeHotelColumn.setCellValueFactory(cellData -> cellData.getValue().nomeHotelProperty());
    	interfacesTable.getSelectionModel().selectedItemProperty().addListener(
    			(observable, oldValue, newValue) -> showApartamentos(oldValue, newValue));
    	loadConfig();
    }
    
    private void showApartamentos(InterfacesOpera oldInterface, InterfacesOpera interfaceOpera) {
    	if (oldInterface != null) {
    		oldInterface.atualizaApartamentos(listaApartamentos.getText());	
    	}
    	
    	listaApartamentos.setText(interfaceOpera.carregaApartamentos());
    }
    
    private boolean showInterfaceDialog(InterfacesOpera interfaceOpera) {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("NovaInterfaceView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Interface Opera");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            
            dialogStage.initOwner(thisStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Define a pessoa no controller.
            NovaInterfaceController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setInterface(interfaceOpera);

            // Mostra a janela e espera até o usuário fechar.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    
    }
}
