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
import model.dao.RacaDao;
import model.entities.Raca;

public class RacaDaoJDBC implements RacaDao{
	
	private Connection conn;
	
	public RacaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Raca obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Racas (nome, deslocamentoBase, bonusForca, bonusDestreza, " +
				            "bonusConstituicao, bonusInteligencia, bonusSabedoria, bonusCarisma, tracosEspeciais) " +
				            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				            Statement.RETURN_GENERATED_KEYS); 
				
			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getDeslocamentoBase());
			st.setInt(3, obj.getBonusForca());
			st.setInt(4, obj.getBonusDestreza());
			st.setInt(5, obj.getBonusConstituicao());
			st.setInt(6, obj.getBonusInteligencia());
			st.setInt(7, obj.getBonusSabedoria());
			st.setInt(8, obj.getBonusCarisma());
			
			String tracos = String.join(";", obj.getTracosEspeciais());
	        st.setString(9, tracos);
	        
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
	public void update(Raca obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE Racas "
				+ "SET nome = ?, deslocamentoBase = ?, bonusForca = ?, bonusDestreza = ?, bonusConstituicao = ?, bonusInteligencia = ?, bonusSabedoria = ?, bonusCarisma = ?, tracosEspeciais = ? "
			    + "WHERE Id = ?");
				
				
			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getDeslocamentoBase());
			st.setInt(3, obj.getBonusForca());
			st.setInt(4, obj.getBonusDestreza());
			st.setInt(5, obj.getBonusConstituicao());
			st.setInt(6, obj.getBonusInteligencia());
			st.setInt(7, obj.getBonusSabedoria());
			st.setInt(8, obj.getBonusCarisma());
			
			String tracos = String.join(";", obj.getTracosEspeciais());
	        st.setString(9, tracos);
			
	        st.setInt(10, obj.getId());
	        
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
			st = conn.prepareStatement("DELETE FROM Racas WHERE id_raca = ?");
			
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
	public Raca findById(Integer Id) {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM Racas WHERE id_raca = ?");
	        st.setInt(1, Id);
	        rs = st.executeQuery();

	        if (rs.next()) { 
	            Raca obj = instantiateRaca(rs);
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
	public List<Raca> findAll() {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM Racas ORDER BY nome");
	        rs = st.executeQuery();

	        List<Raca> list = new ArrayList<>();

	        while (rs.next()) {
	            Raca obj = instantiateRaca(rs);
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
	
	
	private Raca instantiateRaca(ResultSet rs) throws SQLException {
	    Raca obj = new Raca();
	    obj.setId(rs.getInt("id_raca"));
	    obj.setNome(rs.getString("nome"));
	    obj.setDeslocamentoBase(rs.getInt("deslocamentoBase"));
	    obj.setBonusForca(rs.getInt("bonusForca"));
	    obj.setBonusDestreza(rs.getInt("bonusDestreza"));
	    obj.setBonusConstituicao(rs.getInt("bonusConstituicao"));
	    obj.setBonusInteligencia(rs.getInt("bonusInteligencia"));
	    obj.setBonusSabedoria(rs.getInt("bonusSabedoria"));
	    obj.setBonusCarisma(rs.getInt("bonusCarisma"));

	    String tracosDoBanco = rs.getString("tracosEspeciais");
	    if (tracosDoBanco != null && !tracosDoBanco.isEmpty()) {
	        String[] tracosArray = tracosDoBanco.split(";");
	        obj.setTracosEspeciais(new ArrayList<>(Arrays.asList(tracosArray)));
	    }
	    return obj;
	}
	
}
