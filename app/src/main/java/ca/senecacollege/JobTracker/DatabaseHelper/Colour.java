package ca.senecacollege.JobTracker.DatabaseHelper;

/*
 * This is colour class used by MyDBHandler helper class for generating getter and setter. it takes following
 * paratmeters: colour_id, hexcode and name. this class is used by the notification class to set colour of
 * the notification
 *
 */

public class Colour{

        private int colour_id;
        private String hexcode;
        private String name;

        public Colour(){}

        public Colour(String _hexcode, String _name){
            this.hexcode = _hexcode;
            this.name = _name;
        }

        public void setColourId(int _colour_id){
            this.colour_id = _colour_id;
        }

        public int getColourId() {
            return this.colour_id;
        }

        public void setColourName (String _name){
            this.name = _name;
        }

        public String getName() {
            return this.name;
        }

        public void setHexcode(String _hexcode){
            this.hexcode = _hexcode;
        }

        public String getHexcode() {
            return  this.hexcode;
        }
}
