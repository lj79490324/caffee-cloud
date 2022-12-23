package com.coffee.system.model;

import lombok.Data;

/**
 * @author rabit
 */
@Data
public class User {
    private String username;
    private String  password;
    private boolean enable;
}
