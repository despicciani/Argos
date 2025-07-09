package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Personagem {
	private String nome;
	private Classe classe;
	private Raca raca;
	private int vidaAtual;
	private int vidaMax;
	private int manaAtual;
	private int manaMax;
	private Integer sanidade;
	private int xp = 0;
	private int nivel = 1;
	private double deslocamento;
	private int bonusProfic;
	private int forca;
	private int destreza;
	private int constituicao;
	private int inteligencia;
	private int sabedoria;
	private int carisma;
	private int sucessosMorte;
	private int falhasMorte;
	private List<String> habilidades = new ArrayList<>();
	private Atributo atributoAtaque;
	private List<Item> inventario = new ArrayList<>();
	private List<Pericia> pericias = new ArrayList<>();
	private Integer idCampanha;
	private Integer Id;

	public Personagem() {
		
	}
	
	public Personagem(String nome, Classe classe, Raca raca, int vidaMax, int manaMax, Integer sanidade, int xp, double deslocamento,
			int bonusProfic, int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma,
			Atributo atributoAtaque, Integer idCampanha) {
		this.nome = nome;
		this.classe = classe;
		this.raca = raca;
		this.vidaAtual = vidaMax;
		this.vidaMax = vidaMax;
		this.manaAtual = manaMax;
		this.manaMax = manaMax;
		this.sanidade = sanidade;
		this.deslocamento = deslocamento;
		this.bonusProfic = bonusProfic;
		this.forca = forca;
		this.destreza = destreza;
		this.constituicao = constituicao;
		this.inteligencia = inteligencia;
		this.sabedoria = sabedoria;
		this.carisma = carisma;
		this.sucessosMorte = 0;
		this.falhasMorte = 0;
		this.atributoAtaque = atributoAtaque;
		this.idCampanha = idCampanha;
	}
	
	public List<Pericia> getPericias() {
		return pericias;
	}

	public void setPericias(List<Pericia> pericias) {
		this.pericias = pericias;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	public int getVidaAtual() {
		return vidaAtual;
	}

	public void setVidaAtual(int vidaAtual) {
		this.vidaAtual = vidaAtual;
	}

	public int getVidaMax() {
		return vidaMax;
	}

	public void setVidaMax(int vidaMax) {
		this.vidaMax = vidaMax;
	}

	public int getManaAtual() {
		return manaAtual;
	}

	public void setManaAtual(int manaAtual) {
		this.manaAtual = manaAtual;
	}

	public int getManaMax() {
		return manaMax;
	}

	public void setManaMax(int manaMax) {
		this.manaMax = manaMax;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getClasseArmadura() {
		return 10 + getModificadorDestreza();
	}

	public double getDeslocamento() {
		return deslocamento;
	}

	public void setDeslocamento(int deslocamento) {
		this.deslocamento = deslocamento;
	}

	public int getBonusProfic() {
		return bonusProfic;
	}

	public void setBonusProfic(int bonusProfic) {
		this.bonusProfic = bonusProfic;
	}

	public int getForca() {
		return forca;
	}

	public void setForca(int forca) {
		this.forca = forca;
	}

	public int getDestreza() {
		return destreza;
	}

	public void setDestreza(int destreza) {
		this.destreza = destreza;
	}

	public int getConstituicao() {
		return constituicao;
	}

	public void setConstituicao(int constituicao) {
		this.constituicao = constituicao;
	}

	public int getInteligencia() {
		return inteligencia;
	}

	public void setInteligencia(int inteligencia) {
		this.inteligencia = inteligencia;
	}

	public int getSabedoria() {
		return sabedoria;
	}

	public void setSabedoria(int sabedoria) {
		this.sabedoria = sabedoria;
	}

	public int getCarisma() {
		return carisma;
	}

	public void setCarisma(int carisma) {
		this.carisma = carisma;
	}

	public List<String> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<String> habilidades) {
		this.habilidades = habilidades;
	}

	public Atributo getAtributoAtaque() {
		return atributoAtaque;
	}

	public void setAtributoAtaque(Atributo atributoAtaque) {
		this.atributoAtaque = atributoAtaque;
	}

	public int getCdMagia() {
		return switch (this.atributoAtaque) {
		case FORCA -> getModificadorForca() + 10;
		case DESTREZA -> getModificadorDestreza() + 10;
		case CONSTITUICAO -> getModificadorConstituicao() + 10;
		case INTELIGENCIA -> getModificadorInteligencia() + 10;
		case SABEDORIA -> getModificadorSabedoria() + 10;
		case CARISMA -> getModificadorCarisma() + 10;
		};
	}

	public int getModificadorAtq() {
		return switch (this.atributoAtaque) {
		case FORCA -> getModificadorForca() + getBonusProfic();
		case DESTREZA -> getModificadorDestreza() + getBonusProfic();
		case CONSTITUICAO -> getModificadorConstituicao() + getBonusProfic();
		case INTELIGENCIA -> getModificadorInteligencia() + getBonusProfic();
		case SABEDORIA -> getModificadorSabedoria() + getBonusProfic();
		case CARISMA -> getModificadorCarisma() + getBonusProfic();
		};
	}

	public List<Item> getInventario() {
		return inventario;
	}

	public void setInventario(List<Item> inventario) {
		this.inventario = inventario;
	}

	public int getModificadorForca() {
		return (int) Math.floor((getForca() + raca.getBonusForca() - 10) / 2.0);
	}

	public int getModificadorDestreza() {
		return (int) Math.floor((getDestreza() + raca.getBonusDestreza() - 10) / 2.0);
	}

	public int getModificadorConstituicao() {
		return (int) Math.floor((getConstituicao() + raca.getBonusConstituicao() - 10) / 2.0);
	}

	public int getModificadorInteligencia() {
		return (int) Math.floor((getInteligencia() + raca.getBonusInteligencia() - 10) / 2.0);
	}

	public int getModificadorSabedoria() {
		return (int) Math.floor((getSabedoria() + raca.getBonusSabedoria() - 10) / 2.0);
	}

	public int getModificadorCarisma() {
		return (int) Math.floor((getCarisma() + raca.getBonusCarisma() - 10) / 2.0);
	}
	
	public Integer getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(Integer idCampanha) {
		this.idCampanha = idCampanha;
	}

	public int calcularBonusPericia(Pericia pericia) {
	    int modificador = 0;
	    switch (pericia.getAtributoBase()) {
	        case FORCA:
	            modificador = getModificadorForca();
	            break;
	        case DESTREZA:
	            modificador = getModificadorDestreza();
	            break;
	        case CONSTITUICAO:
	            modificador = getModificadorDestreza();
	            break;
	        case INTELIGENCIA:
	            modificador = getModificadorDestreza();
	            break;
	        case SABEDORIA:
	            modificador = getModificadorDestreza();
	            break;
	        case CARISMA:
	            modificador = getModificadorDestreza();
	            break;
	    }

	    if (pericias.contains(pericia)) {
	        modificador += this.getBonusProfic();
	    }
	    
	    return modificador;
	}
	
	public int getSucessosMorte() {
		return sucessosMorte;
	}

	public void setSucessosMorte(int sucessosMorte) {
		this.sucessosMorte = sucessosMorte;
	}

	public int getFalhasMorte() {
		return falhasMorte;
	}

	public void setFalhasMorte(int falhasMorte) {
		this.falhasMorte = falhasMorte;
	}
	
	public Integer getSanidade() {
		return sanidade;
	}

	public void setSanidade(Integer sanidade) {
		this.sanidade = sanidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personagem other = (Personagem) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Personagem [nome=" + nome + ", classe=" + classe + ", raca=" + raca + ", vidaAtual=" + vidaAtual
				+ ", vidaMax=" + vidaMax + ", manaAtual=" + manaAtual + ", manaMax=" + manaMax + ", xp=" + xp
				+ ", nivel=" + nivel + ", deslocamento=" + deslocamento + ", bonusProfic=" + bonusProfic + ", forca="
				+ forca + ", destreza=" + destreza + ", constituicao=" + constituicao + ", inteligencia=" + inteligencia
				+ ", sabedoria=" + sabedoria + ", carisma=" + carisma + ", habilidades=" + habilidades
				+ ", atributoAtaque=" + atributoAtaque + ", inventario=" + inventario + ", pericias=" + pericias
				+ ", idCampanha=" + idCampanha + ", Id=" + Id + "]";
	}

	
}
