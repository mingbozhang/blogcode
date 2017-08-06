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

//        System.out.println("########### 删除一个节点47 ###############");
//        redBlackTree.delete(47);
//        redBlackTree.printNodesInOrder();
//
        System.out.println("########### 删除一个节点49 ###############");
        redBlackTree.delete(49);
        redBlackTree.printNodesInOrder();

//        System.out.println("########### 删除一个节点51 ###############");
//        redBlackTree.delete(51);
//        redBlackTree.printNodesInOrder();
//
//        System.out.println("########### 删除一个节点44 ###############");
//        redBlackTree.delete(44);
//        redBlackTree.printNodesInOrder();
//
//        System.out.println("########### 删除一个节点48 ###############");
//        redBlackTree.delete(48);
//        redBlackTree.printNodesInOrder();
    }

}
