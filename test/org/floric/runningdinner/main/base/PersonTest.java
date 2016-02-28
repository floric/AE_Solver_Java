package org.floric.runningdinner.main.base;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by florian on 28.02.2016.
 */
public class PersonTest {

    private static String firstName = "ABC";
    private static String lastName = "XYZ";

    private static String firstNameNew = "ABCNew";
    private static String lastNameNew = "XYZNew";
    private Person p;

    @Before
    public void createPerson() {
        p = new Person(firstName, lastName);
    }

    @Test
    public void testGetFirstName() throws Exception {
        assert(p.getFirstName().compareTo(firstName) == 0);
    }

    @Test
    public void testSetFirstName() throws Exception {
        p.setFirstName(firstNameNew);
        assert(p.getFirstName().compareTo(firstNameNew) == 0);
    }

    @Test
    public void testGetLastName() throws Exception {
        assert(p.getLastName().compareTo(lastName) == 0);
    }

    @Test
    public void testSetLastName() throws Exception {
        p.setLastName(lastNameNew);
        assert(p.getLastName().compareTo(lastNameNew) == 0);
    }
}