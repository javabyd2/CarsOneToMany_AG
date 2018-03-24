package com.sdabyd2.onetomany;

import com.sdabyd2.onetomany.entity.UserDetailsEntity;
import com.sdabyd2.onetomany.entity.VehicleEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();

        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public static void main(String[] args) {

        UserDetailsEntity userDetailsEntity1 = new UserDetailsEntity();
        userDetailsEntity1.setUserName("Adrian");

        UserDetailsEntity userDetailsEntity2 = new UserDetailsEntity();
        userDetailsEntity2.setUserName("Karol");


        Transaction transaction = null;
        Session session = getSession();

        transaction = session.beginTransaction(); // rozpoczynamy transakcję

        session.save(userDetailsEntity1);
    //    session.save(userDetailsEntity2);

        transaction.commit(); // kończymy (commitujemy) transakcję


        VehicleEntity vehicleEntity1 = new VehicleEntity();
        vehicleEntity1.setVehicleName("Passat");

        vehicleEntity1.setUserDetailsEntity(userDetailsEntity1);

        transaction = session.beginTransaction();
        session.save(vehicleEntity1);
        transaction.commit();


        //-----------------------
        VehicleEntity vehicleEntity2 = new VehicleEntity();
        vehicleEntity2.setVehicleName("BMW");
        vehicleEntity2.setUserDetailsEntity(userDetailsEntity2);

            //  -- jeśli użytkownika userDetailsEntity2 nie ma w bazie (tzn. nie jest session.save(userDetailsEntity2);), wyskoczy błąd
            // żeby dodawać auto i użytkownika jednocześnie, trzeba użyć kaskadowości!
        transaction = session.beginTransaction();
        session.save(vehicleEntity2);
        transaction.commit();
        //-----------------------

        // Cwiczenie - 12 samochodów i 8 użytkowników. Każdy użytkownik ma mieć auto!

        UserDetailsEntity userDetailsEntity10 = new UserDetailsEntity();
        userDetailsEntity10.setUserName("Jan");
        UserDetailsEntity userDetailsEntity11 = new UserDetailsEntity();
        userDetailsEntity11.setUserName("Marian");
        UserDetailsEntity userDetailsEntity12 = new UserDetailsEntity();
        userDetailsEntity12.setUserName("Andrzej");
        UserDetailsEntity userDetailsEntity13 = new UserDetailsEntity();
        userDetailsEntity13.setUserName("Jerzy");
        UserDetailsEntity userDetailsEntity14 = new UserDetailsEntity();
        userDetailsEntity14.setUserName("Władysław");
        UserDetailsEntity userDetailsEntity15 = new UserDetailsEntity();
        userDetailsEntity15.setUserName("Stanisław");
        UserDetailsEntity userDetailsEntity16 = new UserDetailsEntity();
        userDetailsEntity16.setUserName("Grzegorz");
        UserDetailsEntity userDetailsEntity17 = new UserDetailsEntity();
        userDetailsEntity17.setUserName("Mieczysław");

        VehicleEntity vehicleEntity10 = new VehicleEntity();
        vehicleEntity10.setVehicleName("Audi");
        vehicleEntity10.setUserDetailsEntity(userDetailsEntity10);
        VehicleEntity vehicleEntity11 = new VehicleEntity();
        vehicleEntity11.setVehicleName("Mercedes");
        vehicleEntity11.setUserDetailsEntity(userDetailsEntity11);
        VehicleEntity vehicleEntity12 = new VehicleEntity();
        vehicleEntity12.setVehicleName("Opel");
        vehicleEntity12.setUserDetailsEntity(userDetailsEntity12);
        VehicleEntity vehicleEntity13 = new VehicleEntity();
        vehicleEntity13.setVehicleName("Peugeot");
        vehicleEntity13.setUserDetailsEntity(userDetailsEntity13);
        VehicleEntity vehicleEntity14 = new VehicleEntity();
        vehicleEntity14.setVehicleName("Citroen");
        vehicleEntity14.setUserDetailsEntity(userDetailsEntity14);
        VehicleEntity vehicleEntity15 = new VehicleEntity();
        vehicleEntity15.setVehicleName("Renault");
        vehicleEntity15.setUserDetailsEntity(userDetailsEntity15);
        VehicleEntity vehicleEntity16 = new VehicleEntity();
        vehicleEntity16.setVehicleName("Ford");
        vehicleEntity16.setUserDetailsEntity(userDetailsEntity16);
        VehicleEntity vehicleEntity17 = new VehicleEntity();
        vehicleEntity17.setVehicleName("Honda");
        vehicleEntity17.setUserDetailsEntity(userDetailsEntity17);
        VehicleEntity vehicleEntity18 = new VehicleEntity();
        vehicleEntity18.setVehicleName("Ferrari");
        vehicleEntity18.setUserDetailsEntity(userDetailsEntity15);
        VehicleEntity vehicleEntity19 = new VehicleEntity();
        vehicleEntity19.setVehicleName("Porsche");
        vehicleEntity19.setUserDetailsEntity(userDetailsEntity15);
        VehicleEntity vehicleEntity20 = new VehicleEntity();
        vehicleEntity20.setVehicleName("Daewoo");
        vehicleEntity20.setUserDetailsEntity(userDetailsEntity13);
        VehicleEntity vehicleEntity21 = new VehicleEntity();
        vehicleEntity21.setVehicleName("Nissan");
        vehicleEntity21.setUserDetailsEntity(userDetailsEntity17);

        transaction = session.beginTransaction();
        session.save(vehicleEntity10);
        session.save(vehicleEntity11);
        session.save(vehicleEntity12);
        session.save(vehicleEntity13);
        session.save(vehicleEntity14);
        session.save(vehicleEntity15);
        session.save(vehicleEntity16);
        session.save(vehicleEntity17);
        session.save(vehicleEntity18);
        session.save(vehicleEntity19);
        session.save(vehicleEntity20);
        session.save(vehicleEntity21);
        transaction.commit();


        // Wyświetlanie
        List<UserDetailsEntity> users = session.createQuery("FROM " + UserDetailsEntity.class.getName()).list();

        Map<Integer, String> idAndNameUsers = new HashMap<>();

        System.out.println(users);
        for (UserDetailsEntity item : users) {
            System.out.println("Użytkownik: " + item.getUserName() + ", id = " + item.getUserId());
            idAndNameUsers.put(item.getUserId(), item.getUserName());
        }

        List<VehicleEntity> vehicles = session.createQuery(("FROM " + VehicleEntity.class.getName())).list();
        System.out.println(vehicles);
        for (VehicleEntity item : vehicles) {
            System.out.println("Pojazd " + item.getVehicleName() + " należy do " +
                                            idAndNameUsers.get(item.getUserDetailsEntity().getUserId()));
        }
        // koniec wyświetlania




    }
}
