package me.scpark.springdeveloper;

public class Student {
    private String firstName;
    private String lastName;

    // 생성자 (Constructor)
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter (스프링이 JSON으로 변환할 때 필요합니다)
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}