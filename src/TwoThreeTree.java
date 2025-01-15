public class TwoThreeTree<K extends Comparable<K>,V>{
    //this class represents the 2-3 Tree
    Node<K,V> root;
    int size;

    public TwoThreeTree(){
        this.root = null;
        this.size = 0;
    }
    public void init() {
        // Step 1: Create a new internal node
        Node<K, V> x = new Node<>();

        // Step 2: Create sentinel leaf nodes
        Node<K, V> l = new Node<>();
        Node<K, V> m = new Node<>();

        // Step 3: Set keys for sentinel leaves
        l.key = null; // Representing -∞ (could also use a separate mechanism for -∞)
        m.key = null; // Representing +∞ (could also use a separate mechanism for +∞)

        // Step 4: Set parent pointers from leaves to internal node x
        l.setParentNode(x);
        m.setParentNode(x);

        // Step 5: Set internal node's key
        x.key = null; // Representing +∞ (or use a comparable maximum value)

        // Step 6: Assign sentinel nodes to their positions as children of x
        x.setChildren(l,m,null);

        // Step 7: Set the root of the tree to x and initialize the size
        this.root = x;
        this.size = 0; // Initially, the size is zero
    }

    public Node<K,V> search(Node<K,V> x, K key){
        if(x!=null){
            if(x.isLeaf()){
                if(x.getKey().equals(key)){
                    return x;
                }
                else{
                    return null;
                }
            }
            if(key.compareTo(x.getLeftChild().getKey()) <= 0){
                return search(x.getLeftChild(),key);
            }
            else if(key.compareTo(x.getMiddleChild().getKey()) <= 0){
                return search(x.getMiddleChild(),key);
            }
            else{
                return search(x.getRightChild(),key);
            }
        }
        return null;
    }
    public void updateKey(Node<K, V> x) {
        // Step 1: Start with the left child's key
        x.key = x.getLeftChild().key;

        // Step 2: Check if the middle child exists
        if (x.getMiddleChild() != null) {
            x.key = x.getMiddleChild().key; // Update key to the middle child's key
        }

        // Step 3: Check if the right child exists
        if (x.getRightChild() != null) {
            x.key = x.getRightChild().key; // Update key to the right child's key
        }
    }

    public Node<K, V> insertAndSplit(Node<K, V> x, Node<K, V> z) {
        // Current children of x
        if (x != null) {
            Node<K, V> l = x.getLeftChild();    // Left child
            Node<K, V> m = x.getMiddleChild();  // Middle child
            Node<K, V> r = x.getRightChild();   // Right child (could be null)

            // Case 1: If x has space for a new child (r == null)
            if (r == null) {
                if (z.getKey().compareTo(l.getKey()) < 0) {
                    // z is smaller than l
                    x.setChildren(z, l, m);
                } else if (z.getKey().compareTo(m.getKey()) < 0) {
                    // z fits between l and m
                    x.setChildren(l, z, m);
                } else {
                    // z is greater than m
                    x.setChildren(l, m, z);
                }
                return null; // No split needed
            }

            // Case 2: x is full (three children) - need to split
            Node<K, V> y = new Node<>(); // Create a new node y for the split

            if (z.getKey().compareTo(l.getKey()) < 0) {
                // z is the smallest: [z, l] in x, [m, r] in y
                x.setChildren(z, l, null);
                y.setChildren(m, r, null);
            } else if (z.getKey().compareTo(m.getKey()) < 0) {
                // z fits between l and m: [l, z] in x, [m, r] in y
                x.setChildren(l, z, null);
                y.setChildren(m, r, null);
            } else if (z.getKey().compareTo(r.getKey()) < 0) {
                // z fits between m and r: [l, m] in x, [z, r] in y
                x.setChildren(l, m, null);
                y.setChildren(z, r, null);
            } else {
                // z is the largest: [l, m] in x, [r, z] in y
                x.setChildren(l, m, null);
                y.setChildren(r, z, null);
            }

            // The keys of x and y need to be updated after the split
            updateKey(x);
            updateKey(y);

            return y; // Return the new node y to the parent for further adjustments
        }
        return null;
    }
    public void insert(K key, V value, long timestamp) {
        // Step 1: Create a new Node for the leaf to be inserted
        Node<K, V> z = new Node<>(key, value, timestamp);

        // Step 2: Start at the root of the tree
        Node<K, V> y = this.root;

        // Step 3: Traverse down the tree until a leaf node is reached
        while (y!= null && !y.isLeaf()) {
            if (z.getKey().compareTo(y.getLeftChild().getKey()) < 0) {
                // Go to the left child
                y = y.getLeftChild();
            } else if (y.getMiddleChild() != null && z.getKey().compareTo(y.getMiddleChild().getKey()) < 0) {
                // Go to the middle child
                y = y.getMiddleChild();
            } else {
                // Go to the right child
                y = y.getRightChild();
            }
        }

        // Step 4: Insert the new leaf into the tree at the correct position
        if(y!=null){
            Node<K, V> x = y.getParentNode(); // Parent of the leaf
            z = insertAndSplit(x, z); // Insert and handle potential splits

            // Step 5: Handle upward propagation of splits
            while (x != null) {
                if (z == null) {
                    // No split happened, update the keys of the current internal node
                    updateKey(x);
                    break;
                }

                // If we need to propagate a split upwards
                x = x.getParentNode();
                z = insertAndSplit(x, z); // Recursively propagate the split
            }

            // Step 6: Handle the case where the root is split
            if (z != null) {
                // Create a new root node
                Node<K, V> w = new Node<>();
                w.setChildren(this.root, z, null);
                this.root = w; // Update the root of the tree
                updateKey(w); // Update the new root's key
            }

            // Increment the size of the tree
            this.size += 1;
        }
    }
    public void printTreeContents(Node<K, V> node, int depth) {
        if (node == null) {
            return; // Base case: node is null
        }

        // If node is a leaf, print its key and value
        if (node.isLeaf()) {
            System.out.println(" ".repeat(depth * 4) + "Leaf - Key: " + node.getKey() + ", Value: " + node.getValue());
            return;
        }

        // If node is an internal node, traverse its children and print their contents
        System.out.println(" ".repeat(depth * 4) + "Internal Node - Key: " + node.getKey());

        // Visit left child
        if (node.getLeftChild() != null) {
            System.out.println(" ".repeat((depth + 1) * 4) + "Left Child:");
            printTreeContents(node.getLeftChild(), depth + 2);
        }

        // Visit middle child
        if (node.getMiddleChild() != null) {
            System.out.println(" ".repeat((depth + 1) * 4) + "Middle Child:");
            printTreeContents(node.getMiddleChild(), depth + 2);
        }

        // Visit right child
        if (node.getRightChild() != null) {
            System.out.println(" ".repeat((depth + 1) * 4) + "Right Child:");
            printTreeContents(node.getRightChild(), depth + 2);
        }
    }

    public void printTree() {
        System.out.println("Printing Tree Contents:");
        printTreeContents(root, 0); // Start printing from the root node at depth 0
    }
    @Override
    public String toString() {
        return "TwoThreeTree{" +
                "root=" + root +
                ", size=" + size +
                '}'+" "+
                "leftchild= "+root.getLeftChild()
                + "middlechild= "+root.getMiddleChild()
                + "rightchild= "+root.getRightChild();
    }
}
