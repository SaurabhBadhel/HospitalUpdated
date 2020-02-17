package com.pureintentions.hospital.Doctor;



/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoggedInUser user = null;



    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }


    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    public void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    public LoggedInUser getLoggedInUser(){
        return this.user;
    }



}
