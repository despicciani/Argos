package model.services;

import java.util.List;

import model.dao.PericiaDao;
import model.entities.Pericia;

public class PericiaService {
	private PericiaDao dao;
	
	public PericiaService(PericiaDao dao) {
		this.dao = dao;
	}

	public List<Pericia> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Pericia obj) {
        if (obj.getNome() == null || obj.getNome().trim().isEmpty()) { //exemplo de rn
            System.out.println("Erro de validação: O nome da pericia não pode ser vazio.");
            return; 
        }
        
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
	}
	
	public void remove(Pericia obj) {
        dao.deleteById(obj.getId());
    }
}
