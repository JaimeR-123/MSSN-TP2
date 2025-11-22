package dla;

import java.util.ArrayList;
import java.util.List;

import dla.Walker.State;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class DLA implements IProcessingApp 
{
    private int NUM_WALKERS = 100;
    private int NUM_STEPS_PER_FRAME = 50;
    public List<Walker> walkers;
    
    @Override
    public void setup(PApplet parent) 
    {
        walkers = new ArrayList<Walker>();
        Walker w = new Walker(parent, new PVector(parent.width / 2, parent.height / 2));
        walkers.add(w);
        
        for (int i=0; i<NUM_WALKERS;i++)
        {
            w = new Walker(parent);
            w.setStickiness(1.0f);
            walkers.add(w);
        }
    }

    @Override
    public void draw(PApplet parent, float dt) 
    {
        parent.background(200);
        for (int i = 0; i < NUM_STEPS_PER_FRAME; i++)
        {
            for (Walker w : walkers) {
                if (w.getState() == State.WANDER)
                {
                    w.wander(parent);
                    w.updateState(parent, walkers);
                    //Adiciona um Walker sempre que um para
                    if(w.getState() == State.STOPPED){
                        Walker x = new Walker(parent);
                        walkers.add(x);
                        break;

                    }
                }  
            }
        }

        for (Walker w : walkers) {
            w.display(parent);
        }
        
    }

    @Override
    public void keyPressed(PApplet parent) {
    }

    @Override
    public void mousePressed(PApplet parent) {
    }

    
}
