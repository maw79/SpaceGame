import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.svg.RadialGradientFill;

import java.util.ArrayList;

public class Planet {

    float Radius = 0;
    float LocX = 0;
    float LocY = 0;

    //Color colorB;
    //Color colorE;

    Circle cir;
    //ShapeFill Pfill;

    ArrayList<Circle> c = new ArrayList<Circle>();
    ArrayList<ShapeFill> Pfill = new ArrayList<ShapeFill>();
    ArrayList<Color> colors = new ArrayList<Color>();

    public Planet(float RadiusIn, int R, int G, int B, float LocXin, float LocYin){
        Radius = RadiusIn;
        LocX = LocXin;
        LocY = LocYin;
        int i = 0;
        int j = 0;
        while(i < Radius){
            if(i%10 == 0){
                colors.add(new Color(R+i,G+i,B+i));
                c.add(new Circle(LocX, LocY, Radius-i));
                Pfill.add(new GradientFill(0,0,colors.get(j),10,10,colors.get(j)));
                j++;
            }
            i++;
        }
        cir = new Circle(LocX,LocY,Radius);
        //Pfill = new GradientFill(LocX-Radius,LocY-Radius,colorB,LocX+Radius,LocY+Radius,colorE,true);
    }

    public Circle GetCircle(){
        return cir;
    }

    public float GetX(){
        return LocX;
    }

    public float GetY(){
        return LocY;
    }

}
