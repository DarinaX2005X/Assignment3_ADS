public class MyHashTable<K, V> {
    private class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;

    private int size;
    private double loadFactor = 0.75;
    private int capacity = 11;
    private int M = (int)(capacity * loadFactor);
    public MyHashTable() {
        this(11, 0.75);
    }

    public MyHashTable(int capacity) {
        this(capacity, 0.75);
    }

    public MyHashTable(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.chainArray = new HashNode[capacity];
        this.M = (int)(capacity*loadFactor);
    }

    private int hash(K key) {
        return (key.hashCode() & Integer.MAX_VALUE) % capacity;
    }

    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);
        if (chainArray[index] == null) {
            chainArray[index] = newNode;
        } else {
            HashNode<K, V> current = chainArray[index];
            do {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                if(current.next == null) {
                    current.next = newNode;
                }
                current = current.next;
            } while(current != null);
        }
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        HashNode<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    chainArray[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public boolean contains(V value) {
        return getKey(value) != null;
    }

    public K getKey(V value) {
        for (HashNode<K, V> node : chainArray) {
            HashNode<K, V> current = node;
            while (current != null) {
                if (current.value.equals(value)) {
                    return current.key;
                }
                current = current.next;
            }
        }
        return null;
    }
}