public class Weapon {
	
	int cool, lastFire = 0, dam,  range;
	Ship owner;
	
	public Weapon(int c, int d, int r, Ship own){
		cool = c;
		dam = d;
		range = r;
		owner = own;
	}
	
	public void shoot(){
		if(lastFire == 0){
			owner.shots.add(new Shot(dam, range, owner.vxR, owner.vyR, owner.alliance, (owner.X() + 16*owner.vxR), (owner.Y() + 16*owner.vyR)));
			lastFire = cool;
		}
	}
	
	public void coolSelf(){
		if(lastFire > 0){
			lastFire --;
		}
	}
	
}
