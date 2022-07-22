package com.milad.api.graph.quadtree.helper;

import com.milad.api.utility.math.vectors.vector2D.Vector2D;

/**
 * Quad and Oct trees can be used for approximating long range force calculation between two bodies in O(n * logn ) time complexity
 * both of these trees have two kind of nodes, leafs which contain bodies and parent nodes which don't have bodies but have children
 * this approximation is determined by concept of super nodes which is proportional to each body which is under calculation
 * we define super node as "a node which is far enough from our special body which we want approximate forces"
 * when a node in tree becomes super in proportional to our original body then we can ignore all bodies that reside in this node
 * and consider them as one
 * The way we approximate forces is we ignore all child bodies in this node and instead we use these ignored child bodies average
 * force related properties
 * for example if we are calculating one stars repulsive force and others, we calculate their average mass and average position
 * instead of calculating one by one repulsive force calculation
 * this class is intended for doing this job
 * @author Milad
 */
public class SuperNodeCenter {
    
    private Vector2D sumOfAllVerticePositions;
    private int sumOfVerticeNumbers;

    public SuperNodeCenter() {
        sumOfAllVerticePositions = new Vector2D();
        sumOfVerticeNumbers = 0;
    }
    
    public void changeCenter(Vector2D verticePos){
        this.sumOfAllVerticePositions.setxAxis(this.sumOfAllVerticePositions.getxAxis() + verticePos.getxAxis());
        this.sumOfAllVerticePositions.setyAxis(this.sumOfAllVerticePositions.getyAxis() + verticePos.getyAxis());
        this.sumOfVerticeNumbers++;
        
    }
    public Vector2D getCenter(){
        int x = (int) (sumOfAllVerticePositions.getxAxis() / this.sumOfVerticeNumbers);
        int y = (int) (sumOfAllVerticePositions.getyAxis() / this.sumOfVerticeNumbers);
        return new Vector2D(x, y);
    }

    public int getSumOfVerticeNumbers() {
        return sumOfVerticeNumbers;
    }
    
    public void toString() {}
    public void f1ToString() {}


}
