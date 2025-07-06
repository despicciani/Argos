package model.services;

import java.util.List;

import model.dao.ClasseDao;
import model.entities.Classe;
	
public class ClasseService {
	private ClasseDao dao;
	
	public ClasseService(ClasseDao dao) {
		this.dao = dao;
	}

	public List<Classe> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Classe obj) {
        if (obj.getNome() == null || obj.getNome().trim().isEmpty()) { //exemplo de rn
            System.out.println("Erro de validação: O nome da classe não pode ser vazio.");
            return; 
        }
        
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
	}
	
	public void remove(Classe obj) {
        dao.deleteById(obj.getId());
    }
}
