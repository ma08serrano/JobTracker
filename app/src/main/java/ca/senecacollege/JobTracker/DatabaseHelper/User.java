package ca.senecacollege.JobTracker.DatabaseHelper;

/*
 * this is a User class where it keeps an track of user data within app. This class takes following
 * parameters: email, username, password, firstname and last name.
 *
 */

public class User{
        private int user_id;
        private String email;
        private String username;
        private String password;
        private String first_name;
        private String last_name;

        private static User currentUser = new User();

        public User(){}

        public User( String _email, String _username, String _password, String _first_name, String _last_name){
            this.email = _email;
            this.username = _username;
            this.password = _password;
            this.first_name = _first_name;
            this.last_name = _last_name;
        }

        public void setId(int _user_id){
            this.user_id = _user_id;
        }
        
        public int getID() {
            return this.user_id;
        }

        public void setEmail(String _email){
            this.email = _email;
        }

        public String getEmail() {
            return  this.email;
        }

        public void setUsername(String _username){
            this.username = _username;
        }

        public String getUsername(){
            return  this.username;
        }

        public void setPassword(String _password){
            this.password = _password;
        }

        public String getPassword(){
            return  this.password;
        }

        public void setFirstName(String _first_name){
            this.first_name = _first_name;
        }

        public String getFirstName(){
            return  this.first_name;
        }

        public void setLastName(String _last_name){
            this.last_name = _last_name;
        }

        public String getLastName(){
            return  this.last_name;
        }

        public static void setUser(User _currentUser){
            currentUser = _currentUser;
        }

        public static User getInstance() {return currentUser;}
}

