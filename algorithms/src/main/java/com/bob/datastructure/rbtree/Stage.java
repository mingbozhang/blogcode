package com.bob.datastructure.rbtree;

/**
 * Created by zhangmingbo on 7/17/17.
 */
public class Stage {


    public static void main(String[] args) {

        RedBlackTree redBlackTree = new RedBlackTree();
        for (int j = 0; j <= 999; j++) {

            redBlackTree.insert(new Double(Math.random()*100).intValue());
        }

        redBlackTree.printNodes();

    }

}
