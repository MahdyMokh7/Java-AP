import java.util.Vector;  

public class Car {  
    private int id;  
    private String status;  
    private Vector<Integer> stage;  
    private Time time;  
    private static int max_id;
 
    public Car(Vector<Integer> stage, Time time) {  
     
        this.status = status;  
        this.stage = stage;  
        this.time = time;  
    }  

     


    public static void main(String[] args) {  
        Vector<Integer> stages_Id = new Vector<>();  
        
        Car.Time time = new Car.Time(1); 

        Car car = new Car(1, stages_Id, time); 

        
    }  
}