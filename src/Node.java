public class Node<K,V>{
    //this is the class that represents a node in the 2-3 Tree

    K key;
    V value;
    long timestamp;
    Node<K,V> parentNode;
    Node<K,V> leftChild;
    Node<K,V> middleChild;
    Node<K,V> rightChild;

    Node(K key, V value, long timestamp){
        this.key = key;
        this.value = value;
        this.parentNode = null;
        this.leftChild = null;
        this.middleChild = null;
        this.rightChild = null;
        this.timestamp = timestamp;
    }

    public Node() {

    }

    public K getKey(){
        return this.key;
    }
    public V getValue(){
        return this.value;
    }
    public Node<K,V> getParentNode(){
        return this.parentNode;
    }
    public Node<K,V> getLeftChild(){
        return this.leftChild;
    }
    public Node<K,V> getMiddleChild(){
        return this.middleChild;
    }
    public Node<K,V> getRightChild(){
        return this.rightChild;
    }
    public void setValue(V value){
        this.value = value;
    }
    public void setKey(K key){
        this.key = key;
    }
    public void setParentNode(Node<K,V> parentNode){
        this.parentNode = parentNode;
    }
    public void setChildren(Node<K,V> leftChild, Node<K,V> middleChild, Node<K,V> rightChild){
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild;
    }
    public boolean isLeaf(){
        return this.leftChild == null && this.middleChild == null && this.rightChild == null;
    }
    @Override
    public String toString(){
        return "Node{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Node){
            Node node = (Node) o;
            return key.equals(node.getKey());
        }
        return false;
    }
}
