package br.com.sistemaxm.enums;

public enum Sexo {

	Masculino("M","Masculino"),
	Feminino("F","Feminino");
	
	private String key;
	private String value;
	
	private Sexo(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public static Sexo getEn(String value) {
		if (value.equals("Masculino")) {
			return Masculino;
		}
		if (value.equals("Feminino")) {
			return Feminino;
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
