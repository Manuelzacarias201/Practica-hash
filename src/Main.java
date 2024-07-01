import Models.HashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashTable<String, String[]> hashTable1 = new HashTable<>(10000);
        HashTable<String, String[]> hashTable2 = new HashTable<>(10000);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Insertar datos manualmente");
            System.out.println("2. Insertar datos desde archivo CSV");
            System.out.println("3. Buscar registro por ID");
            System.out.println("4. Mostrar todos los registros");
            System.out.println("5. Salir");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (choice) {
                case 1:
                    insertDataManually(hashTable1, hashTable2, scanner);
                    break;
                case 2:
                    insertDataFromCSV(hashTable1, hashTable2);
                    break;
                case 3:
                    searchRecord(hashTable1, hashTable2, scanner);
                    break;
                case 4:
                    showAllData(hashTable1, hashTable2);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void insertDataManually(HashTable<String, String[]> hashTable1, HashTable<String, String[]> hashTable2, Scanner scanner) {
        System.out.println("Ingrese los datos en el siguiente formato: ID,Nombre,Dirección,Ciudad,Estado");
        String input = scanner.nextLine();
        String[] business = input.split(","); // use comma as separator
        String key = business[0];

        // Insertar en la primera tabla hash usando la primera función hash
        hashTable1.put(key, business, true);

        // Insertar en la segunda tabla hash usando la segunda función hash
        hashTable2.put(key, business, false);

        System.out.println("Datos insertados correctamente.");
    }

    private static void insertDataFromCSV(HashTable<String, String[]> hashTable1, HashTable<String, String[]> hashTable2) {
        String fileName = "bussines.csv";
        int numElements = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String splitBy = ",";
            while ((line = br.readLine()) != null) {
                String[] business = line.split(splitBy); // use comma as separator
                String key = business[0];

                // Insertar en la primera tabla hash usando la primera función hash
                hashTable1.put(key, business, true);

                // Insertar en la segunda tabla hash usando la segunda función hash
                hashTable2.put(key, business, false);

                numElements++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Número de elementos insertados: " + numElements);
    }

    private static void searchRecord(HashTable<String, String[]> hashTable1, HashTable<String, String[]> hashTable2, Scanner scanner) {
        System.out.println("Ingrese la clave del registro a buscar:");
        String key = scanner.nextLine();

        long startTime, endTime;
        String[] result;

        // Buscar usando la primera función hash
        startTime = System.nanoTime();
        result = hashTable1.get(key, true);
        endTime = System.nanoTime();
        if (result != null) {
            System.out.println("Registro encontrado usando hashFunction1: " + String.join(", ", result));
        } else {
            System.out.println("Registro no encontrado usando hashFunction1.");
        }
        System.out.println("Tiempo de búsqueda usando hashFunction1: " + (endTime - startTime) + " nanosegundos");

        // Buscar usando la segunda función hash
        startTime = System.nanoTime();
        result = hashTable2.get(key, false);
        endTime = System.nanoTime();
        if (result != null) {
            System.out.println("Registro encontrado usando hashFunction2: " + String.join(", ", result));
        } else {
            System.out.println("Registro no encontrado usando hashFunction2.");
        }
        System.out.println("Tiempo de búsqueda usando hashFunction2: " + (endTime - startTime) + " nanosegundos");
    }

    private static void showAllData(HashTable<String, String[]> hashTable1, HashTable<String, String[]> hashTable2) {
        System.out.println("Datos en hashTable1:");
        hashTable1.displayAll();

        System.out.println("Datos en hashTable2:");
        hashTable2.displayAll();
    }
}
