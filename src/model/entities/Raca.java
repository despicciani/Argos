package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Raca {
	private String nome;
	private double deslocamentoBase;
	private int bonusForca;
	private int bonusDestreza;
	private int bonusConstituicao;
	private int bonusInteligencia;
	private int bonusSabedoria;
	private int bonusCarisma;
	private List<String> tracosEspeciais = new ArrayList<>();
	private Integer Id;

	public Raca() {
		
	}

	public Raca(String nome, double deslocamentoBase, int bonusForca, int bonusDestreza, int bonusConstituicao,
			int bonusInteligencia, int bonusSabedoria, int bonusCarisma) {
		super();
		this.nome = nome;
		this.deslocamentoBase = deslocamentoBase;
		this.bonusForca = bonusForca;
		this.bonusDestreza = bonusDestreza;
		this.bonusConstituicao = bonusConstituicao;
		this.bonusInteligencia = bonusInteligencia;
		this.bonusSabedoria = bonusSabedoria;
		this.bonusCarisma = bonusCarisma;
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

	public double getDeslocamentoBase() {
		return deslocamentoBase;
	}

	public void setDeslocamentoBase(double deslocamentoBase) {
		this.deslocamentoBase = deslocamentoBase;
	}

	public int getBonusForca() {
		return bonusForca;
	}

	public void setBonusForca(int bonusForca) {
		this.bonusForca = bonusForca;
	}

	public int getBonusDestreza() {
		return bonusDestreza;
	}

	public void setBonusDestreza(int bonusDestreza) {
		this.bonusDestreza = bonusDestreza;
	}

	public int getBonusConstituicao() {
		return bonusConstituicao;
	}

	public void setBonusConstituicao(int bonusConstituicao) {
		this.bonusConstituicao = bonusConstituicao;
	}

	public int getBonusInteligencia() {
		return bonusInteligencia;
	}

	public void setBonusInteligencia(int bonusInteligencia) {
		this.bonusInteligencia = bonusInteligencia;
	}

	public int getBonusSabedoria() {
		return bonusSabedoria;
	}

	public void setBonusSabedoria(int bonusSabedoria) {
		this.bonusSabedoria = bonusSabedoria;
	}

	public int getBonusCarisma() {
		return bonusCarisma;
	}

	public void setBonusCarisma(int bonusCarisma) {
		this.bonusCarisma = bonusCarisma;
	}

	public List<String> getTracosEspeciais() {
		return tracosEspeciais;
	}

	public void setTracosEspeciais(List<String> tracosEspeciais) {
		this.tracosEspeciais = tracosEspeciais;
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
		Raca other = (Raca) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Raca [nome=" + nome + ", deslocamentoBase=" + deslocamentoBase + ", bonusForca=" + bonusForca
				+ ", bonusDestreza=" + bonusDestreza + ", bonusConstituicao=" + bonusConstituicao
				+ ", bonusInteligencia=" + bonusInteligencia + ", bonusSabedoria=" + bonusSabedoria + ", bonusCarisma="
				+ bonusCarisma + ", tracosEspeciais=" + tracosEspeciais + "]";
	}
	
}
