package br.com.sistemaxm.enums;

public enum AcessorioEnum {
	
	AR(1, "AR"),
	MP3(2, "MP3");
	
	private Integer key;
	private String value;
	
	private AcessorioEnum(Integer key, String value) {
		this.key=key;
		this.value=value;
	}

}
