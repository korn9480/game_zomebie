import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

class Main {
    // + current_map : char [][] static เเผนที่ปัจจุบัน 
    public static String[][] map;
    public static String[][] past_map;
    public static ArrayList<Zombie> zombies =new ArrayList<Zombie>();
    
  // + past_map : char [][] static เเผนที่รอบที่เเล้ว
    public static void buildMap(int size){
  // + list_zombie : class Zombie ซอมบี้ทั้งหมด
        // สร้างเเผนที่
        map = new String[size][size];
        for (int i=0;i<map.length;i++){
            for (int j=0;j<map[0].length;j++){
                map[i][j]="_";
            }
        }
    }
    // เเสดงเเผนที่
    public static void showMap(Player P){
        for (int i=0;i<map.length;i++){
            for (String j:map[i]){
                System.out.print(j+"  ");
            }
            if (i==0) System.out.print("name : "+P.getName());
            else if (i==1) System.out.print("HP : "+P.getHp());
            else if (i==2) System.out.print(P.getWeapon());
            else if (i==3) System.out.print("kill : "+P.getTotalKill());
            System.out.println();
        }
    }
    //cleatMap
    public static void drawCharacter(Zombie[] zombies,Player P){
        // วาดผู้เล่น
        map[P.row][P.col]=P.body;
        // วาดซอมบี้หลายตัว
        for(Zombie z : zombies){
            map[z.row][z.col]=z.body;
        }
    }
    // + playerPlay() : void ผู้เล่นเดินยิงพวกนี้
    public static void builZombie(int number){
        for(int i=0;i<number;i++){
            Zombie z = new Zombie(1,1,1,"0");
            zombies.add(z);// .append()
        }
    }
    public static void clearMap(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    
    public static void playerPlayMove(Scanner kbd,Player P){
        System.out.print("move : ");
        String control =kbd.next();
        control = control.toLowerCase();
        if (!control.equals("0")){
            if (control.length()==1){
                Character walk = P.playerWalk(control);
                P.playerMove(walk.row,walk.col);
            }
            else if (control.length()==2){
                int num_walk_int;
                try{
                    String num_walk =control.substring(1);
                    num_walk_int= Integer.parseInt(num_walk);
                    control = control.substring(0,1);
                }catch(Exception e){
                    String num_walk = control.substring(0,1);
                    num_walk_int= Integer.parseInt(num_walk);
                    control =control.substring(1);
                }
                Character walk = P.playerWalk(control);
                P.playerMove(walk.row,walk.col,num_walk_int);
            }
        }
    }
    public static void startGame(Scanner kbd) {
        clearMap();
        Player P = loadMap(kbd);
        clearMap();
        while(P.getHp()>0 && zombies.size()>0){
          P.random_weapon();
          showMap(P);
          playerPlayMove(kbd,P);
          clearMap();
          showMap(P);
          System.out.print("shoot : ");
          String control =kbd.next();
          //ไม่ยิง ใส่ 0
          control = control.substring(0,1);
          if (!control.equals("0")){
                Character walk=P.playerWalk(control.toLowerCase());
                P.shoot(walk.row,walk.col);
          }
          showMap(P);
          past_map=map;
          // เก็บซ้อมก่อนที่จะถูกลบ
          int number_zombie = zombiesPlay(P);
          countKill(P, number_zombie);
          clearMap();
        }
        if (zombies.isEmpty()){
            System.out.println("you win!!!");
        }
        else if (!zombies.isEmpty()) System.out.println("you lose!!!");
        System.out.println("name : "+P.getName()+", kill : "+P.getTotalKill());
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(Exception e){}

    }
    public static Player loadMap(Scanner kbd) {
        buildMap(9);
        // สร้าง ผู้เล่น
        System.out.print("name : ");
        String player_name = kbd.next();
        System.out.print("body : ");
        String body = kbd.next();
        Player P = new Player(5,1,1,body,4,4,player_name);
        map[P.row][P.col]=P.body;
        builZombie(10);
        return P;
    }
    public static void countKill(Player P, int number_zombie) {
        int kill = number_zombie - zombies.size();
        P.setTotalKill(kill);
    }
    // + ZombiePlay(Zombie[] list_zombie) : ซอมบี้ทั้งหมดเดิน
    public static int zombiesPlay(Player P) {
        int number_zombie = zombies.size();
        for (int i=0;i<zombies.size();i++){
            Zombie z = zombies.get(i);
            boolean over = z.over();
            if (!over){
                Character zombie_walk = z.cheeckDirection(P.row,P.col);
                z.zombieMove(zombie_walk.row,zombie_walk.col,P);
            }
            else if(over){
                zombies.remove(i);// 6
                i--;  
            }
        }
        return number_zombie;
    }
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        String menu;
        do{
            startGame(kbd);
            System.out.println("do you play again ?");
            System.out.println("1.play\n2.not play");
            System.out.print("Enter your menu : ");
            menu = kbd.next();
        }while(menu.equals("1"));
    }
}