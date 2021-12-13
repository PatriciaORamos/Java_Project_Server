package ca.nscc.gamefrogger.model.entity;

public class Frogger extends Sprite {

	
	private int life;
	
	public int getLife() { return life;}
	public void setLife(int life) {this.life = life;}
	
	public Frogger(int x, int y) {
		super(50, 50, "/froggerUp.png");
		this.life = 3;
	}

}
