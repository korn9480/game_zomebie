package item;
public class Weapon{
    private String name;
    private int phase;// ระยะการยิง

    public Weapon(String name,int phase){
        this.name=name;
        this.phase=phase;
    }
    public String getName(){
        return name;
    }
    public int getPhase(){
        return phase;
    }
    public String toString(){
        return "weapon : "+name+" -> phase : "+phase;
    }
}