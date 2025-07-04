package model.entities;

import java.util.Objects;

public class Item {
	private String nome;
	private String descricao;
	private String funcao;
	private String propriedade;
	private int ataque;
	private Integer Id;
	
	public Item() {
		
	}

	public Item(String nome, String descricao, String funcao, String propriedade, int ataque) {
		this.nome = nome;
		this.descricao = descricao;
		this.funcao = funcao;
		this.propriedade = propriedade;
		this.ataque = ataque;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
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
		Item other = (Item) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Item [nome=" + nome + ", descricao=" + descricao + ", funcao=" + funcao + ", propriedade=" + propriedade
				+ ", ataque=" + ataque + "]";
	}
	
}
