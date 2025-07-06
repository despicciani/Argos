package model.services;

import java.util.List;

import model.dao.ItemDao;
import model.entities.Item;

public class ItemService {
	private ItemDao dao;
	
	public ItemService(ItemDao dao) {
		this.dao = dao;
	}

	public List<Item> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Item obj) {
        if (obj.getNome() == null || obj.getNome().trim().isEmpty()) { //exemplo de rn
            System.out.println("Erro de validação: O nome do item não pode ser vazio.");
            return; 
        }
        
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
	}
	
	public void remove(Item obj) {
        dao.deleteById(obj.getId());
    }
}
