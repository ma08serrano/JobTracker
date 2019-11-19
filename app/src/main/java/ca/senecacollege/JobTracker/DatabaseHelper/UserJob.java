package ca.senecacollege.JobTracker.DatabaseHelper;

/*
 * this is a Userjob link table class where it will take following parameters: user_id and job_id.
 * this table will maintain a one to many relation where one user will have many job applied.
 *
 */

public class UserJob{
        private int user_job_id;
        private int user_id;
        private int job_id;

    private int category_id;

        public UserJob(){}

        public UserJob(int _user_job_id, int _user_id, int _job_id){
            this.user_job_id  = _user_job_id ;
            this.user_id = _user_id;
            this.job_id = _job_id;
        }

        public void setUser_job_id(int _user_job_id){
            this.user_job_id  = _user_job_id;
        }

        public int getUser_job_id() {
            return this.user_job_id;
        }

        public void setUserId(int _user_id){
            this.user_id = _user_id;
        }

        public int getUser_id() {
            return this.user_id;
        }

        public void set_job_id(int _job_id){
            this.job_id = _job_id;
        }

        public int getJob_id() {
            return this.job_id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

}
