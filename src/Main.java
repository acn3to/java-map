import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Map<String, Student> studentsMap = new HashMap<>();

    public static void main(String[] args) {
        studentsMap.put("Rock Lee", new Student("Rock Lee", 90.5));
        studentsMap.put("Ichigo", new Student("Ichigo", 80.0));
        studentsMap.put("Gengar", new Student("Gengar", 70.54));
        studentsMap.put("Balrog", new Student("Balrog", 92.01));
        studentsMap.put("Vader", new Student("Vader", 88.5));

        System.out.println("Map Content:");
        studentsMap.forEach((name, student) -> System.out.println(name + ": " + student.getGrade()));

        System.out.println("\nGrade of Ichigo: " + retrieveGrade("Ichigo"));
        System.out.println("Students with grades above 80.5: " + listStudentsAboveGrade(80.5));

        List<String> removedStudents = removeStudentsBelowGrade(80.0);
        System.out.println("Removed students: " + removedStudents);

        Map<String, List<Student>> sortedStudents = sortStudentsByGrade();
        System.out.println("\nSorted students:");
        sortedStudents.values().forEach(students ->
                students.forEach(student -> System.out.println(student.getName() + ": " + student.getGrade()))
        );

        Map<String, List<Student>> groupedStudents = groupStudentsByGradeRange();
        System.out.println("\nGrouped students:");
        groupedStudents.forEach((range, students) -> {
            System.out.println("Range " + range + ": ");
            students.forEach(student -> System.out.println(student.getName() + " - " + student.getGrade()));
        });
    }

    public static double retrieveGrade(String name) {
        return studentsMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(name))
                .findFirst()
                .map(entry -> entry.getValue().getGrade())
                .orElseGet(() -> {
                    System.out.println("Student " + name + " not found.");
                    return -1.0;
                });
    }

    public static List<String> listStudentsAboveGrade(double minGrade) {
        return studentsMap.entrySet().stream()
                .filter(entry -> entry.getValue().getGrade() > minGrade)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    public static List<String> removeStudentsBelowGrade(double limitGrade) {
        List<String> removedStudents = studentsMap.entrySet().stream()
                .filter(entry -> entry.getValue().getGrade() < limitGrade)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        removedStudents.forEach(studentsMap.keySet()::remove);
        return removedStudents;
    }

    public static Map<String, List<Student>> sortStudentsByGrade() {
        return studentsMap.values().stream()
                .collect(Collectors.groupingBy(
                        student -> calculateGradeRange(student.getGrade()),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    public static Map<String, List<Student>> groupStudentsByGradeRange() {
        return studentsMap.values().stream()
                .collect(Collectors.groupingBy(
                        student -> calculateGradeRange(student.getGrade()),
                        HashMap::new,
                        Collectors.toList()
                ));
    }

    private static String calculateGradeRange(double grade) {
        return switch ((int) grade / 10) {
            case 10, 9 -> "A";
            case 8 -> "B";
            case 7 -> "C";
            case 6 -> "D";
            default -> "F";
        };
    }
}
