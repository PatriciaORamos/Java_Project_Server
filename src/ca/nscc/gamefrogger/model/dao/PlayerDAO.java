package ca.nscc.gamefrogger.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import ca.nscc.gamefrogger.model.entity.Player;

public class PlayerDAO extends GenericDAO<Player> {

	private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS players" + "("
			+ " player_id INT AUTO_INCREMENT PRIMARY KEY," + " player_name VARCHAR(255) NOT NULL," + " player_score INT"
			+ ")";

	public void createTable() {
		createTable(SQL_CREATE);
	}

	public int insert(Player p) {
		int id = 0;
		try {
			String insert = "INSERT INTO players (player_name, player_score) VALUES (?,?)";
			Object[] param = { p.getName(), p.getScore() };
			ResultSet rs = insert(insert, param);
			while (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public Player selectById(int id) {
		Player player = null;
		try {
			String selectById = "SELECT * FROM players WHERE player_id = ?";
			ResultSet rs = selectById(selectById, id);
			while (rs.next()) {
				player = new Player();
				player.setId(new Long(rs.getInt("player_id")));
				player.setName(rs.getString("player_name"));
				player.setScore(rs.getInt("player_score"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return player;
	}
	
	public void updateScore(int score, int id) {
		String update = "UPDATE players SET player_score=? WHERE player_id=?";
		Object[] param = { score, id};
		update(update, param);
	}

}
