package com.milad.api.graph.quadtree.nodes;

import java.util.ArrayList;

import com.milad.api.graph.quadtree.abstracts.Body;
import com.milad.api.graph.quadtree.exceptions.ExternalNodeIsNotDenseLeaf;

/*
 * The MIT License
 *
 * Copyright 2015 PC.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 * both quad tree and octree data structures have leafs with two states
 * one of states is for normal situation of these trees, leafs have one body
 * in this case, tree can expand too much deep for some situations which result in very bad performance of algorithm
 * for this issues we can set a thresh hold that can stop tree from expanding too much deep
 * when we reach this thresh hold we switch leaf node to dense lead node
 * "dense leaf node" is a node with more than one body
 * for dense leaf, force calculation becomes normal force calculation between two nodes
 * 
 * @author Milad
 */
public class ExternalNode {
    
    private ArrayList<Body> body;
    private boolean isDenseLeaf;

    /**
     * Initializing class
     */
    public ExternalNode() {
        body = new ArrayList<>();
        isDenseLeaf = false;
    }
    
    /**
     * if there isn't set any body for this leaf it will return false else true
     * @return boolean
     */
    public boolean isEmpty(){
        return this.body.isEmpty();
    }
    
    /**
     * if this leaf is a dense leaf node adds new node to its array list
     * if it is not a dense leaf it will throw an ExternalNodeIsNotDenseLeaf
     * @param newBody
     * @return 
     * @throws OctQuadTree.Exceptions.ExternalNodeIsNotDenseLeaf 
     */
    public boolean addToDenseLeaf(Body newBody) throws ExternalNodeIsNotDenseLeaf{
        if(!this.isDenseLeaf)
            throw new ExternalNodeIsNotDenseLeaf("This leaf is not set as dense leaf!");
        return this.body.add(newBody);
    }
    
    /**
     * return the first node in array
     * use this for when node state is normal
     * @return T
     */
    public Body getBody(){
        return this.body.get(0);
    }
    
    /**
     * sets the first body in array
     * use this for when node state is normal
     * @param body 
     */
    public void setBody(Body body){
        this.body.add(0, body);
    }

    public boolean isIsDenseLeaf() {
        return isDenseLeaf;
    }

    public void setIsDenseLeaf(boolean isDenseLeaf) {
        this.isDenseLeaf = isDenseLeaf;
    }

    /**
     * Get all bodies set for this node
     * use this when leaf state is "dense leaf"
     * @return 
     */
    public ArrayList<Body> getAllBodies() {
        return body;
    }
    
    
}
