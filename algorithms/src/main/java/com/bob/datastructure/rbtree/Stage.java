package com.bob.datastructure.rbtree;

/**
 * Created by zhangmingbo on 7/17/17.
 */
public class Stage {


    public static void main(String[] args) {


        RedBlackTree redBlackTree = new RedBlackTree();

        for (int i = 0; i < 50; i++) {

            redBlackTree.insert(i);
            redBlackTree.insert(i + 50);
        }

        for (int i = 0; i < 100; i++) {

            if (i % 2 == 0) {

                redBlackTree.delete(i);
            }
        }

        redBlackTree.printNodes();

    }

}
