package model.services;

import java.util.List;

import db.DbException;
import model.dao.CampanhaDao;
import model.dao.PersonagemDao;
import model.entities.Campanha;
import model.entities.Personagem;

public class CampanhaService {

	    private CampanhaDao campanhaDao;
	    private PersonagemDao personagemDao;

	    public CampanhaService(CampanhaDao campanhaDao, PersonagemDao personagemDao) {
			this.campanhaDao = campanhaDao;
			this.personagemDao = personagemDao;
		}

		public List<Campanha> findAll() {
	        return campanhaDao.findAll();
	    }
	    
	    public void saveOrUpdate(Campanha obj) {
	        if (obj.getNome() == null || obj.getNome().trim().isEmpty()) {
	            System.err.println("Erro de validação: O nome da campanha é obrigatório.");
	            return;
	        }
	        
	        if (obj.getId() == null) {
	            Campanha existente = campanhaDao.findByNome(obj.getNome());
	            if (existente != null) {
	                throw new DbException("Já existe uma campanha com este nome: " + obj.getNome());
	            }
	        }
	        
	        if (obj.getId() == null) {
	            campanhaDao.insert(obj);
	        } else {
	            campanhaDao.update(obj);
	        }
	    }
	    
	    public void remove(Campanha obj) {
	        campanhaDao.deleteById(obj.getId());
	    }

	    public Campanha carregarCampanhaCompleta(Integer campanhaId) {
	        Campanha campanha = campanhaDao.findById(campanhaId);
	        
	        if (campanha != null) {
	            List<Personagem> personagens = personagemDao.findAllByCampanhaId(campanhaId);
	            
	            campanha.setPersonagens(personagens);
	        }
	        
	        return campanha;
	    }
	}

