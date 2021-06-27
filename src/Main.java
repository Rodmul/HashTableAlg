import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static HashX[] hashArray;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        int p, q, n;
        q = scanner.nextInt();
        p = scanner.nextInt();
        n = scanner.nextInt();

        hashArray = new HashX[q];
        for (int i = 0; i < q; i++) {
            hashArray[i] = new HashX(" ", -2);
        }

        String currentAction;
        String currentKey;
        int currentValue;
        for (int i = n; i > 0; i--) {
            currentAction = scanner.next();
            currentKey = scanner.next();
            if (i != n){
                System.out.println(" ");
            }
            switch (currentAction) {
                case "PUT":
                    currentValue = Integer.parseInt(scanner.next());
                    System.out.print("key=" + currentKey + " ");
                    System.out.print("hash=" + hashFunc(currentKey, p, q) + " ");
                    put(hashFunc(currentKey, p, q), currentValue, currentKey);
                    break;
                case "GET":
                    System.out.print("key=" + currentKey + " ");
                    System.out.print("hash=" + hashFunc(currentKey, p, q) + " ");
                    get(hashFunc(currentKey, p, q), currentKey);
                    break;
                case "DEL":
                    System.out.print("key=" + currentKey + " ");
                    System.out.print("hash=" + hashFunc(currentKey, p, q) + " ");
                    delete(hashFunc(currentKey, p, q), currentKey);
                    break;
            }

        }
    }

    private static int hashFunc(String key, int p, int q) {
        long sum = 0;
        char[] temp = key.toCharArray();
        for (int i = 0; i < key.length(); i++) {
            sum += (temp[i] - 'a' + 1) * Math.pow(p, i);
        }
        sum %= q;
        return (int) sum;
    }

    private static void put(int hash, int value, String key) {
        System.out.print("operation=PUT ");
        System.out.print("result=");
        if (hashArray[hash].value == -2 || hashArray[hash].value == -1 || hashArray[hash].key.equals(key)) {
            hashArray[hash].value = value;
            hashArray[hash].key = key;
            System.out.print("inserted ");
            System.out.print("value=" + value);
        } else {
            int start = hash++;
            if (hash == hashArray.length){
                hash = 0;
            }
            while (start != hash && hashArray[hash].value != -2) {
                if (hashArray[hash].value == -1) {
                    break;
                }
                hash = (hash + 1) % hashArray.length;
            }
            if (start == hash) {
                System.out.print("overflow");
            } else {
                System.out.print("collision ");
                System.out.print("linear_probing=" + hash + " ");
                System.out.print("value=" + value);
                hashArray[hash].value = value;
                hashArray[hash].key = key;
            }
        }
    }

    private static void get(int hash, String key) {
        System.out.print("operation=GET ");
        System.out.print("result=");
        if (hashArray[hash].key.equals(key)) {
            System.out.print("found ");
            System.out.print("value=" + hashArray[hash].value);
        } else if (!hashArray[hash].key.equals(" ")) {
            System.out.print("collision ");
            int start = hash++;
            if (hash == hashArray.length){
                hash = 0;
            }
            while (start != hash) {
                if (hashArray[hash].key.equals(key)) {
                    System.out.print("linear_probing=" + hash + " ");
                    System.out.print("value=" + hashArray[hash].value);
                    break;
                }
                if (hashArray[hash].value == -2){
                    break;
                }
                hash = (hash + 1) % hashArray.length;
            }
            if (start == hash) {
                System.out.print("linear_probing=" + Math.abs(hash - 1) + " ");
                System.out.print("value=no_key");
            }
            if (hashArray[hash].value == -2){
                System.out.print("linear_probing=" + hash + " ");
                System.out.print("value=no_key");
            }
        } else {
            System.out.print("no_key");
        }
    }

    private static void delete(int hash, String key) {
        System.out.print("operation=DEL ");
        System.out.print("result=");
        if (hashArray[hash].key.equals(key)) {
            hashArray[hash].value = -1;
            hashArray[hash].key = "Tombstone";
            System.out.print("removed");
        } else if (!hashArray[hash].key.equals(" ")) {
            System.out.print("collision ");
            int start = hash++;
            if (hash == hashArray.length){
                hash = 0;
            }
            while (start != hash) {
                if (hashArray[hash].key.equals(key)) {
                    System.out.print("linear_probing=" + hash + " ");
                    System.out.print("value=removed");
                    break;
                }
                if (hashArray[hash].value == -2){
                    break;
                }
                hash = (hash + 1) % hashArray.length;
            }
            if (start == hash) {
                System.out.print("linear_probing=" + Math.abs(hash - 1) + " ");
                System.out.print("value=no_key");
            }
            if (hashArray[hash].value == -2){
                System.out.print("linear_probing=" + hash + " ");
                System.out.print("value=no_key");
            }
        } else {
            System.out.print("no_key");
        }
    }
}

class HashX {
    String key;
    int value;

    HashX(String key, int value) {
        this.key = key;
        this.value = value;
    }
}