package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.PericiaDao;
import model.entities.Atributo;
import model.entities.Pericia;

public class PericiaDaoJDBC implements PericiaDao{
	
	private Connection conn;

	public PericiaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Pericia obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Pericias (nome, atributo_base, id_campanha_fk) " +
				    "VALUES (?, ?, ?)",  Statement.RETURN_GENERATED_KEYS); 
				
			st.setString(1, obj.getNome());
			st.setString(2, obj.getAtributoBase().name());
			st.setInt(3, obj.getIdCampanha());
			
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
	public void update(Pericia obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE Pericias "
				    + "SET nome = ?, atributoBase = ? "
		            + "WHERE id_pericia = ?");
				
				
			st.setString(1, obj.getNome());
			st.setString(2, obj.getAtributoBase().name());
	        st.setInt(3, obj.getId());
	        
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
			st = conn.prepareStatement("DELETE FROM Pericias WHERE id_pericia = ?");
			
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
	public Pericia findById(Integer Id) {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM Pericias WHERE id_pericia= ?");
	        st.setInt(1, Id);
	        rs = st.executeQuery();

	        if (rs.next()) { 
	            Pericia obj = instantiatePericia(rs);
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
	public List<Pericia> findAll() {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM Pericias ORDER BY nome");
	        rs = st.executeQuery();

	        List<Pericia> list = new ArrayList<>();

	        while (rs.next()) {
	            Pericia obj = instantiatePericia(rs);
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
	
	private Pericia instantiatePericia(ResultSet rs) throws SQLException {
	    Pericia pericia = new Pericia();
	    pericia.setId(rs.getInt("id_pericia"));
	    pericia.setNome(rs.getString("nome"));
	    pericia.setAtributoBase(Atributo.valueOf(rs.getString("atributo_base")));
	    pericia.setIdCampanha(rs.getInt("id_campanha_fk"));
	    return pericia;
	}
}
