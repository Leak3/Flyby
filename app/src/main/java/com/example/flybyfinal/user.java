package com.example.flybyfinal;

public class user {
    private String username;
    private String password;
    private String email;
    private String fullname;
    private Double balance;


    //constructor for class given all the values.
    public user(String username, String password, String email, String fullname, Double balance) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.balance = balance;
    }



    //getters and setters for class.
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    //tostring method
    @Override
    public String toString() {
        return "user{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", balance=" + balance +
                '}';
    }
}
