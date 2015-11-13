package com.milad.api.graph.quadtree;

import com.milad.api.graph.quadtree.abstracts.Body;
import com.milad.api.graph.quadtree.exceptions.ExternalNodeBodyIsSet;
import com.milad.api.graph.quadtree.exceptions.ExternalNodeIsNotDenseLeaf;
import com.milad.api.graph.quadtree.exceptions.NodeIsInternalException;
import com.milad.api.graph.quadtree.nodes.QuadTreeNode;
import com.milad.api.utility.math.vectors.vector2D.Vec2DMath;

/**
 * This class implements quad tree according to its recursive algorithm also it
 * can calculate long range force on bodies
 *
 * @author Milad
 */
public abstract class QuadTree {

    private QuadTreeNode root;
    private int frameMaxX;
    private int frameMaxY;
    private final float theta = (float) 0.6;

    public QuadTree(int frameMaxX, int frameMaxY) {
        this.frameMaxX = frameMaxX;
        this.frameMaxY = frameMaxY;
        this.root = new QuadTreeNode(0, 0, frameMaxX, frameMaxY, 0);
    }

    /**
     *
     * @param body
     * @throws ExternalNodeIsNotDenseLeaf
     * @throws NodeIsInternalException
     * @throws ExternalNodeBodyIsSet
     */
    public void insert(Body body) throws ExternalNodeIsNotDenseLeaf, NodeIsInternalException, ExternalNodeBodyIsSet {
        QuadTreeNode tempNode = this.root;
        this.insert(tempNode, body);
    }

    /**
     *
     * @param node
     * @param body
     * @throws ExternalNodeIsNotDenseLeaf
     * @throws NodeIsInternalException
     * @throws ExternalNodeBodyIsSet
     */
    private void insert(QuadTreeNode node, Body body) throws ExternalNodeIsNotDenseLeaf, NodeIsInternalException, ExternalNodeBodyIsSet {

        int i1 = node.getNodeIndex(body);
        if (node.isExternal()) {
            if (node.isDenseLeaf()) {
                node.setThisDenseLeafNodeBody(body);
            } else if (node.isEmptyExternal()) {
                node.setThisExternalNodeBody(body);
            } else {
                Body secndBody = node.makeThisNodeInternal();
                node.changeCenter(body.getPosition());
                insert(node.getChildren()[i1], body);
                int i2 = node.getNodeIndex(secndBody);
                node.changeCenter(secndBody.getPosition());
                insert(node.getChildren()[i2], secndBody);

            }
        } else {
            node.changeCenter(body.getPosition());
            insert(node.getChildren()[i1], body);
        }
    }

    /**
     *
     * @param body
     */
    public void ForceOnBody(Body body) {
        QuadTreeNode tempNode = this.root;
        this.calcForce(tempNode, body);
    }

    /**
     *
     * @param node
     * @param body
     */
    protected void calcForce(QuadTreeNode node, Body body) {
        //if node is external
        if (node.isExternal()) {
            // if node is dense leaf calculate force like normal between all bodies in dense leaf and given body
            if (node.isDenseLeaf()) {
                for (Body dlbody : node.getExternalNode().getAllBodies()) {
                    if (body.getId() != dlbody.getId()) {
                        forceCalculationHelper(body, dlbody);
                    }
                }
            } else if (node.isEmptyExternal()) {
                /*do nothing*/
            } else {
                //Normal Force calculation
                if (node.getExternalNode().getBody().getId() != body.getId()) {
                    forceCalculationHelper(node.getExternalNode().getBody(), body);
                }
            }
        } /*if node is internal*/ else {
            // first check super node condition for approximating long range force
            if ((node.getWidth() / Vec2DMath.value(Vec2DMath.subtraction(node.getCenterOfSuperNode(), body.getPosition()))) <= this.theta) {
                forceCalculationHelper(node.getSuperNode(), body);
            } /*recursivley calculate force for each child*/ else {
                calcForce(node.getChildren()[0], body);
                calcForce(node.getChildren()[1], body);
                calcForce(node.getChildren()[2], body);
                calcForce(node.getChildren()[3], body);
            }
        }

    }

    /**
     *
     */
    public void resetTree() {
        this.root = null;
        this.root = new QuadTreeNode(0, 0, frameMaxX, frameMaxY, 0);
    }

    /**
     *
     * @param body
     * @param dlbody
     */
    protected abstract void forceCalculationHelper(Body body, Body dlbody);
}
