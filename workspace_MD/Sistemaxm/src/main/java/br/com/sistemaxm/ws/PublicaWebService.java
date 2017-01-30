package br.com.sistemaxm.ws;

import javax.xml.ws.Endpoint;

public class PublicaWebService {

	public static void main(String[] args) {
		
		//EstoqueWs service = new EstoqueWs();
		ServicoWs service = new ServicoWs();
		String url = "http://localhost:8080/Sistemaxm";
		
		Endpoint.publish(url, service);
	}

}
