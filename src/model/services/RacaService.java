package model.services;

import java.util.List;

import db.DbException;
import model.dao.PersonagemDao;
import model.dao.RacaDao;
import model.entities.Raca;

public class RacaService {
	private RacaDao dao;
	private PersonagemDao personagemDao;
	
	public RacaService(RacaDao dao, PersonagemDao personagemDao) {
		super();
		this.dao = dao;
		this.personagemDao = personagemDao;
	}

	public List<Raca> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Raca obj) {
        if (obj.getNome() == null || obj.getNome().trim().isEmpty()) { 
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
		if (personagemDao.countByRacaId(obj.getId()) > 0) {
            throw new DbException("Não é possível deletar esta raça, pois ela está sendo utilizada por personagens.");
        }
        dao.deleteById(obj.getId());
    }
}
