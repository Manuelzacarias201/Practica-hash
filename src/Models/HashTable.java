package Models;

import java.util.LinkedList;

public class HashTable<K, V> {
    private LinkedList<HashNode<K, V>>[] chainArray;
    private int M; // tamaño del array

    // Constructor
    public HashTable(int M) {
        this.M = M;
        chainArray = new LinkedList[M];
        for (int i = 0; i < M; i++) {
            chainArray[i] = new LinkedList<>();
        }
    }

    // Primera función hash
    private int hashFunction1(K key) {
        return Math.abs(key.hashCode()) % M;
    }

    // Segunda función hash
    private int hashFunction2(K key) {
        return (Math.abs(key.hashCode()) / M) % M;
    }

    // Método para insertar
    public void put(K key, V value, boolean useHashFunction1) {
        int index = useHashFunction1 ? hashFunction1(key) : hashFunction2(key);
        LinkedList<HashNode<K, V>> chain = chainArray[index];
        for (HashNode<K, V> node : chain) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        chain.add(new HashNode<>(key, value));
    }

    // Método para obtener
    public V get(K key, boolean useHashFunction1) {
        int index = useHashFunction1 ? hashFunction1(key) : hashFunction2(key);
        LinkedList<HashNode<K, V>> chain = chainArray[index];
        for (HashNode<K, V> node : chain) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    // Método para obtener todos los datos
    public void displayAll() {
        for (int i = 0; i < M; i++) {
            LinkedList<HashNode<K, V>> chain = chainArray[i];
            if (!chain.isEmpty()) {
                for (HashNode<K, V> node : chain) {
                    System.out.println("Key: " + node.key + " -> Value: " + String.join(", ", (String[]) node.value));
                }
            }
        }
    }
}
