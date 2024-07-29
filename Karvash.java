import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Karvash {
    private ArrayList<Stage> stages;
    private ArrayList<Car> cars;
    private ArrayList<Worker> workers;
    private final Time time;
    private int last_car_id;

    public static final String PASS_TIME = "pass_time";
    public static final String CAR_ARRIVAL = "car_arrival";
    public static final String GET_STAGE_STATUS = "get_stage_status";
    public static final String GET_WORKER_STATUS = "get_worker_status";
    public static final String GET_CAR_STATUS = "get_car_status";
    public static final String QUIT = "done";

    // Constructor
    public Karvash() {
        this.time = new Time();
        this.stages = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.workers = new ArrayList<>();
        this.last_car_id = 0;
    }
    
    // Copy Constructor
    public Karvash(Karvash other) {
        this.stages = new ArrayList<>(other.stages.size());
        for (Stage stage : other.stages) {
            this.stages.add(new Stage(stage)); // Assuming Stage has a copy constructor
        }

        this.cars = new ArrayList<>(other.cars.size());
        for (Car car : other.cars) {
            this.cars.add(new Car(car)); // Assuming Car has a copy constructor
        }

        this.workers = new ArrayList<>(other.workers.size());
        for (Worker worker : other.workers) {
            this.workers.add(new Worker(worker)); // Assuming Worker has a copy constructor
        }

        this.time = new Time(other.time); // Assuming Time has a copy constructor
        this.last_car_id = other.last_car_id;
    }

    private Car find_car(int id) {
        Car founded_car = null;
        boolean is_found = false;
        Loop: for (Car car : this.cars) {
            if (car.get_id() == id) {
                founded_car = car;
                is_found = true;
                break Loop;
            }
        }

        if (!is_found) {
            System.err.println("Could not find car");
            System.err.println("car_id: " + id);
        }

        return founded_car;
    }

    private Worker find_worker(int id) {
        Worker founded_worker = null;
        boolean is_found = false;
        Loop: for (Worker worker : this.workers) {
            if (worker.get_id() == id) {
                founded_worker = worker;
                is_found = true;
                break Loop;
            }
        }

        if (!is_found) {
            System.err.println("Could not find worker");
            System.err.println("worker_id: " + id);
        }

        return founded_worker;
    }

    private Stage find_stage(int stage_id) {
        Stage founded_stage = null;
        boolean is_found = false;
        Loop: for (Stage stage : this.stages) {
            if (stage.get_id() == stage_id) {
                founded_stage = stage;
                is_found = true;
                break Loop;
            }
        }

        if (!is_found) {
            System.err.println("Could not find stage");
            System.err.println("stage_id: " + stage_id);
        }

        return founded_stage;
    }

    private void update_all_statuses() {
        for (Stage stage: stages) {
            // We definitely have a next stage (because the -1 was handled in class Stage / temp_done_cars) 
            ArrayList<Car> temp_done_cars = stage.update_status_stage();

            for (Car car: temp_done_cars) {
                int stage_id = car.get_next_stage();
                Stage temp_stage = this.find_stage(stage_id);
                temp_stage.add_car(car, stage.get_id(), false);
            }
           
        } 
    }

    private void get_car_status(String[] words) {
        // length check for Exception
        if (words.length != 2) {    
            System.err.println("Not Found, incorrect arguments car status...");  // ********
            return ;
        }

        try {
            int car_id = Integer.parseInt(words[1]);
            Car car_intended = this.find_car(car_id);
            if (car_intended != null)
                car_intended.print_car_status();
            else
                System.err.println("car_id not found");
        } catch (Exception e) {
            System.err.println("Could not convert second arg of car status to int");
        }
    }

    private void get_worker_status(String[] words) {
        // length check for Exception
        if (words.length != 2) {    
            System.err.println("Not Found, incorrect arguments stage status...");  // ********
            return ;
        }

        try { 
            int worker_id = Integer.parseInt(words[1]);
            Worker stage_intended = this.find_worker(worker_id);
            if (stage_intended != null)
                stage_intended.print_worker_status();
            else    
                System.err.println("worker id not found");
        } catch (Exception e) {
            System.err.println("Could not convert second arg of stage status to int");
        }
    }

    private void get_stage_status(String[] words) {
        // length check for Exception
        if (words.length != 2) {    
            System.err.println("Not Found, incorrect arguments stage statu...");  // ********
            return ;
        }

        try {
            int stage_id = Integer.parseInt(words[1]);
            Stage stage_intended = this.find_stage(stage_id);
            if (stage_intended != null)
                stage_intended.print_stage_status();
            else 
                System.err.println("stage id not found");
        } catch (Exception e) {
            System.err.println("Could not convert second arg of Stage status to int");
        }
    }

    private void car_arrival(String[] words) {
        // length check for Exception
        if (words.length < 2) {    
            System.err.println("Not Found, incorrect arguments car arrival...");  // ********
            return;
        }

        boolean flag_command = true;
        ArrayList<Integer> values = new ArrayList<>();
        for (String word : words) {
            if (flag_command) {
                flag_command = false;
                continue;
            }
            try {
                int value = Integer.parseInt(word);
                values.add(value);
            } catch (NumberFormatException e) {
                System.err.println("Error: Could not convert word '" + word + "' to integer in car arrival.");
                return;
            }
        }
        this.last_car_id++;
        Car car = new Car(this.last_car_id, values, this.time);
        this.cars.add(car);
        // ////////////////// add to the correct stage and how to handle the rest stages (talk! (done))
        int stage_id = car.get_next_stage();
        Stage stage_intended = this.find_stage(stage_id);
        stage_intended.add_car(car, -1, true);

        car.print_car_status();  
    }

    private void pass_time(String[] words) {
        // length check for Exception
        if (words.length != 2) {    
            System.err.println("Not Found, incorrect arguments pass time...");  // ********
            return ;
        }
        int time_to_pass = Integer.parseInt(words[1]);
        int until = this.time.getTime() + time_to_pass;
        while (this.time.getTime() < until) {

            update_all_statuses();

            this.time.increment();
        }
    }

    private void add_stage(String[] values) {
        // check Stage class constructor *********************
        int id = Integer.parseInt(values[0]);
        int price = Integer.parseInt(values[1]);
        Stage stage = new Stage(id, price, this.time);
        this.stages.add(stage);
    }

    private void add_worker(String[] values) {
        // check Worker class constructor *********************
        int id = Integer.parseInt(values[0]);
        int stage_id = Integer.parseInt(values[1]);
        int time_to_finish = Integer.parseInt(values[2]);
        Worker worker = new Worker(id, time_to_finish, stage_id, time);
        this.workers.add(worker);
        Stage stage = find_stage(stage_id);
        stage.add_worker(worker);
    }

    public void print_all_stages() {
        System.out.println();
        System.out.println("Stages Information:");
        for (Stage stage : stages) {
            stage.print_stage_debug();
        }
        System.out.println();
    }

    public void print_all_workers() {
        System.out.println();
        System.out.println("Workers Information:");
        for (Worker worker : workers) {
            worker.print_worker_debug();
        }
        System.out.println();
    }
    
    public void store_workers_from_csv(String file_path) {
        String line;
        String delimiter = ",";
        boolean flag_first = true;

        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            while ((line = br.readLine()) != null) {
                if (flag_first) {
                    flag_first = false;
                    continue;
                }
                String[] values = line.split(delimiter);
                add_worker(values);
            }
            for (Stage stage: this.stages) {
                stage.sort_workers();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldnt read csv file...");
            System.exit(0);
        }
    }

    public void store_stages_from_csv(String file_path) {
        String line;
        String delimiter = ",";
        boolean flag_first = true;

        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            while ((line = br.readLine()) != null) {
                if (flag_first) {
                    flag_first = false;
                    continue;
                }
                String[] values = line.split(delimiter);
                add_stage(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldnt read csv file...");
            System.exit(0);
        }
    }

    private void get_request_from_user() {
        Scanner scanner = new Scanner(System.in); 
        MAIN_LOOP: while (true) {
            String line = scanner.nextLine();
            String[] words = line.split("\\s+");  // split by whitespace
            String command = words[0].trim();
            
            // searching for the command to execcute
            switch (command) {  
                case PASS_TIME -> this.pass_time(words);
                case CAR_ARRIVAL -> this.car_arrival(words);
                case GET_STAGE_STATUS -> this.get_stage_status(words);
                case GET_WORKER_STATUS -> this.get_worker_status(words);
                case GET_CAR_STATUS -> this.get_car_status(words);
                case QUIT -> {
                    scanner.close();  
                    break MAIN_LOOP; // Assuming MAIN_LOOP is a labeled block  
                }
                default -> System.err.println("NOT FOUND, not a correct request... WHY!?");  
            }
        }
    }

    public void run() {
        System.out.println("Start of Karvash");
        System.out.println("----------------------");

        get_request_from_user();

        System.out.println("----------------------");
        System.out.println("End of Karvash");
        System.gc();
    }
}
