package ch.i10a.reversi.gameplay;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ch.i10a.reversi.ai.TreeNode;
import ch.i10a.reversi.gameplay.MoveList.Move;
import ch.i10a.reversi.gui.Board;
import ch.i10a.reversi.gui.Field;

/**
 * Handler for performing and checking possible Moves.
 *
 */
public class MoveHandler {

	private static final boolean debug = false;

	private static Field[][] fields;

	/**
	 * Registers the fields of the board for later access.
	 */
	public static void registerFields(Field[][] fields) {
		MoveHandler.fields = null;
		MoveHandler.fields = fields;
	}

	// -------------------- public methods --------------------
	/**
	 * Performs the move on the active field. This method runs synchronized
	 * to avoid side-effects.
	 * 
	 * @param activeField the field on which the move is being performed.
	 * 
	 * @return ArrayList of fields that are beaten with the move that was done
	 */
	public synchronized static ArrayList<Field> doMove(Field activeField) {
			PlayerI activePlayer = PlayerManager.getActivePlayer();
			ArrayList<Field> hitFields = new ArrayList<Field>();

			//check on empty Field
			if(activeField.getValue() == 0){
				//check on enemies in the surrounding fields, no enemy, no further check
				if(MoveHandler.checkNeighbourEnemies(activeField, activePlayer)){
					if(MoveHandler.checkHit(activeField, activePlayer)){
						// updates the value depending on the player
						activeField.setValue(activePlayer.getColor() == Color.WHITE ? -1 : 1);
						activeField.repaint();
						hitFields = MoveHandler.hitEnemyStones(activeField,activePlayer);
					}
				}
			}

			return hitFields;
	}

	/**
	 *  This method determines if a player can move. If he can, it collects the possible field hits.
	 *  But if he can't the pass method is hit and it is checked if the following
	 *  player can't move too. If both cannot move, the game is over (implemented in
	 *  setDoublePass, which sets the flag for it)
	 * 
	 */
	public static void checkIfNextPlayerIsPossibleToMove() {
		if(!MoveHandler.checkWholeFieldHit()){
			PlayerManager.setPass();
			PlayerManager.nextPlayer();
			if(!MoveHandler.checkWholeFieldHit()){
				PlayerManager.setDoublePass();
			} else {
				collectingPossibleFieldHits();
			}
		} else {
			collectingPossibleFieldHits();
		}
	}

	/**
	 * this method checks if a field on the board is still animating
	 * 
	 * @return boolean, if the whole animation is complete
	 */
	public static boolean isAnimating() {
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j].isAnimating()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Representation of hitEnemyStones with two parameters, which is necessary
	 * for the not simulated hitEnemyStones method 
	 * 
	 * @param the field where the stone was played, the active Player via Interface PlayerI
	 * 
	 * @return ArrayList of Fields which stones are beaten
	 */
	public static ArrayList<Field> hitEnemyStones(Field field, PlayerI player){
		return hitEnemyStones(field, player, false);
	}
	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in all directions.
	 * Also updates the fields with the correspondent value. if only computing (simulation), the direct value is set.
	 * if the hits are done for real, the doubled value is set for animation set up
	 * 
	 * @param the field where the stone was played, the active Player via Interface PlayerI, the boolean simulate for computing or realtime animation
	 * 
	 * @return ArrayList of Fields which stones are beaten
	 */
	public static ArrayList<Field> hitEnemyStones(Field field, PlayerI player, boolean simulate){
		ArrayList<Field> hitFields = getHits(field, player);

		// update the stones count of this player
		// "+ 1" because one stone is set by the player!
		if (!simulate) {
			PlayerManager.updateStones(hitFields.size() + 1);
		}

		int j = 0;
		while (j < hitFields.size()) {
			if (simulate) {
				hitFields.get(j).setValue(player.getValue());
			} else {
				hitFields.get(j).setValue(player.getValue() * 2);
			}
//			fields[hitFields.get(j).getColNum()][hitFields.get(j).getRowNum()].setValue(player.getValue()*2);
//			hitFields.get(j).update(PlayerManager.getActivePlayer().getValue());
			j++;
		}

		return hitFields;
	}

