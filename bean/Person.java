package com.jonny.myexamplepac.bean;

/**
 * Created by Jonny on 2016/4/14.
 */
public class Person {
    public String firstName;
    public String lastName;
    public String email;

    @Override
    public String toString() {
        return "Person{"+firstName+"  "+lastName+"  "+email+"}";
    }
}
