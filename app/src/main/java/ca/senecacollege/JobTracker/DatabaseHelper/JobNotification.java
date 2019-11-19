package ca.senecacollege.JobTracker.DatabaseHelper;

/*
 * This is jobNotification linking table class used by MyDBHandler class for generating getter and setter.
  * it takes following paratmeters: job_notification_id, user_job_id and notification_id.
  * this linking class is necessary for linkking job and notification for many to many relationship.
 *
 */

public class JobNotification{
        private int job_notification_id;
        private int user_job_id;
        private int notification_id;

        public JobNotification(){}

        public JobNotification(int _user_job_id, int _notification_id){
            this.user_job_id = _user_job_id;
            this.notification_id = _notification_id;
        }

        public void setJobNotificationId(int _job_notification_id){
            this.job_notification_id  = _job_notification_id;
        }

        public int getJob_notification_id() {
            return this.job_notification_id;
        }

        public void setUserJobId(Integer _user_job_id){
            this.user_job_id = _user_job_id;
        }

        public int getUser_job_id() {
            return this.user_job_id;
        }

        public void setNotificationId(int _notification_id){
            this.notification_id = _notification_id;
        }

        public int getNotification_id() {
            return this.notification_id;
        }
}
