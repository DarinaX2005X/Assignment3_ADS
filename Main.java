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
        int hash = 17;

        for (int i = 0; i < name.length(); i++) {
            hash = hash * 31 + name.charAt(i);
        }

        for (int i = 0; i < surname.length(); i++) {
            hash = hash * 31 + surname.charAt(i);
        }

        return hash * 31 + (int)(gpa * 10000);
    }
}

public class Main {
    public static void main(String[] args) {
        MyHashTable<Integer, Student> table = new MyHashTable<>();

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int nameLength = random.nextInt(4, 8);
            int surnameLength = random.nextInt(6, 15);

            String name = "" + (char) ('A' + random.nextInt(26));
            for (int k = 1; k < nameLength; k++) {
                char letter = (char) ('a' + random.nextInt(26));
                name += letter;
            }
            String surname = "" + (char) ('A' + random.nextInt(26));
            for (int k = 1; k < surnameLength; k++) {
                char letter = (char) ('a' + random.nextInt(26));
                surname += letter;
            }

            int gpa = random.nextInt(400);

            Student value = new Student(name, surname, gpa);
            table.put(value.hashCode(), value);
        }
    }
}
