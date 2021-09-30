package br.com.finchsolucoes.xgracco.domain.entity;

import java.util.Set;

public class UserLdapAutenticated {

    private final String username;
    private final String email;
    private final Set<String> groups;

    public UserLdapAutenticated(String username, String email, Set<String> memberOf) {
        this.username = username;
        this.email = email;
        this.groups = memberOf;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getGroups() {
        return groups;
    }
}
