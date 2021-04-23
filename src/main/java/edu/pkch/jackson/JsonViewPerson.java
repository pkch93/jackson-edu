package edu.pkch.jackson;

import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

/**
 * com.fasterxml.jackson.annotation.JsonView 는 기본적으로 선언이 된 필드를 직렬화한다.
 *
 * {@link com.fasterxml.jackson.annotation}
 */
public class JsonViewPerson {

    @JsonView(View.Name.class)
    private String name;

    @JsonView(View.Age.class)
    private int age;

    private int grade;

    private LocalDateTime birthDay;

    public JsonViewPerson(String name, int age, int grade, LocalDateTime birthDay) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.birthDay = birthDay;
    }
}
