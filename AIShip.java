import java.util.ArrayList;

public class AIShip extends Ship{

	public AIShip(double thrust, double weight, ArrayList<Ship> sList, int x, int y, byte ally, boolean isPlayer, ArrayList<Shot> shots) {
		super(thrust, weight, sList, x, y, ally, isPlayer, shots);
		trackDistance = 300;
	}

}
