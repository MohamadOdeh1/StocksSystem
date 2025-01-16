class Node<K, V> {
    K key;
    V value;
    long timestamp;
    Node<K, V> parent;
    Node<K, V> left;
    Node<K, V> middle;
    Node<K, V> right;

    public Node() {}

    public Node(K key, V value, long timestamp) {
        this.key = key;
        this.value = value;
        this.timestamp = timestamp;
        this.left = this.middle = this.right = null;
    }

    public boolean isLeaf() {
        return left == null && middle == null && right == null;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<K, V> getParentNode() {
        return parent;
    }

    public void setParentNode(Node<K, V> parent) {
        this.parent = parent;
    }

    public Node<K, V> getLeftChild() {
        return left;
    }

    public Node<K, V> getMiddleChild() {
        return middle;
    }

    public Node<K, V> getRightChild() {
        return right;
    }

    public void setChildren(Node<K, V> left, Node<K, V> middle, Node<K, V> right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        if (left != null) left.setParentNode(this);
        if (middle != null) middle.setParentNode(this);
        if (right != null) right.setParentNode(this);
    }

    @Override
    public String toString() {
        return "Node{" + "key=" + key + ", value=" + value + '}';
    }
}