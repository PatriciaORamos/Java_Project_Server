package ca.nscc.gamefrogger.model.entity;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

import javax.swing.JLabel;

import ca.nscc.gamefrogger.model.service.GameService;
import ca.nscc.gamefrogger.server.GameProperties;

public class Vehicle extends Sprite implements Runnable {
	
	final int CLIENT_PORT = 5656;
	
	private Boolean moving, increase;
	private Thread t;
	private Frogger myFrogger;
	private int labelWidth;
	private Socket s;	

	public Boolean getMoving() { return moving; }
	public Frogger getMyFrogger() {return myFrogger;}
	
	public void setMoving(Boolean moving) { this.moving = moving;}
	public void setIncrease(Boolean increase) { this.increase = increase;}
	public void setMyFrogger(Frogger temp) { this.myFrogger = temp; }
	
	public Vehicle() {		
		this.moving = false;
	}
	
	public Vehicle(String image, Boolean increase, int labelWidth) {
		super(300, 50, image);
		this.moving = true;
		this.increase = increase;
		this.labelWidth = labelWidth;
		
		try {
			s = new Socket("localhost", CLIENT_PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 	}
	
	public void moveVehicle() {
		t = new Thread(this, "vehicle");
		t.start();
	}
	
	@Override
	public void run() {		
		while(this.moving) {
			int tx = this.x;
			int ty = this.y;
			
			if(increase) { 
				tx += 1 + 20;
				
				if ( tx > GameProperties.SCREEN_WIDTH ) {
					tx = -1 * this.width;
				}
			} else {
				tx -= 20 + 20;
				if (tx + labelWidth < 0) {
					tx = GameProperties.SCREEN_WIDTH;
				}				
			}
			
			this.setX(tx);
			this.setY(ty);
			
			try {
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);

				String commandOut = "VEHICLE_POSITION " + this.x + " " + this.y + " " + this.filename + "\n";
				System.out.println("Sending: " + commandOut);
				out.println(commandOut);
				out.flush();

//				s.close();

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
