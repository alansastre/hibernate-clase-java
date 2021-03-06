package util;

import com.example.model.Employee;
import com.example.model.Project;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class ManyToManyTest {


    @Test
    @DisplayName("createProjects")
    public void createProjects() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        // session.createQuery("from Employee e where e.projects")

        Project project1 = new Project("Proyecto 1", "PRJ1", "Proyecto Fin de curso Frontend");
        Project project2 = new Project("Proyecto 2", "PRJ2", "Proyecto Fin de curso Backend");
        Project project3 = new Project("Proyecto 3", "PRJ3", "Proyecto Fin de curso Full stack");

        Employee employee1 = new Employee("Employee 1", 33, true, 30000D, Instant.now());
        Employee employee2 = new Employee("Employee 2", 33, true, 30000D, Instant.now());

        employee1.getProjects().add(project1);
        employee1.getProjects().add(project2);
        employee1.getProjects().add(project3);

        employee2.getProjects().add(project2);

        session.save(project1);
        session.save(project2);

        session.save(project3);

        session.save(employee1);
        session.save(employee2);

        session.getTransaction().commit();

    }

    @Test
    @DisplayName("createProjectBidirectional")
    public void createProjectBidirectional() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Project project1 = new Project("Proyecto 1", "PRJ1", "Proyecto Fin de curso Frontend");
        Project project2 = new Project("Proyecto 2", "PRJ2", "Proyecto Fin de curso Backend");
        Project project3 = new Project("Proyecto 3", "PRJ3", "Proyecto Fin de curso Full stack");

        Employee employee1 = new Employee("Employee 1", 33, true, 30000D, Instant.now());
        Employee employee2 = new Employee("Employee 2", 33, true, 30000D, Instant.now());

        // project1.getEmployees().add(employee1); // not owner
        employee1.getProjects().add(project1); // owner

        session.save(employee1);
        session.save(project1);

        session.getTransaction().commit();
        session.close();

        System.out.println("=================================");
        session = HibernateUtil.getSessionFactory().openSession();
        Project project1DB = session.find(Project.class, project1.getId());
        System.out.println(project1DB);
        System.out.println(project1DB.getEmployees()); // LAZY - hace un segundo select para recuperar employees


        System.out.println(project1DB.getEmployees().get(0));
        session.close();

    }


    @Test
    @DisplayName("Delete project from Employee")
    public void deleteProjectFromEmployee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Project project1 = new Project("Proyecto 1", "PRJ1", "Proyecto Fin de curso Frontend");
        Project project2 = new Project("Proyecto 2", "PRJ2", "Proyecto Fin de curso Backend");
        Project project3 = new Project("Proyecto 3", "PRJ3", "Proyecto Fin de curso Full stack");
        Employee employee1 = new Employee("Employee 1", 33, true, 30000D, Instant.now());
        Employee employee2 = new Employee("Employee 2", 33, true, 30000D, Instant.now());
        employee1.getProjects().add(project1);
        employee1.getProjects().add(project2);
        employee1.getProjects().add(project3);

        session.save(employee1);
        session.save(project1);
        session.save(project2);
        session.save(project3);

        session.getTransaction().commit();
        session.close();

        System.out.println("=================================");
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Employee employee = session.find(Employee.class, employee1.getId());
        employee.getProjects().remove(0); // Desasociar el primer project
        session.save(employee);
        session.getTransaction().commit();
        session.close();

    }

    @Test
    @DisplayName("Delete Employee From Project")
    public void deleteEmployeeFromProject() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Project project1 = new Project("Proyecto 1", "PRJ1", "Proyecto Fin de curso Frontend");
        Project project2 = new Project("Proyecto 2", "PRJ2", "Proyecto Fin de curso Backend");
        Project project3 = new Project("Proyecto 3", "PRJ3", "Proyecto Fin de curso Full stack");
        Employee employee1 = new Employee("Employee 1", 33, true, 30000D, Instant.now());
        Employee employee2 = new Employee("Employee 2", 33, true, 30000D, Instant.now());
        employee1.getProjects().add(project1);
        employee1.getProjects().add(project2);
        employee1.getProjects().add(project3);

        session.save(employee1);
        session.save(project1);
        session.save(project2);
        session.save(project3);

        session.getTransaction().commit();
        session.close();

        System.out.println("=================================");
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Project project1DB = session.find(Project.class, project1.getId());
        project1DB.getEmployees().remove(0); // Desasociar el primer employee
        session.save(project1DB);
        session.getTransaction().commit();
        session.close();

    }


}
