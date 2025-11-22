package dla;

import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Walker {
    
    public enum State {
        STOPPED,
        WANDER
    }

    private PVector pos;
    private State state;
    private int colour;
    private static int radius = 2;
    private boolean isCenter = false;

    
    private float customCenterSize = 50;

    private float stickiness = 1.0f;

    public Walker(PApplet p)
    {
        //pos = new PVector(p.random(p.width), p.random(p.height));
        pos = new PVector(p.width / 2, p.height / 2);
        PVector d = PVector.random2D();
        pos.add(d.mult(p.height / 2));

        setState(p, State.WANDER);
        isCenter = false;
    }

    public Walker(PApplet p, PVector pos) {
        this.pos = pos;
        isCenter = true;
        setState(p, State.STOPPED);
        this.stickiness = 1.0f;
    }

    public void setState(PApplet p, State state)
    {
        this.state = state;
        if (state == State.STOPPED)
        {
            colour = p.color(0);
        }
        else
        {
            colour = p.color(255);
        }

    }

    public State getState()
    {
        return state;
    }

    public void wander(PApplet p)
    {
        PVector step = PVector.random2D();
        pos.add(step);

        pos.lerp(new PVector(p.width / 2, p.height / 2), 0.0002f);
    }

    public void updateState(PApplet p, List<Walker> walkers)
    {
        /*if (state == State.STOPPED)
            return;*/
        
        for (Walker w : walkers)
        {
            if (w.state == State.STOPPED) {
                float wRadius = w.isCenter ? w.customCenterSize/2 : radius;
                float thisRadius = this.isCenter ? this.customCenterSize/2 : radius;

                float dist = PVector.dist(this.pos, w.pos);
                if (dist < wRadius + thisRadius) {
                    if (p.random(1.0f) < this.stickiness){
                    setState(p, State.STOPPED);
                    break;
                    }
                }
            }
        }
    }

    public void display(PApplet p)
    {
        p.fill(colour);

        if (isCenter && state == State.STOPPED) {
            p.rectMode(PApplet.CENTER);
            p.circle(pos.x, pos.y, customCenterSize);
            //p.rect(pos.x, pos.y, customSize, customSize);
        } else {
            float size = (state == State.STOPPED && isCenter) ? customCenterSize : 2 * radius;
            p.circle(pos.x, pos.y, size);
        }
    }

    public void setStickiness(float stick){
        this.stickiness = stick;
    }
}
