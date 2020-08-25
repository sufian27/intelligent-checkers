import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main {
  static HashMap<Character, Integer> row_map = new HashMap<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


          System.out.println("WELCOME TO THE GAME");
          System.out.println("1. 4x4 checkers");
          System.out.println("2. 8x8 checkers");
          int option = sc.nextInt();
          int game_sz = 0;
          if(option == 1){
            game_sz = 4;
          }else{
            game_sz = 8;
          }


          System.out.println("Pick your player :");
          System.out.println("1. Player 1 -- Black");
          System.out.println("2. Player 2 -- White");
          int player = sc.nextInt();

          if(player == 1){
            System.out.println("Your king is 3");
          }
          else{
            System.out.println("Your king is 6");
          }

          System.out.println("1. An agent plays randomly");
          System.out.println("2. An agent uses ALPHA-BETA pruning with MINIMAX");
          System.out.println("3. An agent uses H-MINIMAX with a fixed depth cutoff");
          int game_option = sc.nextInt();

          System.out.println("-------- INSTRUCTIONS --------------");
          System.out.println("Each cell in the game is represented by abcd... in the rows and KLMN... in the columns");
          System.out.println("Enter your input as aK[space]bL to go from aK to bL");


          State g = new State(game_sz, 1);

          Character temp22 = 'a';
          Character temp23 = 'K';
          for(int i = 2; i < game_sz + 2; i++){
            row_map.put(temp22++, i);
            row_map.put(temp23++, i);
          }



          System.out.println("<<<<<<<INITIAL BOARD>>>>>>>>>>>");
          g.printBoard();
         
      if(game_option==1){  /**RANDOM GAME */
        State state_RandomGame= new State(game_sz, 1);
        while(state_RandomGame.utility==-1){
          if(player==2){
              System.out.println("<<<<<STATE AFTER PLAYER 1 MOVE>>>>>>>>>");
              moveAIrandom(player, state_RandomGame);
              state_RandomGame.printBoard();
              state_RandomGame.children.clear();
              state_RandomGame.possibleMoves();
              if(state_RandomGame.children.size()==0){
                break;
              }
              state_RandomGame.children.clear();

              moveUser(game_sz, state_RandomGame);
              System.out.println("<<<<<STATE AFTER PLAYER 2 MOVE>>>>>>>>>");
              state_RandomGame.printBoard();
              state_RandomGame.children.clear();
              state_RandomGame.possibleMoves();
              if(state_RandomGame.children.size()==0){
                break;
              }
              state_RandomGame.children.clear();
          }else{

            moveUser(game_sz, state_RandomGame);
            System.out.println("<<<<<STATE AFTER PLAYER 1 MOVE>>>>>>>>>");
            state_RandomGame.printBoard();
            state_RandomGame.children.clear();

            if(state_RandomGame.utility!=-1){
              break;
            }
            state_RandomGame.possibleMoves();
              if(state_RandomGame.children.size()==0){
                break;
              }
              state_RandomGame.children.clear();
            System.out.println("<<<<<STATE AFTER PLAYER 2 MOVE>>>>>>>>>");
            moveAIrandom(player, state_RandomGame);
            state_RandomGame.printBoard();
            state_RandomGame.children.clear();
            state_RandomGame.possibleMoves();
              if(state_RandomGame.children.size()==0){
                break;
              }
              state_RandomGame.children.clear();

            
          }
        }
        if(state_RandomGame.utility==player){
          System.out.println("You Have Won!");
        }else if(state_RandomGame.utility==0){
          System.out.println("DRAW");
          
        }else{
          System.out.println("The AI has Won :( Better luck next time.");
        }
      }
        //  g.printBoard();
      if(game_option==3){  /**H-Minimax */
          State state_HMinGame= new State(game_sz, 1);
          while(state_HMinGame.utility==-1){
            // moveUser(game_sz, player, g);
            if(player==2){
                System.out.println("<<<<<STATE AFTER PLAYER 1 MOVE>>>>>>>>>");
                State temp= Minimax.miniMax(state_HMinGame);
                state_HMinGame.deepArrayCopy(state_HMinGame, temp);
                state_HMinGame.printBoard();
                state_HMinGame.children.clear();
                state_HMinGame.possibleMoves();
                if(state_HMinGame.children.size()==0){
                  break;
                }
                state_HMinGame.children.clear();
                if(state_HMinGame.turn==1){
                  state_HMinGame.turn=2;
                }else{
                  state_HMinGame.turn=1;
                }
                moveUser(game_sz, state_HMinGame);
                System.out.println("<<<<<STATE AFTER PLAYER 2 MOVE>>>>>>>>>");
                state_HMinGame.printBoard();
                state_HMinGame.children.clear();
                state_HMinGame.possibleMoves();
                if(state_HMinGame.children.size()==0){
                  break;
                }
                state_HMinGame.children.clear();
            }else{
  
              moveUser(game_sz, state_HMinGame);
              System.out.println("<<<<<STATE AFTER PLAYER 1 MOVE>>>>>>>>>");
              state_HMinGame.printBoard();
              state_HMinGame.children.clear();
            
              if(state_HMinGame.utility!=-1){
                break;
              }
              state_HMinGame.possibleMoves();
                if(state_HMinGame.children.size()==0){
                  break;
                }
                state_HMinGame.children.clear();
              System.out.println("<<<<<STATE AFTER PLAYER 2 MOVE>>>>>>>>>");
              State temp= Minimax.miniMax(state_HMinGame);
              state_HMinGame.deepArrayCopy(state_HMinGame, temp);
              state_HMinGame.printBoard();
              state_HMinGame.children.clear();
              state_HMinGame.possibleMoves();
              if(state_HMinGame.children.size()==0){
                break;
              }
              state_HMinGame.children.clear();
              if(state_HMinGame.turn==1){
                state_HMinGame.turn=2;
              }else{
                state_HMinGame.turn=1;
              }
  
              
            }
          }
          if(state_HMinGame.utility==player){
            System.out.println("You Have Won!");
          }else if(state_HMinGame.utility==0){
            System.out.println("DRAW");
          }else {
            System.out.println("The AI has Won :( Better luck next time.");
          }
        }

        if(game_option==2){  /**Alpha - Beta */
          State state_ABGame= new State(game_sz, 1);
          while(state_ABGame.utility==-1){
            // moveUser(game_sz, player, g);
            if(player==2){
                System.out.println("<<<<<STATE AFTER PLAYER 1 MOVE>>>>>>>>>");
                State temp= Alpha_Beta_Minimax.miniMax(state_ABGame);
                state_ABGame.deepArrayCopy(state_ABGame, temp);
                state_ABGame.printBoard();
                state_ABGame.children.clear();
                state_ABGame.possibleMoves();
                if(state_ABGame.children.size()==0){
                  break;
                }
                state_ABGame.children.clear();
                if(state_ABGame.turn==1){
                  state_ABGame.turn=2;
                }else{
                  state_ABGame.turn=1;
                }
                moveUser(game_sz, state_ABGame);
                System.out.println("<<<<<STATE AFTER PLAYER 2 MOVE>>>>>>>>>");
                state_ABGame.printBoard();
                state_ABGame.children.clear();
                state_ABGame.possibleMoves();
                if(state_ABGame.children.size()==0){
                  break;
                }
                state_ABGame.children.clear();
            }else{
  
              moveUser(game_sz, state_ABGame);
              System.out.println("<<<<<STATE AFTER PLAYER 1 MOVE>>>>>>>>>");
              state_ABGame.printBoard();
              state_ABGame.children.clear();

              if(state_ABGame.utility!=-1){
                break;
              }
              state_ABGame.possibleMoves();
                if(state_ABGame.children.size()==0){
                  break;
                }
                state_ABGame.children.clear();
              System.out.println("<<<<<STATE AFTER PLAYER 2 MOVE>>>>>>>>>");
              State temp= Alpha_Beta_Minimax.miniMax(state_ABGame);
              state_ABGame.deepArrayCopy(state_ABGame, temp);
              state_ABGame.printBoard();
              state_ABGame.children.clear();
              state_ABGame.possibleMoves();
              if(state_ABGame.children.size()==0){
                break;
              }
              state_ABGame.children.clear();
              if(state_ABGame.turn==1){
                state_ABGame.turn=2;
              }else{
                state_ABGame.turn=1;
              }
  
              
            }
          }
          if(state_ABGame.utility==player){
            System.out.println("You Have Won!");
          }else if(state_ABGame.utility==0){
            System.out.println("DRAW");
          }else{
            System.out.println("The AI has Won :( Better luck next time.");
          }
        }
        
        }


        public static void  moveAIrandom(int player, State g) {

          
          g.possibleMoves();
          int size_children = g.children.size();
          int result = new Random().nextInt(size_children);
          int i = 0;
          for(State s : g.children){
            if(i == result && g.arrayCompare(g.board, s.board)==-1){
              g.deepArrayCopy(g, s);
              if(g.turn==1){
                g.turn=2;
              }else{
                g.turn=1;
              }
            }
            i++;
          }
          g.utlitiyChecker(g);
         
          
        }
          


  public static void moveUser(int game_sz, State g) {

            boolean moved = false;
            Scanner sc=new Scanner(System.in);
            int initX=0;
            int initY=0;
            int finX=0;
            int finY=0;
            int i=0;

            while(!moved){
              if(i>0){
                System.out.println("Invalid Move. Try Again");
              }
              i++;
              State copyState=new State(game_sz, g.turn);
              copyState.deepArrayCopy(copyState, g);
             
              while(true){
              System.out.println("Your move:  (Keep making moves for jumps and press q to finalize move)");

              String str = sc.nextLine();

              if(str.equals("q")){
                break;
              }
              if(!str.equals("") && str.length()==5){
                  char[] input = str.toCharArray();
                  if(input[0]>='a' && input[0]<=((char)('a'+game_sz-1)) && input[3]>='a' && input[3]<=((char)('a'+game_sz-1))) {
                    if(input[1]>='K' && input[1]<=(char)('K'+game_sz-1) && input[4]>='K' && input[4]<=(char)('K'+game_sz-1)){
                          initX = row_map.get(input[0]);
                          initY = row_map.get(input[1]);
                          finX = row_map.get(input[3]);
                          finY = row_map.get(input[4]);
                    }
                  }
              }

               

             // System.out.println(initX + " " + initY + " " + finX + " " + finY);

              movePlayer(initX, initY, finX, finY, copyState);
              
   
             }

              g.children.clear();
              g.possibleMoves();
              
              
              for(State x: g.children){
                if(x.arrayCompare(x.board, copyState.board)==0 && g.arrayCompare(g.board, x.board)==-1){
                  
                  
                    g.deepArrayCopy(g, x);
                   
                    if(g.turn==1){
                      g.turn=2;
                    }else{
                      g.turn=1;
                    }
                    moved=true;
                }
              }
            }
            g.utlitiyChecker(g);
          }


        public static void movePlayer(int initX, int initY, int finX, int finY, State copyState ){
          copyState.board[finX][finY] = copyState.board[initX][initY];
          copyState.board[initX][initY] = 0;
      
          if(copyState.turn == 1 && finX == copyState.game_size - 1+2){
            copyState.board[finX][finY] = 3;
              copyState.no_of_black_kings++;
          }
  
          if(copyState.turn == 2 && finX == 0+2){
             copyState.board[finX][finY] = 6;
              copyState.no_of_white_kings++;
  
          }

          if (Math.abs(initX - finX) == 2 && Math.abs(initY - finY) == 2) {
            copyState.board[(initX+finX)/2][(initY+finY)/2] = 0;
          }

        }

}