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
        insertFixUpCase1(addedNode);

        return true;
    }

    /**
     * @param value
     * @return
     */
    private Node findNode(int value) {

        if (root == null) {
            return null;
        }

        Node currentNode = root;

        while (true) {

            if (value == currentNode.value) {
                //找到了
                return currentNode;

            } else if (value > currentNode.value) {

                if (currentNode.rightChild != null) {
                    currentNode = currentNode.rightChild;
                } else {
                    return null;
                }

            } else {
                if (currentNode.leftChild != null) {
                    currentNode = currentNode.leftChild;
                } else {
                    return null;
                }
            }


        }

    }


    /**
     * 情形3:父节点和叔父节点都是红色。这种情况下，我们需要将祖父节点的颜色下沉一层，即祖父节点变成红色，父节点和叔父变成黑色。
     *
     * @param node
     */
    private void insertFixUpCase3(Node node) {

        //父节点是红色

        Node uncleNode = getUncleNode(node);

        if (uncleNode != null && uncleNode.color == Color.RED) {
            //叔父节点是红色

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

            getGrandparentNode(node).color = Color.RED;

            //调整
            insertFixUpCase1(getGrandparentNode(node));


        } else {
            //叔父节点是黑色
            //情形4：父节点是红色，叔父节点是黑色，祖父节点肯定是黑色的，要不然就违反了红黑树不能连续红色节点的性质
            insertFixUpCase4(node);
        }


    }

    /**
     * 情形1:如果插入节点是根节点，则设置为黑色
     *
     * @param node
     */
    private void insertFixUpCase1(Node node) {
        if (node.parent == null) {
        /*

            root(b)

        */
            //情形1:如果插入节点是根节点，则设置为黑色
            root = node;
            node.color = Color.BLACK;
        } else {
            insertFixUpCase2(node);
        }

    }

    private void insertFixUpCase2(Node node) {
        if (node.parent.color == Color.BLACK) {
            //父节点是黑色

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
            insertFixUpCase3(node);
        }

    }

    /**
     * 情况4：<br>
     * 该节点红色，父节点是红色，该节点是父节点的"右孩子"，叔父节点是黑色或缺少，祖父节点是黑色，父节点是祖父节点"左孩子"<br>
     * 或者：该节点红色，父节点是红色，该节点是父节点的"左孩子"，叔父节点是黑色或缺少，祖父节点是黑色，父节点是祖父节点"右孩子"<br>
     *
     * @param node
     */
    private void insertFixUpCase4(Node node) {

        /*
                ...                      ...
                n1(b)                     n1(b)
                /   \                     /   \
              n2(r)  n3(b)    ==>      n4(r)  n3(b)
                \       ...            /       ...
                 n4(r)               n2(r)
                  ...                ...

         */

        if (node.parent.leftChild == node && getGrandparentNode(node).rightChild == node.parent) {

            rotateRight(node);

        } else if (node.parent.rightChild == node && getGrandparentNode(node).leftChild == node.parent) {
            rotateLeft(node);
        }

        insertFixUpCase5(node);
    }

    private void insertFixUpCase5(Node node) {
        node.parent.color = Color.BLACK;
        getGrandparentNode(node).color = Color.RED;

        if (node == node.parent.leftChild && node.parent == getGrandparentNode(node).leftChild) {
            rotateRight(node.parent);
        } else {
            rotateLeft(node.parent);
        }
    }

    /**
     * 树左旋
     */
    private void rotateLeft(Node pivot) {
         /*
                n1(b)                     n1(b)
                /   \                     /   \
              n2(r)  n3(b)    ==>      n4(r)  n3(b)
                \                      /
                 n4(r)               n2(r)

          其中n4(r)就是pivot

         */

//        //对应图中的n2(r)
//        Node parent = pivot.parent;
//        //对应图中的n1(b)
//        Node grandParent = pivot.parent.parent;
//
//        grandParent.leftChild = pivot;
//        pivot.parent = grandParent;
//
//        parent.parent = pivot;
//        parent.rightChild = pivot.leftChild;
//        pivot.leftChild = parent;

        //########### 下面是新的 ############

        if (pivot.parent == null) {
            //如果转轴是根节点
            root = pivot;
            return;
        }

        Node grandparentNode = getGrandparentNode(pivot);
        Node parentNode = pivot.parent;
        Node leftchild = pivot.leftChild;

        parentNode.rightChild = leftchild;

        if (!isLeaf(leftchild)) {
            leftchild.parent = parentNode;
        }

        pivot.leftChild = parentNode;
        parentNode.parent = pivot;

        if (root == parentNode) {
            root = pivot;
        }
        pivot.parent = grandparentNode;

        if (grandparentNode != null) {
            if (grandparentNode.leftChild == parentNode) {
                grandparentNode.leftChild = pivot;
            } else {
                grandparentNode.rightChild = pivot;
            }
        }
    }

    /**
     * 树右旋
     */
    private void rotateRight(Node pivot) {

        /*
                n1(b)                     n1(b)
                /   \                     /   \
              n2(b)  n3(r)    ==>      n2(b)  n4(r)
                      /                         \
                 n4(r)                          n3(r)

          其中n4(r)就是pivot

         */

//        //对应图中的n3(r)
//        Node parent = pivot.parent;
//        //对应图中的n1(b)
//        Node grandParent = pivot.parent.parent;
//
//        grandParent.rightChild = pivot;
//        pivot.parent = grandParent;
//
//        parent.parent = pivot;
//        parent.leftChild = pivot.rightChild;
//        pivot.rightChild = parent;

        if (pivot.parent == null) {
            //如果转轴是根节点
            root = pivot;
            return;
        }

        Node grandparentNode = getGrandparentNode(pivot);
        Node parentNode = pivot.parent;
        Node rightChild = pivot.rightChild;

        parentNode.leftChild = rightChild;

        if (isLeaf(rightChild)) {
            rightChild.parent = parentNode;
        }

        pivot.rightChild = parentNode;
        parentNode.parent = pivot;

        if (root == parentNode) {
            root = pivot;
        }
        pivot.parent = grandparentNode;

        if (grandparentNode != null) {
            if (grandparentNode.leftChild == parentNode) {
                grandparentNode.leftChild = pivot;
            } else {
                grandparentNode.rightChild = pivot;
            }
        }


    }

    private Node createLeafNode() {
        Node leaf = new Node();
        leaf.isLeaf = true;
        leaf.color = Color.BLACK;
        return leaf;
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

        //加入叶子节点
        node.leftChild = createLeafNode();
        node.rightChild = createLeafNode();

        if (root == null) {
            root = node;
            return root;
        }

        Node currentNode = root;

        while (true) {

            if (value >= currentNode.value) {

                if (!isLeaf(currentNode.rightChild)) {
                    currentNode = currentNode.rightChild;
                } else {
                    currentNode.rightChild = node;
                    node.parent = currentNode;
                    break;
                }

            } else {
                if (!isLeaf(currentNode.leftChild)) {
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

    /**
     * 删除节点后，取直接后继替换到被删除节点
     *
     * @param value
     * @return
     */
    public boolean delete(int value) {

        return deleteChild(root, value);

    }

    private boolean deleteChild(Node node, int value) {

        if (node.value > value) {
            if (isLeaf(node.leftChild)) {
                return false;
            }

            return deleteChild(node.leftChild, value);
        } else if (node.value < value) {
            if (isLeaf(node.rightChild)) {
                return false;
            }

            return deleteChild(node.rightChild, value);

        } else {
            if (isLeaf(node.rightChild)) {
                deleteOneChild(node);
                return true;
            }

            Node smallest = getSmallestChild(node.rightChild);
            copyValue(smallest, node);
            deleteOneChild(smallest);

            return true;
        }

    }


    private void copyValue(Node fromNode, Node toNode) {

        toNode.value = fromNode.value;
    }

    private void deleteOneChild(Node nodeForDelete) {
        //如果右孩子是叶子节点
        Node child = isLeaf(nodeForDelete.leftChild) ? nodeForDelete.rightChild : nodeForDelete.leftChild;

        if (nodeForDelete.parent == null && isLeaf(nodeForDelete.leftChild) && isLeaf(nodeForDelete.rightChild)) {
            nodeForDelete = null;
            root = nodeForDelete;
            return;
        }

        if (nodeForDelete.parent == null) {
            child.parent = null;
            root = child;
            root.color = Color.BLACK;
            return;
        }

        //如果nodeForDelete节点不是根节点
        //先把待删除节点的儿子节点替换他
        if (nodeForDelete.parent.leftChild == nodeForDelete) {
            nodeForDelete.parent.leftChild = child;
        } else {
            nodeForDelete.parent.rightChild = child;
        }

        if (child == null) {
            return;
        }

        child.parent = nodeForDelete.parent;

        //调整使之符合红黑树的性质。如果是红色的就不需要做什么，因为他被删除了并不影响红黑树的性质被破坏
        if (nodeForDelete.color == Color.BLACK) {
            //如果待删除节点是黑色的

            if (child.color == Color.RED) {
                //如果孩子节点是红色，只要将它变成黑色就可以达到平衡，穿过这个节点的所有路径的黑色节点数量没有减少，保证了性质5
                child.color = Color.BLACK;

            } else {

                deleteFixUpCase1(child);

            }
        }
    }

    /**
     * 获取指定node下的最小节点
     *
     * @param node
     * @return
     */
    private Node getSmallestChild(Node node) {

        Node leftChildNode = node;

        while (leftChildNode.leftChild != null) {
            leftChildNode = leftChildNode.leftChild;
        }

        return leftChildNode;
    }

    /**
     * 这里用null表示叶子节点
     *
     * @param node
     * @return
     */
    private boolean isLeaf(Node node) {

        if (node != null && node.isLeaf) {
            return true;
        }

        return false;
    }

    /**
     * 情形1：node是新的根
     *
     * @param node
     */
    private void deleteFixUpCase1(Node node) {
        if (node.parent == null) {
            //情形1：node是新的根

        } else {
            deleteFixUpCase2(node);
        }

    }

    /**
     * 情形2：node的兄弟节点是红色
     *
     * @param node
     */
    private void deleteFixUpCase2(Node node) {

        Node sib = sibling(node);
        if (sib.color == Color.RED) {

            sib.color = Color.BLACK;
            node.parent.color = Color.RED;


            /*
                     ...
                     n1(b)                           n3(b)
                     /  \                             /  \
                  n2(b)   n3(r)       ==>         n1(r)  n5(b)
                          /   \                    /  \
                       n4(b)  n5(b)             n2(b) n4(b)
                       ...    ...               ...   ...
             */
            if (node == node.parent.leftChild) {
                rotateLeft(sib);
            } else {
                rotateRight(sib);
            }

        }

        deleteFixUpCase3(node);

    }

    /**
     * 情形3：node的父亲、兄弟、兄弟的儿子都是黑色。
     *
     * @param node
     */
    private void deleteFixUpCase3(Node node) {
        Node sib = sibling(node);

        if (sib != null
                && sib.leftChild != null
                && sib.rightChild != null
                && node.parent.color == Color.BLACK
                && sib.color == Color.BLACK
                && sib.leftChild.color == Color.BLACK
                && sib.rightChild.color == Color.BLACK) {


        } else {
            deleteFixUpCase4(node);
        }
    }

    /**
     * 情形4：node的兄弟、兄弟的儿子都是黑色，而父亲节点是红色。
     *
     * @param node
     */
    private void deleteFixUpCase4(Node node) {
        Node sib = sibling(node);

        if (sib != null
                && sib.leftChild != null
                && sib.rightChild != null
                && node.parent.color == Color.BLACK
                && sib.color == Color.BLACK
                && sib.leftChild.color == Color.BLACK
                && sib.rightChild.color == Color.BLACK) {

            sib.color = Color.RED;
            node.parent.color = Color.BLACK;

        } else {
            deleteFixUpCase5(node);

        }
    }

    /**
     * 情形5：node的兄弟是黑色、兄弟的左儿子是红色、兄弟的右儿子是黑色，并且node是其父亲的左儿子
     *
     * @param node
     */
    private void deleteFixUpCase5(Node node) {

        Node sib = sibling(node);
        if (sib.color == Color.BLACK) {
            if (node == node.parent.leftChild
                    && sib.rightChild.color == Color.BLACK
                    && sib.leftChild.color == Color.RED
                    ) {

                sib.color = Color.RED;
                sib.leftChild.color = Color.BLACK;
                rotateRight(sib);

            } else if (node == node.parent.rightChild
                    && sib.leftChild.color == Color.BLACK
                    && sib.rightChild.color == Color.RED) {

                sib.color = Color.RED;
                sib.rightChild.color = Color.BLACK;
                rotateLeft(sib);
            }
        }

        deleteFixUpCase6(node);

    }

    /**
     * 情形6：node的兄弟是黑色、兄弟的右儿子是红色，而node是其父亲的左儿子。
     *
     * @param node
     */
    private void deleteFixUpCase6(Node node) {
        Node sib = sibling(node);

        sib.color = node.parent.color;
        node.parent.color = Color.BLACK;

        if (node == node.parent.leftChild) {
            sib.rightChild.color = Color.BLACK;
            rotateLeft(node.parent);
        } else {
            sib.leftChild.color = Color.BLACK;
            rotateRight(node);
        }

    }


    /**
     * 获得指定节点的祖父节点
     *
     * @param node
     * @return
     */
    private Node getGrandparentNode(Node node) {
        return node.parent.parent;
    }

    /**
     * 获得指定节点的叔父节点
     *
     * @param node
     * @return
     */
    private Node getUncleNode(Node node) {

        if (node.parent.parent.leftChild == node.parent)
            return node.parent.parent.rightChild;
        else
            return node.parent.parent.leftChild;

    }

    /**
     * 得到兄弟节点
     *
     * @param node
     * @return
     */
    private Node sibling(Node node) {
        if (node == node.parent.leftChild)
            return node.parent.rightChild;
        else
            return node.parent.leftChild;

    }

    public void printNodesInOrder() {
        printNodeInOrder(root);
    }

    /**
     * 中序遍历
     *
     * @param node
     */
    private void printNodeInOrder(Node node) {
        if (!isLeaf(node.leftChild)) {
            printNodeInOrder(node.leftChild);
        }
        System.out.println(new StringBuilder(node.value + "").append("(").append(node.color.name()).append(")"));

        if (!isLeaf(node.rightChild)) {
            printNodeInOrder(node.rightChild);
        }

    }

    public void printNodes() {


    }

    private void printNodes(Node node) {


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
        boolean isLeaf = false;

    }
}
