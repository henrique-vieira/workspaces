package br.com.sistemaxm.enums;

public enum StatusFuncionario {
	Ativo("A","Ativo"),
	Desligado("D","Desligado");
	
	private String key;
	private String value;
	
	private StatusFuncionario(String key, String value) {
		this.key = key;
		this.value = value;
	}
	public static StatusFuncionario getEn(String value) {
		if (value.equals("Ativo")) {
			return Ativo;
		}
		if (value.equals("Desligado")) {
			return Desligado;
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
	