	/**
	 * Runs through the board and collects the possible 
	 * fields to hit for every field.
	 * 
	 */
	public static void collectingPossibleFieldHits() {
		PlayerI activePlayer = PlayerManager.getActivePlayer();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Field f = fields[i][j];
				f.setPossibleHits(getHits(f, activePlayer));
				f.repaint();
			}
		}
	}

	/**
	 * Returns true, if the actual Player can hit stones on the whole field, else it returns false 
	 * @return Boolean that's true, if the actual player can beat at least one stone
	 */
	public static boolean checkWholeFieldHit(){
		PlayerI activePlayer = PlayerManager.getActivePlayer();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(fields[i][j].getValue() == 0){
					if(checkHit(fields[i][j], activePlayer)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if hit(s) are possible 
	 * Example (a is the active field, r is the place the enemy is, i is where your stone is):
	 * |-----|-----|-----|-----|
	 * |  a  |     |     |     |
	 * |-----|-----|-----|-----|
	 * |     |  r  |     |     |
	 * |-----|-----|-----|-----|
	 * |     |     |  r  |     |
	 * |-----|-----|-----|-----|
	 * |     |     |     |  i  |
	 * |-----|-----|-----|-----|
	 * @return false if no stones can be hitten
	 */
	public static boolean checkHit(Field field, PlayerI player){
		boolean hit = false;
		if(checkBottomHit(field, player)){
			return true;
		}
		if(checkBottomLeftHit(field, player)){
			return true;
		}
		if(checkLeftHit(field, player)){
			return true;
		}
		if(checkTopLeftHit(field, player)){
			return true;
		}
		if(checkTopHit(field, player)){
			return true;
		}
		if(checkTopRightHit(field, player)){
			return true;
		}
		if(checkRightHit(field, player)){
			return true;
		}
		if(checkBottomRightHit(field, player)){
			return true;
		}
		return hit;
	}
	
	/**
	 * Returns true if a neighbour is an enemy.
	 * Example (a is the active field, r is any place an enemy could be on):
	 * |-----|-----|-----|
	 * |  r  |  r  |  r  |
	 * |-----|-----|-----|
	 * |  r  |  a  |  r  |
	 * |-----|-----|-----|
	 * |  r  |  r  |  r  |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy
	 */
	public static boolean checkNeighbourEnemies(Field field, PlayerI player){
		boolean enemiesDetected = false;
		if(checkNeighbourEnemiesBottom(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesBottomLeft(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesLeft(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesTopLeft(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesTop(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesTopRight(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesRight(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesBottomRight(field, player)){
			return true;
		}
		
		return enemiesDetected;
	}

	/**
	 * Checks if a free field is available on the board.
	 * if not, the game is over. 
	 * 
	 * @return true if a free field is available, false if not 
	 */
	public static boolean checkForFreeFields(){
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if(fields[i][j].getValue() == 0){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * gives back a field that is played by the computer.
	 * for the easiest difficulty, this is a random field out of the playable ones
	 *  
	 * 
	 * @return Field that is played randomly
	 */
	public static Field getRandomMove() {
		System.out.println("RandomMove");
		List<Field> possibleFields = getPossibleFields(PlayerManager.getActivePlayer());
		Random randomGenerator = new Random();
		
		if(possibleFields.size()>0){
			return possibleFields.get(randomGenerator.nextInt(possibleFields.size()));
		}
		return null;
	}
	
	/**
	 * this method is the starting point for calculation of difficulty medium and hard.
	 * The difficulty can be switched from medium to hard by seeting strengthHard to true.
	 * the best move is then calculated by alpha-beta algorithm.
	 * If the game is in the end phase, the depth of the alpha-beta calculation is altered 
	 * to ten as we have enough memory then.
	 *  
	 * @param boolean strengthHard determines the heuristic calculated
	 * 
	 * @return Field that is played, determined by the alpha-beta algorithm
	 */
	public static Field getBestMove(boolean strengthHard) {
		Field[][] activeGameFields = fields;
	
		//first of all, we have to collect all possible fields that can be played by the respective player
		List<Field> possibleFields = getPossibleFields(PlayerManager.getActivePlayer());
		
		//initialisation of alpha and check (abstract for the alpha-beta cuts on this major level
		int alpha = -Integer.MAX_VALUE; // not MIN_VALUE to invert it as beta
		int check = -1;
		
		//the field will be written in this variable
		Field bestMovableField = null;
		
		//to give down the playfield, we have to clone it. Elseway we simulate the moves on the actual board
		Board board = PlayerManager.getActivePlayer().getBoard().clone();

//		System.out.println("========== active game board =========");
		MoveHandler.printBoard(board, PlayerManager.getActivePlayer());
		
		//creation of the node that is put in the alpha-beta algorithm
		TreeNode<Board> root = new TreeNode<Board>(board, possibleFields);

		//here, the iteration over the possible fields starts
		for (Iterator<Field> iterator = possibleFields.iterator(); iterator.hasNext();) {
			Field field = iterator.next();
			root.setField(field.clone());
			
			//if we are in the endgame, the depth is increased
			if(calculateRestMoves(board) < 10){
				check = alphaBeta(-Integer.MAX_VALUE, Integer.MAX_VALUE, 8, PlayerManager.getActivePlayer(), root, strengthHard);
			}
			else{
				check = alphaBeta(-Integer.MAX_VALUE, Integer.MAX_VALUE, 3, PlayerManager.getActivePlayer(), root, strengthHard);
			}
			
			//here the magic happens. The best field is chosen (in fact the one with the highest calculated value
			if (check > alpha) {
				alpha = check;
				bestMovableField = field;
			}
		}

		registerFields(activeGameFields);
		root = null;
		board = null;
		return bestMovableField;
	}
	
	/**
	 * the recursive heart of the program. if the depth is 0, we reached a leaf and can calculate the value for the situation 
	 * with the heuristic (which heuristic to take is determined by the boolean strengthHard). if we are not in the leaf, the
	 * algorithm calculates minimums and maximums for the players and even cuts out unnecessary moves, thus saving memory
	 *  
	 * @param alpha is the value for the alpha cut, beta is the int value for the beta cut
	 * @param PlayerAdapter Interface is needed to determine the player in charge for this move
	 * @param TreeNode node is the played situation from the node above and the new field value to play along
	 * @param boolean strengthHard determines the heuristic calculated
	 * 
	 * @return integer value that shows how good the prognose is for the played move
	 */
	public static int alphaBeta(int alpha, int beta, int depth, PlayerAdapter player, TreeNode<Board> node, boolean strengthHard) {
//		System.out.println("alpha-beta: depth " + depth);
		
		Board actualBoard = node.getData().clone();

		//the fields first have to be registered
		registerFields(actualBoard.getFields());
		Field f = node.getField();
		
		//the played field is allocated to f
		f.setValue(player.getValue());
		
		//the hitted fields are updated for the actual situation by updating the board
		ArrayList<Field> hitFields = hitEnemyStones(f, player, true);
		hitFields.add(f);
		actualBoard = updateSimBoard(actualBoard, hitFields);
//		System.out.println("TestAfterUpdate");
		printBoard(actualBoard, player);
//		System.out.println("----------------");
		
		//for the calculation of the possible fields, a quick switch of players have to be made
		PlayerAdapter otherPlayer = PlayerManager.getOtherPlayer(player);
		List<Field> possibleFields = getPossibleFields(otherPlayer);
		
		//the board is cloned and put in the TreeNode structure to determine the further moves afterwards
		TreeNode<Board> child = new TreeNode<Board>(actualBoard.clone(), possibleFields);
		node.addChild(child);
		
		//if we are in a leaf, we have to calculate the value for the situation on this leaf
		//a switch is made for hard or medium calculation
		if (depth == 0 || calculateRestMoves(actualBoard) == 0 || possibleFields.size() == 0) {
			if(strengthHard){
				int value = calculateSituationHardStrength(actualBoard);
//				System.out.println("Strong Value " + value);
				return value; // heuristic of board
			}
			else{
				int value = calculateSituationMediumStrength(actualBoard);
//				System.out.println("Medium Value " + value);
				return value; // heuristic of board
			}
			
		}

		//here it is iterated over all possible moves that can be made in this node's situation
		for (Field field : child.getPossibleMoves()) {
			//if the player in this node is the computer, do the maximizing
			if (player == PlayerManager.getActivePlayer()) {
				// sysouts
				printBoard(actualBoard, player);
				
				//again the fields has to be cloned to save the original
				child.setField(field.clone());
				alpha = Math.max(alpha, alphaBeta(alpha, beta, depth - 1, PlayerManager.getOtherPlayer(player), child, strengthHard));
				
				//here, the beta pruning takes place according to presetting
				if (alpha >= beta) {
//					System.out.println("Beta Cut Off");
					return alpha; // beta cut-off
				}
			}
			//if the player in this node is the human, do the minimizing
			else {
				// sysouts
				printBoard(actualBoard, player);

				//again the fields has to be cloned to save the original
				child.setField(field.clone());
				beta = Math.min(beta, alphaBeta(alpha, beta, depth - 1, PlayerManager.getOtherPlayer(player), child, strengthHard));
				
				//here, the alpha pruning takes place according to presetting
				if (alpha >= beta) {
//					System.out.println("Alpha Cut Off");
					return beta; // beta cut-off
				}
			}
		}
		
		//to save (lots!!!) of memory, all the no more needed objects are nulled
		f = null;
		hitFields = null;
		child = null;
		node = null;
		actualBoard = null;
		
		//here, depending on the active player, alpha or beta is returned.
		if (player == PlayerManager.getActivePlayer()){
			return alpha;
		}
		else{
			return beta;
		}
		
	}

	/**
	 * Here, the calculation for the difficulty medium takes place. mainly, it just counts the black and
	 * white stones and adds them up against each other (go for the most possible stones of his own)
	 *  
	 * @param the board that has to be calculated
	 * 
	 * @return integer value that shows how good the prognose is for the given board
	 */
	private static int calculateSituationMediumStrength(Board board){
		int situationValue = 0;
		Field field[][] = board.getFields();
		
		for (int i = 0; i < 8; i++) {			
			for (int j = 0; j < 8; j++) {
				if(field[j][i].getValue() == 1){
					situationValue++;
				}
				else if(field[j][i].getValue() == -1){
					situationValue--;
				}				
			}
		}
		return situationValue;
	}

	/**
	 * Here, the calculation for the difficulty hard takes place. it uses the methods getBeginningValue 
	 * to calculate the beginning moves, the getMidgameValue to calculate the midgame values and the
	 * method calculateSituationMediumStrength to calculate the endgame (as the medium calculation wants
	 * to have the most stones on the field, this is perfectly suitable)
	 *  
	 * @param the board that has to be calculated
	 * 
	 * @return integer value that shows how good the prognose is for the given board
	 */
	private static int calculateSituationHardStrength(Board board){
		int situationValue = 0;
		Field field[][] = board.getFields();
		int movesLeft = calculateRestMoves(board);

		//As long as we are only in the sweet sixteen (4x4 center of the board)
		//Meaning we stand on no corners, edges or pre-edges
		if(!checkForFourCornerFieldHits(board)&&!checkForFourEdgesFieldHits(board)&&!checkForFourPreEdgesFieldHits(board)){
//			System.out.println("BeginningValueCalculated");
			return getBeginningValue(board);
		}
		
		//when the sweet sixteen are left (first stone out of the sweet sixteen)
		//we enter the midgame
		else if(movesLeft > 10){	
//			System.out.println("MidgameValueCalculated");
			return getMidgameValue(board);
		}
			//here the heuristic for the endgame kicks in
		else{
			//Endgame
			//we kick again the Medium Strenght calculation. Why?
			//because at the end, it's best to have the most pieces on the
			//board. ant that's exactly that the Medium Calculation does.
//			System.out.println("EndgameValueCalculated");
			return calculateSituationMediumStrength(board);
		}
		
	}
	
	/**
	 * gets the value for the time as everything is in the sweet sixteen
	 * 
	 * @param the board that has to be calculated
	 * 
	 * @return integer situation strength
	 */
	private static int getBeginningValue(Board board){
		Field field[][] = board.getFields();
		int situationValue = 0;
		//if no black stones are left, the situation has to be drastically
		//calculated, as Black will win for shure. ->poison
		if(getNumberOfBlackStones(board) == 0){
			return -2000000;
		}
		//When we are playing in the beginning, we do not want a lot of stones
		// --> agility / mobility
		//again, we use the calculateSituationMediumStrength method, but in the negative
		else{
			int factor = 100;
			return -calculateSituationMediumStrength(board)*factor;
		}
		
		
	}
	
	/**
	 * calculates the value if the game is in midgame. several fields have special characters which are weighted
	 * and therefore factorized into the calculation.
	 * 
	 * @param the board that has to be calculated
	 * 
	 * @return integer situation strength
	 */
	private static int getMidgameValue(Board board){
		Field field[][] = board.getFields();
		int situationValue = 0;
		
		int addFactorCorner = 40;//Corners are vital, so it is added up with a high value
		int addFactorDiagonalCorner = -20;//diagonal Corner fields are ugly to play, a little poisoning here
		int addFactorPreCorner = -10;//playing just before the corner gives the opponent the possibility to get
									//the corner, so a little poisoning as well.
		int addFactorEdge = 20;//An edgeField gives a little advantage in the game
		int addFactorPreEdge = -10;//placing before the edge gives the opponent a good possibility -> small poisoning value
		
		//we have to step over every field of the board
		for (int i = 0; i < 8; i++) {			
			for (int j = 0; j < 8; j++) {
				//heuristic of midgame
				//-----------------begin of Loop-----------------------------------------------------
				
				//if a stone lies on a corner
				if((i == 0 && j == 0)||(i == 7 && j == 7)||(i == 0 && j == 7)||(i == 7 && j == 0)){
					if(field[i][j].getValue() == 1){
						situationValue = situationValue + addFactorCorner;
					}
					else if(field[i][j].getValue() == -1){
						situationValue = situationValue - addFactorCorner;
					}
				}
				
				//if a stone lies on the left edge
				if(i == 0 && (j == 2 || j == 3 || j == 4 || j == 5)){
					if(field[i][j].getValue() == 1){
						situationValue = situationValue + addFactorEdge;
					}
					else if(field[i][j].getValue() == -1){
						situationValue = situationValue - addFactorEdge;
					}
				}
				
				//if a stone lies on the right edge
				if(i == 7 && (j == 2 || j == 3 || j == 4 || j == 5)){
					if(field[i][j].getValue() == 1){
						situationValue = situationValue + addFactorEdge;
					}
					else if(field[i][j].getValue() == -1){
						situationValue = situationValue - addFactorEdge;
					}
				}
				
				//if a stone lies on the upper edge
				if(j == 0 && (i == 2 || i == 3 || i == 4 || i == 5)){
					if(field[i][j].getValue() == 1){
						situationValue = situationValue + addFactorEdge;
					}
					else if(field[i][j].getValue() == -1){
						situationValue = situationValue - addFactorEdge;
					}
				}
				
				//if a stone lies on the lower edge
				if(j == 7 && (i == 2 || i == 3 || i == 4 || i == 5)){
					if(field[i][j].getValue() == 1){
						situationValue = situationValue + addFactorEdge;
					}
					else if(field[i][j].getValue() == -1){
						situationValue = situationValue - addFactorEdge;
					}
				}
				
				//if a stone lies on a pre-diag-corner field (upper left)
				if(i == 1 && j == 1){
					if(field[i][j].getValue() == 1){
						//if the corner field is occupied by a black stone, just count
						if(field[0][0].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorDiagonalCorner;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[0][0].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorDiagonalCorner;
						}
					}
				}
				
				//if a stone lies on a pre-diag-corner field (lower right)
				if(i == 6 && j == 6){
					if(field[i][j].getValue() == 1){
						//if the corner field is occupied by a black stone, just count
						if(field[7][7].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorDiagonalCorner;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[7][7].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorDiagonalCorner;
						}
					}
				}
				
				//if a stone lies on a pre-diag-corner field (lower left)
				if(i == 1 && j == 6){
					if(field[i][j].getValue() == 1){
						//if the corner field is occupied by a black stone, just count
						if(field[0][7].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorDiagonalCorner;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[0][7].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorDiagonalCorner;
						}
					}
				}
				
				//if a stone lies on a pre-diag-corner field (upper right)
				if(i == 6 && j == 1){
					if(field[i][j].getValue() == 1){
						//if the corner field is occupied by a black stone, just count
						if(field[7][0].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorDiagonalCorner;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[7][0].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorDiagonalCorner;
						}
					}
				}
				
				//if a stone lies on a pre-corner field (upper left)
				if((i == 1 && j == 0)||(i == 0 && j == 1)){
					if(field[i][j].getValue() == 1){
						//if the corner field is occupied by a black stone, just count
						if(field[0][0].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorPreCorner;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[0][0].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorPreCorner;
						}
					}
				}
				
				//if a stone lies on a pre-corner field (upper right)
				if((i == 7 && j == 1)||(i == 6 && j == 0)){
					if(field[i][j].getValue() == 1){
						//if the corner field is occupied by a black stone, just count
						if(field[7][0].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorPreCorner;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[7][0].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorPreCorner;
						}
					}
				}
				
				//if a stone lies on a pre-corner field (lower right)
				if((i == 7 && j == 6)||(i == 6 && j == 7)){
					if(field[i][j].getValue() == 1){
						//if the corner field is occupied by a black stone, just count
						if(field[7][7].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorPreCorner;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[7][7].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorPreCorner;
						}
					}
				}
				
				//if a stone lies on a pre-corner field (lower left)
				if((i == 1 && j == 7)||(i == 0 && j == 6)){
					if(field[i][j].getValue() == 1){
						//if the corner field is occupied by a black stone, just count
						if(field[0][7].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorPreCorner;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[0][7].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorPreCorner;
						}
					}
				}
				
				//if a stone lies on a pre-edge field (left)
				if((i == 1 && (j == 2 || j == 3 || j == 4 || j == 5))){
					if(field[i][j].getValue() == 1){
						//if the edge field is occupied by a black stone, just count
						if(field[0][j-1].getValue() == 1 || field[0][j].getValue() == 1 || field[0][j+1].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorPreEdge;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[0][j-1].getValue() == -1 || field[0][j].getValue() == -1 || field[0][j+1].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorPreEdge;
						}
					}
				}
				
				//if a stone lies on a pre-edge field (right)
				if((i == 6 && (j == 2 || j == 3 || j == 4 || j == 5))){
					if(field[i][j].getValue() == 1){
						//if the edge field is occupied by a black stone, just count
						if(field[7][j-1].getValue() == 1 || field[7][j].getValue() == 1 || field[7][j+1].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorPreEdge;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[7][j-1].getValue() == -1 || field[7][j].getValue() == -1 || field[7][j+1].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorPreEdge;
						}
					}
				}
				
				//if a stone lies on a pre-edge field (top)
				if((j == 1 && (i == 2 || i == 3 || i == 4 || i == 5))){
					if(field[i][j].getValue() == 1){
						//if the edge field is occupied by a black stone, just count
						if(field[i-1][0].getValue() == 1 || field[i][0].getValue() == 1 || field[i+1][0].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorPreEdge;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[i-1][0].getValue() == -1 || field[i][0].getValue() == -1 || field[i+1][0].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorPreEdge;
						}
					}
				}
				
				//if a stone lies on a pre-edge field (bottom)
				if((j == 6 && (i == 2 || i == 3 || i == 4 || i == 5))){
					if(field[i][j].getValue() == 1){
						//if the edge field is occupied by a black stone, just count
						if(field[i-1][7].getValue() == 1 || field[i][7].getValue() == 1 || field[i+1][7].getValue() == 1){
							situationValue++;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue + addFactorPreEdge;
						}
					}
					else if(field[i][j].getValue() == -1){
						if(field[i-1][7].getValue() == -1 || field[i][7].getValue() == -1 || field[i+1][7].getValue() == -1){
							situationValue--;
						}
						//if the corner field or nearby is anything else
						else{
							situationValue = situationValue - addFactorPreEdge;
						}
					}
				}
				
				//At last, count the pieces in the sweet sixteen
				if(i == 2 && (j == 2 || j == 3 || j == 4 || j == 5)){
					if(field[i][j].getValue() == 1){
						situationValue++;
					}
					else if(field[i][j].getValue() == -1){
						situationValue--;
					}
				}
				if(i == 3 && (j == 2 || j == 3 || j == 4 || j == 5)){
					if(field[i][j].getValue() == 1){
						situationValue++;
					}
					else if(field[i][j].getValue() == -1){
						situationValue--;
					}
				}
				if(i == 4 && (j == 2 || j == 3 || j == 4 || j == 5)){
					if(field[i][j].getValue() == 1){
						situationValue++;
					}
					else if(field[i][j].getValue() == -1){
						situationValue--;
					}
				}
				if(i == 5 && (j == 2 || j == 3 || j == 4 || j == 5)){
					if(field[i][j].getValue() == 1){
						situationValue++;
					}
					else if(field[i][j].getValue() == -1){
						situationValue--;
					}
				}
			//-----------------end of loop------------------------------------	
			}
		}
		
		return situationValue;
	}
	
	/**
	 * Calculates, how many BlackStones are on the field
	 * 
	 * @return integer of black Stones on the field 
	 */
	private static int getNumberOfBlackStones(Board board){
		int numberOfBlackStones = 0;
		Field field[][] = board.getFields();
		
		for (int i = 0; i < 8; i++) {			
			for (int j = 0; j < 8; j++) {
				if(field[j][i].getValue() == 1){
					numberOfBlackStones++;
				}
			}
		}
		
		return numberOfBlackStones;
	}

	/**
	 * Calculates, how many moves can be made till the end of the game
	 * 
	 * @return integer of moves left
	 */
	private static int calculateRestMoves(Board board){
		int movesLeft = 0;
		Field field[][] = board.getFields();
		
		for (int i = 0; i < 8; i++) {			
			for (int j = 0; j < 8; j++) {
				if(field[j][i].getValue() == 0){
					movesLeft++;
				}
			}
		}
		
		return movesLeft;
	}
	
	/**
	 * Checks if one of the four (16) corner Fields are occupied (determination of midgame or endgame)
	 * 
	 * @return boolean true if a CornerField is occupied
	 */
	private static boolean checkForFourCornerFieldHits(Board board){
		boolean cornerHit = false;
		Field field[][] = board.getFields();
		
		//upper left corner
		if(field[0][0].getValue() != 0 || field[0][1].getValue() != 0 ||field[1][0].getValue() != 0 ||field[1][1].getValue() != 0){
			return true;
		}
		//upper right corner
		if(field[7][0].getValue() != 0 || field[7][1].getValue() != 0 ||field[6][0].getValue() != 0 ||field[6][1].getValue() != 0){
			return true;
		}
		//lower left corner
		if(field[0][7].getValue() != 0 || field[1][7].getValue() != 0 ||field[1][6].getValue() != 0 ||field[0][6].getValue() != 0){
			return true;
		}
		//lower right corner
		if(field[7][7].getValue() != 0 || field[7][6].getValue() != 0 ||field[6][7].getValue() != 0 ||field[6][6].getValue() != 0){
			return true;
		}
		
		return cornerHit;
	}
	
	/**
	 * Checks if one of the four edges are occupied (determination of midgame or endgame)
	 * 
	 * @return boolean true if an edge is occupied
	 */
	private static boolean checkForFourEdgesFieldHits(Board board){
		boolean edgeHit = false;
		Field field[][] = board.getFields();
		
		//left edge
		if(field[0][2].getValue() != 0 || field[0][3].getValue() != 0 ||field[0][4].getValue() != 0 ||field[0][5].getValue() != 0){
			return true;
		}
		//top edge
		if(field[2][0].getValue() != 0 || field[3][0].getValue() != 0 ||field[4][0].getValue() != 0 ||field[5][0].getValue() != 0){
			return true;
		}
		//bottom edge
		if(field[2][7].getValue() != 0 || field[3][7].getValue() != 0 ||field[4][7].getValue() != 0 ||field[5][7].getValue() != 0){
			return true;
		}
		//right edge
		if(field[7][2].getValue() != 0 || field[7][3].getValue() != 0 ||field[7][4].getValue() != 0 ||field[7][5].getValue() != 0){
			return true;
		}
		
		return edgeHit;
	}
	
	/**
	 * Checks if the fields before the Edges are occupied
	 * 
	 * @return boolean true if an pre-edge is hit
	 */
	private static boolean checkForFourPreEdgesFieldHits(Board board){
		boolean preEdgeHit = false;
		Field field[][] = board.getFields();
		
		//left pre-edge
		if(field[1][2].getValue() != 0 || field[1][3].getValue() != 0 ||field[1][4].getValue() != 0 ||field[1][5].getValue() != 0){
			return true;
		}
		//top pre-edge
		if(field[2][1].getValue() != 0 || field[3][1].getValue() != 0 ||field[4][1].getValue() != 0 ||field[5][1].getValue() != 0){
			return true;
		}
		//right pre-edge
		if(field[6][2].getValue() != 0 || field[6][3].getValue() != 0 ||field[6][4].getValue() != 0 ||field[6][5].getValue() != 0){
			return true;
		}
		//bottom pre-edge
		if(field[2][6].getValue() != 0 || field[3][6].getValue() != 0 ||field[4][6].getValue() != 0 ||field[5][6].getValue() != 0){
			return true;
		}
		
		return preEdgeHit;
	}
	
	/**
	 * This method updates the simBoard with the given fields
	 * 
	 * @param board is the board to do the update to
	 * 
	 * @param fields is an Arraylist of Fields to get the update values
	 * 
	 * @return the updated board
	 */
	public static Board updateSimBoard(Board board, ArrayList<Field> fields){
		Field origFields[][] = board.getFields();
		for (int i = 0; i< fields.size(); i++){
			origFields[fields.get(i).getColNum()][fields.get(i).getRowNum()] = fields.get(i);
		}
		
		return board;
	}

	/**
	 * gets the possible fields to play on. determined by:
	 * if a field is played and it hits other stones, it's good to play
	 * but only if it is empty
	 * 
	 * @param PlayerI player Interface for determining of which player is on
	 * 
	 * @return a List of Fields that can be played by the respective player
	 */
	public static List<Field> getPossibleFields(PlayerI player) {
		List<Field> possibleFields = new ArrayList<Field>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!getHits(fields[i][j], player).isEmpty()) {
					if(fields[i][j].getValue() == 0){
						possibleFields.add(fields[i][j]);
					}
					
				}
			}
		}
		return possibleFields;
	}


	// -------------------- !!! helper methods !!! --------------------
	/**
	 * prints out the possible hits
	 */
	private static void printPossibleHits(List<Field> possibleHits) {
		StringBuilder s = new StringBuilder();
		for (Field field : possibleHits) {
			s.append(Move.getMove(field.getRowNum(), field.getColNum()));
			s.append(", ");
		}
		System.out.println(s.toString());
	}
	
	/**
	 * prints out the board situation, if the program is running in debug mode
	 */
	public static void printBoard(Board board, PlayerI player){
		if (!debug) {
			return;
		}

		if (player == PlayerManager.getActivePlayer()) {
			System.out.println("after HitEnemyStones own");
		} else {
			System.out.println("after HitEnemyStones opposite");
		}

		Field field[][] = board.getFields();
		for (int i = 0; i < 8; i++) {
			System.out.println("");
			for (int j = 0; j < 8; j++) {
				System.out.print("|");
				if(field[j][i].getValue() == 1){
					System.out.print("x");
				}
				else if(field[j][i].getValue() == -1){
					System.out.print("o");
				}
				else if(field[j][i].getValue() == 0){
					System.out.print(" ");
				}
				else{
					System.out.print("d");
				}
				
			}
			System.out.print("|");
			
		}
		System.out.println("");
		
	}
	
	
	// -------------------- !!! private methods !!! --------------------
	
	/**
	 * collects all hits made with the given field and player on the actual board
	 * 
	 * @param field is the field played that made the hits possible, player is the player that played the move
	 * 
	 * @return an ArrayList of Fields that are hit by the move done in the field variable
	 */
	private static ArrayList<Field> getHits(Field field, PlayerI player) {
		ArrayList<Field> hitFields = new ArrayList<Field>();
		hitFields.addAll(hitBottomLeft(field, player));
		hitFields.addAll(hitLeft(field, player));
		hitFields.addAll(hitTopLeft(field, player));
		hitFields.addAll(hitTop(field, player));
		hitFields.addAll(hitTopRight(field, player));
		hitFields.addAll(hitRight(field, player));
		hitFields.addAll(hitBottomRight(field, player));
		hitFields.addAll(hitBottom(field, player));
		return hitFields;
	}

	/**
	 * Returns the neighbour top of the active field. If 
	 * the field is at the top border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |  r  |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	private static Field getNeighbourTop(Field field) {
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldRowNum == 0) {
			return null;
		}
		return fields[field.getColNum()][activeFieldRowNum - 1];
	}
	/**
	 * Returns the neighbour bottom to the active field. If 
	 * the field is at the bottom border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |  r  |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	private static Field getNeighbourBottom(Field field) {
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldRowNum == 7) {
			return null;
		}
		return fields[field.getColNum()][activeFieldRowNum + 1];
	}
	/**
	 * Returns the neighbour left to the active field. If 
	 * the field is at the left border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |  r  |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	private static Field getNeighbourLeft(Field field) {
		int activeFieldColNum = field.getColNum();
		if (activeFieldColNum == 0) {
			return null;
		}
		return fields[activeFieldColNum - 1][field.getRowNum()];
	}
	/**
	 * Returns the neighbour right to the active field. If 
	 * the field is at the right border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |  r  |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	private static Field getNeighbourRight(Field field) {
		int activeFieldColNum = field.getColNum();
		if (activeFieldColNum == 7) {
			return null;
		}
		return fields[activeFieldColNum + 1][field.getRowNum()];
	}
	/**
	 * Returns the neighbour top left to the active field. If 
	 * the field is at the top or left border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |  r  |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	private static Field getNeighbourTopLeft(Field field) {
		int activeFieldColNum = field.getColNum();
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldColNum == 0 || activeFieldRowNum == 0) {
			return null;
		}
		return fields[activeFieldColNum - 1][activeFieldRowNum - 1];
	}
	/**
	 * Returns the neighbour top right to the active field. If 
	 * the field is at the right or top border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |  r  |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	private static Field getNeighbourTopRight(Field field) {
		int activeFieldColNum = field.getColNum();
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldColNum == 7 || activeFieldRowNum == 0) {
			return null;
		}
		return fields[activeFieldColNum + 1][activeFieldRowNum - 1];
	}
	/**
	 * Returns the neighbour at bottom right to the active field. If 
	 * the field is at the right or bottom border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |  r  |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	private static Field getNeighbourBottomRight(Field field) {
		int activeFieldColNum = field.getColNum();
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldColNum == 7 || activeFieldRowNum == 7) {
			return null;
		}
		return fields[activeFieldColNum + 1][activeFieldRowNum + 1];
	}
	/**
	 * Returns the neighbour at bottom left to the active field. If 
	 * the field is at the left or bottom border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |  r  |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	private static Field getNeighbourBottomLeft(Field field) {
		int activeFieldColNum = field.getColNum();
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldColNum == 0 || activeFieldRowNum == 7) {
			return null;
		}
		return fields[activeFieldColNum - 1][activeFieldRowNum + 1];
	}
	
	/**
	 * Returns true if the bottom neighbour is an enemy.
	 * Example (a is the active field, r is the place an enemy could be on):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |  r  |     |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy at the bottom
	 */
	private static boolean checkNeighbourEnemiesBottom(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourBottom(field) != null){
			if(getNeighbourBottom(field).getValue() != player.getValue() && getNeighbourBottom(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("1");
			}
		}
		return enemyDetected;
	}
	/**
	 * Returns true if the bottom left neighbour is an enemy.
	 * Example (a is the active field, r is the place an enemy could be on):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |  r  |     |     |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy at the bottom left
	 */
	private static boolean checkNeighbourEnemiesBottomLeft(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourBottomLeft(field) != null){
			if(getNeighbourBottomLeft(field).getValue() != player.getValue() && getNeighbourBottomLeft(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("2");
			}
		}
		return enemyDetected;
	}
	/**
	 * Returns true if the left neighbour is an enemy.
	 * Example (a is the active field, r is the place an enemy could be on):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |  r  |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy at the left
	 */
	private static boolean checkNeighbourEnemiesLeft(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourLeft(field) != null){
			if(getNeighbourLeft(field).getValue() != player.getValue() && getNeighbourLeft(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("3");
			}
		}
		return enemyDetected;
	}
	/**
	 * Returns true if the top left neighbour is an enemy.
	 * Example (a is the active field, r is the place an enemy could be on):
	 * |-----|-----|-----|
	 * |  r  |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy at the top left
	 */
	private static boolean checkNeighbourEnemiesTopLeft(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourTopLeft(field) != null){
			if(getNeighbourTopLeft(field).getValue() != player.getValue() && getNeighbourTopLeft(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("4");
			}
		}
		return enemyDetected;
	}
	/**
	 * Returns true if the top neighbour is an enemy.
	 * Example (a is the active field, r is the place an enemy could be on):
	 * |-----|-----|-----|
	 * |     |  r  |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |  r  |     |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy at the top
	 */
	private static boolean checkNeighbourEnemiesTop(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourTop(field) != null){
			if(getNeighbourTop(field).getValue() != player.getValue() && getNeighbourTop(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("5");
			}
		}
		return enemyDetected;
	}
	/**
	 * Returns true if the top right neighbour is an enemy.
	 * Example (a is the active field, r is the place an enemy could be on):
	 * |-----|-----|-----|
	 * |     |     |  r  |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy at the top right 
	 */
	private static boolean checkNeighbourEnemiesTopRight(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourTopRight(field) != null){
			if(getNeighbourTopRight(field).getValue() != player.getValue() && getNeighbourTopRight(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("6");
			}
		}
		return enemyDetected;
	}
	/**
	 * Returns true if the right neighbour is an enemy.
	 * Example (a is the active field, r is the place an enemy could be on):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |  r  |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy at the bottom right
	 */
	private static boolean checkNeighbourEnemiesRight(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourRight(field) != null){
			if(getNeighbourRight(field).getValue() != player.getValue() && getNeighbourRight(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("7");
			}
		}
		return enemyDetected;
	}
	/**
	 * Returns true if the bottom right neighbour is an enemy.
	 * Example (a is the active field, r is the place an enemy could be on):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |  r  |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy at the bottom  right
	 */
	private static boolean checkNeighbourEnemiesBottomRight(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourBottomRight(field) != null){
			if(getNeighbourBottomRight(field).getValue() != player.getValue() && getNeighbourBottomRight(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("8");
			}
		}
		return enemyDetected;
	}
	
	
	/**
	 * Returns true if hit(s) are possible towards the bottom.
	 * Example (a is the active field, r is the place the enemy is, i is where your stone is):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |  r  |     |
	 * |-----|-----|-----|
	 * |     |  r  |     |
	 * |-----|-----|-----|
	 * |     |  i  |     |
	 * |-----|-----|-----|
	 * @return false if no stones can be hitten
	 */
	private static boolean checkBottomHit(Field field, PlayerI player){
		boolean hit = false;
		if(checkNeighbourEnemiesBottom(field, player)){
			while(checkNeighbourEnemiesBottom(field, player)){
				field = getNeighbourBottom(field);
			}
			field = getNeighbourBottom(field);
			if (field == null) {
				return false;
			}
			if (field.getValue() == player.getValue()){
				hit = true;
				//System.out.println("hit");
			}
		}
		return hit;
	}
	
	/**
	 * Returns true if hit(s) are possible towards the bottom left.
	 * Example (a is the active field, r is the place the enemy is, i is where your stone is):
	 * |-----|-----|-----|-----|-----|
	 * |     |     |     |  a  |     |
	 * |-----|-----|-----|-----|-----|
	 * |     |     |  r  |     |     |
	 * |-----|-----|-----|-----|-----|
	 * |     |  r  |     |     |     |
	 * |-----|-----|-----|-----|-----|
	 * |  i  |     |     |     |     |
	 * |-----|-----|-----|-----|-----|
	 * @return false if no stones can be hitten
	 */
	private static boolean checkBottomLeftHit(Field field, PlayerI player){
		boolean hit = false;
		if(checkNeighbourEnemiesBottomLeft(field, player)){
			while(checkNeighbourEnemiesBottomLeft(field, player)){
				field = getNeighbourBottomLeft(field);
			}
			field = getNeighbourBottomLeft(field);
			if (field == null) {
				return false;
			}
			if (field.getValue() == player.getValue()){
				hit = true;
				//System.out.println("hit");
			}
		}
		return hit;
	}
	
	/**
	 * Returns true if hit(s) are possible towards the Left.
	 * Example (a is the active field, r is the place the enemy is, i is where your stone is):
	 * |-----|-----|-----|-----|
	 * |     |     |     |     |
	 * |-----|-----|-----|-----|
	 * |  i  |  r  |  r  |  a  |
	 * |-----|-----|-----|-----|
	 * |     |     |     |     |
	 * |-----|-----|-----|-----|
	 * |     |     |     |     |
	 * |-----|-----|-----|-----|
	 * @return false if no stones can be hitten
	 */
	private static boolean checkLeftHit(Field field, PlayerI player){
		boolean hit = false;
		if(checkNeighbourEnemiesLeft(field, player)){
			while(checkNeighbourEnemiesLeft(field, player)){
				field = getNeighbourLeft(field);
			}
			field = getNeighbourLeft(field);
			if (field == null) {
				return false;
			}
			if (field.getValue() == player.getValue()){
				hit = true;
				//System.out.println("hit");
			}
		}
		return hit;
	}
	

	/**
	 * Returns true if hit(s) are possible towards the Top Left.
	 * Example (a is the active field, r is the place the enemy is, i is where your stone is):
	 * |-----|-----|-----|-----|
	 * |  i  |     |     |     |
	 * |-----|-----|-----|-----|
	 * |     |  r  |     |     |
	 * |-----|-----|-----|-----|
	 * |     |     |  r  |     |
	 * |-----|-----|-----|-----|
	 * |     |     |     |  a  |
	 * |-----|-----|-----|-----|
	 * @return false if no stones can be hitten
	 */
	private static boolean checkTopLeftHit(Field field, PlayerI player){
		boolean hit = false;
		if(checkNeighbourEnemiesTopLeft(field, player)){
			while(checkNeighbourEnemiesTopLeft(field, player)){
				field = getNeighbourTopLeft(field);
			}
			field = getNeighbourTopLeft(field);
			if (field == null) {
				return false;
			}
			if (field.getValue() == player.getValue()){
				hit = true;
				//System.out.println("hit");
			}
		}
		return hit;
	}
	
	/**
	 * Returns true if hit(s) are possible towards the Top.
	 * Example (a is the active field, r is the place the enemy is, i is where your stone is):
	 * |-----|-----|-----|-----|
	 * |     |     |     |  i  |
	 * |-----|-----|-----|-----|
	 * |     |     |     |  r  |
	 * |-----|-----|-----|-----|
	 * |     |     |     |  r  |
	 * |-----|-----|-----|-----|
	 * |     |     |     |  a  |
	 * |-----|-----|-----|-----|
	 * @return false if no stones can be hitten
	 */
	private static boolean checkTopHit(Field field, PlayerI player){
		boolean hit = false;
		if(checkNeighbourEnemiesTop(field, player)){
			while(checkNeighbourEnemiesTop(field, player)){
				field = getNeighbourTop(field);
			}
			field = getNeighbourTop(field);
			if (field == null) {
				return false;
			}
			if (field.getValue() == player.getValue()){
				hit = true;
				//System.out.println("hit");
			}
		}
		return hit;
	}
	
	/**
	 * Returns true if hit(s) are possible towards the Top right.
	 * Example (a is the active field, r is the place the enemy is, i is where your stone is):
	 * |-----|-----|-----|-----|
	 * |     |     |     |  i  |
	 * |-----|-----|-----|-----|
	 * |     |     |  r  |     |
	 * |-----|-----|-----|-----|
	 * |     |  r  |     |     |
	 * |-----|-----|-----|-----|
	 * |  a  |     |     |     |
	 * |-----|-----|-----|-----|-----|
	 * @return false if no stones can be hitten
	 */
	private static boolean checkTopRightHit(Field field, PlayerI player){
		boolean hit = false;
		if(checkNeighbourEnemiesTopRight(field, player)){
			while(checkNeighbourEnemiesTopRight(field, player)){
				field = getNeighbourTopRight(field);
			}
			field = getNeighbourTopRight(field);
			if (field == null) {
				return false;
			}
			if (field.getValue() == player.getValue()){
				hit = true;
				//System.out.println("hit");
			}
		}
		return hit;
	}
	
	
	
	
	/**
	 * Returns true if hit(s) are possible towards the  right.
	 * Example (a is the active field, r is the place the enemy is, i is where your stone is):
	 * |-----|-----|-----|-----|-----|
	 * |     |     |     |     |     |
	 * |-----|-----|-----|-----|-----|
	 * |     |  a  |  r  |  r  |  i  |
	 * |-----|-----|-----|-----|-----|
	 * |     |     |     |     |     |
	 * |-----|-----|-----|-----|-----|
	 * @return false if no stones can be hitten
	 */
	private static boolean checkRightHit(Field field, PlayerI player){
		boolean hit = false;
		if(checkNeighbourEnemiesRight(field, player)){
			while(checkNeighbourEnemiesRight(field, player)){
				field = getNeighbourRight(field);
			}
			field = getNeighbourRight(field);
			if (field == null) {
				return false;
			}
			if (field.getValue() == player.getValue()){
				hit = true;
				//System.out.println("hit");
			}
		}
		return hit;
	}
	
	/**
	 * Returns true if hit(s) are possible towards the bottom right.
	 * Example (a is the active field, r is the place the enemy is, i is where your stone is):
	 * |-----|-----|-----|-----|
	 * |  a  |     |     |     |
	 * |-----|-----|-----|-----|
	 * |     |  r  |     |     |
	 * |-----|-----|-----|-----|
	 * |     |     |  r  |     |
	 * |-----|-----|-----|-----|
	 * |     |     |     |  i  |
	 * |-----|-----|-----|-----|
	 * @return false if no stones can be hitten
	 */
	private static boolean checkBottomRightHit(Field field, PlayerI player){
		boolean hit = false;
		if(checkNeighbourEnemiesBottomRight(field, player)){
			while(checkNeighbourEnemiesBottomRight(field, player)){
				field = getNeighbourBottomRight(field);
			}
			field = getNeighbourBottomRight(field);
			if (field == null) {
				return false;
			}
			if (field.getValue() == player.getValue()){
				hit = true;
				//System.out.println("hit");
			}
		}
		return hit;
	}

	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in bottom direction 
	 * 
	 * @return ArrayList of Fields which stones are beaten
	 */
	
	private static ArrayList<Field> hitBottom(Field field, PlayerI player){
		ArrayList<Field> hitFields = new ArrayList<Field>();
		//If the neighbour is an enemy, iterate towards direction till an own stone appears
		if(checkBottomHit(field, player)){
			while(checkNeighbourEnemiesBottom(field, player)){
				field = getNeighbourBottom(field);
				hitFields.add(field);
			}
		}

		return hitFields;	
	}

	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in bottom left direction 
	 * 
	 * @return ArrayList of Fields which stones are beaten
	 */
	
	private static ArrayList<Field> hitBottomLeft(Field field, PlayerI player){
		ArrayList<Field> hitFields = new ArrayList<Field>();
		//If the neighbour is an enemy, iterate towards direction till an own stone appears
		if(checkBottomLeftHit(field, player)){
			while(checkNeighbourEnemiesBottomLeft(field, player)){
				field = getNeighbourBottomLeft(field);
				hitFields.add(field);
			}
		}

		return hitFields;	
	}
	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in left direction 
	 * 
	 * @return ArrayList of Fields which stones are beaten
	 */
	
	private static ArrayList<Field> hitLeft(Field field, PlayerI player){
		ArrayList<Field> hitFields = new ArrayList<Field>();
		//If the neighbour is an enemy, iterate towards direction till an own stone appears
		if(checkLeftHit(field, player)){
			while(checkNeighbourEnemiesLeft(field, player)){
				field = getNeighbourLeft(field);
				hitFields.add(field);
			}
		}

		return hitFields;	
	}
	
	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in top left direction 
	 * 
	 * @return ArrayList of Fields which stones are beaten
	 */
	
	private static ArrayList<Field> hitTopLeft(Field field, PlayerI player){
		ArrayList<Field> hitFields = new ArrayList<Field>();
		//If the neighbour is an enemy, iterate towards direction till an own stone appears
		if(checkTopLeftHit(field, player)){
			while(checkNeighbourEnemiesTopLeft(field, player)){
				field = getNeighbourTopLeft(field);
				hitFields.add(field);
			}
		}

		return hitFields;	
	}
	
	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in top direction 
	 * 
	 * @return ArrayList of Fields which stones are beaten
	 */
	
	private static ArrayList<Field> hitTop(Field field, PlayerI player){
		ArrayList<Field> hitFields = new ArrayList<Field>();
		//If the neighbour is an enemy, iterate towards direction till an own stone appears
		if(checkTopHit(field, player)){
			while(checkNeighbourEnemiesTop(field, player)){
				field = getNeighbourTop(field);
				hitFields.add(field);
			}
		}

		return hitFields;	
	}
	
	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in top right direction
	 *  
	 * @return ArrayList of Fields which stones are beaten
	 */
	
	private static ArrayList<Field> hitTopRight(Field field, PlayerI player){
		ArrayList<Field> hitFields = new ArrayList<Field>();
		//If the neighbour is an enemy, iterate towards direction till an own stone appears
		if(checkTopRightHit(field, player)){
			while(checkNeighbourEnemiesTopRight(field, player)){
				field = getNeighbourTopRight(field);
				hitFields.add(field);
			}
		}

		return hitFields;	
	}
	
	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in right direction 
	 * 
	 * @return ArrayList of Fields which stones are beaten
	 */
	
	private static ArrayList<Field> hitRight(Field field, PlayerI player){
		ArrayList<Field> hitFields = new ArrayList<Field>();
		//If the neighbour is an enemy, iterate towards direction till an own stone appears
		if(checkRightHit(field, player)){
			while(checkNeighbourEnemiesRight(field, player)){
				field = getNeighbourRight(field);
				hitFields.add(field);
			}
		}

		return hitFields;	
	}
	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in bottom right direction 
	 * 
	 * @return ArrayList of Fields which stones are beaten
	 */
	
	private static ArrayList<Field> hitBottomRight(Field field, PlayerI player){
		ArrayList<Field> hitFields = new ArrayList<Field>();
		//If the neighbour is an enemy, iterate towards direction till an own stone appears
		if(checkBottomRightHit(field, player)){
			while(checkNeighbourEnemiesBottomRight(field, player)){
				field = getNeighbourBottomRight(field);
				hitFields.add(field);
			}
		}

		return hitFields;	
	}
	
}
