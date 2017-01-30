  package br.com.sistemaxm.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ReferencedBean;
import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.engine.profile.Fetch;

import br.com.sistemaxm.enums.Cambio;
import br.com.sistemaxm.enums.Combustivel;
import br.com.sistemaxm.entidades.Marca;

@Entity
@Table(name="carro", uniqueConstraints={@UniqueConstraint(columnNames=("placa"))})
public class Carro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String placa;
	private String modelo;
	private String uf;
	private String cor;
	private String renavam;
	//private Date vencLicenciamento;
	private Date vencCredencial;
	private Date vencLicencaEspecial;
	private CalendarioLicenciamento calendarioLicenciamento;
	private String chassi;
	private int anoFabricacao;
	private int anoModelo;
	private int qtdePortas;
	private Long km;
	private Long licencaEspecial;
	private boolean cdMp3Player; 
	private boolean sensorRe;
	private boolean rastreador;
	private boolean bloqueador;
	private boolean ar;
	private boolean bancosCouro;
	private boolean direcao;
	private boolean trava;
	private boolean vidro;
	private boolean alarme;
	private boolean airBagMotorista;
	private boolean airBagPassageiro;
	private boolean freioAbs;
	private boolean rodaLigaLeve;
	private boolean gps;	
	private String observacao;
	private Cambio cambio;
	private Combustivel combustivel;	
	private Marca marca;
	private Proprietario proprietario;
	private List<ContratoLocacao> locacoes;
			
	public Carro() {
		super();
	}
	
	public Carro(Long id) {
		this.id = id;
	}
	
	public Carro(Long id, String modelo, String placa, long km) {
		this.id = id;
		this.modelo = modelo;
		this.placa=placa;
		this.km = km;
	}
	
	public Carro(Long id, String modelo, String placa) {
		this.id = id;
		this.modelo = modelo;
		this.placa = placa;
		}
	
	public Carro(Long id, String modelo, long km) {
		this.id = id;
		this.modelo = modelo;
		this.km = km;
	}
		
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(unique=true,nullable=false)
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	@Column(length=2)
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getRenavam() {
		return renavam;
	}
	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getVencCredencial() {
		return vencCredencial;
	}

	public void setVencCredencial(Date vencCredencial) {
		this.vencCredencial = vencCredencial;
	}

	@Temporal(TemporalType.DATE)
	public Date getVencLicencaEspecial() {
		return vencLicencaEspecial;
	}

	public void setVencLicencaEspecial(Date vencLicencaEspecial) {
		this.vencLicencaEspecial = vencLicencaEspecial;
	}

	public Long getLicencaEspecial() {
		return licencaEspecial;
	}

	public void setLicencaEspecial(Long licencaEspecial) {
		this.licencaEspecial = licencaEspecial;
	}

	public String getChassi() {
		return chassi;
	}
	public void setChassi(String chassi) {
		this.chassi = chassi;
	}
	@Column(length=4)
	public int getAnoFabricacao() {
		return anoFabricacao;
	}
	public void setAnoFabricacao(int anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}
	@Column(length=4)
	public int getAnoModelo() {
		return anoModelo;
	}
	public void setAnoModelo(int anoModelo) {
		this.anoModelo = anoModelo;
	}

	public boolean isCdMp3Player() {
		return cdMp3Player;
	}

	public void setCdMp3Player(boolean cdMp3Player) {
		this.cdMp3Player = cdMp3Player;
	}

	public boolean isSensorRe() {
		return sensorRe;
	}

	public void setSensorRe(boolean sensorRe) {
		this.sensorRe = sensorRe;
	}

	public boolean isRastreador() {
		return rastreador;
	}

	public void setRastreador(boolean rastreador) {
		this.rastreador = rastreador;
	}

	public boolean isBloqueador() {
		return bloqueador;
	}

	public void setBloqueador(boolean bloqueador) {
		this.bloqueador = bloqueador;
	}

	public boolean isAr() {
		return ar;
	}

	public void setAr(boolean ar) {
		this.ar = ar;
	}

	public boolean isBancosCouro() {
		return bancosCouro;
	}

	public void setBancosCouro(boolean bancosCouro) {
		this.bancosCouro = bancosCouro;
	}

	public boolean isDirecao() {
		return direcao;
	}

	public void setDirecao(boolean direcao) {
		this.direcao = direcao;
	}

	public boolean isTrava() {
		return trava;
	}

	public void setTrava(boolean trava) {
		this.trava = trava;
	}
		
	public boolean isVidro() {
		return vidro;
	}

	public void setVidro(boolean vidro) {
		this.vidro = vidro;
	}

	public boolean isAlarme() {
		return alarme;
	}

	public void setAlarme(boolean alarme) {
		this.alarme = alarme;
	}

	public boolean isAirBagMotorista() {
		return airBagMotorista;
	}

	public void setAirBagMotorista(boolean airBagMotorista) {
		this.airBagMotorista = airBagMotorista;
	}

	public boolean isAirBagPassageiro() {
		return airBagPassageiro;
	}

	public void setAirBagPassageiro(boolean airBagPassageiro) {
		this.airBagPassageiro = airBagPassageiro;
	}

	public boolean isFreioAbs() {
		return freioAbs;
	}

	public void setFreioAbs(boolean freioAbs) {
		this.freioAbs = freioAbs;
	}

	public boolean isRodaLigaLeve() {
		return rodaLigaLeve;
	}

	public void setRodaLigaLeve(boolean rodaLigaLeve) {
		this.rodaLigaLeve = rodaLigaLeve;
	}

	public boolean isGps() {
		return gps;
	}

	public void setGps(boolean gps) {
		this.gps = gps;
	}
		
	@Enumerated(EnumType.STRING) 
	public Cambio getCambio() {
		return cambio;
	}
	
	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}
	@Enumerated(EnumType.STRING)
	public Combustivel getCombustivel() {
		return combustivel;
	}
	
	public void setCombustivel(Combustivel combustivel) {
		this.combustivel = combustivel;
	}

	@ManyToOne	
	@JoinColumn(name="cod_marca", referencedColumnName="codigo")
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	@OneToMany(mappedBy="carro", fetch=FetchType.LAZY, cascade=CascadeType.ALL)	
	public List<ContratoLocacao> getLocacoes() {
		return locacoes;
		
	}
	
	@ManyToOne
	@JoinColumn(name="cod_proprietario", referencedColumnName="codigo")
	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}

	public void setLocacoes(List<ContratoLocacao> locacoes) {
		this.locacoes = locacoes;
	}

	public int getQtdePortas() {
		return qtdePortas;
	}
	public void setQtdePortas(int qtdePortas) {
		this.qtdePortas = qtdePortas;
	}
	
	public Long getKm() {
		return km;
	}
	public void setKm(Long km) {
		this.km = km;
	}
			
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	@ManyToOne
	@JoinColumn(name="cod_licenciamento", referencedColumnName="codigo")
	public CalendarioLicenciamento getCalendarioLicenciamento() {
		return calendarioLicenciamento;
	}

	public void setCalendarioLicenciamento(CalendarioLicenciamento calendarioLicenciamento) {
		this.calendarioLicenciamento = calendarioLicenciamento;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Carro)) {
			return false;
		}

		Carro ent = (Carro) object;

		if ((this.id == null && ent.id != null)
				|| (this.id != null && !this.id.equals(ent.id))) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
