import java.util.*;

public class Main {
    private static final Map<String, Student> studentsMap = new HashMap<>();

    public static void main(String[] args) {
        studentsMap.put("Rock Lee", new Student("Rock Lee", 9.5));
        studentsMap.put("Ichigo", new Student("Ichigo", 8.0));
        studentsMap.put("Gengar", new Student("Gengar", 7.5));
        studentsMap.put("Balrog", new Student("Balrog", 9.0));
        studentsMap.put("Vader", new Student("Vader", 8.5));

        System.out.println("Map Content:");
        studentsMap.forEach((name, student) -> System.out.println(name + ": " + student.getGrade()));

        System.out.println("\nGrade of Ichigo: " + retrieveGrade("Ichigo"));
        System.out.println("Students with grades above 8.5: " + listStudentsAboveGrade(8.5));

        List<String> removedStudents = removeStudentsBelowGrade(8.0);
        System.out.println("Removed students: " + removedStudents);

        Map<String, Student> sortedStudents = sortStudentsByGrade();
        System.out.println("\nSorted students:");
        sortedStudents.forEach((name, student) -> System.out.println(name + ": " + student.getGrade()));

        Map<String, List<Student>> groupedStudents = groupStudentsByGradeRange();
        System.out.println("\nGrouped students:");
        groupedStudents.forEach((range, students) -> {
            System.out.println("Range " + range + ": ");
            students.forEach(student -> System.out.println(student.getName() + " - " + student.getGrade()));
        });
    }

    public static double retrieveGrade(String name) {
        Student student = studentsMap.get(name);
        if (student != null) {
            return student.getGrade();
        } else {
            System.out.println("Student " + name + " not found.");
            return -1;
        }
    }

    public static List<String> listStudentsAboveGrade(double minGrade) {
        List<String> studentNames = new ArrayList<>();
        for (Map.Entry<String, Student> entry : studentsMap.entrySet()) {
            if (entry.getValue().getGrade() > minGrade) {
                studentNames.add(entry.getKey());
            }
        }
        return studentNames;
    }

    public static List<String> removeStudentsBelowGrade(double limitGrade) {
        List<String> removedStudents = new ArrayList<>();
        Iterator<Map.Entry<String, Student>> iterator = studentsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Student> entry = iterator.next();
            if (entry.getValue().getGrade() < limitGrade) {
                removedStudents.add(entry.getKey());
                iterator.remove();
            }
        }
        return removedStudents;
    }

    public static Map<String, Student> sortStudentsByGrade() {
        List<Map.Entry<String, Student>> sortedList = new ArrayList<>(studentsMap.entrySet());
        sortedList.sort((a, b) -> Double.compare(b.getValue().getGrade(), a.getValue().getGrade()));

        Map<String, Student> sortedStudents = new LinkedHashMap<>();
        for (Map.Entry<String, Student> entry : sortedList) {
            sortedStudents.put(entry.getKey(), entry.getValue());
        }

        return sortedStudents;
    }

    public static Map<String, List<Student>> groupStudentsByGradeRange() {
        Map<String, List<Student>> groupedStudents = new HashMap<>();

        for (Map.Entry<String, Student> entry : studentsMap.entrySet()) {
            String gradeRange = calculateGradeRange(entry.getValue().getGrade());
            if (!groupedStudents.containsKey(gradeRange)) {
                groupedStudents.put(gradeRange, new ArrayList<>());
            }
            groupedStudents.get(gradeRange).add(entry.getValue());
        }

        return groupedStudents;
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
