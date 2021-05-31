package asd.lab3;

public class RBTree implements DataStructure<RBTree.RBNode> {

    protected static class RBNode {
        int element;
        RBNode parent;
        RBNode left;
        RBNode right;
        //true == red, false == black
        boolean color;

        @Override
        public String toString() {
            return "RBNode{" +
                    "element=" + element +
                    ", parent=" + parent +
                    ", left=" + left +
                    ", right=" + right +
                    ", color=" + color +
                    '}';
        }
    }

    private RBNode root;
    private RBNode emptyNode;


    private RBNode search(RBNode node, int element) {
        if (node == emptyNode || element == node.element) {
            return node;
        }

        if (element < node.element) {
            return search(node.left, element);
        }
        return search(node.right, element);
    }

    private void fixDelete(RBNode node) {
        RBNode nodeHelper;
        while (node != root && node.color == false) {
            if (node == node.parent.left) {
                nodeHelper = node.parent.right;
                if (nodeHelper.color == true) {
                    nodeHelper.color = false;
                    node.parent.color = true;
                    leftRotate(node.parent);
                    nodeHelper = node.parent.right;
                }

                if (nodeHelper.left.color == false && nodeHelper.right.color == false) {
                    nodeHelper.color = true;
                    node = node.parent;
                } else {
                    if (nodeHelper.right.color == false) {
                        nodeHelper.left.color = true;
                        nodeHelper.color = false;
                        rightRotate(nodeHelper);
                        nodeHelper = node.parent.right;
                    }
                    nodeHelper.color = node.parent.color;
                    node.parent.color = false;
                    nodeHelper.right.color = false;
                    leftRotate(node.parent);
                    node = root;
                }
            } else {
                nodeHelper = node.parent.left;
                if (nodeHelper.color == true) {
                    nodeHelper.color = false;
                    node.parent.color = true;
                    rightRotate(node.parent);
                    nodeHelper = node.parent.left;
                }

                if (nodeHelper.right.color == false) {
                    nodeHelper.color = true;
                    node = node.parent;
                } else {
                    if (nodeHelper.left.color == false) {
                        nodeHelper.right.color = false;
                        nodeHelper.color = true;
                        leftRotate(nodeHelper);
                        nodeHelper = node.parent.left;
                    }
                    nodeHelper.color = node.parent.color;
                    node.parent.color = false;
                    nodeHelper.left.color = false;
                    rightRotate(node.parent);
                    node = root;
                }
            }
        }
        node.color = false;
    }


    private void rbTransplant(RBNode node1, RBNode node2){
        if (node1.parent == null) {
            root = node2;
        } else if (node1 == node1.parent.left){
            node1.parent.left = node2;
        } else {
            node1.parent.right = node2;
        }
        node2.parent = node1.parent;
    }

    private void delete(RBNode node, int element) {
        RBNode z = emptyNode;
        RBNode x, y;
        while (node != emptyNode){
            if (node.element == element) {
                z = node;
            }

            if (node.element <= element) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == emptyNode) {
            return;
        }

        y = z;
        boolean yOriginalColor = y.color;
        if (z.left == emptyNode) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == emptyNode) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == false){
            fixDelete(x);
        }
    }

    private void fixInsert(RBNode k){
        RBNode u;
        while (k.parent.color == true) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == true) {
                    u.color = false;
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;

                if (u.color == true) {
                    u.color = false;
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = false;
    }

    public RBTree() {
        emptyNode = new RBNode();
        emptyNode.color = false;
        emptyNode.left = null;
        emptyNode.right = null;
        root = emptyNode;
    }

    @Override
    public RBNode search(int k) {
        return search(this.root, k);
    }


    public RBNode minimum(RBNode node) {
        while (node.left != emptyNode) {
            node = node.left;
        }
        return node;
    }


    public void leftRotate(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        if (y.left != emptyNode) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }


    public void rightRotate(RBNode x) {
        RBNode y = x.left;
        x.left = y.right;
        if (y.right != emptyNode) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    @Override
    public void insert(int key) {
        RBNode node = new RBNode();
        node.parent = null;
        node.element = key;
        node.left = emptyNode;
        node.right = emptyNode;
        node.color = true;

        RBNode y = null;
        RBNode x = this.root;

        while (x != emptyNode) {
            y = x;
            if (node.element < x.element) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.element < y.element) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null){
            node.color = false;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    @Override
    public void delete(int data) {
        delete(this.root, data);
    }



}
