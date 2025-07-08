package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ClasseDao;
import model.dao.ItemDao;
import model.dao.PericiaDao;
import model.dao.PersonagemDao;
import model.dao.RacaDao;
import model.entities.Atributo;
import model.entities.Classe;
import model.entities.Item;
import model.entities.Pericia;
import model.entities.Personagem;
import model.entities.Raca;

public class PersonagemDaoJDBC implements PersonagemDao{

	private Connection conn;
	private RacaDao racaDao;
	private ClasseDao classeDao;
	private ItemDao itemDao;
	private PericiaDao periciaDao;

	
	public PersonagemDaoJDBC(Connection conn, RacaDao racaDao, ClasseDao classeDao, ItemDao itemDao, PericiaDao periciaDao) {
		this.conn = conn;
		this.racaDao = racaDao;
        this.classeDao = classeDao;
        this.itemDao = itemDao;
        this.periciaDao = periciaDao;
	}
	
	@Override
	public void insert(Personagem obj) {
		PreparedStatement stPersonagem = null;
        PreparedStatement stInventario = null;
        PreparedStatement stPericias = null;
		try {
			conn.setAutoCommit(false);
			
			stPersonagem = conn.prepareStatement(
					"INSERT INTO Personagens (nome, id_classe_fk, id_raca_fk, vidaAtual, vidaMax, manaAtual, manaMax, xp, nivel, deslocamento, forca, destreza, constituicao, inteligencia, sabedoria, carisma, habilidades, atributoAtaque, id_campanha_fk) " +
				    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",  Statement.RETURN_GENERATED_KEYS); 
				
			
				
			stPersonagem.setString(1, obj.getNome());
			stPersonagem.setInt(2, obj.getClasse().getId());
			stPersonagem.setInt(3, obj.getRaca().getId());
			stPersonagem.setInt(4, obj.getVidaAtual());
			stPersonagem.setInt(5, obj.getVidaMax());
			stPersonagem.setInt(6, obj.getManaAtual());
			stPersonagem.setInt(7, obj.getManaMax());
			stPersonagem.setInt(8, obj.getXp());
			stPersonagem.setInt(9, obj.getNivel());
			stPersonagem.setDouble(10, obj.getDeslocamento());
			stPersonagem.setInt(11, obj.getForca());
			stPersonagem.setInt(12, obj.getDestreza());
			stPersonagem.setInt(13, obj.getConstituicao());
			stPersonagem.setInt(14, obj.getInteligencia());
			stPersonagem.setInt(15, obj.getSabedoria());
			stPersonagem.setInt(16, obj.getCarisma());
			stPersonagem.setString(18, obj.getAtributoAtaque().name());
			stPersonagem.setInt(19, obj.getIdCampanha());
			
			String habilidades = String.join(";", obj.getHabilidades());
	        stPersonagem.setString(17, habilidades);
	        
	        int rowsAffected = stPersonagem.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            ResultSet rs = stPersonagem.getGeneratedKeys();
	            if (rs.next()) {
	                int id = rs.getInt(1); 
	                obj.setId(id); 
	            }
	            DB.closeResultSet(rs);
	        }
	        else {
	        	throw new DbException("Falha ao criar o personagem");
	        }
	        
	        stInventario = conn.prepareStatement(
					"INSERT INTO INVENTARIO (id_personagem_fk, id_item_fk, quantidade) "
					+ "VALUES (?, ?, ?)");
	        
	        for (Item item : obj.getInventario()) {
	            stInventario.setInt(1, obj.getId()); 
	            stInventario.setInt(2, item.getId()); 
	            stInventario.setInt(3, 1); 
	            
	            stInventario.executeUpdate(); 
	        }
	        
	        stPericias = conn.prepareStatement(
	        	    "INSERT INTO PERSONAGEM_PERICIA (id_personagem_fk, id_pericia_fk) VALUES (?, ?)");

	        	for (Pericia pericia : obj.getPericias()) {
	        	    stPericias.setInt(1, obj.getId());
	        	    stPericias.setInt(2, pericia.getId());
	        	    stPericias.executeUpdate();
	        	}
	        
	        conn.commit();
	        
		}
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException(e.getMessage());
			} catch (SQLException e1) {
				throw new DbException(e.getMessage());
			}
		} 
				
		finally {
			DB.closeStatement(stPersonagem);
			DB.closeStatement(stInventario);
			DB.closeStatement(stPericias);
			try {
	            conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
	}

	@Override
	public void update(Personagem obj) {
		PreparedStatement stPersonagem = null;
	    PreparedStatement stInventario = null;
	    PreparedStatement stPericias = null;
	    try {
	        conn.setAutoCommit(false);
	        
	        stPersonagem = conn.prepareStatement(
	            "UPDATE PERSONAGENS " +
	            "SET nome = ?, id_classe_fk = ?, id_raca_fk = ?, vidaAtual = ?, vidaMax = ?, manaAtual = ?, manaMax = ?, xp = ?, nivel = ?, deslocamento = ?, forca = ?, destreza = ?, constituicao = ?, inteligencia = ?, sabedoria = ?, carisma = ?, habilidades = ?, atributoAtaque = ?, id_campanha_fk = ?" +
	            "WHERE id_personagem = ?");

	        stPersonagem.setString(1, obj.getNome());
	        stPersonagem.setInt(2, obj.getClasse().getId());
	        stPersonagem.setInt(3, obj.getRaca().getId());
	        stPersonagem.setInt(4, obj.getVidaAtual());
			stPersonagem.setInt(5, obj.getVidaMax());
			stPersonagem.setInt(6, obj.getManaAtual());
			stPersonagem.setInt(7, obj.getManaMax());
			stPersonagem.setInt(8, obj.getXp());
			stPersonagem.setInt(9, obj.getNivel());
			stPersonagem.setDouble(10, obj.getDeslocamento());
			stPersonagem.setInt(11, obj.getForca());
			stPersonagem.setInt(12, obj.getDestreza());
			stPersonagem.setInt(13, obj.getConstituicao());
			stPersonagem.setInt(14, obj.getInteligencia());
			stPersonagem.setInt(15, obj.getSabedoria());
			stPersonagem.setInt(16, obj.getCarisma());
			stPersonagem.setString(18, obj.getAtributoAtaque().name());
			stPersonagem.setInt(19, obj.getIdCampanha());
			
			String habilidades = String.join(";", obj.getHabilidades());
	        stPersonagem.setString(17, habilidades);
	        
	        
	        stPersonagem.setInt(20, obj.getId()); 
	        stPersonagem.executeUpdate();
	        DB.closeStatement(stPersonagem);

	        stInventario = conn.prepareStatement("DELETE FROM INVENTARIO WHERE id_personagem_fk = ?");
	        stInventario.setInt(1, obj.getId());
	        stInventario.executeUpdate();
	        DB.closeStatement(stInventario);

	        stInventario = conn.prepareStatement(
	            "INSERT INTO INVENTARIO (id_personagem_fk, id_item_fk, quantidade) VALUES (?, ?, ?)");
	        
	        for (Item item : obj.getInventario()) {
	            stInventario.setInt(1, obj.getId());
	            stInventario.setInt(2, item.getId());
	            stInventario.setInt(3, 1);
	            stInventario.executeUpdate();
	        }
	        
	        stPericias = conn.prepareStatement("DELETE FROM PERSONAGEM_PERICIA WHERE id_personagem_fk = ?");
	        stPericias.setInt(1, obj.getId());
	        stPericias.executeUpdate();
	        DB.closeStatement(stPericias); 

	        stPericias = conn.prepareStatement(
	            "INSERT INTO PERSONAGEM_PERICIA (id_personagem_fk, id_pericia_fk) VALUES (?, ?)");

	        for (Pericia pericia : obj.getPericias()) {
	            stPericias.setInt(1, obj.getId());
	            stPericias.setInt(2, pericia.getId());
	            stPericias.executeUpdate();
	        }

	        conn.commit();

	    } catch (SQLException e) {
	        try {
	            conn.rollback();
	            throw new DbException(e.getMessage());
	        } catch (SQLException e1) {
	            throw new DbException(e1.getMessage());
	        }
	    } finally {
	        DB.closeStatement(stPersonagem);
	        DB.closeStatement(stInventario);
	        DB.closeStatement(stPericias);
	        try {
	            conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.getMessage();
	        }
	    }
		
	}

	@Override
	public void deleteById(Integer Id) {
		PreparedStatement st = null;
	    try {
	        conn.setAutoCommit(false);
	        
	        st = conn.prepareStatement("DELETE FROM PERSONAGEM_PERICIA WHERE id_personagem_fk = ?");
	        st.setInt(1, Id);
	        st.executeUpdate();
	        DB.closeStatement(st);

	        st = conn.prepareStatement("DELETE FROM INVENTARIO WHERE id_personagem_fk = ?");
	        st.setInt(1, Id);
	        st.executeUpdate();
	        DB.closeStatement(st);

	        st = conn.prepareStatement("DELETE FROM PERSONAGENS WHERE id_personagem = ?");
	        st.setInt(1, Id);
	        st.executeUpdate();
	        
	        conn.commit();
	    	} catch (SQLException e) {
	        try {
	            conn.rollback();
	            throw new DbException(e.getMessage());
	        } catch (SQLException e1) {
	            throw new DbException(e1.getMessage());
	        }
	    } finally {
	        DB.closeStatement(st);
	        try {
	            conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.getMessage();
	        }
	    }
	}

	@Override
	public Personagem findById(Integer Id) {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement(
	            "SELECT p.*, r.*, c.* " +
	            "FROM PERSONAGENS p " +
	            "INNER JOIN Racas r ON p.id_raca_fk = r.Id " +
	            "INNER JOIN Classes c ON p.id_classe_fk = c.Id " +
	            "WHERE p.id_personagem = ?");
	        st.setInt(1, Id);
	        rs = st.executeQuery();

	        if (rs.next()) {
	            
	        	Raca raca = racaDao.findById(rs.getInt("id_raca_fk"));
	            Classe classe = classeDao.findById(rs.getInt("id_classe_fk")); 

	            Personagem personagem = instantiatePersonagem(rs, raca, classe);
	            
	            List<Item> inventario = buscarInventarioPersonagem(personagem.getId());
	            personagem.setInventario(inventario);
	            
	            List<Pericia> pericias = buscarPericiasDoPersonagem(personagem.getId());
	            personagem.setPericias(pericias); 

	            return personagem;
	        }
	        return null;
	        
	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}

	@Override
	public List<Personagem> findAll() {
		 PreparedStatement st = null;
		    ResultSet rs = null;
		    try {
		        st = conn.prepareStatement("SELECT * FROM PERSONAGENS ORDER BY nome");
		        rs = st.executeQuery();

		        List<Personagem> listaDePersonagens = new ArrayList<>();
		        
		        while (rs.next()) {
		            
		            int racaId = rs.getInt("id_raca_fk");
		            int classeId = rs.getInt("id_classe_fk");

		            Raca raca = this.racaDao.findById(racaId);
		            Classe classe = this.classeDao.findById(classeId);
		            
		            Personagem personagem = instantiatePersonagem(rs, raca, classe);
		            
		            List<Item> inventario = buscarInventarioPersonagem(personagem.getId());
		            personagem.setInventario(inventario);
		            
		            List<Pericia> pericias = buscarPericiasDoPersonagem(personagem.getId());
		            personagem.setPericias(pericias); 
		            
		            listaDePersonagens.add(personagem);
		        }
		        return listaDePersonagens;
		        
		    } catch (SQLException e) {
		        throw new DbException(e.getMessage());
		    } finally {
		        DB.closeStatement(st);
		        DB.closeResultSet(rs);
		    }
	}

	private Personagem instantiatePersonagem(ResultSet rs, Raca raca, Classe classe) throws SQLException {
	    Personagem obj = new Personagem();
	    
	    obj.setId(rs.getInt("id_personagem"));
	    obj.setNome(rs.getString("nome"));
	    obj.setVidaAtual(rs.getInt("vidaAtual"));
	    obj.setVidaMax(rs.getInt("vidaMax"));
	    obj.setManaAtual(rs.getInt("manaAtual"));
	    obj.setManaMax(rs.getInt("manaMax"));
	    obj.setXp(rs.getInt("xp"));
	    obj.setNivel(rs.getInt("nivel"));
	    obj.setDeslocamento(rs.getInt("deslocamento"));
	    obj.setForca(rs.getInt("forca"));
	    obj.setDestreza(rs.getInt("destreza"));
	    obj.setConstituicao(rs.getInt("constituicao"));
	    obj.setInteligencia(rs.getInt("inteligencia"));
	    obj.setSabedoria(rs.getInt("sabedoria"));
	    obj.setCarisma(rs.getInt("carisma"));
	    obj.setAtributoAtaque(Atributo.valueOf(rs.getString("atributoAtaque")));
	    obj.setIdCampanha(rs.getInt("id_campanha_fk"));
	    
	    obj.setRaca(raca);
	    obj.setClasse(classe);
	    
		String habilidadesDoBanco = rs.getString("habilidades");
	    if (habilidadesDoBanco != null && !habilidadesDoBanco.isEmpty()) {
	        String[] tracosArray = habilidadesDoBanco.split(";");
	        obj.setHabilidades(new ArrayList<>(Arrays.asList(tracosArray)));
	    }
		
	    return obj;
	}
	
	private List<Item> buscarInventarioPersonagem(Integer personagemId) throws SQLException {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement(
	            "SELECT id_item_fk, quantidade FROM INVENTARIO WHERE id_personagem_fk = ?");
	        
	        st.setInt(1, personagemId);
	        rs = st.executeQuery();

	        List<Item> inventario = new ArrayList<>();
	        
	        while (rs.next()) {
	            int itemId = rs.getInt("id_item_fk");

	            Item item = this.itemDao.findById(itemId); 
	            
	            inventario.add(item);
	        }
	        return inventario;
	    }
	    finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
			
	private List<Pericia> buscarPericiasDoPersonagem(Integer personagemId) throws SQLException {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT id_pericia_fk FROM PERSONAGEM_PERICIA WHERE id_personagem_fk = ?");
	        
	        st.setInt(1, personagemId);
	        rs = st.executeQuery();

	        List<Pericia> pericias = new ArrayList<>();
	        
	        while (rs.next()) {
	            int periciaId = rs.getInt("id_pericia_fk");

	            Pericia pericia = this.periciaDao.findById(periciaId); 
	            
	            pericias.add(pericia);
	        }
	        return pericias;
	    }
	    finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
	
	public List<Personagem> findAllByCampanhaId(Integer campanhaId){
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM PERSONAGENS WHERE id_campanha_fk = ? ORDER BY nome");
	        st.setInt(1, campanhaId);
	        rs = st.executeQuery();

	        List<Personagem> listaDePersonagens = new ArrayList<>();
	        
	        while (rs.next()) {
	            
	            int racaId = rs.getInt("id_raca_fk");
	            int classeId = rs.getInt("id_classe_fk");

	            Raca raca = this.racaDao.findById(racaId);
	            Classe classe = this.classeDao.findById(classeId);
	            
	            Personagem personagem = instantiatePersonagem(rs, raca, classe);
	            
	            List<Item> inventario = buscarInventarioPersonagem(personagem.getId());
	            personagem.setInventario(inventario);
	            
	            List<Pericia> pericias = buscarPericiasDoPersonagem(personagem.getId());
	            personagem.setPericias(pericias); 
	            
	            listaDePersonagens.add(personagem);
	        }
	        return listaDePersonagens;
	        
	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
	
	@Override
	public Personagem findByNomeAndCampanhaId(String nome, Integer campanhaId) {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement(
	            "SELECT * FROM PERSONAGENS WHERE nome = ? AND id_campanha_fk = ?");
	        st.setString(1, nome);
	        st.setInt(2, campanhaId);
	        rs = st.executeQuery();
	        if (rs.next()) {
	            Raca raca = racaDao.findById(rs.getInt("id_raca_fk"));
	            Classe classe = classeDao.findById(rs.getInt("id_classe_fk"));
	            return instantiatePersonagem(rs, raca, classe);
	        }
	        return null;
	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
	
	@Override
	public int countByRacaId(Integer racaId) {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT COUNT(*) FROM PERSONAGENS WHERE id_raca_fk = ?");
	        st.setInt(1, racaId);
	        rs = st.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1); 
	        }
	        return 0;
	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
	
	@Override
	public int countByClasseId(Integer classeId) {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT COUNT(*) FROM PERSONAGENS WHERE id_classe_fk = ?");
	        st.setInt(1, classeId);
	        rs = st.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1); 
	        }
	        return 0;
	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
}