package model.dao;

import java.util.List;

import model.entities.Raca;

public interface RacaDao {

	public void insert(Raca obj);
	public void update(Raca obj);
	public void deleteById(Integer Id);
	public Raca findById(Integer Id);
	public List<Raca> findAll();
	
}
