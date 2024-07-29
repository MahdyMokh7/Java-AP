public class Worker {  
    public static final String IDLE = "Idle";
    public static final String IN_WORK = "In Work";

    private int id;  
    private String status;  
    private int time_to_finish;  
    private int stage_id;  
    private Time time;  
    private int end_time_prediction;
    private int in_work_car_id;

  
    public Worker(int id, int time_to_finish, int stage_id, Time time) {  
        this.id = id;  
        this.status = IDLE;  
        this.time_to_finish = time_to_finish;  
        this.stage_id = stage_id;  
        this.time = time;  
    }  

    // Copy Constructor
    public Worker(Worker other) {
        this.id = other.id;
        this.status = other.status;
        this.time_to_finish = other.time_to_finish;
        this.stage_id = other.stage_id;
        this.time = new Time(other.time); // Assuming Time has a copy constructor
        this.end_time_prediction = other.end_time_prediction;
        this.in_work_car_id = other.in_work_car_id;
    }

    public void print_worker_debug() {
        System.out.println("Worker ID: " + id);
        System.out.println("Status: " + status);
        System.out.println("Time to Finish: " + time_to_finish);
        System.out.println("Current Stage ID: " + stage_id);
        System.out.println("In Work Car ID: " + in_work_car_id);
        System.out.println("----------------------");
    }

    public int get_id() {
        return this.id;
    }

    public boolean is_worker_done(int intermediate_time) {
        if (end_time_prediction == intermediate_time)
            return true;
        return false;
    }

    public int get_time_to_finish(){
        return time_to_finish;
    }

    public int get_in_work_car_id(){
        return in_work_car_id;
    }

    public void assign_end_time_prediction(int intermediate_time){
        this.end_time_prediction = intermediate_time + time_to_finish;
    }

    public void print_worker_status() {  
        System.out.println(status);  
    }  

    public void update_worker_status(String new_status) {  
        this.status = new_status;   
    }  

    public void update_in_work_car_id(int new_id) {  
        this.in_work_car_id = new_id;   
    }

    public boolean is_worker_free() {  

        if(this.status.equals(IDLE))
            return true;
        return false;
    }
}
