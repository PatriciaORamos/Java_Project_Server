package ca.nscc.gamefrogger.model.service;

import ca.nscc.gamefrogger.model.dao.PlayerDAO;
import ca.nscc.gamefrogger.model.entity.Player;

public class PlayerService {
	
	private PlayerDAO dao;
	
	public PlayerService() {
		this.dao = new PlayerDAO();
		dao.createTable();
	}
	
	public int insert(Player player) {
		return dao.insert(player);		
	}
	
	public Player selectById (int id) { return dao.selectById(id); }	
	
	public void updateScore(int score, int id) { dao.updateScore(score, id); }

}
