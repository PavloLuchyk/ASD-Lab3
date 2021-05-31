package asd.lab3;

public class AVLTree implements DataStructure<AVLTree.AVLNode> {
        public static class AVLNode {
            int key;
            int height;
            AVLNode left;
            AVLNode right;

            AVLNode(int key) {
                this.key = key;
            }
        }

        private AVLNode root;

        @Override
        public AVLNode search(int key) {
            AVLNode current = root;
            while (current != null) {
                if (current.key == key) {
                    break;
                }
                current = current.key < key ?
                        current.right
                        : current.left;
            }
            return current;
        }

        @Override
        public void insert(int key) {
            root = insert(root, key);
        }

        @Override
        public void delete(int key) {
            root = delete(root, key);
        }



        private AVLNode insert(AVLNode node, int key) {
            if (node == null) {
                return new AVLNode(key);
            } else if (node.key > key) {
                node.left = insert(node.left, key);
            } else if (node.key < key) {
                node.right = insert(node.right, key);
            }
            return rebalance(node);
        }

        private AVLNode delete(AVLNode node, int key) {
            if (node == null) {
                return node;
            } else if (node.key > key) {
                node.left = delete(node.left, key);
            } else if (node.key < key) {
                node.right = delete(node.right, key);
            } else {
                if (node.left == null || node.right == null) {
                    node = (node.left == null) ? node.right : node.left;
                } else {
                    AVLNode mostLeftChild = mostLeftChild(node.right);
                    node.key = mostLeftChild.key;
                    node.right = delete(node.right, node.key);
                }
            }
            if (node != null) {
                node = rebalance(node);
            }
            return node;
        }

        private AVLNode mostLeftChild(AVLNode node) {
            AVLNode current = node;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        private AVLNode rebalance(AVLNode z) {
            updateHeight(z);
            int balance = getBalance(z);
            if (balance > 1) {
                if (height(z.right.right) > height(z.right.left)) {
                    z = rotateLeft(z);
                } else {
                    z.right = rotateRight(z.right);
                    z = rotateLeft(z);
                }
            } else if (balance < -1) {
                if (height(z.left.left) > height(z.left.right)) {
                    z = rotateRight(z);
                } else {
                    z.left = rotateLeft(z.left);
                    z = rotateRight(z);
                }
            }
            return z;
        }

        private AVLNode rotateRight(AVLNode y) {
            AVLNode x = y.left;
            AVLNode z = x.right;
            x.right = y;
            y.left = z;
            updateHeight(y);
            updateHeight(x);
            return x;
        }

        private AVLNode rotateLeft(AVLNode y) {
            AVLNode x = y.right;
            AVLNode z = x.left;
            x.left = y;
            y.right = z;
            updateHeight(y);
            updateHeight(x);
            return x;
        }

        private void updateHeight(AVLNode n) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
        }

        private int height(AVLNode n) {
            return n == null ? -1 : n.height;
        }

        public int getBalance(AVLNode n) {
            return (n == null) ? 0 : height(n.right) - height(n.left);
        }
}
