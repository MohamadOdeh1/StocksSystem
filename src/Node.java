class Node<K, V> {
    K key;
    V value;
    long timestamp;
    Node<K, V> parent;
    Node<K, V> left;
    Node<K, V> middle;
    Node<K, V> right;
    TwoThreeTree<Long, Float> priceHistory;
    public Node() {}

    public Node(K key, V value, long timestamp) {
        this.key = key;
        this.value = value;
        this.timestamp = timestamp;
        this.left = this.middle = this.right = null;
        this.priceHistory = new TwoThreeTree<>(); // Initialize the price history tree
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
    public float getPrice() {
        Node<Long, Float> lastPriceNode = priceHistory.root;
        while (lastPriceNode != null && lastPriceNode.right != null) {
            lastPriceNode = lastPriceNode.right;
        }
        return lastPriceNode != null ? lastPriceNode.value : 0;
    }
    public void updatePrice(long timestamp, float priceChange) {
        float newPrice = getPrice() + priceChange;
        priceHistory.insert(timestamp, newPrice,timestamp);
    }

    public void removePriceUpdate(long timestamp) {
        priceHistory.remove(timestamp);
    }
    @Override
    public String toString() {
        return "Node{" + "key=" + key + ", value=" + value + '}';
    }
}