public class Character {

    /*  คลาสเเม่ (Character)
        เลือด ตี จำนวนการเดิน ตำเเหน่ง รูปร่าง(อักษรที่เเทนซอมบี้ ผู้เล่น)
        */
    protected int hp;//เลือด
    protected int dmg;
    protected int walk;//จำนวนการเดิน
    protected String body;// รูปร่าง(อักษรที่เเทนซอมบี้ ผู้เล่น)
    // ตำเเหน่ง
    protected int row;// เเถว เเนวนอน
    protected int col;// คอคัม เเนวตั้ง
    // // constructor ตอนสร้าง ผู้เล่น
    public Character(int hp,int walk,int dmg,String body,int row,int col){
        this.hp= hp;
        this.walk=walk;
        this.body=body;
        this.row=row;
        this.col=col;
        this.dmg=dmg;
    }
    // constructor สร้างซอมบี้
    public Character(int hp,int dmg,int walk,String body){
        this.hp=hp;
        this.dmg=dmg;
        this.walk=walk;
        this.body=body;
    }
    // result zombie move
    public Character(int row,int col){
        this.row=row;
        this.col=col;
    }    
    
}
