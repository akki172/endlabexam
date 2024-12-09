package com.klef.jfsd.exam;

import java.lang.module.Configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class ClientDemo {
	public static void main(String[] args) {
        // Configure and build session factory
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("Hibernate.cfg.xml").build();
        Metadata md = new MetadataSources().getMetadataBuilder(ssr).build();
        
        SessionFactory sf = md.getSessionFactoryBuilder().build();
        
        // Insert operation
        insertDepartment(sf, "IT", "Bangalore", "Dr. Smith");
        insertDepartment(sf, "HR", "Mumbai", "Ms. Johnson");

        // Delete operation
        deleteDepartment(sf, 1);

        // Close factory
        sf.close();
    }

    private static void insertDepartment(SessionFactory factory, String name, String location, String hodName) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Department dept = new Department();
        dept.setName(name);
        dept.setLocation(location);
        dept.setHodName(hodName);

        session.save(dept);
        tx.commit();
        session.close();

        System.out.println("Department inserted successfully!");
    }

    private static void deleteDepartment(SessionFactory factory, int deptId) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("delete from Department where id = :id");
        query.setParameter("id", deptId);

        int result = query.executeUpdate();
        tx.commit();
        session.close();

        if (result > 0) {
            System.out.println("Department deleted successfully!");
        } else {
            System.out.println("No department found with the given ID.");
        }
    }

}
