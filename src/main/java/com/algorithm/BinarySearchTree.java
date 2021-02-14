package com.algorithm;

import java.util.Comparator;

/**
 * 要求元素必须具备可比较性 不能为null
 */
public class BinarySearchTree<E> {
    protected int size;
    protected Node<E> root;
    protected Comparator<E> comparator;



    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(E element) {
        return node(element) != null;
    }

    public void clear() {
        root = null;
    }


    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    /**
     * 添加节点
     * @param element
     */
    public void add(E element) {
        if (null == element) return;

        // 添加第一个节点
        if (null == root) {
            root = createNode(element, null);
            size++;

            afterAdd(root);
            return;
        }


        Node<E> node = root;
        Node<E> parent = root;

        int cmp = 0;
        while (null != node) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                // 相等  直接覆盖值
                node.element = element;
                return;
            }
        }

        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;

        // 添加节点之后  有不同的实现类(AVLTree/RBTree)决定作何操作节点(旋转、染色等)
        afterAdd(newNode);
    }

    /**
     * 添加之后的操作
     * @param node
     */
    protected void afterAdd(Node<E> node) {

    }

    /**
     * 查找对应的节点Node
     * @param element
     * @return
     */
    private Node<E> node(E element) {
        if (null == element) return null;

        Node<E> node = root;

        while (null != node) {
            int cmp = compare(element, node.element);
            if (cmp == 0) {
                return node;
            }
            if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return null;
    }

    /**
     * 对外提供删除接口
     * @param element
     */
    public void remove(E element) {
        remove(node(element));
    }


    /**
     * 删除指定节点
     * @param node
     */
    private void remove(Node<E> node) {
        if (null == node) return;

        size--;

        // 先删除度为2的节点
        // 删除度为2的节点  要找到该节点的前驱或者后继节点 用前驱后者后继节点覆盖要删除的节点，再删除前驱或者后继节点
        // 而前驱或者后继节点的度为1或者0  所以最终删除的节点的度为1或者0
        if (node.hasTwoChildren()) {
            // 后继节点
            Node<E> nextNode = successor(node);
            node.element = nextNode.element;
            node = nextNode;
        }

        Node<E> replacement = node.left != null ? node.left : node.right;

        if (null != replacement) {  // 度为1
            replacement.parent = node.parent;

            if (null == node.parent) {  // root
                root = replacement;
            }else if (node == node.parent.left) {
                node.parent.left = replacement;
            }else {
                node.parent.right = replacement;
            }

            // 对删除节点的父节点或者祖父节点做调整
            // node 节点被删除了 但是node.parent 这条线还在  其他指向node的引用没有了
            afterRemove(node);
        }else if (null == node.parent) {  // 度为0的root
            root = null;
        }else {   // leaf
            if (node == node.parent.left) {
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }

            afterRemove(node);
        }
    }

    /**
     * 删除之后
     * @param node
     */
    protected void afterRemove(Node<E> node) {

    }


    /**
     *
     * @param e1
     * @param e2
     * @return
     */
    private int compare(E e1, E e2) {
        if (null != comparator) {
            return comparator.compare(e1, e2);
        }else {
            return ((Comparable<E>)e1).compareTo(e2);
        }
    }

    /**
     * 对外提供前、中、后序 遍历的接口
     */
    public void preorderTraversal() {
        preorderTraversal(root);
    }

    public void inorderTraversal() {
        inorderTraversal(root);
    }

    public void postorderTraversal() {
        postorderTraversal(root);
    }

    /**
     * 前序遍历
     * @param node
     */
    private void preorderTraversal(Node<E> node) {
        if (null == node) return;

        System.out.println(node.element);
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    /**
     * 中序遍历
     * @param node
     */
    private void inorderTraversal(Node<E> node) {
        if (null == node) return;

        inorderTraversal(node.left);
        System.out.println(node.element);
        inorderTraversal(node.right);
    }

    /**
     * 后序遍历
     * @param node
     */
    private void postorderTraversal(Node<E> node) {
        if (null == node) return;

        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.println(node.element);
    }

    /**
     * 层序遍历  利用队列
     */
    public void levelTraversal() {
        if (null == root) return;

        Queue<Node<E>> queue = new Queue<>();
        // 可以使用java jdk提供的java.util.Queue
        queue.enQueue(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.deQueue();
            System.out.println(node.element);
            if (null != node.left) {
                queue.enQueue(node.left);
            }
            if (null != node.right) {
                queue.enQueue(node.right);
            }
        }
    }

    /**
     * 前驱节点
     * @param node
     * @return
     */
    private Node<E> predessor(Node<E> node) {
        if (null == node) return null;

        // 左子树不为空   node.left.right.right...
        Node<E> p = node.left;
        if (null != p) {
            while (null != p.right) {
                p = p.right;
            }
            return p;
        }

        // 能来到这里的  说明左子树为null
        // 如果 该节点的左子树为空 父节点不为空 且该节点必须为该父节点的左子树  node.parent.parent.parent...
        if (null != node.parent && node == node.parent.left) {
            node = node.parent;
        }

        return node.parent;
    }


    /**
     * 后继节点
     * @param node
     * @return
     */
    private Node<E> successor(Node<E> node) {
        if (null == node) return null;

        Node<E> p = node.right;
        if (null != p) {
            while (null != p.left) {
                p = p.left;
            }
            return p;
        }

        // 能来到这里的  说明右子树为null
        if (null != node.parent && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }


    /**
     * 对外提供 求节点 高
     * @param element
     * @return
     */
    public int height(E element) {
        return height(node(element));
    }

    /**
     * 递归求解 节点高
     * @param node
     * @return
     */
    protected int height(Node<E> node) {
        if (null == node) return 0;

        return 1 + Math.max(height(node.left), height(node.right));
    }


    /**
     * 树高
     * @return
     */
    public int heigth2() {
        // 利用递归求树高
        return height(root);


        // 利用 层序遍历求树高
        /*if (null == root) return 0;

        Queue<Node<E>> queue = new Queue<>();
        queue.enQueue(root);

        int height = 0;
        int levelSize = 1;  // 每层的节点数
        while (!queue.isEmpty()) {
            Node<E> node = queue.deQueue();
            levelSize--;
            if (null != node.left) {
                queue.enQueue(node.left);
            }
            if (null != node.right) {
                queue.enQueue(node.right);
            }

            if (levelSize == 0) {
                height++;
                levelSize = queue.size();
            }
        }

        return height;*/
    }


    /**
     * 判断二叉树是否为完全二叉树
     * @return
     */
    public boolean isCompleted() {

        if (null == root) return false;

        Queue<Node<E>> queue = new Queue<>();
        queue.enQueue(root);

        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.deQueue();

            if (leaf && !node.isLeaf()) {
                return false;
            }

            if (node.left != null) {
                queue.enQueue(node.left);
            }else if(node.right != null) {
                return false;
            }

            if (node.right != null) {
                queue.enQueue(node.right);
            }else {
                leaf = true;   // 当前节点为左子节点或者叶子节点，后续遍历的节点只能为叶子节点
            }
        }

        return true;
    }


    /**
     * 判断是否为完全二叉树(另一种思路)
     * 两个节点之间不可能出现null  出队时的节点为null 退出while，并判断队列中剩余节点是否都为null
     * @return
     */
    public boolean isCompleted2() {
        if (null == root) return false;

        Queue<Node<E>> queue = new Queue<>();
        queue.enQueue(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.deQueue();
            if (null != node) {
                // 不管是否为null  都入队
                queue.enQueue(node.left);
                queue.enQueue(node.right);
            }else {
                break;
            }
        }


        while (!queue.isEmpty()) {
            Node<E> node = queue.deQueue();
            if (node != null) {
                return false;
            }
        }

        return true;
    }


    /**
     * 节点
     * @param <E>
     */
    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;  // 要维护一个父节点

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 判断节点的度是否为2
         * @return
         */
        private boolean hasTwoChildren() {
            if (this.left != null && this.right != null) {
                return true;
            }
            return false;
        }

        /**
         * 判断是否为叶子节点
         * @return
         */
        private boolean isLeaf() {
            if (this.left == null && this.right == null)
                return true;

            return false;
        }
    }
}
