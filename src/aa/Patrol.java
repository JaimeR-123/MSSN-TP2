package aa;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;

public class Patrol extends Behavior{
    private List<PVector> patrolPoints;
    private List<PVector> tempPoints;
    private int targetIndex;
    private float arrivalRadius;
    private boolean definingPath;


    public Patrol (float weight){
        super(weight);
        this.patrolPoints = new ArrayList<PVector>();
        this.tempPoints = new ArrayList<PVector>();
        this.targetIndex = 0;
        this.arrivalRadius = 1.0f;
        this.definingPath = false;
        setPath(defaultPoints());
    }


    public void setPath(List<PVector> newPath){
        this.patrolPoints = new ArrayList<>(newPath);
        this.targetIndex = 0;
    }


    public List<PVector> defaultPoints() {
        //Patrol faz um circulo com numpoints pontos por default
        List<PVector> defaultP = new ArrayList<>();
        PVector center = new PVector(0,0);
        int numPoints = 8;
        float angleIncrement = (float) (2 * Math.PI / numPoints);
        
        for (int i = 0; i < numPoints; i++) {
            float angle = i * angleIncrement;
            float x = center.x + 6 * (float) Math.cos(angle);
            float y = center.y + 6 * (float) Math.sin(angle);
            defaultP.add(new PVector(x, y));
        }
        
        return defaultP;
    }

    public void startPath(){
        this.definingPath = true;
        this.tempPoints.clear();
        System.out.println("Definicao de pontos iniciada");
    }
    public void addPointToPath(PVector point){
        if (definingPath) tempPoints.add(point.copy());
        System.out.println("Ponto " + tempPoints.size() + " adicionado");
    }
    public void finishPath(){
        if (definingPath && !tempPoints.isEmpty()){
            setPath(tempPoints);
            this.definingPath = false;
            System.out.println("Patrol Path definido com " + tempPoints.size() + " pontos");
        } else if(tempPoints.isEmpty()){
            System.out.println("Nao adicionou pontos");
            this.definingPath = false;
        }
        
    }

    public PVector getNextPoint(){
        return patrolPoints.get(targetIndex).copy();
    }

    @Override
    public PVector getDesiredVelocity(Boid me){
        PVector currentTarget = patrolPoints.get(targetIndex);
        PVector toTarget = PVector.sub(currentTarget, me.getPos());

         if (toTarget.mag() < arrivalRadius) {
            targetIndex = (targetIndex + 1) % patrolPoints.size();
            currentTarget = patrolPoints.get(targetIndex);
            toTarget = PVector.sub(currentTarget, me.getPos());
        }
        return toTarget;
    }

    public boolean isDefiningPath(){
        return definingPath;
    }
}
