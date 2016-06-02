package com.github.experimental.transform;

/**
 *
 * @author V.Ladynev
 */
public class UserDto {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return String.format("login: %s", login);
    }

}
