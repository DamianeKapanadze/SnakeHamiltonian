import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    static final int Screen_Width = 600;
    static final int Screen_Height = 600;
    static final int Unit_Size = 25;
    static final int Game_Untis = Screen_Width*Screen_Height/Unit_Size;
    static final int Delay = 1;
    final int x[] = new int[Game_Untis];
    final int y[] = new int[Game_Untis];
    int[][]  path = hamiltonianCycle(Screen_Width/Unit_Size, Screen_Height/Unit_Size);
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX, appleY;
    char direction = 'd';
    boolean running = false;
    boolean ai = true;
    Timer timer;
    Random random;
    public static int[][] hamiltonianCycle(int width, int height){
        int[][] x = new int[height+2][width+2];
        int area = height*width;
        height+=2;
        width += 2;
        int i = 2;
        int j = 1;
        for(int k = 0; k < area; k++){
            x[i][j] = k;
            
            if(j % 2 == 1 && i == height-2 && j < width-2) j++;
            else if(j % 2 == 0 && i == 2 && j < width-2) j++;

            else if(i == 2 && j == width-2)  i--;
            else if(i == 1) j--;
            
            else if(j % 2 == 1 && i < height-2) i++;
            else if(j % 2 == 0 && i > 2) i--;
        }
        return x;
    }
    //form this matrix, there was error of out of bounds, so I added frame of 0s around it
    /*public static int[][] hamiltonianCycle(int width, int height){
        int[][] x = new int[height + 2][width + 2];
        int i = 1;
        int j = 0;
        for(int k = 0; k < height * width; k++){
            x[i][j] = k;
            
            if(j % 2 == 0 && i == height-1 && j < width-1) j++;
            else if(j % 2 == 1 && i == 1 && j < width-1) j++;

            else if(i == 1 && j == width-1)  i--;
            else if(i == 0) j--;
            
            else if(j % 2 == 0 && i<height-1) i++;
            else if(j % 2 == 1 && i > 1) i--;
        }
        return x;
    }*/

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        bodyParts = 6;
        applesEaten = 0;
        direction = 'd';
        x[0] = 0;
        y[0] = 25;
        newApple();
        running = true;
		timer = new Timer(Delay,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            /*
            for(int i = 0; i < Screen_Height/Unit_Size; i++){
                g.drawLine(i*Unit_Size, 0, i*Unit_Size, Screen_Height);
                g.drawLine(0, i*Unit_Size, Screen_Width, i*Unit_Size);
            }*/

            g.setColor(Color.red);
            g.fillRect(appleX, appleY, Unit_Size, Unit_Size);

            for(int i = 0; i < bodyParts; i++){
                if(i == 0){
                    g.setColor(Color.GREEN);
                }else{
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
            }
            //write score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+ applesEaten, (Screen_Width - metrics.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());
        }else{
            gameOver(g);
        }
    }

    public void newApple(){
        appleX = random.nextInt((int)(Screen_Width/Unit_Size)) * Unit_Size;
        appleY = random.nextInt((int)(Screen_Height/Unit_Size)) * Unit_Size;
        for(int i = bodyParts; i > 0; i--){
            if(appleX == x[i] && appleY == y[i]){
                newApple();
            }   
        }
    }

    public void move(){
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){
            case 'u':
                y[0] = y[0] - Unit_Size;
                break;
            case 'd':
                y[0] = y[0] + Unit_Size;
                break;
            case 'r':
                x[0] = x[0] + Unit_Size;
                break;
            case 'l':
                x[0] = x[0] - Unit_Size;
                break;
        }
    }
    
    public void moveAI(){
        
        //int SnekX = x[0]/Unit_Size + 1;
        //int SnekY = y[0]/Unit_Size + 1;
        
        //Debugging
/*
        for (int i = 0; i < 2+Screen_Height/Unit_Size; i++) {
            for (int j = 0; j < 2+Screen_Width/Unit_Size; j++) {
                if(i == SnekY && j == SnekX)
                    System.out.print("H ");

                else System.out.print(path[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println(SnekY + " " + SnekX + " " + direction);
*/
        //System.out.println(path[y[0]/Unit_Size][x[0]/Unit_Size] + " " + path[y[0]/Unit_Size][x[0]/Unit_Size+1]);

        
        
        
        //System.out.println(direction);
        //System.out.println(path[y[0]/Unit_Size][x[0]/Unit_Size] + 1 +"    " + path[y[0]/Unit_Size][x[0]/Unit_Size + 1]);
        
        // miyevi hamiltoninan path-s
/*      ///////////////// mokled es moshaobs cudad \\\\\\\\\\\\\\\\\\\\\\\\\
        if (path[SnekY][SnekX] + 1 == path[SnekY - 1][SnekX] && direction != 'd'){
            direction = 'u';
        }
        else if (path[SnekY][SnekX] + 1 == path[SnekY + 1][SnekX] && direction != 'u'){
            direction = 'd';
        }
        else if (path[SnekY][SnekX] + 1 == path[SnekY][SnekX + 1] && direction != 'l'){
            direction = 'r';
        }
        else if (path[SnekY][SnekX] + 1 == path[SnekY][SnekX - 1] && direction !='r'){
            direction = 'l';
        }else if(path[SnekY][SnekX] == Screen_Width/Unit_Size * Screen_Height/Unit_Size -1 && direction !='u') direction = 'd';
        


        char newdirection = ' ';
        if(path[appleY/Unit_Size][appleX/Unit_Size] > path[SnekY][SnekX] && path[SnekY][SnekX] > 0){
            if(path[appleY/Unit_Size][appleX/Unit_Size] > path[SnekY][SnekX+1] && SnekX < Screen_Width/Unit_Size && direction != 'l')
            newdirection = 'r';
        }
        else if(direction == 'd' && SnekY == 23 && SnekX % 2 == 0){
            newdirection = 'r';
        }
        else if(SnekY == 23 && SnekX % 2 ==1 && direction != 'd'){
            newdirection = 'u';
            System.out.println(SnekY + "  " + SnekX);
        } 
        
        else if(path[y[bodyParts-1]/Unit_Size][x[bodyParts-1]/Unit_Size] > path[appleY/Unit_Size][appleX/Unit_Size] && bodyParts < (Game_Untis/Unit_Size)/2 ){
            if(path[SnekY][SnekX] < Game_Untis/Unit_Size - Screen_Width/Unit_Size){
                if(path[appleY/Unit_Size][appleX/Unit_Size] < path[SnekY][SnekX] && direction !='d') newdirection = 'u';
                //else if(direction == 'd') direction = 'r';
            }
        }
        if( newdirection == 'l' && direction != 'r') direction = newdirection;
        else if( newdirection == 'r' && direction != 'l') direction = newdirection;
        else if( newdirection == 'u' && direction != 'd') direction = newdirection;
        else if( newdirection == 'd' && direction != 'u') direction = newdirection;
        */
        /*
        if(y[0]/25 == 0) direction = 'l';
        else if(y[0]/25 == 22 && (x[0]/25) % 2 == 0 ){
            direction = 'r';
            System.out.println(SnekY + "  " + SnekX);
        }
        */
        //System.out.println(path[y[y.length-1]/Unit_Size][x[x.length-1]/Unit_Size]);
///////////////////////////////////////////////////////////  aq mtavrdeba \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\



///////////////// aq shevamotmot samive mxare da magis mixedvit mivigot gadawkvetileba mowrat gza tu ara \\\\\\\\\\\\\\\\\\
/* 
    char newdir = direction;
    int board_size = (Screen_Width/Unit_Size) * (Screen_Height/Unit_Size);
    int head_x = x[0]/Unit_Size + 1;
    int head_y = y[0]/Unit_Size + 1;
    int pre_x = x[1]/Unit_Size + 1;
    int pre_y = y[1]/Unit_Size + 1;
    int tail_x = x[bodyParts-1]/Unit_Size + 1;
    int tail_y = y[bodyParts-1]/Unit_Size + 1;
    int apple_val = path[appleY/Unit_Size +1][appleX/Unit_Size +1];
    int tail_val = path[y[bodyParts-1]/Unit_Size + 1][x[bodyParts-1]/Unit_Size + 1];
    int val_r = path[y[0]/Unit_Size + 1][x[0]/Unit_Size + 2];
    int val_l = path[y[0]/Unit_Size + 1][x[0]/Unit_Size];
    int val_u = path[y[0]/Unit_Size + 2][x[0]/Unit_Size + 1];
    int val_d = path[y[0]/Unit_Size][x[0]/Unit_Size + 1];
    int bestDist = 95063713;
    */
    /*        
    ///////////////////// IO code mara ar gamodis \\\\\\\\\\\\
    if(direction != 'l' && head_x < Screen_Width/Unit_Size){// can go right
        int dist = (apple_val - val_r + board_size) % board_size;
        if(dist < bestDist){
            newdir = 'r';
            bestDist = dist;
        }
    }
    if(direction != 'r' && head_y > 1){// can go left
        int dist = (apple_val - val_l + board_size) % board_size;
        if(dist < bestDist){
            newdir = 'l';
            bestDist = dist;
        }
    }
    if(direction != 'u' && head_y > 1){// can go down
        int dist = (apple_val - val_d + board_size) % board_size;
        if(dist < bestDist){
            newdir = 'd';
            bestDist = dist;
        }
    }
    if(direction != 'd' && head_y < Screen_Height/Unit_Size){// can go up
        int dist = (apple_val - val_u + board_size) % board_size;
        if(dist < bestDist){
            newdir = 'u';
            bestDist = dist;
        }
    }   


    System.out.println(newdir + " " + bestDist + " ");

////////////////////////////////// BRUHHHHHHHHHHHHHHHH  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

        char newdir = direction;
        int SnekX = x[0]/Unit_Size + 1;
        int SnekY = y[0]/Unit_Size + 1;
        int tail_x = x[bodyParts-1]/Unit_Size + 1;
        int tail_y = y[bodyParts-1]/Unit_Size + 1;
        int apple_x = appleX/Unit_Size +1;
        int apple_y = appleY/Unit_Size +1;
        int board_size = (Screen_Width/Unit_Size) * (Screen_Height/Unit_Size);
        int tail_val = path[y[bodyParts-1]/Unit_Size + 1][x[bodyParts-1]/Unit_Size + 1];
/* 
        path[SnekY][SnekX+1] 
        path[SnekY][SnekX-1] 
        path[SnekY+1][SnekX] 
        path[SnekY-1][SnekX]
*/      if(x[0]/25 == 23 && y[0]/25 == 0) newdir = 'l';
        else if(path[SnekY][SnekX] < path[apple_y][apple_x]){
            if(direction == 'r'){
                if(path[SnekY][SnekX+1] > path[SnekY+1][SnekX] && path[SnekY][SnekX+1] > path[SnekY-1][SnekX] && path[SnekY][SnekX+1] <= path[apple_y][apple_x]){
                    newdir = 'r';
                }else if(path[SnekY+1][SnekX] > path[SnekY][SnekX+1] && path[SnekY+1][SnekX] > path[SnekY-1][SnekX] && path[SnekY+1][SnekX] <= path[apple_y][apple_x]){
                    newdir = 'u';
                }else if(path[SnekY-1][SnekX] > path[SnekY][SnekX+1] && path[SnekY-1][SnekX] > path[SnekY+1][SnekX] && path[SnekY-1][SnekX] <= path[apple_y][apple_x]){
                    newdir = 'd';
                }if(path[SnekY][SnekX-1] > path[SnekY+1][SnekX] && path[SnekY][SnekX-1] > path[SnekY-1][SnekX] && path[SnekY][SnekX-1] <= path[apple_y][apple_x] ){
                    newdir = 'l';
                }else{
                    newdir = miyeviHamiltons(SnekY, SnekX);
                }

            }else if(direction == 'l'){
                if(path[SnekY][SnekX-1] > path[SnekY+1][SnekX] && path[SnekY][SnekX-1] > path[SnekY-1][SnekX] && path[SnekY][SnekX-1] <= path[apple_y][apple_x]){
                    newdir = 'l';
                }else if(path[SnekY-1][SnekX] > path[SnekY][SnekX-1] && path[SnekY+1][SnekX] > path[SnekY-1][SnekX] && path[SnekY+1][SnekX] <= path[apple_y][apple_x]){
                    newdir = 'u';
                }else if(path[SnekY-1][SnekX] > path[SnekY][SnekX-1] && path[SnekY-1][SnekX] > path[SnekY+1][SnekX] && path[SnekY-1][SnekX] <= path[apple_y][apple_x]){
                    newdir = 'd';
                }else{
                    newdir = miyeviHamiltons(SnekY, SnekX);
                }

            }else if(direction == 'u'){
                if(path[SnekY][SnekX+1] > path[SnekY+1][SnekX] && path[SnekY][SnekX+1] > path[SnekY-1][SnekX] && path[SnekY][SnekX+1] <= path[apple_y][apple_x]){
                    newdir = 'r';
                }else if(path[SnekY+1][SnekX] > path[SnekY][SnekX+1] && path[SnekY+1][SnekX] > path[SnekY-1][SnekX] && path[SnekY+1][SnekX] <= path[apple_y][apple_x] && path[SnekY+1][SnekX] !=0){
                    newdir = 'u';
                    System.out.println("zangis shlangi");
                }else if(path[SnekY][SnekX-1] > path[SnekY+1][SnekX] && path[SnekY][SnekX-1] > path[SnekY-1][SnekX] && path[SnekY][SnekX-1] <= path[apple_y][apple_x]){
                    newdir = 'l';
                }else{
                    newdir = miyeviHamiltons(SnekY, SnekX);
                }

            }else if(direction == 'd'){
                if(path[SnekY][SnekX+1] > path[SnekY+1][SnekX] && path[SnekY][SnekX+1] > path[SnekY-1][SnekX] && path[SnekY][SnekX+1] <= path[apple_y][apple_x]){
                    newdir = 'r';
                }else if(path[SnekY-1][SnekX] > path[SnekY][SnekX-1] && path[SnekY-1][SnekX] > path[SnekY+1][SnekX] && path[SnekY-1][SnekX] <= path[apple_y][apple_x]){
                    newdir = 'd';
                }else if(path[SnekY][SnekX-1] > path[SnekY+1][SnekX] && path[SnekY][SnekX-1] > path[SnekY-1][SnekX] && path[SnekY][SnekX-1] <= path[apple_y][apple_x]){
                    newdir = 'l';
                } else{
                    newdir = miyeviHamiltons(SnekY, SnekX);
                }
            }
            if(checkNextMove(newdir, SnekY, SnekX, tail_x, tail_y)){
                newdir = miyeviHamiltons(SnekY, SnekX);
            }
        }else if(path[tail_y][tail_x] > path[apple_y][apple_x]){
            if(direction != 'd' && SnekY > 1) newdir = 'u';
            else newdir = miyeviHamiltons(SnekY, SnekX);
        }else newdir = miyeviHamiltons(SnekY, SnekX);

        //if((path[SnekY][SnekX] - tail_val + board_size ) % board_size > bodyParts + 10) direction = miyeviHamiltons(SnekY, SnekX);
        direction = newdir;
        //System.out.print(direction + " ");
        move();
    }

    public boolean checkNextMove(char dir, int SnekY, int SnekX, int tail_x, int tail_y){
        if(dir == 'r'){
            if(path[SnekY][SnekX+1] > path[tail_y][tail_x]) return false;
            else return true;
        }else if(dir == 'u'){
            if(path[SnekY+1][SnekX] > path[tail_y][tail_x]) return false;
            else return true;
        }else if(dir == 'l'){
            if(path[SnekY][SnekX-1] > path[tail_y][tail_x]) return false;
            else return true;
        }else if(dir == 'd'){
            if(path[SnekY-1][SnekX] > path[tail_y][tail_x]) return false;
            else return true;
        }

        return false;
    }



    public char miyeviHamiltons(int SnekY, int SnekX){
        if (path[SnekY][SnekX] + 1 == path[SnekY - 1][SnekX] && direction != 'd'){
            return 'u';
        }
        else if (path[SnekY][SnekX] + 1 == path[SnekY + 1][SnekX] && direction != 'u'){
            return 'd';
        }
        else if (path[SnekY][SnekX] + 1 == path[SnekY][SnekX + 1] && direction != 'l'){
            return 'r';
        }
        else if (path[SnekY][SnekX] + 1 == path[SnekY][SnekX - 1] && direction !='r'){
            return 'l';
        }else if(path[SnekY][SnekX] == Screen_Width/Unit_Size * Screen_Height/Unit_Size -1 && direction !='u') return 'd';
        else return 'F'; // F for Fucked
    }


    public void checkApples(){
        if(x[0] == appleX && y[0] == appleY){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision(){
        // check snake collision
        for(int i = bodyParts; i > 0; i--){
            if(x[0] == x[i] && y[0] == y[i]){
                running = false;
                //System.out.println("daetaka es shechvenebuli");
            }
        }

        // check left border
        if(x[0] < 0 || x[0] > Screen_Width || y[0] < 0 || y[0] > Screen_Height){
            running = false;
        }

        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        //Write Game over
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (Screen_Width - metrics.stringWidth("Game Over"))/2, Screen_Height/2);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+ applesEaten, (Screen_Width - metrics1.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());

        //add button to restart
        System.out.println(x[3]/25 + " " + y[3]/25);
        System.out.println(x[2]/25 + " " + y[2]/25);
        System.out.println(x[1]/25 + " " + y[1]/25);
        System.out.println(x[0]/25 + " " + y[0]/25);
        System.out.println(direction);
        System.out.println(applesEaten);
        
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running && ai){
            moveAI();
            checkApples();
            checkCollision();
        }
        else if(running && !ai){
            move();
            checkApples();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'r')
                        direction = 'l';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'l')
                        direction = 'r';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'u')
                        direction = 'd';
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'd')
                        direction = 'u';
                    break;
                case KeyEvent.VK_R:
                    if(!running){
                        timer.stop();
                        startGame();
                    }
            }
        }
    }
}