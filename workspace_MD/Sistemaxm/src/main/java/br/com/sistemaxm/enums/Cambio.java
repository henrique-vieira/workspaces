package br.com.sistemaxm.enums;

public enum Cambio {
	
	AUTOMATICO("A", "AUTOMATICO"),

	MANUAL("M", "MANUAL");

	private String key;

	private String value;

	private Cambio(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static Cambio getEn(String value) {
		if (value.equals("AUTOMATICO")) {
			return AUTOMATICO;
		}

		if (value.equals("MANUAL")) {
			return MANUAL;
		}

		return null;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
	
}
