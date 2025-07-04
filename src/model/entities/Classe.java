package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Classe {
	private String nome;
	private int dadoDeVida;
	private List <String> proficiencias = new ArrayList<>();
	private Integer Id;
	
	public Classe() {
		
	}
	
	public Classe(String nome, int dadoDeVida) {
		super();
		this.nome = nome;
		this.dadoDeVida = dadoDeVida;
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

	public int getDadoDeVida() {
		return dadoDeVida;
	}

	public void setDadoDeVida(int dadoDeVida) {
		this.dadoDeVida = dadoDeVida;
	}

	public List<String> getProficiencias() {
		return proficiencias;
	}

	public void setProficiencias(List<String> proficiencias) {
		this.proficiencias = proficiencias;
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
		Classe other = (Classe) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Classe [nome=" + nome + ", dadoDeVida= 1d" + dadoDeVida + ", proficiencias=" + proficiencias + "]";
	}
	
}
