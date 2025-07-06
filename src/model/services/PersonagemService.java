package model.services;

import java.util.Random;

import model.dao.CampanhaDao;
import model.dao.PersonagemDao;
import model.entities.Campanha;
import model.entities.Personagem;

public class PersonagemService {
	
	private PersonagemDao personagemDao;
    private CampanhaDao campanhaDao;

    public PersonagemService(PersonagemDao personagemDao, CampanhaDao campanhaDao) {
    	this.personagemDao = personagemDao;
    	this.campanhaDao = campanhaDao;
    }

    public Personagem findById(Integer id) {
        return personagemDao.findById(id);
    }
    
    public void saveOrUpdate(Personagem obj) {
        if (obj.getNome() == null || obj.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do personagem é obrigatório.");
        }
        
        if (obj.getId() == null) {
            personagemDao.insert(obj);
        } else {
            personagemDao.update(obj);
        }
    }
    
    public void adicionarXpESubirDeNivel(Integer personagemId, int xpGanha) {
        Personagem personagem = personagemDao.findById(personagemId);
        if (personagem == null) {
            throw new IllegalArgumentException("Personagem não encontrado com o ID: " + personagemId);
        }
        
        Campanha campanha = campanhaDao.findById(personagem.getIdCampanha());
        if (campanha == null) {
            throw new IllegalStateException("Personagem está associado a uma campanha inválida.");
        }

        personagem.setXp(personagem.getXp() + xpGanha);

        if (personagem.getXp() >= campanha.getXpProxNivel()) {
            subirDeNivel(personagem, campanha);
        }

        personagemDao.update(personagem);
        System.out.println(personagem.getNome() + " recebeu " + xpGanha + " de XP. XP atual: " + personagem.getXp());
    }

    private void subirDeNivel(Personagem personagem, Campanha campanha) {
        System.out.println("PARABÉNS! " + personagem.getNome() + " subiu para o nível " + (personagem.getNivel() + 1) + "!");

        personagem.setXp(personagem.getXp() - campanha.getXpProxNivel());
        
        personagem.setNivel(personagem.getNivel() + 1);

        Random random = new Random();
        int dadoDeVida = personagem.getClasse().getDadoDeVida();
        int vidaGanha = random.nextInt(dadoDeVida) + 1 + personagem.getModificadorConstituicao();
        personagem.setVidaMax(personagem.getVidaMax() + vidaGanha);
        personagem.setVidaAtual(personagem.getVidaMax()); 
    }
}