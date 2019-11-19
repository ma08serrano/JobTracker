package ca.senecacollege.JobTracker.DatabaseHelper;

/*
 * This is Notification class used by MyDBHandler class that will generate notification for users based
 * on jobs and user setted notification settings. it takes following parameter: name, start_date, end_date,
 * start_time, end_time, all_day, note and colour_id which has a foreign key relation to the colour class.
 *
 */

public class Notification{
        private int notification_id;
        private String name;
        private String start_date;
        private String end_date;
        private String start_time ;
        private String end_time;
        private String all_day;
        private String note;
        private int colour_id;

        public Notification(){}

        public Notification(String _name, String _start_date, String _end_date, String _start_time , String _end_time, String _all_day, String _note, int _colour_id){
            this.name = _name;
            this.start_date = _start_date;
            this.end_date  = _end_date;
            this.start_time  = _start_time ;
            this.end_time  = _end_time;
            this.all_day = _all_day;
            this.note  = _note;
            this.colour_id = _colour_id;
        }

        public void setNotificationId(int _notification_id){
            this.notification_id  = _notification_id;
        }

        public int getNotification_id() {
            return this.notification_id;
        }

        public void setName(String _name){
            this.name = _name;
        }

        public String getName() {
            return this.name;
        }

        public void setStartDate(String _start_date){
            this.start_date  = _start_date;
        }

        public String getStart_date() {
            return this.start_date;
        }

        public void setEndDate(String _end_date){
            this.end_date = _end_date;
        }

        public String getEnd_date() {
            return this.end_date;
        }

        public void setStartTime(String _start_time ){
            this.start_time = _start_time ;
        }

        public String getStart_time() {
            return this.start_time;
        }

        public void setEndTime(String _end_time){
            this.end_time  = _end_time;
        }

        public String getEnd_time() {
            return this.end_time;
        }

        public void setAllDay(String _all_day){
            this.all_day = _all_day;
        }

        public String getAll_day() {
            return this.all_day;
        }

        public void setNote(String _note){
            this.note = _note;
        }

        public String getNote() {
            return this.note;
        }

        public void setColourId(int _colour_id){
            this.colour_id = _colour_id;
        }

        public int getColour_id() {
            return this.colour_id;
        }
}

