package org.floric.runningdinner.main.core;

/** Person class
 *
 * Created by florian on 28.02.2016.
 */
public class Person {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String strFromat) {
        formatCommaInput(strFromat, false);
    }

    public Person() {
        this.firstName = "Vorname";
        this.lastName = "Nachname";
    }

    public void formatCommaInput(String strFromat, boolean needsSafe) {
        String[] names = strFromat.split(",");

        if (names.length >= 2) {
            this.lastName = names[0];
            this.firstName = names[1];
            if (firstName.indexOf(" ") == 0) {
                firstName = firstName.substring(1);
            }
        } else {
            this.firstName = names[0];
        }

        if (needsSafe) {
            Core.getInstance().setToDirtySafeState();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        Core.getInstance().setToDirtySafeState();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        Core.getInstance().setToDirtySafeState();
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }
}
