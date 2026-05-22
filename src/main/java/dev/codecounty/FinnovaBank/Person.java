package dev.codecounty.FinnovaBank;

public abstract class Person {

    private int    personId;
    private String name;
    private String phoneNumber;

    public Person(int personId, String name, String phoneNumber) {
        this.personId    = personId;
        this.name        = name;
        this.phoneNumber = phoneNumber;
    }

    public abstract void displayDetails();

    public int    getPersonId()                     { return personId; }
    public void   setPersonId(int personId)         { this.personId = personId; }

    public String getName()                         { return name; }
    public void   setName(String name)              { this.name = name; }

    public String getPhoneNumber()                  { return phoneNumber; }
    public void   setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }
}