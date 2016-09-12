import org.newdawn.slick.geom.Line;

public class Shot {
	
	int dam, range, alliance;
	float fade = (float) 1.0;
	Line shot;
	
	public Shot(int damage, int r, double xRa, double yRa, int ally, double x, double y){
		dam = damage;
		range = r;
		shot = new Line((float)x, (float)y, (float)(x+r*xRa), (float)(y+r*yRa));
		alliance = ally;
	}
	
	public void fadeOut(){
		fade -= .05;
	}

}
