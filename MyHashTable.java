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
        this(11, 0.85);
    }

    public MyHashTable(int capacity) {
        this(capacity, 0.85);
    }

    public MyHashTable(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
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
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            };
            if (current.key.equals(key)) {
                if (current.value.equals(value))
                    System.out.println(value);
                current.value = value;
                return;
            }
            current.next = newNode;
        }
        size++;

        //if (size >= M)
         //   rehash();
    }
    private void rehash() {
        int newCapacity = capacity*2 + 1;
        HashNode<K,V>[] newChainArray = new HashNode[newCapacity];

        for (int i = 0; i < chainArray.length ; i++) {
            HashNode<K,V> entry = chainArray[i];
            while (entry != null) {
                HashNode<K, V> newEntry = entry;
                entry = entry.next;
                int newIndex = hash(newEntry.key);
                newEntry.next = newChainArray[newIndex];
                newChainArray[newIndex] = newEntry;
            }
        }

        chainArray = newChainArray;
        capacity = newCapacity;
        M = (int) (newCapacity * loadFactor);
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

    public void printBuckets() {
        System.out.println("Capacity: "+ capacity);
        for (int i = 0; i < chainArray.length ; i++) {
            int bucketSize = 0;
            HashNode<K,V> entry = chainArray[i];
            while(entry != null) {
                bucketSize++;
                entry = entry.next;
            }

            System.out.println(i + ": bucketSize = " + bucketSize);
        }
    }
}