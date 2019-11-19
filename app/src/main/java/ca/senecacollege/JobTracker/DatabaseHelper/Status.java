package ca.senecacollege.JobTracker.DatabaseHelper;

/*
 * this is a status class which is used by MyDBHandler to keep a track of status for job application process
 * it takes one variable which is status_name which will act in jobs table as a look up table.
 *
 */

public class Status{
        private int status_id;
        private String status_name;

        public Status(){}

        public Status(String _status_name){
            this.status_name = _status_name;
        }

        public void setStatusId(int _status_id){
            this.status_id = _status_id;
        }

        public int getStatusId(){
            return this.status_id;
        }

        public void setStatusName(String _status_name){
            this.status_name = _status_name;
        }

        public String getSttusName(){
            return this.status_name;
        }
}

