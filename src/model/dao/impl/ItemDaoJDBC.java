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
import model.dao.ItemDao;
import model.entities.Item;

public class ItemDaoJDBC implements ItemDao	{

	private Connection conn;
	
	public ItemDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Item obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Itens (nome, descricao, funcao, propriedade, ataque) " +
				    "VALUES (?, ?, ?, ?, ?)",  Statement.RETURN_GENERATED_KEYS); 
				
			st.setString(1, obj.getNome());
	        st.setString(2, obj.getDescricao());
	        st.setString(3, obj.getFuncao());
	        st.setString(4, obj.getPropriedade());
	        st.setString(5, obj.getAtaque());
	        
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
	public void update(Item obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE Itens "
				    + "SET nome = ?, descricao = ?, funcao = ?, propriedade = ?, ataque = ?"
		            + "WHERE Id = ?");
				
				
			st.setString(1, obj.getNome());
			
	        st.setString(2, obj.getDescricao());
			
	        st.setString(3, obj.getFuncao());
	        
	        st.setString(4, obj.getPropriedade());
	        
	        st.setString(5, obj.getAtaque());
			
	        st.setInt(6, obj.getId());
	        
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
			st = conn.prepareStatement("DELETE FROM Itens WHERE id_item = ?");
			
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
	public Item findById(Integer Id) {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM Itens WHERE id_item = ?");
	        st.setInt(1, Id);
	        rs = st.executeQuery();

	        if (rs.next()) { 
	            Item obj = instantiateItem(rs);
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
	public List<Item> findAll() {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM Itens ORDER BY nome");
	        rs = st.executeQuery();

	        List<Item> list = new ArrayList<>();

	        while (rs.next()) {
	            Item obj = instantiateItem(rs);
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
	
	private Item instantiateItem(ResultSet rs) throws SQLException {
	    Item obj = new Item();
	    obj.setId(rs.getInt("id_item"));
	    obj.setNome(rs.getString("nome"));
	    obj.setDescricao(rs.getString("descricao"));
	    obj.setFuncao(rs.getString("funcao"));
	    obj.setPropriedade(rs.getString("propriedade"));
	    obj.setAtaque(rs.getString("ataque"));
	    
	    return obj;
	}
	
}
