package ch.i10a.reversi.gameplay;

import java.awt.Color;
import java.util.ArrayList;

import ch.i10a.reversi.gui.Field;

/**
 * Handler for performing and checking possible Moves.
 *
 */
public class MoveHandler {

	private static Field[][] fields;

	/**
	 * Registers the fields of the board for later access.
	 */
	public static void registerFields(Field[][] fields) {
		MoveHandler.fields = fields;
	}

	// -------------------- public methods --------------------
	/**
	 * Performs the move on the active field. This method runs synchronized
	 * to avoid side-effects.
	 * 
	 * @param activeField the field on which the move is being performed.
	 */
	public synchronized static void doMove(Field activeField) {
		PlayerI activePlayer = PlayerManager.getActivePlayer();

		//check on empty Field
		if(activeField.getValue() == 0){
			//check on enemies in the surrounding fields, no enemy, no further check
			if(MoveHandler.checkNeighbourEnemies(activeField, activePlayer)){
				if(MoveHandler.checkHit(activeField, activePlayer)){
					// updates the value depending on the player
					activeField.setValue(activePlayer.getColor() == Color.WHITE ? -1 : 1);
					MoveHandler.hitEnemyStones(activeField,activePlayer);
					
					// updates
					activeField.repaint();
					PlayerManager.setUnPass();
					PlayerManager.nextPlayer();
				}
			}
		}

		if(!MoveHandler.checkWholeFieldHit()){
			PlayerManager.setPass();
			PlayerManager.nextPlayer();
			if(!MoveHandler.checkWholeFieldHit()){
				PlayerManager.setDoublePass();
			}
			
		}
	}

	/**
	 * Returns an ArrayList of Fields, which represents the beaten stones in all directions 
	 * @return ArrayList of Fields which stones are beaten
	 */
	public static void hitEnemyStones(Field field, PlayerI player){
		ArrayList<Field> hitFields = getHits(field, player);

		// update the stones count of this player
		// "+ 1" because one stone is set by the player!
		PlayerManager.updateStones(hitFields.size() + 1);

		int j = 0;
		while (j < hitFields.size()) {
			fields[hitFields.get(j).getColNum()][hitFields.get(j).getRowNum()].setValue(player.getValue()*2);
			hitFields.get(j).update(PlayerManager.getActivePlayer().getValue());
			j++;
		}
		
	}

	/**
	 * Runs through the board and collects the possible 
	 * fields to hit for every field.
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
	






	// -------------------- !!! private methods !!! --------------------
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
