package com.bob.datastructure.rbtree;

/**
 * Created by zhangmingbo on 7/17/17.
 */
public class Stage {


    public static void main(String[] args) {


        RedBlackTree redBlackTree = new RedBlackTree();

        for (int i = 44; i < 55; i++) {

            redBlackTree.insert(i);
        }

        System.out.println("########### 插入出数据后 ###############");
        redBlackTree.printNodesInOrder();

        redBlackTree.delete(49);
        System.out.println("########### 删除一个黑色节点49 ###############");
        redBlackTree.printNodesInOrder();
    }

}
