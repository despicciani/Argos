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
import model.dao.CampanhaDao;
import model.entities.Campanha;

public class CampanhaDaoJDBC implements CampanhaDao {

    private Connection conn;

    public CampanhaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Campanha obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "INSERT INTO CAMPANHAS (nome, xpParaProximoNivel) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getXpProxNivel());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Campanha obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "UPDATE CAMPANHAS SET nome = ?, xpParaProximoNivel = ? WHERE id_campanha = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getXpProxNivel());
            st.setInt(3, obj.getId());
            
            st.executeUpdate();
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM CAMPANHAS WHERE id_campanha = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Campanha findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM CAMPANHAS WHERE id_campanha = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateCampanha(rs);
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
    public List<Campanha> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM CAMPANHAS ORDER BY nome");
            rs = st.executeQuery();

            List<Campanha> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateCampanha(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Campanha instantiateCampanha(ResultSet rs) throws SQLException {
        Campanha obj = new Campanha();
        obj.setId(rs.getInt("id_campanha"));
        obj.setNome(rs.getString("nome"));
        obj.setXpProxNivel(rs.getInt("xpParaProximoNivel"));
        return obj;
    }
}