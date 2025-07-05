package model.entities;

import java.util.List;
import java.util.Objects;

public class Campanha {
	private String nome;
	private int xpProxNivel;
	private List<Personagem> personagens;
	private Integer Id;
	
	public Campanha() {
		
	}

	public Campanha(String nome, int xpProxNivel, List<Personagem> personagens) {
		this.nome = nome;
		this.xpProxNivel = xpProxNivel;
		this.personagens = personagens;
	}
	
	public Campanha(String nome, int xpProxNivel) {
		this.nome = nome;
		this.xpProxNivel = xpProxNivel;
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

	public int getXpProxNivel() {
		return xpProxNivel;
	}

	public void setXpProxNivel(int xpProxNivel) {
		this.xpProxNivel = xpProxNivel;
	}

	public List<Personagem> getPersonagens() {
		return personagens;
	}

	public void setPersonagens(List<Personagem> personagens) {
		this.personagens = personagens;
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
		Campanha other = (Campanha) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Campanha [nome=" + nome + ", xpProxNivel=" + xpProxNivel + ", personagens=" + personagens + ", Id=" + Id
				+ "]";
	}

	
	
}
