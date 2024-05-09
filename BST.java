public class BST<K extends Comparable<K>, V> {
    private Node<K, V> root;
    private static class Node<K, V> {
        private K key;
        private V value;
        private int length = 1;
        private Node<K, V> left, right, parent;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
            return;
        }

        Node<K, V> node = root;
        Node<K, V> parent = null;
        while (node != null) {
            parent = node;
            int res = key.compareTo(node.key);
            if (res < 0)
                node = node.left;
            else if (res > 0)
                node = node.right;
            else {
                node.value = value;
                return;
            }
        }
        Node<K, V> newNode = new Node<>(key, value);
        newNode.parent = parent;
        int res = key.compareTo(parent.key);
        if (res < 0)
            parent.left = newNode;
        else if (res > 0)
            parent.right = newNode;

        while (parent != null) {
            parent.length++;
            parent = parent.parent;
        }
    }

//    public V get(K key) {
//
//    }
//
//    public void delete(K key) {
//
//    }
//
//    public Iterable<K> iterator() {
//
//    }

    public int size() {
        return root.length;
    }
}
