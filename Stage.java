import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class Stage {  
    private int id;
    private int price;
    private int income;  // درامد تا اون لحظه
    private Time time;
    //private int number_of_washed_cars;
    //private int number_of_cars_in_queue;
    //private int number_of_cars_being_washed;   //متدش کنیم! طول سه وکتور پایین را برمیکردانیم
    private ArrayList<Car> current_cars;
    private ArrayList<Car> queue_cars;
    private ArrayList<Car> done_cars;
    private ArrayList<Worker> workers;
    //private ArrayList<Worker> in_work_workers;


 
    public Stage(int id , int price, Time time) {   // Constructor
        
        this.id = id;
        this.price = price;  
        this.time = time; 
        this.income = 0;  
    }  

    public Stage(Stage other) {   // Copy Constructor
        this.id = other.id;
        this.price = other.price;
        this.income = other.income;
        this.time = other.time; // Assuming Time class has a proper copy constructor
        this.current_cars = new ArrayList<>(other.current_cars); // Shallow copy of the ArrayList
        this.queue_cars = new ArrayList<>(other.queue_cars); // Shallow copy of the ArrayList
        this.done_cars = new ArrayList<>(other.done_cars); // Shallow copy of the ArrayList
        this.workers = new ArrayList<>(other.workers); // Shallow copy of the ArrayList
    }
    

    public void add_worker(Worker worker) {  
        workers.add(worker);  
    }  

    public void add_car(Car car) { // for car arrival command
        for(Worker worker : workers){
            if(worker.is_worker_free()){
                assign_idle_worker_to_car_in_queue (time.getTime(), true);
                return ;
            }
        }
        queue_cars.add(car);
        // check if we have idle worker, then car add on current stage, else add on queue vector       
    } 

    public void sort_workers(){
        // Sorting the list by time attribute
        Collections.sort(workers, new Comparator<Worker>() {
            @Override
            public int compare(Worker o1, Worker o2) {
                return Long.compare(o1.get_time_to_finish(), o2.get_time_to_finish());
            }
        });
    }
    
    public void print_stage_status() {  
          
    } 

    public void update_status_worker() {  
        
    } 

    public int get_id() {  
        return id;  
    }  

    public int find_new_stage_id(Car car){
        ArrayList<Integer> stages = car.get_stage();
        for (int i = 0; i < stages.size(); i++) {  
            if (stages.get(i) == id) {   
                if (i + 1 < stages.size()) {  
                    return stages.get(i + 1); 
                }
                else {  
                    return -1;
                }  
            }  
        } 
        return 0; 
    }

    public void assign_idle_worker_to_car_in_queue (int intermediate_time, boolean flag_from_add_car) {
        int end_time = 0;
        for (Car car : queue_cars){ 
            for (Worker worker : workers){
                if(worker.is_worker_free()){
                    car.update_car_status(Car.IN_SERVICE); //in queue car array_list             
                    worker.update_worker_status(Worker.IN_WORK);
                    worker.update_in_work_car_id(car.get_id());
                    if (!flag_from_add_car) {
                        System.out.println(intermediate_time+ " car "+ car.get_id()+": Queue "+ id +" -> Stage " + id);
                    }
                    current_cars.add(car);
                    queue_cars.remove(car);  //syntax doroste? yani hamoon car ro pak mikone?
                    end_time = intermediate_time + worker.get_time_to_finish();
                    worker.assign_end_time_prediction(end_time);

                }
            }
        }
    }

    public ArrayList<Car> update_status_stage() {  
        //in pass time function we take current time and new_time;
        //in carvash class, when we call pass_time command:
        //current_time = time.get_id;
        //time.update_time(added_value_of_time);
        //new_time = time.get_id;
        //update_status_stage(current_time , new_time);   //اینطوری لازم نیست کلاس تایم زیر کلاس همه کلاسا باشه. چون باید تمام ایدی کلاس های تایم سنکرون باشند در حالیکه اگه هر کلاس یه کلاس تایم داشته باشه دیگه ایدی تایم سنکرون نیست 
        

        //1:برای حالت اول تخصیص کارگر
        //2:تغیر وضعیت برای همه
        int intermediate_time = time.getTime();
        int new_stage_id;
        //تخصیص کارگرT همان انتقال از صف به مرحله.
        //انتقال از مرحله به مرحله بعد
        ArrayList<Car> temp_done_cars = new ArrayList<Car>();
        assign_idle_worker_to_car_in_queue ( intermediate_time, false);
        for (Worker worker : workers){
            for (Car car : current_cars){ 
                if(!worker.is_worker_free()){
                    if(intermediate_time == worker.get_end_time_prediction()){    

                        if(car.get_id() == worker.get_in_work_car_id()){
                            car.update_car_status("done"); 
                            worker.update_worker_status(Worker.IDLE);

                            temp_done_cars.add(car);
                            new_stage_id = find_new_stage_id(car);
                            if(new_stage_id != -1){
                                System.out.println(intermediate_time +" car "+ car.get_id()+": Stage "+ id +" -> Stage " + new_stage_id);
                            }
                            if(new_stage_id == -1){
                                System.out.println(intermediate_time +" car "+ car.get_id()+": Stage "+ id +" -> Done ");
                            }
                            done_cars.add(car);
                            current_cars.remove(car);
                            car.update_max_id_index(); 
                            income = income + price;
                        }                        
                    }                
                }
            }
        }
        return temp_done_cars;
    } 
}