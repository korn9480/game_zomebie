import java.util.Random;   
//  คลาสลูก(Zombie)
//     คลาสซ้อมบี้
//       สุ่มเกิด (Random)
//       method 
//       เดินไปหาผู้เล่น (walk)

public class Zombie extends Character{
    public Zombie(int hp, int dmg, int walk, String body) {
        super(hp, dmg, walk, body);
        this.body="\033[0;32m" +body+  "\033[0m";
        randomBorn();
    }

    // สุ่มดาเมจ
    public void randowDmg(){
        Random random = new Random();
        this.dmg= random.nextInt(5);    
    }
    
    // สุ่มจุดเกิด
    public void randomBorn(){ 
        Random random = new Random();
        int max = Main.map.length;
        while(true){
            int randomRow = random.nextInt(max);  
            int randomCol = random.nextInt(max);  
            if(Main.map[randomRow][randomCol]=="_"){ // 0 H _
                this.row=randomRow;
                this.col=randomCol;// 0 0
                Main.map[this.row][this.col]=this.body;
                break;
            }
        }
    }
    // หาว่าจะให้เคลื่อนที่ไปทางไหน - หรือ +
    public int searchDirection(int zombie,int player){
        // _ P _ Z
        if (player<zombie) return -1;
        // _ Z _ P
        else return 1;
    }
  // - cheeckDirection() : void หาทิศทางที่จะเดิน
    public Character cheeckDirection(int playerRow,int playerCol){
        int resultRow = Math.abs(playerRow-this.row);
        int resultCol = Math.abs(playerCol-this.col);

        int moveRow=0;
        int moveCol=0;
        // ถ้า  เเถว ตรงกับ ผู้เล่นให้เพิ่มค่าเเถว
        if(this.row==playerRow){
            // System.out.println("== row");
            moveCol =searchDirection(this.col,playerCol);
        }
        // ถ้า คอลัม ตรบผู้เล่นให้เพิ่มค่าคอลัม
        else if (this.col==playerCol){
            moveRow =searchDirection(this.row,playerRow);
        }
        else if (resultRow<resultCol){
            moveRow= searchDirection(this.row,playerRow);
        }
        else{
            moveCol = searchDirection(this.col,playerCol);
        } 
        return new Character(moveRow,moveCol);
    }
  // - cheeckMove() : void เช็คว่าเป็นช่องว่างไม
    public boolean cheeckMove(int row,int col,String odj){
        return Main.map[this.row+row][this.col+col]==odj;  //******
    }
    
  // + Move() : void ซ้อมบี้เดิน
    public void zombieMove(int row,int col,Player P){
        if (cheeckMove(row,col,"_")){
            Main.map[this.row][this.col]="_";
            this.row+=row;
            this.col+=col;    
            Main.map[this.row][this.col]=body;
        }
        else if (cheeckMove(row,col,P.body)){
            randowDmg();// 1-5
            int Hp_player= P.getHp();
            P.setHp(Hp_player - this.dmg);
        }
    }
  // + over() : Boolean 
    public boolean over(){
        return Main.past_map[this.row][this.col]!=this.body;
    }
    public String toString(){
        return "row = "+row+", col = "+col;
    }
} 


