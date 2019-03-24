package com.jme.mygame;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;

public class BasicRules {

    public void move(Board board, Ray ray, CollisionResults results, Builder selected) {
        board.getBoardNode().collideWith(ray, results); // Cursor is going to collide with board tiles
        if(results.size() > 0) // cursor collided with the board
        {
            CollisionResult closest = results.getClosestCollision(); // closest tile that was hit
            for(Vector2f coordinates : selected.getAdjacentTiles())  // Target comparison
            {
                Board.BoardTile target = board.collidingTile((int)coordinates.x, (int)coordinates.y, closest); // an attempt of setting the target tile
                if(target != null){
                    if(target.getCoordinates().equals(coordinates)) //if target tile matches one of available tiles (by height difference and being occupied)
                    {
                        board.showAvailableTiles(selected, false);
                        board.getTile(selected.getColumn(), selected.getRow()).setMovable(true); // Current tile is no longer occupied
                        board.getTile(selected.getColumn(), selected.getRow()).setBuildable(true); // Current tile is no longer occupied
                        selected.setFloorLvl(target.getHeight());
                        selected.getBuilderModel().setLocalTranslation(-52.0f + coordinates.x*20.0f, Builder.OFFSETS[selected.getFloorLvl().height],-52.0f+coordinates.y*20.f);
                        selected.setCoordinates((int)coordinates.x, (int)coordinates.y);
                        board.getTile((int)coordinates.x, (int)coordinates.y).setMovable(false);   // Target tile is going to be occupied now
                        board.getTile((int)coordinates.x, (int)coordinates.y).setBuildable(false);   // Target tile is going to be occupied now
                        selected.setMoved(true);
                        break;
                    }
                }
            }

        }
    }
    public void build(Board board, Ray ray, CollisionResults results, Builder selected) {
        board.getBoardNode().collideWith(ray, results); // Cursor is going to collide with board tiles
        if(results.size() > 0) // cursor collided with the board
        {
            CollisionResult closest = results.getClosestCollision(); // closest tile that was hit
            for(Vector2f coordinates : selected.getAdjacentTiles())  // Target comparison
            {
                Board.BoardTile target = board.collidingTile((int)coordinates.x, (int)coordinates.y, closest); // an attempt of setting the target tile
                if(target != null){
                    if(target.getCoordinates().equals(coordinates)) //if target tile matches one of available tiles (by height difference and being occupied)
                    {
                        board.showAvailableTiles(selected, false);
                        board.buildTile((int)coordinates.x, (int)coordinates.y);
                        selected.setBuilt(true);
                        break;
                    }
                }
            }

        }
    }

    public boolean isWinAccomplished(Builder builder)
    {
        if(builder.getFloorLvl().equals(Floor.SECOND))
            return true;
        else return false;
    }
}
