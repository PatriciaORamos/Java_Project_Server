package ca.nscc.gamefrogger.model.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import ca.nscc.gamefrogger.model.entity.Player;
import ca.nscc.gamefrogger.model.entity.Vehicle;
import ca.nscc.gamefrogger.server.GameProperties;

public class GameService implements Runnable {	
	
	private PlayerService playerService;
	
	final int CLIENT_PORT = 5656;
	private Socket s;
	private Scanner in;	

	public GameService (Socket clientSocket) {
		this.s = clientSocket;
		playerService = new PlayerService();
	}

	public void run() {
		try {
			in = new Scanner(s.getInputStream());
			processRequest();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// processing the requests
	public void processRequest() throws IOException {
		while (true) {
			if (!in.hasNext()) {
				return;
			}
			String command = in.next();
			if (command.equals("Quit")) {
				return;
			} else {
				executeCommand(command);
			}
		}
	}

	public void executeCommand(String command) throws IOException {
		if (command.equals("ADD_PLAYER")) {
			String player1Name = in.next();
			String player2Name = in.next();
			
			int player1Id = playerService.insert(new Player(player1Name));
			int player2Id = playerService.insert( new Player(player2Name) );
			
			Socket s = new Socket("localhost", CLIENT_PORT);
			OutputStream outstream = s.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);

			String commandOut = "PLAYER_ID " + player1Id + " " + player2Id + "\n";
			System.out.println("Sending: " + commandOut);
			out.println(commandOut);
			out.flush();

			s.close();
			
		} else if (command.equals("MOVE_FROG")) {
			int dx = in.nextInt();
			int dy = in.nextInt();			
			int frogHeight = in.nextInt();
			String direction = in.next();
			
			if(direction.equals("UP")) {			
				if(dy >= 180) {
					dy -= GameProperties.CHARACTER_STEP;
				}
			} else if (direction.equals("DOWN")) {
				if(dy < 740 && dy > frogHeight) {
					dy += GameProperties.CHARACTER_STEP;		
				}			
			} else if (direction.equals("LEFT")) {
				if (dx > 0) {
					dx -= GameProperties.CHARACTER_STEP;
				}
			} else if (direction.equals("RIGHT")) {			
				if (dx < 490) {
					dx += GameProperties.CHARACTER_STEP;
				}
			}
			
			Socket s = new Socket("localhost", CLIENT_PORT);			
			OutputStream outstream = s.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);

			String commandOut = "FROG_POSITION " + dx + " " + dy + "\n";
			
			System.out.println("Sending: " + commandOut);
			out.println(commandOut);
			out.flush();

			s.close();	
			
		} else if(command.equals("MOVE_VEHICLE")) {

			String image = in.next();
			boolean increase = in.nextBoolean();
			int labelWidth = in.nextInt();
			int vehicleX = in.nextInt();
			int vehicleY = in.nextInt();

			Vehicle vehicleT = new Vehicle(image, increase, labelWidth);
			vehicleT.setX(vehicleX);
			vehicleT.setY(vehicleY);
			vehicleT.getRectangle().setBounds(300, 50, vehicleX, vehicleY);
			vehicleT.moveVehicle();		
			
		} else if(command.equals("STOP_CARS")) {
			//Find thread of vehicle and stop
			for (Thread t : Thread.getAllStackTraces().keySet()) {
				if(t.getName().equals("vehicle")) {
					t.stop();
					s.close();
				}
			}			
		} else if(command.equals("UPDATE_SCORE")) {
			int id = in.nextInt();
			int score = in.nextInt();
		}
	}

}
