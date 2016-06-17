package com.bematech.integraopera.util;

import java.io.File;
import java.io.IOException;

import com.bematech.integraopera.model.Configuracao;
import com.bematech.integraopera.model.InterfacesOpera;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfiguracaoUtil {

	public static String CONFIG_FILENAME = "configuracoes.json"; 
	
	
	//public void salvarConfiguracoes(List<InterfacesOpera> lista) {
	public void salvarConfiguracoes(Configuracao config) {
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(new File(CONFIG_FILENAME), config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Configuracao carregarConfiguracoes() {
		ObjectMapper om = new ObjectMapper();
		Configuracao results;
		try {
			results = om.readValue(new File(CONFIG_FILENAME),
					   new TypeReference<Configuracao>() { } );
			return results;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public InterfacesOpera findInterfaceByCodUH(Configuracao config, String codUH) {
		for (InterfacesOpera intf : config.getInterfaces()) {
			if (intf.isAptoInterface(codUH)) {
				return intf;
			}
		}
		return null;
	}


	
}
