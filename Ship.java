import java.util.ArrayList;

import org.newdawn.slick.geom.*;

public class Ship{

	Shape bBox = new Rectangle(100, 100, 32, 32);
	ArrayList<Line> tLines = new ArrayList<Line>();
	ArrayList<Ship> sList = new ArrayList<Ship>();
	ArrayList<Shot> shots, alreadyHit = new ArrayList<Shot>();
	Weapon w = new Weapon(10, 10, 500, this);
	int armor = 100;
	byte alliance;
	boolean player = false;
	int trackDistance = 0;
	Ship target;
	double thrust, weight, rotVel = 0, vx = 0, vy = 0, vxR = 0, vyR = 1;
	Transform t = new Transform();


	public Ship(double thrust, double weight, ArrayList<Ship> sList, int x, int y, byte ally, boolean isPlayer, ArrayList<Shot> shots){
		bBox = new Rectangle(x-16, y-16, 32, 32);
		alliance = ally;
		player = isPlayer;
		if(player){
			armor = 300;
		}
		this.thrust = thrust;
		this.weight = weight;
		this.sList = sList;
		tLines.add(new Line((float)(X() - 16*vxR), (float)(Y() - 16*vyR), (float)(X() - 16*vxR), (float)(Y() - 16*vyR)));
		this.shots = shots;
	}
	
	public void hitByShots(){
		int damages = 0;
		for(int i = 0; i < shots.size(); i++){
			if(!alreadyHit.contains(shots.get(0)) &&bBox.intersects(shots.get(i).shot) && alliance != shots.get(i).alliance){
				damages += shots.get(i).dam;
				alreadyHit.add(shots.get(i));
			}
		}
		armor -= damages;
	}

	public void calcRatios(){
		vxR = (bBox.getPoint(1)[0] - bBox.getPoint(2)[0])/32;
		vyR = (bBox.getPoint(1)[1] - bBox.getPoint(2)[1])/32;
	}

	public int X(){
		return (int) bBox.getCenterX();
	}

	public int Y(){
		return (int) bBox.getCenterY();
	}

	public void refreshShipList(ArrayList<Ship> sList){
		this.sList = sList;
	}

	public float rotAngle(){
		if(vxR < .5 && vxR > -.5){
			if(vyR >= 0){
				return (float) (Math.acos(vxR) * 360/(2*Math.PI))+90;
			}else{
				return (float) (-(Math.acos(vxR)) * 360/(2*Math.PI))+90;
			}
		}else{
			if(vxR >= 0){
				return (float) (Math.asin(vyR) * 360/(2*Math.PI))+90;
			}else{
				return (float) (-(Math.asin(vyR) - Math.PI) * 360/(2*Math.PI))+90;
			}
		}
	}

	public void applyForces(){
		drag();
		Line pastLine = tLines.get(tLines.size()-1);
		t.concatenate(Transform.createTranslateTransform((float)vx, (float)vy));
		t.concatenate(Transform.createRotateTransform((float) rotVel, bBox.getCenterX(), bBox.getCenterY()));
		bBox = bBox.transform(t);
		tLines.add(new Line((float)(X() - 16*vxR),(float)(Y() - 16*vyR), pastLine.getPoint(0)[0], pastLine.getPoint(0)[1]));
		if(tLines.size() >= 15){
			tLines.remove(0);
		}
		t = new Transform();
	}

	public void drag(){
		vy *= .99;
		vx *= .99;
		rotVel *= .99;
	}

	public void rotRight(){
		rotVel += .02 * thrust/weight;
	}
	public void slowRotRight(){
		rotVel += .01 * thrust/weight;
	}

	public void rotLeft(){
		rotVel -= .02 * thrust/weight;
	}
	public void slowRotLeft(){
		rotVel -= .02 * thrust/weight;
	}


	public void throttleFore(){
		vy += vyR*thrust/weight;
		vx += vxR*thrust/weight;
	}

	public void throttleBack(){
		vy -= vyR*thrust/weight;
		vx -= vxR*thrust/weight;
	}

	public void throttleLeft(){
		vy -= vxR*thrust/weight;
		vx += vyR*thrust/weight;
	}

	public void throttleRight(){
		vy += vxR*thrust/weight;
		vx -= vyR*thrust/weight;
	}

	public void findATarget(){
		int dist = Integer.MAX_VALUE;
		Line l;
		for(Ship s : sList){
			if(s != this && s.alliance != alliance){
				l = new Line(s.X(), s.Y(), X(), Y());
				if(l.length() < dist){
					dist = (int) l.length();
					target = s;
				}
			}
		}
	}

	public float angleToTarget(Ship target){
		Line l = new Line(target.X(), target.Y(), X(), Y());
		double xR = (target.X() - X())/l.length();
		double yR = (target.Y() - Y())/l.length();

		if(xR < .5 && xR > -.5){
			if(yR >= 0){
				return (float) (Math.acos(xR) * 360/(2*Math.PI))+90;
			}else{
				return (float) (-(Math.acos(xR)) * 360/(2*Math.PI))+90;
			}
		}else{
			if(xR >= 0){
				return (float) (Math.asin(yR) * 360/(2*Math.PI))+90;
			}else{
				return (float) (-(Math.asin(yR) - Math.PI) * 360/(2*Math.PI))+90;
			}
		}

	}

	public void avoidFriendlies(){
		for(Ship s : sList){
			if(s != this && s.alliance == alliance){

				Line l = new Line(s.X(), s.Y(), X(), Y());
				double xR = (s.X() - X())/l.length();
				double yR = (s.Y() - Y())/l.length();
				
				if(l.length() < 50){
					vy -= .1*thrust/weight * yR;
					vx -= .1*thrust/weight * xR;
				}

			}
		}

	}

	public void trackTarget(){
		if(target == null){
			findATarget();
		}
		int rA = (int) rotAngle();
		int tA = (int) angleToTarget(target);
		if(!(Math.abs(rotAngle() - angleToTarget(target)) < 180)){
			if(rotAngle() >= 180){
				rA -= 360;
			}else{
				tA -= 360;
			}
		}
		int deadAngle = 170;

		if(Math.abs(tA - rA) < deadAngle){

			rotVel = (tA - rA) * (3.14/360);
			Line l = new Line(target.X(), target.Y(), X(), Y());
			if(l.length() > w.range){
				throttleFore();
			}else{
				//w.shoot();
			}

		}else{
			if(tA - rA < 0){
				rotRight();
			}else{
				rotLeft();
			}
		}

	}

	public String toString(){
		return "Player";
	}

}
