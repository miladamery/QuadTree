package com.milad.api.graph.quadtree.nodes;

import com.milad.api.graph.quadtree.abstracts.Body;
import com.milad.api.graph.quadtree.helper.SuperNodeCenter;
import com.milad.api.utility.math.vectors.vector2D.Vector2D;

/**
 *
 * @author PC
 */
public class QuadTreeSuperNode implements Body{

    private SuperNodeCenter center;

    public QuadTreeSuperNode() {
        center = new SuperNodeCenter();
    }
    
    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector2D getPosition() {
        return center.getCenter();
    }
    
    public void changeCenter(Vector2D verticePos){
        this.center.changeCenter(verticePos);
    }
    
    public int getSuperNodeBodySize(){
        return this.center.getSumOfVerticeNumbers();
    }
}
