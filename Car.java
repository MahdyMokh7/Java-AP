import java.util.ArrayList;

public class Car { 
    public static final String DONE = "Done";
    public static final String IN_SERVICE = "In Service";
    public static final String IN_LINE = "In Line";

    private final int id; 
    private String status;  
    private ArrayList<Integer> stages;  
    private final Time time;  
    private int max_id_index;
 
    // Constructor
    public Car(int id , ArrayList<Integer> stage, Time time) {  

        this.id = id;
        this.stages = stage;  
        this.time = time;  
        this.max_id_index = 0;
    }  

    // Copy Constructor
    public Car(Car other) {
        this.id = other.id;
        this.status = other.status;
        this.stages = new ArrayList<>(other.stages); // Create a new ArrayList to copy stages
        this.time = new Time(other.time); // Assuming Time has a copy constructor
        this.max_id_index = other.max_id_index; // Copy the static variable
    }

    public int get_id() {  
        return id;  
    } 

    public ArrayList<Integer> get_stage(){
        return stages;
    }

    public void print_car_status() {  
        System.out.println(String.format("%s: %d", this.status, this.stages.get(max_id_index))); 
    }

    public void update_car_status(String new_status) {  
        this.status = new_status;

    }
    
    public int get_next_stage() {
        if(max_id_index >= stages.size()){
            return -1;
        }  
        return stages.get(max_id_index);
    }
    
    public void update_max_id_index(){
        this.max_id_index++;
    }
    
}