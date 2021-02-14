package com.algorithm;

public class AVLTree<E> extends BinarySearchTree<E> {

    @Override
    protected void afterAdd(Node<E> node) {
        // 有可能添加之后整棵树没有失衡
        // 添加节点 可能导致其祖父节点等的失衡
        // 需要向上查找  知道node.parent = null即根节点 为止
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 如果添加节点之后还是平衡的  更新高度
                updateHeight(node);
            }else {
                // 向上寻找第一个失衡的祖父节点(高度最低的失衡节点)  使其平衡 至此 整棵树平衡(下面节点的失衡导致整棵树失衡)
                rebalance(node);

                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        // 删除节点后 可能导致其父节点或者其祖父节点失衡
        // 调整后 该子树的高度可能-1  导致上面的树失衡 所以要循环上面祖父节点是否是失衡节点
        // 很有可能循环到root节点  所以删除之后的调整有可能是 logn 的旋转
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                updateHeight(node);
            }else {
                rebalance(node);
            }
        }
    }


    @Override
    protected AVLNode<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

    /**
     * 重新平衡节点
     * @param grand
     */
    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();

        if (((AVLNode<E>) parent).isLeftChild()) {
            if (((AVLNode<E>) node).isLeftChild()) {   // LL  单旋  grand右旋
                rotateRight(grand);
            }else {  // LR  双旋  parent左旋 grand右旋
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else {
            if (((AVLNode<E>) node).isRightChild()) {  // RR   单旋  grand左旋
                rotateLeft(grand);
            }else {  // RL  双旋   parent右旋  grand左旋
                rotateRight(parent);
                rotateLeft(grand);
            }
        }
    }


    /**
     * 左旋
     * @param grand
     */
    private void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;

        afterRotate(grand, parent, child);
    }

    /**
     * 右旋
     * @param grand
     */
    private void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;

        afterRotate(grand, parent, child);
    }


    /**
     * 更新各个节点的parent
     * @param grand
     * @param parent
     * @param child
     */
    private void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        // parent成为子树的根节点
        parent.parent = grand.parent;
        // 还需要明确parent是左右子树
        if (((AVLNode)grand).isLeftChild()) {
            grand.parent.left = parent;
        }else if (((AVLNode)grand).isRightChild()) {
            grand.parent.right = parent;
        }else {
            root = parent;   // grand.parent == null   在 isLeftChild()/isRightChild()中做了判断
        }

        // 更新 child的parent
        if (null != child) {
            child.parent = grand;
        }

        // 更新 grand的parent
        grand.parent = parent;

        // 更新节点高度
        updateHeight(grand);
        updateHeight(parent);
    }

    /**
     * 更新给定节点高度
     * @param node
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>)node).updateHeight();
    }

    /**
     * 判断给定节点是否平衡
     * @param node
     * @return
     */
    private boolean isBalanced(Node<E> node) {
        return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
    }


    /**
     * 节点
     * @param <E>
     */
    private static class AVLNode<E> extends Node<E> {

        int height = 1;  // 高度  新增节点高度为1

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * 判断是否为左子树
         * @return
         */
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 判断是否为右子树
         * @return
         */
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 平衡因子  左子树高-右子树高
         * @return
         */
        public int balanceFactor() {
            int leftHeigth = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;

            return leftHeigth - rightHeight;
        }

        /**
         * 返回节点较高的子树
         * @return
         */
        public Node<E> tallerChild() {
            int leftHeigth = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;

            if (leftHeigth > rightHeight) return left;
            if (leftHeigth < rightHeight) return right;

            return isLeftChild() ? left : right;
        }

        /**
         * 更新当前节点的高度
         */
        public void updateHeight() {
            int leftHeigth = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;

            // 当前节点高度 = max(左子树高, 右子树高) + 1
            height = 1 + Math.max(leftHeigth, rightHeight);
        }
    }

}
