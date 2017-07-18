package com.bob.datastructure.rbtree;

/**
 * 红黑树<br>
 * <p>
 * 性质1 节点是红色或黑色。<br>
 * 性质2 根是黑色。<br>
 * 性质3 所有叶子都是黑色（叶子是NIL节点）。<br>
 * 性质4 每个红色节点必须有两个黑色的子节点。（从每个叶子到根的所有路径上不能有两个连续的红色节点。）<br>
 * 性质5 从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点。<br>
 * <p>
 * <br>
 * 注释中表示出的节点不包括叶子节点（nil节点）<br>
 * root(b)表示黑色的根节点，new(r)表示新插入的红色节点，n2(b),表示名为n2的黑色节点，以此类推<br>
 * <p>
 * Created by zhangmingbo on 7/18/17.
 */
public class RedBlackTree {


    private Node root;


    public boolean insert(int value) {

        //将新的节点插入到树中
        Node addedNode = insertRedNode(value);

        //调整树结构，使之符合红黑树性质
        fixUpTree(addedNode);

        return true;
    }


    /**
     * 把node作为新加入的节点进行红黑树结构调整，使之符合所有性质。
     *
     * @param node
     */
    private void fixUpTree(Node node) {
        /*


            root(b)


        */
        //情形1:如果插入节点是根节点，则设置为黑色
        if (node.parent == null) {
            node.color = Color.BLACK;
            return;
        }

        if (node.parent.color == Color.BLACK) {
            /*

                  root(b)
                 /      \
                n2(b)    n3(b)
               /
              new(r)

            */
            //情形2：如果父节点是黑色的，所有性质都没有受到影响
            //不需要做什么
        } else {
            Node uncleNode = getUncleNode(node);

            if (uncleNode != null && uncleNode.color == Color.RED && node.parent.color == Color.RED) {

                /*

                     ...                     ...
                    n1(b)                    n1(r)
                   /      \                 /    \
                 n2(r)    n3(r)    ==>    n2(b)  n2(b)   ==>  （按照情形一的方式对n1节点进行调整）
                 /                        /
               new(r)                  new(r)

                 */
                //情形3:父节点和叔父节点都是红色。这种情况下，我们需要将祖父节点的颜色下沉一层，即祖父节点变成红色，父节点和叔父变成黑色。
                uncleNode.color = Color.BLACK;
                node.parent.color = Color.BLACK;

                getGrandpaNode(node).color = Color.RED;

                //调整
                fixUpTree(getGrandpaNode(node));
            }

            //如果，祖父是


        }

    }


    /**
     * 将节点插入到树中，默认都是红色
     *
     * @param value
     * @return
     */
    private Node insertRedNode(int value) {
        Node node = new Node();
        node.color = Color.RED;
        node.value = value;

        Node currentNode = root;

        while (true) {

            if (value >= currentNode.value) {

                if (currentNode.rightChild != null) {
                    currentNode = currentNode.rightChild;
                } else {
                    currentNode.rightChild = node;
                    node.parent = currentNode;
                    break;
                }

            } else {
                if (currentNode.leftChild != null) {
                    currentNode = currentNode.leftChild;
                } else {
                    currentNode.leftChild = node;
                    node.parent = currentNode;
                    break;
                }

            }

        }

        return node;
    }

    public boolean delete(int value) {

        return true;
    }

    /**
     * 获得指定节点的祖父节点
     *
     * @param node
     * @return
     */
    private Node getGrandpaNode(Node node) {
        return node.parent.parent;
    }

    /**
     * 获得指定节点的叔父节点
     *
     * @param node
     * @return
     */
    private Node getUncleNode(Node node) {

        if (node.parent.parent.leftChild == node.parent) {
            return node.parent.parent.rightChild;
        } else {
            return node.parent.parent.leftChild;
        }

    }


    /**
     * Created by zhangmingbo on 7/17/17.
     */
    private enum Color {

        RED,
        BLACK

    }

    /**
     * 红黑树节点
     * <p>
     * Created by zhangmingbo on 7/17/17.
     */
    private static class Node {


        Node parent;
        Node leftChild;
        Node rightChild;
        int value;
        Color color;

    }
}
