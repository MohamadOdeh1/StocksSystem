public class TwoThreeTree<K extends Comparable<K>, V> {
    Node<K, V> root;
    int size;

    public TwoThreeTree() {
        this.root = null;
        this.size = 0;
    }

    public void init() {
        Node<K, V> x = new Node<>();
        Node<K, V> l = new Node<>();
        Node<K, V> m = new Node<>();
        l.key = null; // Representing -∞
        m.key = null; // Representing +∞
        l.setParentNode(x);
        m.setParentNode(x);
        x.key = null; // Representing +∞
        x.setChildren(l, m, null);
        this.root = x;
        this.size = 0;
    }

    public Node<K, V> search(Node<K, V> x, K key) {
        if (x == null || x.getKey() == null) {
            return null;
        } else {
            if (x.isLeaf()) {
                if (x.getKey().equals(key)) {
                    return x;
                } else {
                    return null;
                }
            }
            if (key.compareTo(x.getLeftChild().getKey()) <= 0) {
                return search(x.getLeftChild(), key);
            } else if (x.getMiddleChild() != null && key.compareTo(x.getMiddleChild().getKey()) <= 0) {
                return search(x.getMiddleChild(), key);
            } else {
                return search(x.getRightChild(), key);
            }
        }
    }

    public void updateKey(Node<K, V> x) {
        if (x.getLeftChild().getKey() != null) {
            x.key = x.getLeftChild().key;
        }
        if (x.getMiddleChild() != null && x.getMiddleChild().getKey() != null) {
            x.key = x.getMiddleChild().key;
        }
        if (x.getRightChild() != null && x.getRightChild().getKey() != null) {
            x.key = x.getRightChild().key;
        }
    }

    public Node<K, V> insertAndSplit(Node<K, V> x, Node<K, V> z) {
        if (x == null) {
            return z;
        }

        Node<K, V> l = x.getLeftChild();
        Node<K, V> m = x.getMiddleChild();
        Node<K, V> r = x.getRightChild();

        if (r == null) {
            if (z.getKey().compareTo(l.getKey()) < 0) {
                x.setChildren(z, l, m);
            } else if (z.getKey().compareTo(m.getKey()) < 0) {
                x.setChildren(l, z, m);
            } else {
                x.setChildren(l, m, z);
            }
            return null;
        }

        Node<K, V> y = new Node<>();
        if (z.getKey().compareTo(l.getKey()) < 0) {
            x.setChildren(z, l, null);
            y.setChildren(m, r, null);
        } else if (z.getKey().compareTo(m.getKey()) < 0) {
            x.setChildren(l, z, null);
            y.setChildren(m, r, null);
        } else if (z.getKey().compareTo(r.getKey()) < 0) {
            x.setChildren(l, m, null);
            y.setChildren(z, r, null);
        } else {
            x.setChildren(l, m, null);
            y.setChildren(r, z, null);
        }

        updateKey(x);
        updateKey(y);

        return y;
    }

    public void insert(K key, V value, long timestamp) {
        Node<K, V> z = new Node<>(key, value, timestamp);
        Node<K, V> y = this.root;

        while (y != null && !y.isLeaf()) {
            if (y.getLeftChild().getKey() == null || z.getKey().compareTo(y.getLeftChild().getKey()) < 0) {
                y = y.getLeftChild();
            } else if (y.getMiddleChild() != null && (y.getMiddleChild().getKey() == null || z.getKey().compareTo(y.getMiddleChild().getKey()) < 0)) {
                y = y.getMiddleChild();
            } else {
                y = y.getRightChild();
            }
        }

        if (y != null) {
            Node<K, V> x = y.getParentNode();
            z = insertAndSplit(x, z);

            while (x != null) {
                if (z == null) {
                    updateKey(x);
                    break;
                }
                x = x.getParentNode();
                z = insertAndSplit(x, z);
            }

            if (z != null) {
                Node<K, V> w = new Node<>();
                w.setChildren(this.root, z, null);
                this.root = w;
                updateKey(w);
            }

            this.size += 1;
        }
    }

    public void remove(K key) {
        Node<K, V> nodeToRemove = search(root, key);
        if (nodeToRemove == null) {
            return;
        }

        deleteNode(nodeToRemove);
        size--;
    }

    public Node<K, V> borrowOrMerge(Node<K, V> y) {
        Node<K, V> z = y.getParentNode();
        if (z == null) return null;

        if (y == z.getLeftChild()) {
            Node<K, V> x = z.getMiddleChild();
            if (x != null && x.getRightChild() != null) {
                y.setChildren(y.getLeftChild(), x.getLeftChild(), null);
                x.setChildren(x.getMiddleChild(), x.getRightChild(), null);
            } else if (x != null) {
                x.setChildren(y.getLeftChild(), x.getLeftChild(), x.getMiddleChild());
                y = null;
                z.setChildren(x, z.getRightChild(), null);
            }
            return z;
        }
        if (y == z.getMiddleChild()) {
            Node<K, V> x = z.getLeftChild();
            if (x != null && x.getRightChild() != null) {
                y.setChildren(x.getRightChild(), y.getLeftChild(), null);
                x.setChildren(x.getLeftChild(), x.getMiddleChild(), null);
            } else if (x != null) {
                x.setChildren(x.getLeftChild(), x.getMiddleChild(), y.getLeftChild());
                y = null;
                z.setChildren(x, z.getRightChild(), null);
            }
        }
        Node<K, V> x = z.getMiddleChild();
        if (x != null && x.getRightChild() != null) {
            y.setChildren(x.getRightChild(), y.getLeftChild(), null);
            x.setChildren(x.getLeftChild(), x.getMiddleChild(), null);
        } else if (x != null) {
            y = null;
            z.setChildren(z.getLeftChild(), x, null);
        }
        return z;
    }

    public void deleteNode(Node<K, V> x) {
        Node<K, V> y = x.getParentNode();
        if (y == null) {
            root = null;
            return;
        }

        if (x == y.getLeftChild()) {
            y.setChildren(y.getMiddleChild(), y.getRightChild(), null);
        } else if (x == y.getMiddleChild()) {
            y.setChildren(y.getLeftChild(), y.getRightChild(), null);
        } else {
            y.setChildren(y.getLeftChild(), y.getMiddleChild(), null);
        }
        x = null;
        while (y != null) {
            if (y.getMiddleChild() != null) {
                updateKey(y);
                y = y.getParentNode();
            } else if (y != this.root) {
                y = borrowOrMerge(y);
            } else {
                this.root = y.getLeftChild();
                if (this.root != null) {
                    this.root.setParentNode(null);
                }
                y = null;
            }
        }
    }


    public void printTree() {
        if (this.root == null) {
            System.out.println("The tree is empty.");
        } else {
            printTree(this.root, 0);
        }
    }

    private void printTree(Node<K, V> node, int level) {
        String indent = "  ".repeat(level);

        if (node.isLeaf()) {
            System.out.println(indent + "Leaf: " + node);
        } else {
            System.out.println(indent + "Internal Node: " + node.key);
        }

        if (node.getLeftChild() != null) {
            System.out.print(indent + "Left -> ");
            printTree(node.getLeftChild(), level + 1);
        }

        if (node.getMiddleChild() != null) {
            System.out.print(indent + "Middle -> ");
            printTree(node.getMiddleChild(), level + 1);
        }

        if (node.getRightChild() != null) {
            System.out.print(indent + "Right -> ");
            printTree(node.getRightChild(), level + 1);
        }
    }
}