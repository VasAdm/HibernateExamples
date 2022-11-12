import org.hibernate.PropertyAccessException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.Scanner;

public class Main {
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    private static final Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
    private static final SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
    private static final Session session = sessionFactory.openSession();


    public static void main(String[] args) {
        boolean on = true;
        while (on) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("С какой таблицей работаем? [Courses, Students] или введите - exit");
            String input = scanner.nextLine();

            switch (input) {
                case "Courses" -> forCourse();
                case "Students" -> forStudent();
                case "exit" -> on = false;
                default -> System.out.println("Нет такой таблицы");
            }
        }
        sessionFactory.close();


    }

    private static void forCourse() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите id курса или \"exit\"");

            String userInput = scanner.nextLine();
            int id;

            if (userInput.equals("exit")) {
                break;
            } else if (userInput.matches("[0-9]+")) {
                id = Integer.parseInt(userInput);
            } else {
                continue;
            }
            try {
                Course course = session.get(Course.class, id);
                String row = course.getName() + " - " + course.getStudentsCount();
                System.out.println(row);
            } catch (PropertyAccessException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Нет такого id");
            }
        }
    }

    private static void forStudent() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите id студента или \"exit\"");

            String userInput = scanner.nextLine();
            int id;

            if (userInput.equals("exit")) {
                break;
            } else if (userInput.matches("[0-9]+")) {
                id = Integer.parseInt(userInput);
            } else {
                continue;
            }
            try {
                Student student = session.get(Student.class, id);
                String row = student.getName() + " - " + student.getAge() + " - " + student.getRegistrationDate();
                System.out.println(row);
            } catch (PropertyAccessException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Нет такого id");
            }
        }


    }
}