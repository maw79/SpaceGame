///////////////////////////////////////////////////////////////////////////////////
//SolarSystem.java
//Type: Solar system object
//Author: Aaron Riggs
//Date: 9/16/16
///////////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.*;

public class SolarSystem {
    ArrayList<Planet> planets = new ArrayList<Planet>();
    Planet sun;

    public SolarSystem(int planetnum){
        sun = new Planet(1000,Color.red,Color.orange,0,0);

        float x;
        float y;
        int i = 0;
        while(i < planetnum){
            x = sun.GetX() + 2000;
            y = 0;
            planets.add(new Planet(1000,Color.blue,Color.green,x,y));
        }
    }

    public Planet GetSun(){
        return sun;
    }

    public ArrayList<Planet> GetPlanets(){
        return planets;
    }
}
