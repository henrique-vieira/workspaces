package br.com.sistemaxm.enums;

public enum Combustivel {

	GASOLINA("G","GASOLINA"), ALCOOL("A","ALCOOL"), DIESEL("D","DIESEL"), FLEX("F","FLEX");
	
	private final String key;
	private final String value;
	
	private Combustivel(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	public static Combustivel getEn(String value) {
		if(value.equals("GASOLINA")) {
			return GASOLINA;
		}
		if(value.equals("ALCOOL")) {
			return ALCOOL;
		}
		if(value.equals("DIESEL")) {
			return DIESEL;
		}
		if(value.equals("FLEX")) {
			return FLEX;
		}
		return null;
	}
	@Override
	public String toString() {
		return value;
	}
}
