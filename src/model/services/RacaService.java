package model.services;

import java.util.List;

import model.dao.RacaDao;
import model.entities.Raca;

public class RacaService {
	private RacaDao dao;
	
	public RacaService(RacaDao dao) {
		this.dao = dao;
	}

	public List<Raca> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Raca obj) {
        if (obj.getNome() == null || obj.getNome().trim().isEmpty()) { //exemplo de rn
            System.out.println("Erro de validação: O nome da raça não pode ser vazio.");
            return; 
        }
        
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
	}
	
	public void remove(Raca obj) {
        dao.deleteById(obj.getId());
    }
	
}
