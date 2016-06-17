package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bematech.integraopera.model.InterfacesOpera;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JSONTest {

	private ObservableList<InterfacesOpera> interfacesData = FXCollections.observableArrayList();
	
	private void fillInterfaceList() {
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
		interfacesData.add(intf);
	}

	public JSONTest() {
		fillInterfaceList();
		ObjectMapper om = new ObjectMapper();
		try {
			
			//String s = om.writeValueAsString(interfacesData);
		//	System.out.println(s);
			File f = new File("teste.json");
			
			if (f.exists()) {
				
				List<InterfacesOpera> results = om.readValue(f,
						   new TypeReference<List<InterfacesOpera>>() { } );
				
				System.out.println(results);
				/*
				List<InterfacesOpera> lista = om.readValue(f, new ArrayList<InterfacesOpera>().getClass());
				for (InterfacesOpera intf : lista) {
					interfacesData.add(intf);
				}
				*/
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new JSONTest();

	}

}
