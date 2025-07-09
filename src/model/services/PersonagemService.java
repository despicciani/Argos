package model.services;

import java.util.Random;

import db.DbException;
import model.dao.CampanhaDao;
import model.dao.PersonagemDao;
import model.entities.Personagem;

public class PersonagemService {
	
	private PersonagemDao personagemDao;

    public PersonagemService(PersonagemDao personagemDao, CampanhaDao campanhaDao) {
    	this.personagemDao = personagemDao;
    }

    public Personagem findById(Integer id) {
        return personagemDao.findById(id);
    }
    
    public void saveOrUpdate(Personagem obj) {
        if (obj.getNome() == null || obj.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do personagem é obrigatório.");
        }
        
        if (obj.getIdCampanha() == null) {
            throw new IllegalArgumentException("O personagem precisa estar associado a uma campanha.");
        }
        
        if (obj.getForca() > 20 || obj.getForca() < 1) {
            throw new IllegalArgumentException("Valor de Força inválido. Deve ser entre 1 e 20.");
        }
        
        if (obj.getDestreza() > 20 || obj.getDestreza() < 1) {
            throw new IllegalArgumentException("Valor de Destreza inválido. Deve ser entre 1 e 20.");
        }
        
        if (obj.getConstituicao() > 20 || obj.getConstituicao() < 1) {
            throw new IllegalArgumentException("Valor de Constituicao inválido. Deve ser entre 1 e 20.");
        }
        
        if (obj.getInteligencia() > 20 || obj.getInteligencia() < 1) {
            throw new IllegalArgumentException("Valor de Inteligencia inválido. Deve ser entre 1 e 20.");
        }
        
        if (obj.getSabedoria() > 20 || obj.getSabedoria() < 1) {
            throw new IllegalArgumentException("Valor de Sabedoria inválido. Deve ser entre 1 e 20.");
        }
        
        if (obj.getCarisma() > 20 || obj.getCarisma() < 1) {
            throw new IllegalArgumentException("Valor de Carisma inválido. Deve ser entre 1 e 20.");
        }
        
        if (obj.getId() == null) {
            Personagem existente = personagemDao.findByNomeAndCampanhaId(obj.getNome(), obj.getIdCampanha());
            if (existente != null) {
                throw new DbException("Já existe um personagem com o nome '" + obj.getNome() + "' nesta campanha.");
            }
        }
        
        if (obj.getId() == null) {
            personagemDao.insert(obj);
        } else {
            personagemDao.update(obj);
        }
    }
    
    public void receberDano(Integer personagemId, int dano) {
    	 if (dano < 0) {
    	        return; 
    	    }

    	    Personagem p = personagemDao.findById(personagemId);
    	    
    	    if (p != null) {
    	        int vidaAnterior = p.getVidaAtual();

    	        int novaVida = vidaAnterior - dano;
    	        p.setVidaAtual(Math.max(0, novaVida)); 

    	        if (vidaAnterior > 0 && p.getVidaAtual() == 0) {
    	            System.out.println(p.getNome() + " caiu inconsciente e está morrendo!");
    	            p.setSucessosMorte(0);
    	            p.setFalhasMorte(0);
    	        }
    	        
    	        personagemDao.update(p);
    	        
    	        System.out.println(p.getNome() + " recebeu " + dano + " de dano! Vida atual: " + p.getVidaAtual());
    	        
    	        if (p.getVidaAtual() == 0 && vidaAnterior > 0) {
    	             System.out.println("Ele agora precisa fazer testes de morte no início de seus turnos.");
    	        }
    	    }
    }
    
    public void receberCura(Integer personagemId, int cura) {
        if (cura < 0) return; 

        Personagem p = personagemDao.findById(personagemId);
        if (p != null && p.getVidaAtual() > 0) { 
            int novaVida = p.getVidaAtual() + cura;
            p.setVidaAtual(Math.min(novaVida, p.getVidaMax()));
            personagemDao.update(p);
            System.out.println(p.getNome() + " recebeu " + cura + " de cura! Vida atual: " + p.getVidaAtual());
        }
    }
    
    public void adicionarXpESubirDeNivel(Integer personagemId, int xpGanha) {
        Personagem personagem = personagemDao.findById(personagemId);
        if (personagem == null) {
            throw new IllegalArgumentException("Personagem não encontrado com o ID: " + personagemId);
        }

        System.out.println(personagem.getNome() + " (Nível " + personagem.getNivel() + ") ganhou " + xpGanha + " de XP.");
        personagem.setXp(personagem.getXp() + xpGanha);


        int xpNecessario = (personagem.getNivel() * 10) + 10;
        System.out.println("XP necessário para o próximo nível: " + xpNecessario + ". XP atual: " + personagem.getXp());
        
        if (personagem.getXp() >= xpNecessario) {
            subirDeNivel(personagem, xpNecessario);
        }
        
        personagemDao.update(personagem);
        System.out.println("XP final: " + personagem.getXp());
    }

    private void subirDeNivel(Personagem personagem, int xpCustoDoNivel) {
        System.out.println("PARABÉNS! " + personagem.getNome() + " subiu para o nível " + (personagem.getNivel() + 1) + "!");

        personagem.setXp(personagem.getXp() - xpCustoDoNivel);
        
        personagem.setNivel(personagem.getNivel() + 1);

        Random random = new Random();
        int dadoDeVida = personagem.getClasse().getDadoDeVida();
        int vidaGanha = random.nextInt(dadoDeVida) + 1 + personagem.getModificadorConstituicao();
        personagem.setVidaMax(personagem.getVidaMax() + vidaGanha);
        personagem.setVidaAtual(personagem.getVidaMax()); 
    }
    
    public void receberDanoDeSanidade(Integer personagemId, int dano) {
        Personagem p = personagemDao.findById(personagemId);

        if (p != null && p.getSanidade() != null) { 
            int novaSanidade = p.getSanidade() - dano;
            p.setSanidade(Math.max(0, novaSanidade));
            personagemDao.update(p);
            System.out.println(p.getNome() + " sofreu " + dano + " de dano de sanidade! Sanidade atual: " + p.getSanidade());
        } else {
            System.out.println("Este personagem não é afetado por dano de sanidade.");
        }
    }
}