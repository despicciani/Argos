package model.dao;

import java.util.List;

import model.entities.Personagem;

public interface PersonagemDao {
	
	public void insert(Personagem obj);
	public void update(Personagem obj);
	public void deleteById(Integer Id);
	public Personagem findById(Integer Id);
	public List<Personagem> findAll();
	public List<Personagem> findAllByCampanhaId(Integer campanhaId);
	
}
