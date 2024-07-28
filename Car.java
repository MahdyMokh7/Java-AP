import java.util.ArrayList;

public class Car {  
    private int id; 
    private String status;  
    private ArrayList<Integer> stage;  
    private Time time;  
    private static int max_id_index;
 
    public Car(int id , ArrayList<Integer> stage, Time time) {  

        this.id = id;
        this.status = status;  
        this.stage = stage;  
        this.time = time;  
        max_id_index = 0;
    }  

    public int get_id() {  
        return id;  
    } 

    public ArrayList<Integer> get_stage(){
        return stage;
    }

    public void print_car_status() {  
        System.out.println(status); 
        // for command 2 
    }

    public void update_car_status(String new_status) {  
        this.status = new_status;

    }
    
    public int get_next_stage() {
        if(max_id_index >= stage.size()){
            return -1;
        }  
        return stage.get(max_id_index);
    }
    
    public void update_max_id_index(){
        this.max_id_index++;
    }
    
}