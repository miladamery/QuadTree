package com.milad.api.graph.quadtree.nodes;

import com.milad.api.graph.quadtree.abstracts.Body;
import com.milad.api.graph.quadtree.entity.Quad;
import com.milad.api.graph.quadtree.exceptions.ExternalNodeBodyIsSet;
import com.milad.api.graph.quadtree.exceptions.ExternalNodeIsNotDenseLeaf;
import com.milad.api.graph.quadtree.exceptions.NodeIsInternalException;
import com.milad.api.utility.math.vectors.vector2D.Vector2D;

/**
 * Quad tree nodes have two states, leaf and parent leafs have bodies and
 * parents are for determining where is a body or in other word, this trees have
 * internal and external nodes
 * internal node are like leafs and externals are like parents
 *
 * Quad and Oct trees can be used for approximating long range force calculation
 * between two bodies in O(n * log n ) time complexity
 * this approximation is determined by concept of
 * super nodes which is proportional to each body which is under calculation
 * we define super node as "a node which is far enough from our special body which we want approximate forces"
 *
 * this class job is providing needed operations for creating quad tree
 *
 * @author Milad
 */
public class QuadTreeNode {

    private QuadTreeNode[] children;
    private QuadTreeSuperNode superNode;
    private ExternalNode externalNode;
    private final Quad quad;
    private int depth;
    private final int denseLeafThreshHold = 8;

    /**
     * @param startPointX
     * @param startPointY
     * @param endPointX
     * @param endPointY
     * @param depth
     */
    public QuadTreeNode(int startPointX, int startPointY, int endPointX, int endPointY, int depth) {
        this.children = null;
        this.superNode = null;
        this.externalNode = new ExternalNode();
        this.quad = new Quad(startPointX, startPointY, endPointX, endPointY);
        this.depth = depth;
        if (depth == this.denseLeafThreshHold) {
            externalNode.setIsDenseLeaf(true);
        }
    }

    /**
     * if node is considered as super node this is required to change its center
     * if new body is going to add to it
     *
     * @param axis
     */
    public void changeCenter(Vector2D axis) {
        this.superNode.changeCenter(axis);
    }

    /**
     * Switch node state from external to internal
     *
     * @return Body2D
     * @throws NodeIsInternalException
     */
    public Body makeThisNodeInternal() throws NodeIsInternalException {
        if (isInternal()) {
            throw new NodeIsInternalException("Node is Already Internal!");
        }
        this.children = new QuadTreeNode[4];
        this.superNode = new QuadTreeSuperNode();
        int splitX = (int) ((this.quad.getStartAxis().getxAxis() + this.quad.getEndAxis().getxAxis()) / 2);
        int splitY = (int) (this.quad.getStartAxis().getyAxis() + this.quad.getEndAxis().getyAxis() / 2);
        this.children[0] = new QuadTreeNode(
                (int) this.quad.getStartAxis().getxAxis(),
                (int) this.quad.getStartAxis().getyAxis(),
                splitX, splitY, depth + 1);
        this.children[1] = new QuadTreeNode(
                splitX,
                (int) this.quad.getStartAxis().getyAxis(),
                (int) this.quad.getEndAxis().getxAxis(),
                splitY, depth + 1);
        this.children[2] = new QuadTreeNode(
                (int) this.quad.getStartAxis().getxAxis(),
                splitY,
                splitX,
                (int) this.quad.getEndAxis().getyAxis(), depth + 1);
        this.children[3] = new QuadTreeNode(splitX, splitY,
                (int) this.quad.getEndAxis().getxAxis(),
                (int) this.quad.getEndAxis().getyAxis(), depth + 1);
        Body body = this.externalNode.getBody();
        this.externalNode = null;

        return body;

    }

    /**
     * if this node is normal external(it's not dense leaf) this will set given
     * body2D as it's body value
     *
     * @param body
     * @throws NodeIsInternalException
     * @throws ExternalNodeBodyIsSet
     */
    public void setThisExternalNodeBody(Body body) throws NodeIsInternalException, ExternalNodeBodyIsSet {
        if (isInternal()) {
            throw new NodeIsInternalException("Node is internal. Can not set body for this Node!");
        }
        if (!this.isEmptyExternal()) {
            throw new ExternalNodeBodyIsSet("External node body is set!");
        }
        this.externalNode.setBody(body);
    }

    /**
     * 
     * @param body
     * @throws ExternalNodeIsNotDenseLeaf 
     */
    public void setThisDenseLeafNodeBody(Body body) throws ExternalNodeIsNotDenseLeaf{
        this.externalNode.addToDenseLeaf(body);
    }
    
    /**
     * returns the number of child that body belongs to
     *
     * @param body
     * @return Integer between 0-3 which tells which child Body2D value belongs
     * to
     */
    public int getNodeIndex(Body body) {
        int splitX = (int) ((this.quad.getStartAxis().getxAxis() + this.quad.getEndAxis().getxAxis()) / 2);
        int splitY = (int) (this.quad.getStartAxis().getyAxis() + this.quad.getEndAxis().getyAxis()) / 2;
        int i = (body.getPosition().getxAxis() >= splitX ? 1 : 0) + (body.getPosition().getyAxis() >= splitY ? 2 : 0);
        return i;
    }

    /**
     * checks if node is simple external
     *
     * @return boolean
     */
    public boolean isExternal() {
        return this.children == null;
    }

    /**
     * checks if node is internal
     *
     * @return boolean
     */
    public boolean isInternal() {
        return this.children != null;
    }

    /**
     * checks if simple external node is empty
     *
     * @return boolean
     */
    public boolean isEmptyExternal() {
        return this.externalNode.isEmpty();
    }

    /**
     * checks if node is denseLeaf
     *
     * @return boolean
     */
    public boolean isDenseLeaf() {
        return this.externalNode.isIsDenseLeaf();
    }

    /**
     * returns center of super node
     * @return 
     */
    public Vector2D getCenterOfSuperNode() {
        return this.superNode.getPosition();
    }

    /**
     * returns quad width
     * @return 
     */
    public int getWidth() {
        return this.quad.getWidth();
    }

    /**
     * returns how many bodies are in this node
     * @return 
     */
    public int getSize() {
        return this.superNode.getSuperNodeBodySize();
    }

    public QuadTreeSuperNode getSuperNode() {
        return superNode;
    }

    public QuadTreeNode[] getChildren() {
        return children;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public ExternalNode getExternalNode() {
        return externalNode;
    }

}
