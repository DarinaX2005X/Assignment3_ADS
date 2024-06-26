import java.util.Random;

class Student {
    private String name;
    private String surname;
    private int gpa;

    public Student(String name, String surname, int gpa) {
        this.name = name;
        this.surname = surname;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student{name= " + name + " surname = " + surname + " gpa = " + gpa + "}";
    }

    @Override
    public int hashCode() {
        int hash = gpa;

        for (int i = 0; i < name.length(); i++) {
            hash = hash * 31 + name.charAt(i);
        }

        for (int i = 0; i < surname.length(); i++) {
            hash = hash * 31 + surname.charAt(i);
        }

        return hash;
    }
}

public class Main {
    public static void main(String[] args) {
        MyHashTable<Integer, Student> table = new MyHashTable<>(200);

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int nameLength = 3 + (int) (Math.random() * 6);
            int surnameLength = 4 + (int) (Math.random() * 12);

            String name = "" + (char) ('A' + (int) (Math.random() * 26));
            for (int k = 1; k < nameLength; k++) {
                char letter = (char) ('a' + (int) (Math.random() * 26));
                name += letter;
            }
            String surname = "" + (char) ('A' + (int) (Math.random() * 26));
            for (int k = 1; k < surnameLength; k++) {
                char letter = (char) ('a' + (int) (Math.random() * 26));
                surname += letter;
            }

            int gpa = (int) (Math.random() * 400);

            Student value = new Student(name, surname, gpa);
            table.put(value.hashCode(), value);
        }
        table.printBuckets();

        BST<Integer, String> bst = new BST<>();
        bst.put(777, "Almaty");
        bst.put(888, "Astana");
        bst.put(778, "London");
        bst.put(333, "Taldykorgan");
        //bst.delete(888);
        System.out.println(bst.size());
        System.out.println(bst.get(778));

        for (Integer key : bst.iterator()) {
            System.out.println("key is " + key + " and value is " + bst.get(key));
        }
    }
}
