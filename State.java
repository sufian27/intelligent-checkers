import java.util.LinkedHashSet;
import java.util.Set;

public class State implements Comparable<State>{

    int[][] board;
    int no_of_whites;
    int no_of_blacks;
    int no_of_white_kings;
    int no_of_black_kings;
    int player; //player is what the user chooses
    int game_size;

    Set<State> children = new LinkedHashSet<State>();
    double h_val;
    int utility = -1;
    boolean visited;

    // 2 -- White || 1 -- Black
    int turn;

    //3 -- black king || 6 -- white king

    public State(int game_size, int turn) {
        
        this.turn = turn;
        this.game_size = game_size;
        no_of_blacks=2;
        no_of_whites=2;
        board = new int[game_size+4][game_size+4];
        for(int i=0; i<game_size+4;i++){
            for(int j=0;j<2;j++){
                board[i][j]=-9;
            }
        }
        for(int i=0; i<game_size+4;i++){
            for(int j=game_size+2;j<game_size+4;j++){
                board[i][j]=-9;
            }
        }
        for(int i=0; i<2;i++){
            for(int j=2;j<game_size+2;j++){
                board[i][j]=-9;
            }
        }
        for(int i=game_size+2; i<game_size+4;i++){
            for(int j=2;j<game_size+2;j++){
                board[i][j]=-9;
            }
        }
        board[0+2][1+2] = 1;
        board[0+2][3+2] = 1;
        board[game_size - 1+2][2+0] = 2;
        board[game_size - 1+2][2+2] = 2;
        

        if (game_size == 8) {
            no_of_blacks=12;
             no_of_whites=12;
            board[2+2][1+2] = 1;
            board[1+2][4+2] = 1;
            board[game_size - 3+2][0+2] = 2;
            board[game_size - 3+2][2+2] = 2;
            board[2+2][5+2] = 1;
            board[2+2][7+2] = 1;
            board[game_size - 3+2][4+2] = 2;
            board[game_size - 3+2][6+2] = 2;

            board[0+2][5+2] = 1;
            board[0+2][7+2] = 1;
            board[game_size - 1+2][4+2] = 2;
            board[game_size - 1+2][6+2] = 2;

            board[1+2][0+2] = 1;
            board[4][5] = 1;
            board[1+2][2+2] = 1;
            board[game_size - 2+2][1+2] = 2;
            board[game_size - 2+2][3+2] = 2;

            board[1+2][6+2] = 1;
            board[game_size - 2+2][5+2] = 2;
            board[game_size - 2+2][7+2] = 2;

        }

    }
    public int arrayCompare(int[][] a, int[][] other){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                if(other[i][j]!=a[i][j]){
                    return -1;
                }
            }
        }
        return 0;
    }

    @Override
    public int compareTo(State other)
    {
        for(int i=0;i<this.board.length;i++){
            for(int j=0;j<this.board[0].length;j++){
                if(other.board[i][j] > this.board[i][j]){
                    return -1;
                }
            }
        }

        return 0;
        
    }
    public void utlitiyChecker(State s){
        if(s.no_of_blacks > 0 && s.no_of_whites == 0){
            s.utility = 2;
            return;
        } 
        else if(s.no_of_blacks == 0 && s.no_of_whites > 0){
            s.utility =-2 ;
            return;
        }
    }
    public void possibleMoves() {
        //Calculate what moves are possible
        //Create new state for each move that is possible
        //Make the move on each of those new states
        //Add that state to the children list

        if(no_of_blacks > 0 && no_of_whites == 0){
            utility = -2;
            return;
        } 
        else if(no_of_blacks == 0 && no_of_whites > 0){
            utility = 2;
            return;
        }
            for(int i = 2; i < game_size+2; i++) {
                for (int j = 2; j < game_size+2; j++) {
                    if (board[i][j]==turn){
                        addStatesForPiece(i, j);
                    }
                    if (board[i][j] == turn*3) {
                        addStatesForKing(i, j);
                    }
                }
            }
        
    }

    public void addStatesForPiece(int initX, int initY) {
        if (turn == 2) {
            if (initY != 0+2) {

                if (board[initX - 1][initY - 1] == 1 || board[initX - 1][initY - 1] == 3){ 
                    State s = new State(game_size, 1);
                    deepArrayCopy(s, this);
                    try { 
                        int xTemp = initX;
                        int yTemp = initY;
                        while ((board[xTemp - 2][yTemp - 2] == 0 && (board[xTemp - 1][yTemp - 1] == 1 || board[xTemp - 1][yTemp - 1] == 3)) ||
                        (board[xTemp - 2][yTemp + 2] == 0 && (board[xTemp - 1][yTemp + 1] == 1 || board[xTemp - 1][yTemp + 1] == 3))){
                            if (board[xTemp - 2][yTemp - 2] == 0 && (board[xTemp - 1][yTemp - 1] == 1 || board[xTemp - 1][yTemp - 1] == 3)) { //left
                                s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                                s.board[xTemp - 1][yTemp - 1] = 0;
                                s.no_of_blacks--;
                                if (board[xTemp - 1][yTemp - 1] == 3) {
                                    s.no_of_black_kings--;
                                }
                                xTemp = xTemp - 2;
                                yTemp = yTemp - 2;
                            } else if (board[xTemp - 2][yTemp + 2] == 0 && (board[xTemp - 1][yTemp + 1] == 1 || board[xTemp - 1][yTemp + 1] == 3)) {
                                s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                                s.board[xTemp - 1][yTemp + 1] = 0;
                                s.no_of_blacks--;
                                if(board[xTemp - 1][yTemp + 1] == 3) {
                                    s.no_of_black_kings--;
                                }
                                xTemp = xTemp - 2;
                                yTemp = yTemp + 2;
                            } 
                        }
                    } catch (Exception e)  {

                    } finally {
                        if(arrayCompare(s.board, this.board)==-1){
                            children.add(s);
                        }                    }
                } else if (board[initX - 1][initY - 1] == 0){
                    State s = new State(game_size, 1);
                    deepArrayCopy(s, this);
                    s.makeMove(initX, initY, initX-1, initY-1);
                    if(arrayCompare(s.board, this.board)==-1){
                        children.add(s);
                    }               
                } 
            } 


            if(initY != game_size - 1+2){


                if (board[initX - 1][initY + 1] == 1 || board[initX - 1][initY + 1] == 3){

                    State s = new State(game_size, 1);
                    deepArrayCopy(s, this);
                    try {
                        int xTemp = initX;
                        int yTemp = initY;
                        while ((board[xTemp - 2][yTemp + 2] == 0 && ((board[xTemp - 1][yTemp + 1] == 1 || board[xTemp - 1][yTemp + 1] == 3)))  ||
                        (board[xTemp - 2][yTemp - 2] == 0 && (board[xTemp - 1][yTemp - 1] == 1 || board[xTemp - 1][yTemp - 1] == 3))) {
                            if (board[xTemp - 2][yTemp + 2] == 0 && (board[xTemp - 1][yTemp + 1] == 1 || board[xTemp - 1][yTemp + 1] == 3)) { //right
                                s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                                s.board[xTemp - 1][yTemp + 1] = 0;
                                s.no_of_blacks--;
                                if(board[xTemp - 1][yTemp + 1] == 3){
                                    s.no_of_black_kings--;
                                }
                                xTemp = xTemp - 2;
                                yTemp = yTemp + 2;
                            } else if (board[xTemp - 2][yTemp - 2] == 0 && (board[xTemp - 1][yTemp - 1] == 1 || board[xTemp - 1][yTemp - 1] == 3)) {
                                s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                                s.board[xTemp - 1][yTemp - 1] = 0;
                                s.no_of_blacks--;
                                if(board[xTemp - 1][yTemp - 1] == 3) {
                                    s.no_of_black_kings--;
                                }
                                xTemp = xTemp - 2;
                                yTemp = yTemp - 2;
                            } 
                        }
                    } catch (Exception e)  {

                    } finally {
                        if(arrayCompare(s.board, this.board)==-1){
                            children.add(s);
                        }                    
                    }
                } else if (board[initX - 1][initY + 1] == 0){
                    State s = new State(game_size, 1);
                    deepArrayCopy(s, this);
                    s.makeMove(initX, initY, initX-1, initY+1);
                    if(arrayCompare(s.board, this.board)==-1){
                        children.add(s);
                    }                
                } 
            }
        }

        if (turn == 1) {
            if(initY != 0+2){ 
                if (board[initX + 1][initY - 1] == 2 || board[initX + 1][initY - 1] == 6){
                    State s = new State(game_size, 2);
                    deepArrayCopy(s, this);
                    try {
                        int xTemp = initX;
                        int yTemp = initY;
                        while ((board[xTemp + 2][yTemp - 2] == 0 && (board[xTemp + 1][yTemp - 1] == 2 || board[xTemp + 1][yTemp - 1] == 6)) ||
                        (board[xTemp + 2][yTemp + 2] == 0 && (board[xTemp + 1][yTemp + 1] == 2 || board[xTemp + 1][yTemp + 1] == 6))){
                            if (board[xTemp + 2][yTemp- 2] == 0 && (board[xTemp + 1][yTemp - 1] == 2 || board[xTemp + 1][yTemp - 1] == 6)) {
                                s.makeMove(xTemp, yTemp, xTemp+2, yTemp-2);
                                s.board[xTemp + 1][yTemp - 1] = 0;
                                s.no_of_whites--;
                                if (board[xTemp + 1][yTemp - 1] == 6) {
                                    s.no_of_white_kings--;
                                }
                                xTemp = xTemp + 2;
                                yTemp = yTemp - 2;
                            } else if (board[xTemp + 2][yTemp + 2] == 0 && (board[xTemp + 1][yTemp + 1] == 2 || board[xTemp + 1][yTemp + 1] == 6)) {
                                s.makeMove(xTemp, yTemp, xTemp+2, yTemp+2);
                                s.board[xTemp + 1][yTemp + 1] = 0;
                                s.no_of_whites--;
                                if(board[xTemp + 1][yTemp + 1] == 6){
                                    s.no_of_white_kings--;
                                }
                                xTemp = xTemp + 2;
                                yTemp = yTemp + 2;
                            } 
                        }
                    } catch (Exception e)  {

                    } finally {
                        if(arrayCompare(s.board, this.board)==-1){
                            children.add(s);
                        }                    
                    }
                } else if (board[initX + 1][initY - 1] == 0){
                    State s = new State(game_size, 2);
                    deepArrayCopy(s, this);
                    s.makeMove(initX, initY, initX+1, initY-1);
                    if(arrayCompare(s.board, this.board)==-1){
                        children.add(s);
                    }                
                } 
             
            }

            if(initY != game_size - 1+2){ //going right
                if (board[initX + 1][initY + 1] == 2 || board[initX + 1][initY + 1] == 6) {
                    State s = new State(game_size, 2);
                    deepArrayCopy(s, this);
                    try {
                        int xTemp = initX;
                        int yTemp = initY;
                        while ((board[xTemp + 2][yTemp + 2] == 0 && (board[xTemp + 1][yTemp + 1] == 2 || board[xTemp + 1][yTemp + 1] == 6)) ||
                        (board[xTemp + 2][yTemp - 2] == 0 && (board[xTemp + 1][yTemp - 1] == 2 || board[xTemp + 1][yTemp - 1] == 6))){
                            if (board[xTemp + 2][yTemp + 2] == 0 && (board[xTemp + 1][yTemp + 1] == 2 || board[xTemp + 1][yTemp + 1] == 6)) {
                                s.makeMove(initX, initY, initX+2, initY+2);
                                s.board[initX + 1][initY + 1] = 0;
                                s.no_of_whites--;
                                if (board[xTemp + 1][yTemp + 1] == 6) {
                                    s.no_of_white_kings--;
                                }
                                xTemp = xTemp + 2;
                                yTemp = yTemp + 2;
                            } else if (board[xTemp + 2][yTemp - 2] == 0 && (board[xTemp + 1][yTemp - 1] == 2 || board[xTemp + 1][yTemp - 1] == 6)) {
                                s.makeMove(initX, initY, initX+2, initY-2);
                                s.board[initX + 1][initY - 1] = 0;
                                s.no_of_whites--;
                                if (board[xTemp + 1][yTemp - 1] == 6) {
                                    s.no_of_white_kings--;
                                }
                                xTemp = xTemp + 2;
                                yTemp = yTemp - 2;
                            } 
                        }
                    } catch (Exception ex) {

                    } finally {
                        if(arrayCompare(s.board, this.board)==-1){
                            children.add(s);
                        }                    
                    }
                } else if(board[initX + 1][initY + 1] == 0){
                    State s = new State(game_size, 2);
                    deepArrayCopy(s, this);
                    s.makeMove(initX, initY, initX+1, initY+1);
                    if(arrayCompare(s.board, this.board)==-1){
                        children.add(s);
                    }                
                }
  
            }
        }

    }

    public void addStatesForKing(int initX, int initY) {
        if (turn == 1) {
            if (board[initX-2][initY-2] == 0 && (board[initX - 1][initY - 1] == 2 || board[initX - 1][initY - 1] == 6)){  //look top left for kill
                State s = new State(game_size, 2);
                deepArrayCopy(s, this);
                try { 
                    int xTemp = initX;
                    int yTemp = initY;
                    while ((s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 2 || s.board[xTemp - 1][yTemp - 1] == 6)) ||
                    (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 2 || s.board[xTemp - 1][yTemp + 1] == 6)) || 
                    (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 2 || s.board[xTemp + 1][yTemp - 1] == 6)) ||
                    (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 2 || s.board[xTemp + 1][yTemp + 1] == 6))) {
                        if (s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 2 || s.board[xTemp - 1][yTemp - 1] == 6) ) { //left
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                            s.board[xTemp - 1][yTemp - 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp - 1][yTemp - 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 2 || s.board[xTemp - 1][yTemp + 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                            s.board[xTemp - 1][yTemp + 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp - 1][yTemp + 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 2 || s.board[xTemp + 1][yTemp - 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp-2);
                            s.board[xTemp + 1][yTemp - 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp + 1][yTemp - 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 2 || s.board[xTemp + 1][yTemp + 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp+2);
                            s.board[xTemp + 1][yTemp + 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp + 1][yTemp + 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp + 2;
                        }
                    }
                } catch (Exception e)  {
                    
                } finally {
                    children.add(s);
                }
            } else if (board[initX - 1][initY - 1] == 0){
                State s = new State(game_size, 2);
                deepArrayCopy(s, this);
                s.makeMove(initX, initY, initX-1, initY-1);
                children.add(s);
            } 
            
            if (board[initX-2][initY+2] == 0 && (board[initX - 1][initY + 1] == 2 || board[initX - 1][initY + 1] == 6)){ //look top right for kill
                State s = new State(game_size, 2);
                deepArrayCopy(s, this);
                try { 
                    int xTemp = initX;
                    int yTemp = initY;
                    while ((s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 2 || s.board[xTemp - 1][yTemp - 1] == 6)) ||
                    (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 2 || s.board[xTemp - 1][yTemp + 1] == 6)) || 
                    (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 2 || s.board[xTemp + 1][yTemp - 1] == 6)) ||
                    (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 2 || s.board[xTemp + 1][yTemp + 1] == 6))) {
                        if (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 2 || s.board[xTemp - 1][yTemp + 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                            s.board[xTemp - 1][yTemp + 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp - 1][yTemp + 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 2 || s.board[xTemp - 1][yTemp - 1] == 6) ) { //left
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                            s.board[xTemp - 1][yTemp - 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp - 1][yTemp - 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 2 || s.board[xTemp + 1][yTemp - 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp-2);
                            s.board[xTemp + 1][yTemp - 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp + 1][yTemp - 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 2 || s.board[xTemp + 1][yTemp + 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp+2);
                            s.board[xTemp + 1][yTemp + 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp + 1][yTemp + 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp + 2;
                        }
                    }
                } catch (Exception e)  {
                    
                } finally {
                    children.add(s);
                }
            } else if (board[initX - 1][initY + 1] == 0){
                State s = new State(game_size, 2);
                deepArrayCopy(s, this);
                s.makeMove(initX, initY, initX-1, initY+1);
                children.add(s);
            } 
            
            
            if (board[initX+2][initY-2] == 0 && (board[initX + 1][initY - 1] == 2 || board[initX + 1][initY - 1] == 6)){  //look bottom left for kill
                State s = new State(game_size, 2);
                deepArrayCopy(s, this);
                try { 
                    int xTemp = initX;
                    int yTemp = initY;
                    while ((s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 2 || s.board[xTemp - 1][yTemp - 1] == 6)) ||
                    (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 2 || s.board[xTemp - 1][yTemp + 1] == 6)) || 
                    (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 2 || s.board[xTemp + 1][yTemp - 1] == 6)) ||
                    (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 2 || s.board[xTemp + 1][yTemp + 1] == 6))) {
                        if (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 2 || s.board[xTemp + 1][yTemp - 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp-2);
                            s.board[xTemp + 1][yTemp - 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp + 1][yTemp - 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 2 || s.board[xTemp - 1][yTemp - 1] == 6) ) { //left
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                            s.board[xTemp - 1][yTemp - 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp - 1][yTemp - 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 2 || s.board[xTemp - 1][yTemp + 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                            s.board[xTemp - 1][yTemp + 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp - 1][yTemp + 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 2 || s.board[xTemp + 1][yTemp + 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp+2);
                            s.board[xTemp + 1][yTemp + 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp + 1][yTemp + 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp + 2;
                        }
                    }
                } catch (Exception e)  {
                    
                } finally {
                    children.add(s);
                }
            } else if (board[initX + 1][initY - 1] == 0){
                State s = new State(game_size, 2);
                deepArrayCopy(s, this);
                s.makeMove(initX, initY, initX+1, initY-1);
                children.add(s);
            } 
            
            if (board[initX+2][initY+2] == 0 && (board[initX + 1][initY + 1] == 2 || board[initX + 1][initY + 1] == 6)){ //look bottom right for kill
                State s = new State(game_size, 2);
                deepArrayCopy(s, this);
                try { 
                    int xTemp = initX;
                    int yTemp = initY;
                    while ((s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 2 || s.board[xTemp - 1][yTemp - 1] == 6)) ||
                    (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 2 || s.board[xTemp - 1][yTemp + 1] == 6)) || 
                    (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 2 || s.board[xTemp + 1][yTemp - 1] == 6)) ||
                    (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 2 || s.board[xTemp + 1][yTemp + 1] == 6))) {

                        if (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 2 || s.board[xTemp + 1][yTemp + 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp+2);
                            s.board[xTemp + 1][yTemp + 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp + 1][yTemp + 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 2 || s.board[xTemp - 1][yTemp - 1] == 6) ) { //left
                            System.out.println(s.board[xTemp - 2][yTemp - 2]);
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                            s.board[xTemp - 1][yTemp - 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp - 1][yTemp - 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 2 || s.board[xTemp - 1][yTemp + 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                            s.board[xTemp - 1][yTemp + 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp - 1][yTemp + 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 2 || s.board[xTemp + 1][yTemp - 1] == 6)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp-2);
                            s.board[xTemp + 1][yTemp - 1] = 0;
                            s.no_of_whites--;
                            if (s.board[xTemp + 1][yTemp - 1] == 6) {
                                s.no_of_white_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp - 2;
                        }
                    }
                } catch (Exception e)  {
                    
                } finally {
                    children.add(s);
                }
            } else if (board[initX + 1][initY + 1] == 0){
                State s = new State(game_size, 2);
                deepArrayCopy(s, this);
                s.makeMove(initX, initY, initX+1, initY+1);
                children.add(s);
            } 
            
        }
        
        if (turn == 2) {
            if (board[initX-2][initY-2] == 0 && (board[initX - 1][initY - 1] == 1 || board[initX - 1][initY - 1] == 3)){  //look top left for kill
                State s = new State(game_size, 1);
                deepArrayCopy(s, this);
                try { 
                    int xTemp = initX;
                    int yTemp = initY;
                    while ((s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 1 || s.board[xTemp - 1][yTemp - 1] == 3)) ||
                    (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 1 || s.board[xTemp - 1][yTemp + 1] == 3)) || 
                    (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 1 || s.board[xTemp + 1][yTemp - 1] == 3)) ||
                    (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 1 || s.board[xTemp + 1][yTemp + 1] == 3))) {
                        if (s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 1 || s.board[xTemp - 1][yTemp - 1] == 3) ) { //left
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                            s.board[xTemp - 1][yTemp - 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp - 1][yTemp - 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 1 || s.board[xTemp - 1][yTemp + 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                            s.board[xTemp - 1][yTemp + 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp - 1][yTemp + 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 1 || s.board[xTemp + 1][yTemp - 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp-2);
                            s.board[xTemp + 1][yTemp - 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp + 1][yTemp - 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 1 || s.board[xTemp + 1][yTemp + 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp+2);
                            s.board[xTemp + 1][yTemp + 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp + 1][yTemp + 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp + 2;
                        }
                    }
                } catch (Exception e)  {
                    
                } finally {
                    children.add(s);
                }
            } else if (board[initX - 1][initY - 1] == 0){
                State s = new State(game_size, 1);
                deepArrayCopy(s, this);
                s.makeMove(initX, initY, initX-1, initY-1);
                children.add(s);
            } 
            
            if (board[initX-2][initY+2] == 0 && (board[initX - 1][initY + 1] == 1 || board[initX - 1][initY + 1] == 3)){ //look top right for kill
                State s = new State(game_size, 1);
                deepArrayCopy(s, this);
                try { 
                    int xTemp = initX;
                    int yTemp = initY;
                    while ((s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 1 || s.board[xTemp - 1][yTemp - 1] == 3)) ||
                    (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 1 || s.board[xTemp - 1][yTemp + 1] == 3)) || 
                    (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 1 || s.board[xTemp + 1][yTemp - 1] == 3)) ||
                    (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 1 || s.board[xTemp + 1][yTemp + 1] == 3))) {
                        if (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 1 || s.board[xTemp - 1][yTemp + 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                            // System.out.println("Lora Kill 2");
                            s.board[xTemp - 1][yTemp + 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp - 1][yTemp + 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 1 || s.board[xTemp - 1][yTemp - 1] == 3) ) { //left
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                            // System.out.println("Lora Kill 1");
                            s.board[xTemp - 1][yTemp - 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp - 1][yTemp - 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 1 || s.board[xTemp + 1][yTemp - 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp-2);
                            // System.out.println("Lora Kill 3");
                            s.board[xTemp + 1][yTemp - 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp + 1][yTemp - 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 1 || s.board[xTemp + 1][yTemp + 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp+2);
                            // System.out.println("Lora Kill 4");
                            s.board[xTemp + 1][yTemp + 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp + 1][yTemp + 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp + 2;
                        }
                    }
                } catch (Exception e)  {
                    
                } finally {
                    children.add(s);
                }
            } else if (board[initX - 1][initY + 1] == 0){
                State s = new State(game_size, 1);
                deepArrayCopy(s, this);
                s.makeMove(initX, initY, initX-1, initY+1);
                children.add(s);
            } 
            
            
            if (board[initX+2][initY-2] == 0 && (board[initX + 1][initY - 1] == 1 || board[initX + 1][initY - 1] == 3)){  //look bottom left for kill
                State s = new State(game_size, 1);
                deepArrayCopy(s, this);
                try { 
                    int xTemp = initX;
                    int yTemp = initY;
                    while ((s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 1 || s.board[xTemp - 1][yTemp - 1] == 3)) ||
                    (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 1 || s.board[xTemp - 1][yTemp + 1] == 3)) || 
                    (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 1 || s.board[xTemp + 1][yTemp - 1] == 3)) ||
                    (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 1 || s.board[xTemp + 1][yTemp + 1] == 3))) {
                        // System.out.println("hello 3");
                        if (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 1 || s.board[xTemp + 1][yTemp - 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp-2);
                            // System.out.println("Lora Kill3");
                            s.board[xTemp + 1][yTemp - 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp + 1][yTemp - 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 1 || s.board[xTemp - 1][yTemp - 1] == 3) ) { //left
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                            // System.out.println("Lora Kill1");
                            s.board[xTemp - 1][yTemp - 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp - 1][yTemp - 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 1 || s.board[xTemp - 1][yTemp + 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                            // System.out.println("Lora Kill2");
                            s.board[xTemp - 1][yTemp + 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp - 1][yTemp + 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 1 || s.board[xTemp + 1][yTemp + 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp+2);
                            // System.out.println("Lora Kill4");
                            s.board[xTemp + 1][yTemp + 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp + 1][yTemp + 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp + 2;
                        }
                    }
                } catch (Exception e)  {
                    
                } finally {
                    children.add(s);
                }
            } else if (board[initX + 1][initY - 1] == 0){
                State s = new State(game_size, 1);
                deepArrayCopy(s, this);
                s.makeMove(initX, initY, initX+1, initY-1);
                children.add(s);
                // System.out.println("Lora3");
            } 
            
            if (board[initX+2][initY+2] == 0 && (board[initX + 1][initY + 1] == 1 || board[initX + 1][initY + 1] == 3)){ //look bottom right for kill
                State s = new State(game_size, 1);
                deepArrayCopy(s, this);
                try { 
                    int xTemp = initX;
                    int yTemp = initY;
                    while ((s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 1 || s.board[xTemp - 1][yTemp - 1] == 3)) ||
                    (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 1 || s.board[xTemp - 1][yTemp + 1] == 3)) || 
                    (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 1 || s.board[xTemp + 1][yTemp - 1] == 3)) ||
                    (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 1 || s.board[xTemp + 1][yTemp + 1] == 3))) {
                        // System.out.println("Hello4");
                        // System.out.println("xTemp: " + xTemp + ", yTemp: " + yTemp);
                        if (s.board[xTemp + 2][yTemp + 2] == 0 && (s.board[xTemp + 1][yTemp + 1] == 1 || s.board[xTemp + 1][yTemp + 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp+2);
                            // System.out.println("Lora Kill4");
                            s.board[xTemp + 1][yTemp + 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp + 1][yTemp + 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp - 2][yTemp - 2] == 0 && (s.board[xTemp - 1][yTemp - 1] == 1 || s.board[xTemp - 1][yTemp - 1] == 3) ) { //left
                            System.out.println(s.board[xTemp - 2][yTemp - 2]);
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp-2);
                            // System.out.println("Lora Kill1");
                            s.board[xTemp - 1][yTemp - 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp - 1][yTemp - 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp - 2;
                        } else if (s.board[xTemp - 2][yTemp + 2] == 0 && (s.board[xTemp - 1][yTemp + 1] == 1 || s.board[xTemp - 1][yTemp + 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp-2, yTemp+2);
                            // System.out.println("Lora Kill2");
                            s.board[xTemp - 1][yTemp + 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp - 1][yTemp + 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp - 2;
                            yTemp = yTemp + 2;
                        } else if (s.board[xTemp + 2][yTemp- 2] == 0 && (s.board[xTemp + 1][yTemp - 1] == 1 || s.board[xTemp + 1][yTemp - 1] == 3)) {
                            s.makeMove(xTemp, yTemp, xTemp+2, yTemp-2);
                            // System.out.println("Lora Kill3");
                            s.board[xTemp + 1][yTemp - 1] = 0;
                            s.no_of_blacks--;
                            if (s.board[xTemp + 1][yTemp - 1] == 3) {
                                s.no_of_black_kings--;
                            }
                            xTemp = xTemp + 2;
                            yTemp = yTemp - 2;
                        }
                    }
                } catch (Exception e)  {
                    
                } finally {
                    children.add(s);
                }
            } else if (board[initX + 1][initY + 1] == 0){
                State s = new State(game_size, 1);
                deepArrayCopy(s, this);
                s.makeMove(initX, initY, initX+1, initY+1);
                children.add(s);
                // System.out.println("Lora4 outside");
            } 
            //  }  
        }
        
        
    }

    public double getHeuristicBlack(){
        if(no_of_blacks>0 && no_of_whites==0){
            h_val = 100.0;
            return h_val;
        }else if(no_of_blacks==0){
            h_val = 0.0;

            return h_val;
        }
        if(board.length==4){
            h_val = (50.0-((2-no_of_whites)*25.0) +(25.0*no_of_white_kings));
            return h_val;
            }else {
            h_val= (50.0-((12-no_of_whites)*(25.0/6)) +((25.0/6)*no_of_white_kings));
              return h_val;  
        }
    }
    public double getHeuristicWhite(){
        if(no_of_blacks==0 && no_of_whites>0){
            h_val = 100.0;
            return h_val;
        }else if(no_of_whites==0){
            h_val =  0.0;
            return h_val;
        }
        if(board.length==4){
            h_val = (50.0-((2-no_of_blacks)*25.0) +(25.0*no_of_black_kings));
            return h_val;
        }else {
            h_val = (50.0-((12-no_of_blacks)*(25.0/6)) +((25.0/6)*no_of_black_kings));
            return h_val;
        }
    }

    /**
     * 
     * @param initX initial x of piece
     * @param initY initial y of piece 
     * @param finX  final x of piece
     * @param finY  final y of piece 
     * @return  true if move has been made
     */
    public boolean makeMove(int initX, int initY, int finX, int finY) {
        if (board[initX][initY] == 0) {
            utlitiyChecker(this);
            return false;
        }



        
        board[finX][finY] = board[initX][initY];
        board[initX][initY] = 0;
    
        if(turn == 2 && finX == game_size - 1+2){
            board[finX][finY] = 3;
            no_of_black_kings++;
        }

        if(turn == 1 && finX == 0+2){
            board[finX][finY] = 6;
            no_of_white_kings++;

        }
        utlitiyChecker(this);

        return true;
    }

    public void printBoard() {
        char character='a';
        char character_1 = 'K';
        System.out.print(" ");
        for(int i = 2; i < game_size + 2; i++){
            System.out.print(" "+ character_1++);
        }
        System.out.println("   ");
        
        for (int i=2; i<game_size+2;i++) {
            System.out.print(character + " ");
            character++;
            for (int j=2;j<game_size+2;j++){
               System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }



    //generate children for a state
    public void getChildren(){
        System.out.println("***");
        possibleMoves();
        for(State d : children){
            d.printBoard();
        }
    }

    public void deepArrayCopy(State b, State c){
        for(int i=0;i<c.board.length;i++){
            for(int j=0;j<c.board[0].length;j++){
                b.board[i][j]=c.board[i][j];
            }
        }
        b.no_of_black_kings = c.no_of_black_kings;
        b.no_of_white_kings = c.no_of_white_kings;
        b.no_of_blacks = c.no_of_blacks;
        b.no_of_whites = c.no_of_whites;
        b.utility=c.utility;

    }


   
}