import java.util.Iterator;

public class BST<K extends Comparable<K>, V> {
    private Node<K, V> root;
    private int length;
    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> left, right, parent;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void setLeft(Node left) {
            this.left = left;

        }
    }

    public void put(K key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
            length = 1;
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

        length++;
    }

    public V get(K key) {
        Node<K, V> node = getNode(key);
        return node != null ? node.value : null;
    }

    private Node<K, V> getNode(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0)
                node = node.left;
            else if (cmp > 0)
                node = node.right;
            else
                return node;
        }
        return null;
    }

    public void delete(K key) {
        Node<K, V> node = getNode(key);

        if (node == null) return;
        Node<K, V> parent = node.parent;

        if (parent == null) {
            if (node.left != null) {
                // this left node becomes new root
                root = node.left;
                root.parent = null;
                // if deleting node also has right
                if (node.right != null) {
                    // find the most right node of new root
                    Node<K, V> right = root;
                    while (right.right != null)
                        right = right.right;
                    // right of deleting node becomes right of the most right node of new root
                    right.right = node.right;
                    node.right.parent = right;

                }
            } else if (node.right != null) {
                // if deleting node has only right part - that right part becomes new root
                root = node.right;
                root.parent = null;
            } else {
                root = null;
            }
        } else {
            if (node == parent.left) {
                if (node.left != null) {
                    // this left node becomes new left of parent
                    parent.left = node.left;
                    node.left.parent = parent;
                    // if deleting node also has right
                    if (node.right != null) {
                        // find the most right node of new left node
                        Node<K, V> right = node.left;
                        while (right.right != null)
                            right = right.right;
                        // right of deleting node becomes right of the most right node of new left node
                        right.right = node.right;
                        node.right.parent = right;
                    }
                } else if (node.right != null) {
                    // if deleting node has only right part - that right part becomes new left node
                    parent.left = node.right;
                    node.right.parent = parent;
                } else {
                    parent.left = null;
                }
            } else {
                // if deleting node has left
                if (node.left != null) {
                    // this left node becomes new right of parent
                    parent.right = node.left;
                    node.left.parent = parent;
                    // if deleting node also has right
                    if (node.right != null) {
                        // find the most right node of new right node
                        Node<K, V> right = node.left;
                        while (right.right != null)
                            right = right.right;
                        // right of deleting node becomes right of the most right node of new right node
                        right.right = node.right;
                        node.right.parent = right;
                    }
                } else if (node.right != null) {
                    // if deleting node has only right part - that right part becomes new right node
                    parent.right = node.right;
                    node.right.parent = parent;
                } else {
                    parent.right = null;
                }
            }
        }
        length--;
    }

    private Node<K, V> getNextNode(Node<K, V> node) {
        if (node == null)
            return null;
        else if (node.right != null) {
            node = node.right;
            while (node.left != null)
                node = node.left;
            return node;
        } else {
            Node<K, V> child = node;
            Node<K, V> parent = node.parent;
            while (parent != null && child == parent.right) {
                child = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    public Iterable<K> iterator() {
        // find most left node
        Node<K, V> node = root;
        if (node != null)
            while (node.left != null)
                node = node.left;

        Node<K, V> mostLeftNode = node;
        return new Iterable<K>() {
            @Override
            public Iterator<K> iterator() {
                return new KeyIterator(mostLeftNode);
            }
        };
    }

    public int size() {
        return length;
    }

    class KeyIterator implements java.util.Iterator<K> {
        private Node<K, V> next;
        private Node<K, V> current;

        public KeyIterator(Node<K, V> first) {
            this.next = first;
            this.current = null;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public K next() {
            if (next == null)
                return null;

            current = next;
            next = getNextNode(next);
            return current.key;
        }
    }

}
