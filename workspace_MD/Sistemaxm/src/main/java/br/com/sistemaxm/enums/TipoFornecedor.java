package br.com.sistemaxm.enums;

public enum TipoFornecedor {
	
	SERVICO("S","Servico"), PRODUTO("P","Produto");
	
	private final String key;
	private final String value;
	
	private TipoFornecedor(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	public static TipoFornecedor getEn(String value) {
		if (value.equals("Servico")) {
			return SERVICO;
		}
		else {
			return PRODUTO;
		}
	}
	
	public String toString() {
		return value;
	}

}
