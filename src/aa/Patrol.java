package aa;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;

public class Patrol extends Behavior{
    private List<PVector> patrolPoints;
    private int targetIndex;
    private float arrivalRadius;


    public Patrol (float weight){
        super(weight);
        this.patrolPoints = new ArrayList<PVector>();
        this.targetIndex = 0;
        this.arrivalRadius = 1.0f;
        definePath(defaultPoints());
    }


    public void definePath(List<PVector> newPath){
        this.patrolPoints = new ArrayList<>(newPath);
        this.targetIndex = 0;
    }


    private List<PVector> defaultPoints() {
        //Patrol faz um circulo com numpoints pontos por default
        List<PVector> defaultP = new ArrayList<>();
        PVector center = new PVector(0,0);
        int numPoints = 4;
        float angleIncrement = (float) (2 * Math.PI / numPoints);
        
        for (int i = 0; i < numPoints; i++) {
            float angle = i * angleIncrement;
            float x = center.x + 15 * (float) Math.cos(angle);
            float y = center.y + 15 * (float) Math.sin(angle);
            defaultP.add(new PVector(x, y));
        }
        
        return defaultP;
    }

    @Override
    public PVector getDesiredVelocity(Boid me){

        
        return PVector.sub(target, me.getPos());

    }
    
}
