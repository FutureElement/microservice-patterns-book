package com.ms.zg.book.security.model;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

@Builder
@Getter

public class User implements Serializable {
    private String id;
    private String username;
    private Set<String> roles;
}
