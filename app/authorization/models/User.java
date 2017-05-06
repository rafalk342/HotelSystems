package authorization.models;

import authorization.Authenticator;
import models.Model;

public class User extends Model {

    public User() {}

    public User(int id, String mail, String pass, boolean isAdmin) {
        this.id = id;
        this.mail = mail;
        this.passHash = Authenticator.getHash(pass);
        this.isAdmin = isAdmin;
    }

    public int id;
    public String mail;
    public String passHash;
    public boolean isAdmin;
}

