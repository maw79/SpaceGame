///////////////////////////////////////////////////////////////////////////////////
//Planet.java
//Type: Planet object
//Author: Aaron Riggs
//Date: 9/10/15
///////////////////////////////////////////////////////////////////////////////////

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

    Circle cir;

    ArrayList<Circle> c = new ArrayList<Circle>();
    ArrayList<ShapeFill> Pfill = new ArrayList<ShapeFill>();
    ArrayList<Color> colors = new ArrayList<Color>();

    ///////////////////////////////////////////////////////////////////////////////////
    //Planet
    //Type:Constructor
    //Inputs:
    //  float RadiusIn
    //  Color Cb - Beginning Color
    //  Color Ce - Ending Color
    //  float LocXin - X location of the Planet
    //  float LocYin - Y location of the Planet
    ///////////////////////////////////////////////////////////////////////////////////
    public Planet(float RadiusIn, Color Cb, Color Ce, float LocXin, float LocYin){
        Radius = RadiusIn;
        LocX = LocXin;
        LocY = LocYin;

        ArrayList<Float> r = CList((int)Cb.r,(int)Ce.r);
        ArrayList<Float> g = CList((int)Cb.g,(int)Ce.g);
        ArrayList<Float> b = CList((int)Cb.b,(int)Ce.b);

        int i = 0;
        int j = 0;
        while(i < Radius){
            if(i%10 == 0){
                colors.add(new Color(r.get(j),g.get(j),b.get(j)));
                c.add(new Circle(LocX, LocY, Radius-i));
                Pfill.add(new GradientFill(0,0,colors.get(j),10,10,colors.get(j)));
                j++;
            }
            i++;
        }
        cir = new Circle(LocX,LocY,Radius);
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

    private ArrayList<Float> CList(float BeginColor, float EndColor){
        ArrayList<Float> Colors = new ArrayList<>();
        boolean dir;
        float dif;
        float temp;
        if(BeginColor > EndColor) {
            dif = BeginColor - EndColor;
            dir = true;
            temp = BeginColor;
        }else {
            dif = EndColor - BeginColor;
            dir = false;
            temp = EndColor;
        }
        float num = dif/(int)(Radius/10);
        for(int i = 0; i < Radius/10; i++){
            if(dir == true){
                Colors.add(temp);
                temp = temp + num;
            }else{
                Colors.add(temp);
                temp = temp - num;
            }
        }
        return Colors;
    }

}
