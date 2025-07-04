package model.dao;

import java.util.List;

import model.entities.Classe;

public interface ClasseDao {

	public void insert(Classe obj);
	public void update(Classe obj);
	public void deleteById(Integer Id);
	public Classe findById(Integer Id);
	public List<Classe> findAll();
	
}
