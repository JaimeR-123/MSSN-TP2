package aa;

import java.util.ArrayList;
import java.util.List;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class BoidApp implements IProcessingApp{
    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;
    private int index;
    private Patrol patrol;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        b = new Boid(new PVector(), 1, 0.5f, p.color(0), p, plt);
       
        b.addBehavior(new Wander(1f));
        patrol = new Patrol(1f);
        b.addBehavior(patrol);


        target = new Body(new PVector(), new PVector(), 1f, 0.2f, p.color(255, 0, 0));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        Eye eye = new Eye(b, allTrackingBodies);
        b.setEye(eye);

    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);

        b.applyBehavior(index, dt);

        b.display(p, plt);

        if(this.index == 1) drawPatrolPoint(p);
    }

    public void drawPatrolPoint(PApplet p){
        PVector nextPoint = patrol.getNextPoint();

        if(nextPoint != null){
            float[] pixelCoord = plt.getPixelCoord(nextPoint.x, nextPoint.y);
            
            p.pushStyle();
            p.fill(255, 0, 0, 200);
            p.ellipse(pixelCoord[0], pixelCoord[1], 10, 10);
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
        PVector worldPos = new PVector((float) ww[0], (float) ww[1]);

        if (patrol.isDefiningPath()){
            patrol.addPointToPath(worldPos);
        }
        
    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == '1') {
            this.index = 0;
            System.out.println("Behavior: Wander" );
        } else if (p.key == '2') {
            this.index = 1;
            System.out.println("Behavior: Patrol" );
        } else if (p.key == 'A' || p.key == 'a') {
            if (this.index == 1){
                patrol.startPath();
            }
        } else if (p.key == ' '){
            if (patrol.isDefiningPath()){
                patrol.finishPath();
            }
        }

    }
}