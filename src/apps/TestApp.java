package apps;
import tools.SubPlot;

import processing.core.PApplet;
import setup.IProcessingApp;


public class TestApp implements IProcessingApp{
    private double[] window = {0, 5, 0, 7};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;

    @Override
    public void setup(PApplet parent){
        plt = new SubPlot (window, viewport, parent.width, parent.height);

        float[] xy = plt.getPixelCoord(2.5, 2.5);
        parent.circle(xy[0], xy[1], 100);
    }

    @Override
    public void draw(PApplet parent, float dt)
    {
      
    }

    @Override
    public void keyPressed(PApplet parent)
    {
    }

    @Override
    public void mousePressed(PApplet parent) {
        
    }
}
