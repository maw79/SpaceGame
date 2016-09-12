import java.awt.Dimension;
import java.awt.Toolkit;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

	public class Window extends StateBasedGame{
		
		public static final String gamename = "SpaceShips";
		public static final int menu = 0, play = 1, settings = 2;
		public static Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();
		
		public Window(String gamename){
			super(gamename);
			this.addState(new MainMenu(menu));
			this.addState(new Play(play));
			this.addState(new Settings(settings));
		}
		
		public void initStatesList(GameContainer gc) throws SlickException {
			this.getState(menu).init(gc, this);
			this.getState(play).init(gc, this);
			this.getState(settings).init(gc,this);
			this.enterState(menu);
		}
		
		public static void main(String[] args){
			AppGameContainer appgc;
			try{
				appgc = new AppGameContainer(new Window(gamename));
				appgc.setDisplayMode(screenSize.width, screenSize.height, true);
				appgc.setTargetFrameRate(60);
				appgc.start();
				
			}catch(SlickException e){
				
			}
		}

	}