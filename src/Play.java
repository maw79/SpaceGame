///////////////////////////////////////////////////////////////////////////////////
//Play.java
//Type: Rendering File
//Author: Aaron Riggs
//Date: 9/10/16
///////////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.*;

public class Play extends BasicGameState{

	int id = 0;
	ArrayList<Ship> players = new ArrayList<Ship>();
	ArrayList<Shot> shots = new ArrayList<Shot>();
	Ship s;
	Image shipPic;
	SolarSystem solar;

	public Play(int state) {
		id = state;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		shipPic = new Image("/res/Ship.png");
		s = new Ship(2, 10, players, 0, 0, (byte) 0, true, shots);
		players.clear();
		players.add(s);
		players.add(new AIShip(10, 10, players, 700, 0, (byte) 1, false, shots));
		players.add(new AIShip(10, 10, players, 0 , 700, (byte) 1, false, shots));
		players.add(new AIShip(10, 10, players, 700 , 700, (byte) 1, false, shots));

		solar = new SolarSystem(1);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		players.size();
		g.rotate(116, 66, s.rotAngle());
		//g.drawImage(shipPic, 100, 50);
		g.rotate(116, 66, -s.rotAngle());

		g.translate(-s.bBox.getCenterX() + gc.getWidth()/2, -s.bBox.getCenterY() + gc.getHeight()*3/4);
		g.rotate(s.X(), s.Y(), -s.rotAngle());

		for(Shot s : shots){
			g.setColor(new Color((float)1.0, (float)1.0, (float)1.0, s.fade));
			g.draw(s.shot);
		}

		DrawPlanet(solar.sun,g);
		int i = 0;
		while(i < solar.planets.size()){
			DrawPlanet(solar.planets.get(i),g);
		}

		for(Ship ship : players){
			
			for(Line l : ship.tLines){
				g.setColor(new Color((float)1.0, (float)1.0, (float)1.0, (float) (1/Math.sqrt(15 - ship.tLines.indexOf(l)+1))) );
				g.draw(l);
			}

			g.rotate(ship.X(), ship.Y(), ship.rotAngle());
			g.drawImage(shipPic, ship.X() - shipPic.getWidth()/2, ship.Y() - shipPic.getHeight()/2);
			g.rotate(ship.X(), ship.Y(), -ship.rotAngle());

		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		Input input = gc.getInput();

		clearShots();

		new Vector2f(Mouse.getX(), gc.getHeight()-Mouse.getY());

		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			sbg.getState(0).init(gc, sbg);
			sbg.enterState(0);
		}

		if(input.isKeyDown(Input.KEY_W)){
			s.throttleFore();
		}
		if(input.isKeyDown(Input.KEY_S)){
			s.throttleBack();
		}
		if(input.isKeyDown(Input.KEY_Q)){
			s.throttleLeft();
		}
		if(input.isKeyDown(Input.KEY_E)){
			s.throttleRight();
		}
		if(input.isKeyDown(Input.KEY_A)){
			s.rotLeft();
		}
		if(input.isKeyDown(Input.KEY_D)){
			s.rotRight();
		}
        if(input.isKeyPressed(Input.KEY_R)){
            players.add(new AIShip(10, 10, players, 700, 0, (byte) 1, false, shots));
        }

		clearShips();
		
		if(s.armor <= 0){
			sbg.getState(0).init(gc, sbg);
			sbg.enterState(0);
		}

		if(input.isKeyDown(Input.KEY_SPACE)){
			s.w.shoot();
		}

	}

	public void DrawPlanet(Planet p,Graphics g){
		int i = 0;
		while(i < p.Pfill.size()){
			g.fill(p.c.get(i),p.Pfill.get(i));
			i++;
		}
	}
	
	public void clearShots(){
		ArrayList<Shot> shotR = new ArrayList<Shot>();

		for(Shot shot : shots){
			shot.fadeOut();
			if(shot.fade <= 0){
				shotR.add(shot);
			}
		}
		
		for(Ship s : players){
			s.alreadyHit.removeAll(shotR);
		}

		shots.removeAll(shotR);
		
		for(Shot s : shotR){
			s = null;
		}

	}
	
	public void clearShips(){
		ArrayList<Ship> remover = new ArrayList<Ship>();

		for(Ship ship : players){
			ship.applyForces();
			ship.w.coolSelf();
			ship.hitByShots();
			ship.calcRatios();
			if(!ship.player){
				ship.findATarget();
				ship.trackTarget();
				ship.avoidFriendlies();
			}
			if(ship.armor <= 0){
				remover.add(ship);
			}
		}

		players.removeAll(remover);
		
		for(Ship s : remover){
			s = null;
		}

	}

	public int getID() {
		return id;
	}

}