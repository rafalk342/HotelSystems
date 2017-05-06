package authorization;

import authorization.models.Session;
import authorization.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

import static play.mvc.Controller.session;

public class Authenticator {

    public static User getUser() throws NoSuchFieldException, IllegalAccessException {
        if(session().containsKey("sessionId")) {
            String sessionId = session().get("sessionId");
            Session session = new Session();

            if(session.find("sessionId", session.sessionId)) {
                User user = new User();
                user.get(session.userId);
                return user;
            }
        }
        return null;
    }

    public static void logIn(String email, String pass) throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        if(user.find("mail", email) && checkPass(pass, user.passHash)) {
            makeNewSession(user);
        } else {
            throw new NoSuchUserException();
        }
    }

    public static void LogOut() throws NoSuchFieldException, IllegalAccessException {
        if(session().containsKey("sessionId")) {
            String sessionId = session().get("sessionId");
            Session session = new Session();

            if(session.find("sessionId", session.sessionId)) {
                session.delete();
            }
            session().remove("sessionId");
        }
    }

    public static void SignUp(User user, String pass) throws NoSuchFieldException, IllegalAccessException {
        if(new User().find("mail", user.mail)) {
            throw new ThereIsSuchUserNameException();
        }
        user.passHash = getHash(pass);
        user.save();
    }

    public static String getHash(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public static boolean checkPass(String pass, String passHash) {
        return BCrypt.checkpw(pass, passHash);
    }


    public static void makeNewSession(User user) throws NoSuchFieldException, IllegalAccessException {
        final int LEN_OF_SESSION_ID = 20;
        final long TEN_DAYS = 1000*3600*24*10;
        Session session = new Session(0, "0", user.id, new Date().getTime() + TEN_DAYS);
        session.getNewId(LEN_OF_SESSION_ID);
        session.save();
        session().put("sessionId", session.sessionId);
    }

}

class NoSuchUserException extends RuntimeException {}

class ThereIsSuchUserNameException extends RuntimeException {}