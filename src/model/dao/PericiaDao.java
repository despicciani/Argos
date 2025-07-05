package model.dao;

import java.util.List;

import model.entities.Pericia;


public interface PericiaDao {
	
	public void insert(Pericia obj);
	public void update(Pericia obj);
	public void deleteById(Integer Id);
	public Pericia findById(Integer Id);
	public List<Pericia> findAll();
	
}
