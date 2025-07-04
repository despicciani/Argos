package model.dao;

import java.util.List;

import model.entities.Item;

public interface ItemDao {

	public void insert(Item obj);
	public void update(Item obj);
	public void deleteById(Integer Id);
	public Item findById(Integer Id);
	public List<Item> findAll();
	
}
