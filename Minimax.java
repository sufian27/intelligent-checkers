class Minimax {
    static int iterations = 0;
    static int maxDepth = 0;
    //1 is maximiser
    //2 is minimiser
    public static void minimax_helper(State s) {
        s = miniMax(s);
    }

    public static State miniMax(State s) { //assuming the this is called on the turn of the computer
        if(s.game_size == 4) {
            maxDepth = 8;
        } else if (s.game_size == 8) {
            maxDepth = 4;
        }
        if (s.turn == 2) { 
            return getMin(s, 0);
        } else { 
            return getMax(s, 0);
        }
    } 
    public static int h_value(State s){
        int pieces = 0;
        if(s.game_size == 4){
          pieces = 2;
        }
        else if(s.game_size == 8){
          pieces = 12;
        }
        return ((Math.abs(s.no_of_blacks - pieces)) * (-5) + Math.abs(s.no_of_whites - pieces) * (5) + s.no_of_black_kings * 10 + s.no_of_white_kings * (-10));
    }
    public static State getMax(State s, int depth) {
        s.possibleMoves(); 
        if (isTerminal(s) || depth > maxDepth) {
            return s;
        }
        int bestScore = Integer.MIN_VALUE;
        State minNode = null;
        for (State c : s.children) {

            int score = h_value(getMin(c, depth+1)); //do getMin on all the children of the Max node
            if (score >= bestScore) {
                bestScore = score;
                minNode = c;
            }
        }
        return minNode;
    }
    public static State getMin(State s, int depth) {
        s.possibleMoves();
        if (isTerminal(s) || depth > maxDepth) {
            return s;
        }
        int bestScore = Integer.MAX_VALUE;
        State maxNode = null;

        for (State c : s.children) {
            
            int score = h_value(getMax(c, depth+1)); //do getMax on all the children of the Max node
            if (score <= bestScore) {
                bestScore = score;
                maxNode = c;
            }
        }
        return maxNode;
    }

    public static boolean isTerminal(State s) {
        if (s.children.size() == 0 || s.utility == -2 ||s.utility == 2 || s.utility == 0) {
            return true;
        } 
        return false;
    }
}