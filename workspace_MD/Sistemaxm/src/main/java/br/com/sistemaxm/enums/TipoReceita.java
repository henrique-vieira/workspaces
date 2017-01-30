package br.com.sistemaxm.enums;

public enum TipoReceita {
	ServicosExtras("S","Serviços Extras"),
	ViagemAeroporto("V","Viagem Aeroporto"),
	ViagemAlmoço("A","Viagem Almoço"),
	Treinamento("T","Treinamento"),
	ParadaManutencao("P","Parada/Manutenção");
	
	private String key;
	private String value;
	
	
	private TipoReceita(String key, String value) {
		this.key = key;
		this.value = value;
	}
	public static TipoReceita getEn(String value) {
		if (value.equals("Servicos Extras")) {
			return ServicosExtras;
		}
		
		if(value.equals("Viagem Aeroporto")){
			return ViagemAeroporto;
		}
		
		if(value.equals("Viagem Almoço")){
			return ViagemAlmoço;
		}
		
		if(value.equals("Treinamento")){
			return Treinamento;
		}
		
		if(value.equals("Parada/Manutencao")){
			return ParadaManutencao;
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
