/*
 * Created on 29/06/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.bematech.integraopera.controller;

import java.util.LinkedList;

/**
 * @author desenv0032
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FilaProcessos {

	private LinkedList<Processo> lista = new LinkedList<Processo>();
	
	
	public synchronized void add(Processo o) {
		lista.addLast(o);
		notify();
	}

	public synchronized Processo get() throws InterruptedException {
		if (lista.isEmpty())
			wait();
		if (!lista.isEmpty())
			return (Processo)lista.removeFirst();
		else
			return null;
	}

	
	
	

}
