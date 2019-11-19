package ca.senecacollege.JobTracker.DatabaseHelper;

/*
 * This is category class used by MyDBHandler helper class for generating getter and setter. it takes following
 * paratmeters: category_id, and name. this class is used by the user job class to set link table
 *
 */

public class Category{

    private int category_id;
    private String name;

    public Category(){}

    public Category(String _name){
        this.name = _name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
