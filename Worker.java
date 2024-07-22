public class Worker {  
    private int id;  
    private String status;  
    private int time_to_finish;  
    private int stage_id;  
    private Time time;  

  
    public Worker(int id, int time_to_finish, int stage_id, Time time) {  
        this.id = id;  
        this.status = "Idle";  
        this.time_to_finish = time_to_finish;  
        this.stage_id = stage_id;  
        this.time = time;  
    }  

    
    public void print_worker_status() {  
        System.out.println(status);  
    }  

    public void update_status_worker(String newStatus) {  
        this.status = newStatus;   
    }  

    public boolean is_worker_free() {  

        if(this.status=="idle"){

            return true;
        }
        else{

            return false;
        }
    }  

   
    

    public static void main(String[] args) {  
        Worker.Time time = new Worker.Time(1);  
        Worker worker1 = new Worker(1, 8, 1, time);  
    
    }  
}