package ca.nscc.gamefrogger.model.entity;

public class Player {
	
	private Long id;
	private String name;
	private Integer score;
	
	public Player() {
		this.id = 0L; this.name = ""; this.score = 0;
	}
	
	public Player(String name) {
		this.id = 0L; this.name = name; this.score = 0;
	}
	
	public Player(String name, int score) {
		this.id = 0L; this.name = name; this.score = score;
	}
	
	public Long getId() {return id;}
	public String getName() {return name;}
	public Integer getScore() {return score;}
	
	public void setId(Long id) {this.id = id;}
	public void setName(String name) {this.name = name;}	
	public void setScore(Integer score) {this.score = score;}
	
	
}
