// ConsoleColors
import item.Weapon;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class Player extends Character{
    // ระบุ ชื่อ อาวุธ
    private String name;
    private Weapon weapon = new Weapon("", 0);
    private int totalKill;// จำนวน kill สะสม
    // private Weapon listWeapon = new Weapon[];
    private Weapon list_weapon[] = new Weapon[]{
        // ปีน
        new Weapon("gun",Main.map.length),
        // ดาบ
        new Weapon("sword",2),
        // เหล็ก  
        new Weapon("iron ingot",2),
        //มีด
        new Weapon("knife",1),
        //หมัด
        new Weapon("punch",1)
    };
    // constructor
    public Player(int hp,int dmg,int walk,String body,int row,int col,String name){
        super(hp,walk,dmg,body,row,col);
        this.name = name;
        this.body= "\033[0;33m" +body+  "\033[0m";
        //          codeสีต่างๆ   ข้อความ   codeปิดสี
        this.totalKill = 0;
    }
    // getNmae
    public String getName(){
        return name;
    }
    // getWeapon 
    public Weapon getWeapon(){
        return weapon;
    }
    //getHP
    public int getHp(){
        return hp;
    }  
    // getdmg
    public int getDmg() {
        return dmg;
    }
    // getwalk
    public int getWalk() {
        return walk;
    }
    // getTotalKill
    public int getTotalKill(){
        return totalKill;
    }
    public void setHp(int hp){
        this.hp=hp;
    }
    // random_weapon()
    public void random_weapon(){
        Random rd = new Random();
        int index = rd.nextInt(5);
        weapon = list_weapon[index];
    }
    // เเสดงทิศทางการยิง
    private String direction_shoot(int row ,int col){
        String direction;
        if (row>0) direction="v";
        else if (row<0) direction="^";
        else if (col>0) direction=">";
        else direction="<";
        return direction;
    }
  // + shoot() : void ยิงซอมบี้
    public void shoot(int row,int col){ // 0 -1 +1
        int shoot_row=this.row;
        int shoot_col=this.col;
        for (int i=0;i<weapon.getPhase();i++){
                shoot_row+=row;
                shoot_col+=col;
                try{
                    String direction= direction_shoot(row,col);
                    Main.map[shoot_row][shoot_col]=direction;
                    Main.clearMap();
                    Main.showMap(this);  
                    TimeUnit.SECONDS.sleep(1);
                    Main.map[shoot_row][shoot_col]="_";
                }catch(Exception e) {
                    Main.clearMap();
                    break;
                }
        }
    }
  // - checkMove() : boolean ตรวจสอบทางที่จะเดิน เป็น _ ไม
    public boolean checkMove(int row,int col){//  ค่า + -
        try{
            return Main.map[this.row+row][this.col+col]=="_";
        }catch(Exception e){
            return false; 
        }
    }
  // + playerMove() : void เดินเเบบ1ช่อง
    public void playerMove(int row,int col){// a s w d 
        if (checkMove(row,col)){
            // System.out.println(">>>");
            Main.map[this.row][this.col]="_";
            this.row+=row;
            this.col+=col;
            Main.map[this.row][this.col]=body;
        }
    }
  // + playerMove(int box) : void เดินเเบบหลายช่อง 1-3
    public void playerMove(int row,int col,int pace){
        // กัน จำนวนก้าวเกิน pace = 5
        if (pace>3){
            pace=3;
        }
        for (int i=0;i<pace;i++){
            if (checkMove(row,col)){
                Main.map[this.row][this.col]="_";
                this.row+=row;
                this.col+=col;
                Main.map[this.row][this.col]=body;
                Main.clearMap();
                Main.showMap(this);
                try{
                    TimeUnit.SECONDS.sleep(1);;
                }catch(Exception e){}
            }
        }
    }
  // + sumMyKill(int kill) : void รวมจำนวนkillต่อรอบ
    public void setTotalKill(int kill){// 10 + 3=kill
        totalKill = totalKill + kill;
    }

    // get control
    public Character playerWalk(String control){
        int row=0;// เดินซ้ายขวา
        int col=0;//ขึ้นลง 
        // เดินบน
        if (control.equals("w")){
            row= -1;
        }
        //ลง
        else if (control.equals("s")){
            row = +1;
        // ซ้าย
        }else if (control.equals("a")){
            col = -1;
        // ขวา
        }else if (control.equals("d")){
            col = +1;
        }
        // int row,int col
        return new Character(row, col);
    }
}
