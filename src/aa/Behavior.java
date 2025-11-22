package aa;

public abstract class Behavior implements IBehavior{
    protected float weight;

    public Behavior(float weight){
        this.weight = weight;
    }

    
    public void setWeight(float weight){
        this.weight = weight;
    }

    public float getWeight(){
        return this.weight;
    }
}
