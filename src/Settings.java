import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.*;

public class Settings extends BasicGameState{

    ArrayList<Rectangle> gui = new ArrayList<Rectangle>();
    int id = 0;

    public Settings (int state){
        id = state;
    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        gui.add(new Rectangle(100, gc.getHeight()/2, gc.getWidth()-200, 100));
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {
        for(Rectangle r : gui){
            g.draw(r);
        }
        g.setColor(Color.white);
        int SW = gc.getWidth();
        int SH = gc.getHeight();
        g.drawString("Play Game",(int)(SW*.50),(int)(SH*.53));
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        Input input = gc.getInput();

        Vector2f mCoord = new Vector2f(Mouse.getX(), gc.getHeight()-Mouse.getY());

        if(input.isKeyPressed(Input.KEY_ESCAPE)){
            sbg.getState(0).init(gc, sbg);
            sbg.enterState(0);
        }

        if(gui.get(0).contains(mCoord.getX(), mCoord.getY()) && Mouse.isButtonDown(0)){
            sbg.getState(1).init(gc, sbg);
            sbg.enterState(1);
        }

    }

    public int getID() {
        return id;
    }
}
