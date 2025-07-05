package model.dao;

import java.util.List;

import model.entities.Campanha;

public interface CampanhaDao {
	
	public void insert(Campanha obj);
	public void update(Campanha obj);
	public void deleteById(Integer Id);
	public Campanha findById(Integer Id);
	public List<Campanha> findAll();
	
}
