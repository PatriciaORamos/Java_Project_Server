package ca.nscc.gamefrogger.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class GenericDAO<T> {
	
	void createTable(String createSql) {
		try {
			PreparedStatement pstmt = ConnectionFactory.getConnection().prepareStatement(createSql);			
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	ResultSet insert(String insertSql, Object[] parametros) {
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = ConnectionFactory.getConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < parametros.length; i++) {
				pstmt.setObject(i + 1, parametros[i]);
			}
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	ResultSet selectById(String selectByIdSql, int id) {
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = ConnectionFactory.getConnection().prepareStatement(selectByIdSql);
			pstmt.setObject(1, id);
			pstmt.executeQuery();			
			rs = pstmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	void update (String updateSql,  Object[] parametros) {	
		try {
			PreparedStatement pstmt = ConnectionFactory.getConnection().prepareStatement(updateSql);
			for (int i = 0; i < parametros.length; i++) {
				pstmt.setObject(i + 1, parametros[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}