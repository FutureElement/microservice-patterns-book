package com.ms.zg.book.graphql.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private String id;
    private String username;
    private String password;
    private Gender gender;
    private int age;
    private Role[] roles;

}
