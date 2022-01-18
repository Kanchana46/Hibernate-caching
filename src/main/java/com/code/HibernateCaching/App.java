package com.code.HibernateCaching;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class App 
{
    public static void main( String[] args )
    {
    	Configuration con = new Configuration().configure().addAnnotatedClass(UserDetails.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
        SessionFactory sessionFactory = con.buildSessionFactory(serviceRegistry);       
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        // First level cache
        /*UserDetails user = null;
        user = session.get(UserDetails.class, 2);
        System.out.println(user);
        
        user = session.get(UserDetails.class, 2);
        System.out.println(user);
        
        session.getTransaction().commit();
        session.close();*/
        
        // Second level cache
        /*Session session1 = sessionFactory.openSession();
        session1.beginTransaction();
        
        UserDetails user = null;
        user = session1.get(UserDetails.class, 2);
        System.out.println(user);
        
        session1.getTransaction().commit();
        session1.close();
        
        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();
        
        user = session2.get(UserDetails.class, 2);
        System.out.println(user);
        
        session2.getTransaction().commit();
        session2.close();*/
        
        Session session1 = sessionFactory.openSession();
        session1.beginTransaction();
        
        UserDetails user = null;
        Query q1 = session1.createQuery("from UserDetails where id=4");
        q1.setCacheable(true);
        user = (UserDetails)q1.uniqueResult();
        System.out.println(user);
        
        session1.getTransaction().commit();
        session1.close();
        
        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();
        
        Query q2 = session2.createQuery("from UserDetails where id=4");
        q2.setCacheable(true);
        user = (UserDetails)q2.uniqueResult();
        System.out.println(user);
        
        session2.getTransaction().commit();
        session2.close();
        
    }
}
