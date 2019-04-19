package com.ms.zg.book.springcloudcontractconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    private String id;
    private String username;
    private Role[] roles;
}
