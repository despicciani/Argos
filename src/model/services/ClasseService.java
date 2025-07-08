package model.services;

import java.util.List;

import db.DbException;
import model.dao.ClasseDao;
import model.dao.PersonagemDao;
import model.entities.Classe;
	
public class ClasseService {
	private ClasseDao dao;
	private PersonagemDao personagemDao;
	
	public ClasseService(ClasseDao dao, PersonagemDao personagemDao) {
		this.dao = dao;
		this.personagemDao = personagemDao;
	}

	public List<Classe> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Classe obj) {
        if (obj.getNome() == null || obj.getNome().trim().isEmpty()) { 
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
		
		if (personagemDao.countByRacaId(obj.getId()) > 0) {
            throw new DbException("Não é possível deletar esta raça, pois ela está sendo utilizada por personagens.");
        }
        dao.deleteById(obj.getId());
    }
}
