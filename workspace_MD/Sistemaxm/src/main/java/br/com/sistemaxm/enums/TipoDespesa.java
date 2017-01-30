package br.com.sistemaxm.enums;

public enum TipoDespesa {

	Licenciamento("L","Licenciamento"),
	Multa("M","Multa"),
	Outros("O","Outros"),
	Ipva("I","Ipva"),
	Iss("Is","Iss"),
	Depreciacao("D","Depreciacao"),
	licencaViagem("Lv","Licença Viagem"),
	Financiamento("F","Financiamento"),
	TesteFumaca("T","Teste Fumaca"),
	Rg29("R","RG 29"),
	seguroDeTerceiros("S","Seguro de Terceiros"),
	Rastreamento("R","Rastreamento");
	
	private String key;
	private String value;
	
	private TipoDespesa(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public static TipoDespesa getEn(String value) {
		if (value.equals("Licenciamento")) {
			return Licenciamento;
		}
		if (value.equals("Multa")) {
			return Multa;
		}
		if (value.equals("Outros")) {
			
			return Outros;
		}
		if (value.equals("Ipva")) {
			return Ipva;
		}
		if (value.equals("ISS")) {
			return Iss;
		}
		if (value.equals("Depreciacao")) {
			return Depreciacao;
		}
		if (value.equals("Licença Viagem")) {
			return licencaViagem;
		}
		if (value.equals("Financiamento")) {
			return Financiamento;
		}
		if (value.equals("Teste Fumaca")) {
			return TesteFumaca;
		}
		if (value.equals("RG 29")) {
			return Rg29;
		}
		if (value.equals("Seguro de Terceiros")) {
			return seguroDeTerceiros;
		}
		if (value.equals("Rastreamento")) {
			return Rastreamento;
		}
		
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return value;
	}
	
}
