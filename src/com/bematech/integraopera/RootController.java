package com.bematech.integraopera;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.bematech.integraopera.controller.FilaProcessos;
import com.bematech.integraopera.controller.ProcessamentoRetorno;
import com.bematech.integraopera.controller.ProcessamentoServidor;
import com.bematech.integraopera.model.Configuracao;
import com.bematech.integraopera.util.ConfiguracaoUtil;
import com.bematech.integraopera.util.LogUtil;
import com.bematech.integraopera.view.ConfiguracoesController;
import com.bematech.integraopera.view.LogController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RootController extends Application {

	private String VERSAO = "2.1.0";
	private Stage primaryStage;
	private BorderPane rootLayout;
	private MenuItem iniciarItem = new MenuItem("Iniciar Execução");
	private MenuItem pararItem = new MenuItem("Parar Execução");
	private ProcessamentoRetorno procRetorno;
	private ProcessamentoServidor procServidor; 

	
	private Image createImage() {
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(
				"images/Duke16.gif")); 
		if (icon != null) {
			return icon.getImage();
		}
		
		icon = new ImageIcon(getClass().getResource("/images/Duke16.gif"));
		if (icon != null) {
			return icon.getImage();
		}

		icon = new ImageIcon(getClass().getResource(System.getProperty("user.dir")+"/images/Duke16.gif"));
		if (icon != null) {
			return icon.getImage();
		} else {
			System.out.println("Arquivo não encontrado em "+ System.getProperty("user.dir")+"/images/Duke16.gif");
		}
		
		return null;
	}

	private void addAppToTray() {
		java.awt.Toolkit.getDefaultToolkit();
		if (!SystemTray.isSupported()) {
            System.out.println("No system tray support, application exiting.");
            Platform.exit();
        }
		final SystemTray tray = SystemTray.getSystemTray();
		final TrayIcon trayIcon = new TrayIcon(createImage());
		
		MenuItem logItem = new MenuItem("Log");
		MenuItem exitItem = new MenuItem("Sair");
		MenuItem configItem = new MenuItem("Configurações");
		
		
		final PopupMenu popup = new PopupMenu();
		trayIcon.setToolTip("Log de Integrador CMNet - Opera " + VERSAO);

		popup.add(new MenuItem("Versão " + VERSAO));
		popup.addSeparator();
		pararItem.setEnabled(false);
		popup.add(iniciarItem);
		popup.add(pararItem);
		popup.addSeparator();
		popup.add(logItem);
		popup.add(configItem);
		popup.addSeparator();
		popup.add(exitItem);
		
		
        exitItem.addActionListener(event -> {
            
            Platform.exit();
            tray.remove(trayIcon);
            System.exit(0);
        });
		

		trayIcon.setPopupMenu(popup);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("Tray Icon não pode ser adicionado!");
			return;
		}
		configItem.addActionListener(event -> Platform.runLater(this::showConfiguracoes));
		logItem.addActionListener(event -> Platform.runLater(this::showLog));
		iniciarItem.addActionListener(event -> Platform.runLater(this::iniciar));
	}

	private void showStage() {
        if (primaryStage != null) {
        	primaryStage.show();
        	primaryStage.toFront();
        }
    }
	
	public void showLog() {
		this.showStage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootController.class.getResource("view/LogView.fxml"));
			AnchorPane logView = (AnchorPane) loader.load();
			rootLayout.setCenter(logView);
			LogController logController = loader.getController();
			LogUtil.getInstance().setLogUtilInterface(logController);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void iniciar() {
		iniciarItem.setEnabled(false);
		pararItem.setEnabled(true);
		
		ConfiguracaoUtil configUtil = new ConfiguracaoUtil();
		Configuracao config = configUtil.carregarConfiguracoes();
		int portaLocal = Integer.parseInt(config.getPortaEscuta());
		
		FilaProcessos filaRetornos = new FilaProcessos();
		int timeOut = 10;
		
		procServidor = new ProcessamentoServidor(portaLocal, config, timeOut, filaRetornos);
		procRetorno = new ProcessamentoRetorno(filaRetornos);
		
		new Thread(procServidor).start();
		new Thread(procRetorno).start();
		LogUtil.getInstance().logMensagem("Execução iniciada.");
	}
	public void parar() {
		iniciarItem.setEnabled(true);
		pararItem.setEnabled(false);
		procServidor.setTerminaProcesso(true);
		procRetorno.setTerminaProcesso(true);
		LogUtil.getInstance().logMensagem("Execução parada.");
	}
	
	public void showConfiguracoes() {
		this.showStage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootController.class.getResource("view/ConfiguracoesView.fxml"));
			AnchorPane configView = (AnchorPane) loader.load();
			rootLayout.setCenter(configView);

			
			ConfiguracoesController configController = loader.getController();
			configController.setThisStage(primaryStage);
			//configController.loadConfig(); */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootController.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.hide();
			//primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		Platform.setImplicitExit(false);
		javax.swing.SwingUtilities.invokeLater(this::addAppToTray);
		//primaryStage.initStyle(StageStyle.TRANSPARENT);
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Integrador CMNet - Opera");
		
		initRootLayout();

		// showConfiguracoes();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
