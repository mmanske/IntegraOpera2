package com.bematech.integraopera;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class Main {

	
	private String VERSAO = "2.1";
	
	private Image createImage() {
		//ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("images/Duke16.gif"));
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/Duke16.gif"));
		return icon.getImage();
	}
	
	private void buildGUI() {
		// Check the SystemTray support
		if (!SystemTray.isSupported()) {
			System.out.println("Tray Icon n„o suportado!");
			return;
		}

		final PopupMenu popup = new PopupMenu();
		final TrayIcon trayIcon = new TrayIcon(createImage());
		final SystemTray tray = SystemTray.getSystemTray();

		trayIcon.setToolTip("Log de Integrador CMNet - Opera " +VERSAO);

		MenuItem logItem = new MenuItem("Log");
		MenuItem exitItem = new MenuItem("Sair");
		MenuItem configItem = new MenuItem("Configurações");

		popup.add(logItem);
		popup.add(configItem);
		popup.addSeparator();
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("Tray Icon não pode ser adicionado!");
			return;
		}

		logItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//IntegracaoVexConsole.mostraConsole();
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				sairListener();
			}
		});
		
		configItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ConfiguradorVex.createConfig(false);
			}
		});

	}

	private void sairListener() {
		System.exit(0);
	}
	
	public Main(String[] args) {
		buildGUI();
		RootController.launch(args);
		
		
		final SystemTray tray = SystemTray.getSystemTray();
		final TrayIcon[] trayIcon = tray.getTrayIcons();
		trayIcon[0].displayMessage("Integrador CMNet - Opera", "Versão "
				+ VERSAO, MessageType.INFO);

		
	}
	
	
	public static void main(String[] args) {
		new Main(args);
	}
	
	
}
