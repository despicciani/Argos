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
import model.entities.Classe;

public class ClasseDaoJDBC implements ClasseDao{

	private Connection conn;
	
	public ClasseDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Classe obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Classes (nome, dadoDeVida, proficiencias) " +
				    "VALUES (?, ?, ?)",  Statement.RETURN_GENERATED_KEYS); 
				
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getDadoDeVida());
			
			String proficiencias = String.join(";", obj.getProficiencias());
	        st.setString(3, proficiencias);
	        
	        int rowsAffected = st.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            ResultSet rs = st.getGeneratedKeys();
	            if (rs.next()) {
	                int id = rs.getInt(1); 
	                obj.setId(id); 
	            }
	            DB.closeResultSet(rs);
			
	        }
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Classe obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE Classes "
				    + "SET nome = ?, dadoDeVida = ?, proficiencias = ? "
		            + "WHERE Id = ?");
				
				
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getDadoDeVida());
			
			String proficiencias = String.join(";", obj.getProficiencias());
	        st.setString(3, proficiencias);
			
	        st.setInt(4, obj.getId());
	        
	        st.executeUpdate();

		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}		
		
	}

	@Override
	public void deleteById(Integer Id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM Classes WHERE id_classe = ?");
			
			st.setInt(1, Id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}		
	}

	@Override
	public Classe findById(Integer Id) {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM Classes WHERE id_classe = ?");
	        st.setInt(1, Id);
	        rs = st.executeQuery();

	        if (rs.next()) { 
	            Classe obj = instantiateClasse(rs);
	            return obj;
	        }
	        return null; 
	    }
	    catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    }
	    finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}

	@Override
	public List<Classe> findAll() {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM Classes ORDER BY nome");
	        rs = st.executeQuery();

	        List<Classe> list = new ArrayList<>();

	        while (rs.next()) {
	            Classe obj = instantiateClasse(rs);
	            list.add(obj);
	        }
	        return list;
	    }
	    catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    }
	    finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
	
	private Classe instantiateClasse(ResultSet rs) throws SQLException {
	    Classe obj = new Classe();
	    obj.setId(rs.getInt("id_classe"));
	    obj.setNome(rs.getString("nome"));
	    obj.setDadoDeVida(rs.getInt("dadoDeVida"));

	    String proficienciasDoBanco = rs.getString("proficiencias");
	    if (proficienciasDoBanco != null && !proficienciasDoBanco.isEmpty()) {
	        String[] tracosArray = proficienciasDoBanco.split(";");
	        obj.setProficiencias(new ArrayList<>(Arrays.asList(tracosArray)));
	    }
	    return obj;
	}

}
