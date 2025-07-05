package model.entities;

import java.util.Objects;

public class Pericia {
	private String nome;
	private Atributo atributoBase;
	private Integer Id;
	private Integer idCampanha;
	
	public Pericia() {
		
	}

	public Pericia(String nome, Atributo atributoBase) {
		this.nome = nome;
		this.atributoBase = atributoBase;
	}
	
	public Integer getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(Integer idCampanha) {
		this.idCampanha = idCampanha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Atributo getAtributoBase() {
		return atributoBase;
	}

	public void setAtributoBase(Atributo atributoBase) {
		this.atributoBase = atributoBase;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
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
		Pericia other = (Pericia) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Pericia [nome=" + nome + ", atributoBase=" + atributoBase + ", Id=" + Id + "]";
	}
	
	
}
