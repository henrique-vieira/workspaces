package br.com.sistemaxm.enums;

public enum TipoRota {
	ADM("A","ADM"),
	TURNO("T","TURNO");
	
	private String key;
	private String value;
	
	private TipoRota(String key, String value) {
		this.key = key;
		this.value = value;
	}
	public static TipoRota getEn(String value) {
		if (value.equals("ADM")) {
			return ADM;
		}
		if (value.equals("TURNO")) {
			return TURNO;
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
	
	
}
