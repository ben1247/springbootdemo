package com.shuyun.sbd.utils.queue.sort;

/**
 * Component: 遍历二叉树
 * Description:
 * Date: 16/11/28
 *
 * @author yue.zhang
 */
public class BinTree {

    private BTNode root;

    public BinTree(BTNode root){
        this.root = root;
    }

    public BTNode getRoot() {
        return root;
    }

    /**
     * 初始化，构造二叉树
     * @return
     */
    public static BTNode init(){
        BTNode a = new BTNode('A');
        BTNode b = new BTNode('B', null, a);
        BTNode c = new BTNode('C');
        BTNode d = new BTNode('D', b, c);
        BTNode e = new BTNode('E');
        BTNode f = new BTNode('F', e, null);
        BTNode g = new BTNode('G', null, f);
        BTNode h = new BTNode('H', d, g);

        return h; // 根结点
    }

    public static void visit(BTNode p){
        System.out.println(p.getKey() + " ");
    }

    /**
     * 递归实现前序遍历
     * @param p
     */
    public static void preOrder(BTNode p){
        if(p != null){
            visit(p);
            preOrder(p.getLeft());
            preOrder(p.getRight());
        }
    }

    /**
     * 递归实现中序遍历
     * @param p
     */
    public static void inOrder(BTNode p){
        if(p != null){
            inOrder(p.getLeft());
            visit(p);
            inOrder(p.getRight());
        }
    }

    /**
     * 递归实现后序遍历
     * @param p
     */
    public static void postOrder(BTNode p){
        if(p != null){
            inOrder(p.getLeft());
            inOrder(p.getRight());
            visit(p);
        }
    }

    public static void main(String [] args){
        BinTree tree = new BinTree(init());
        System.out.print(" 递归实现前序遍历:");
        preOrder(tree.getRoot());
        System.out.println("\n");

        System.out.print(" 递归实现中序遍历:");
        inOrder(tree.getRoot());
        System.out.println("\n");

        System.out.print(" 递归实现后序遍历:");
        postOrder(tree.getRoot());
        System.out.println("\n");
    }
}
