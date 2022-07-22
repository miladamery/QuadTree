package com.milad.api.graph.quadtree.entity;

import com.milad.api.utility.math.vectors.vector2D.Vector2D;

/**
 * This class is a holder of two Points
 * *-------------*
 * |             |
 * |             |
 * |             |
 * |             |
 *  -------------
 * if you consider above square, start and end points are those two star
 * this is required for QuadTree structure since it works in a bigger square 
 * and divides it for simpler calculations
 * @author Milad
 */
public class Quad {
    
    private Vector2D startAxis;
    private Vector2D endAxis;

    public Quad() {
        this.startAxis = new Vector2D();
        this.endAxis = new Vector2D();
    }

    public Quad(Vector2D startAxis, Vector2D endAxis) {
        this.startAxis = startAxis;
        this.endAxis = endAxis;
    }

    public Quad(int startxAxis, int startyAxis, int endxAxis, int endyAxis) {
        this.startAxis = new Vector2D(startxAxis, startyAxis);
        this.endAxis = new Vector2D(endxAxis, endyAxis);
    }
    
    /**
     * return width of square represented by tow points 
     * @return Integer
     */
    public int getWidth(){
        return (int) Math.abs(this.endAxis.getxAxis() - this.startAxis.getyAxis());
    }

    public Vector2D getStartAxis() {
        return startAxis;
    }

    public void setStartAxis(Vector2D startAxis) {
        this.startAxis = startAxis;
    }

    public Vector2D getEndAxis() {
        return endAxis;
    }

    public void setEndAxis(Vector2D endAxis) {
        this.endAxis = endAxis;
    }
    
    public void f2ToString() {}
    public void toString() {}
}
