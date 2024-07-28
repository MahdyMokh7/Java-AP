import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import javax.print.DocFlavor.STRING;


public class Karvash {
    private ArrayList<Stage> stages;
    private ArrayList<Car> cars;
    private ArrayList<Worker> workers;
    private Time time;
    private int last_car_id = 0;

    public static final String PASS_TIME = "pass_time";
    public static final String CAR_ARRIVAL = "car_arrival";
    public static final String GET_STAGE_STATUS = "get_stage_status";
    public static final String GET_WORKER_STATUS = "get_worker_status";
    public static final String GET_CAR_STATUS = "get_car_status";
    public static final String QUIT = "done";


    private Car find_car(int id) {
        Car founded_car;
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
        Worker founded_worker;
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
        Stage founded_stage;
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
            // روی ماشین های done حتما پیامیش کن و       // give_car_next_stage() را روی ان صدا کن      -1درصورت تمام شدن ارایه استیج ها
            ArrayList<car> temp_done_cars = stage.update_status_stage();

            for (Car car: temp_done_cars) {
                int stage_id = car.get_next_stage();
                Stage temp_stage = this.find_stage(stage_id);
                temp_stage.add_car(temp_stage)
            }
        } 
    }

    private void get_car_status(String[] words) {
        // length check for Exception
        if (words.length != 2) {    
            System.err.println("Not Found, incorrect arguments car status...");  // ********
            return ;
        }

        int car_id; 
        try {
            car_id = Integer.parseInt(words[1]);
        } catch (Exception e) {
            System.err.println("Could not convert second arg of car status to int");
        }

        Stage car_intended = this.find_stage(car_id);
        
        car_intended.print_car_status();
    }

    private void get_worker_status(String[] words) {
        // length check for Exception
        if (words.length != 2) {    
            System.err.println("Not Found, incorrect arguments stage status...");  // ********
            return ;
        }

        int stage_id; 
        try {
            stage_id = Integer.parseInt(words[1]);
        } catch (Exception e) {
            System.err.println("Could not convert second arg of stage status to int");
        }

        Stage stage_intended = this.find_stage(stage_id);
        
        stage_intended.print_stage_status();
    }

    private void get_stage_status(String[] words) {
        // length check for Exception
        if (words.length != 2) {    
            System.err.println("Not Found, incorrect arguments stage statu...");  // ********
            return ;
        }

        int stage_id; 
        try {
            stage_id = Integer.parseInt(words[1]);
        } catch (Exception e) {
            System.err.println("Could not convert second arg of Stage status to int");
        }

        Stage stage_intended = this.find_stage(stage_id);
        
        stage_intended.print_stage_status();
    }

    private void car_arrival(String[] words) {
        // length check for Exception
        if (words.length < 2) {    
            System.err.println("Not Found, incorrect arguments car arrival...");  // ********
            return;
        }

        boolean flag_command = true;
        Vector<Integer> values = new Vector<Integer>();
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
        // ////////////////// add to the correct stage and how to handle the rest stages (talk!)

        // give_car_next_stage()

        car.print_status();  
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
        Stage stage = new Stage(id=id, price=price, time=this.time);
        this.stages.add(stage);
    }

    private void add_worker(String[] values) {
        // check Worker class constructor *********************
        int id = Integer.parseInt(values[0]);
        int stage_id = Integer.parseInt(values[1]);
        int time_to_finish = Integer.parseInt(values[2]);
        Worker worker = new Worker(id=id, stage_id=stage_id, time_to_finish=time_to_finish, time=time);
        this.workers.add(worker);
        Stage stage = find_stage(stage_id);
        stage.add_worker(worker);
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
            String command = words[0];
            
            // searching for the command to execcute
            if (command == PASS_TIME) {
                this.pass_time(words);
            }
            else if (command == CAR_ARRIVAL) {
                this.car_arrival(words);
            }
            else if (command == GET_STAGE_STATUS) {
                this.get_stage_status(words);
            }
            else if (command == GET_WORKER_STATUS) {
                this.get_worker_status(words);
            }
            else if (command == GET_CAR_STATUS) {
                this.get_car_status(words);
            }
            else if (command == QUIT) {
                scanner.close();
                break MAIN_LOOP;
            }
            else {
                System.err.println("NOT FOUND, not a correct request...");  // correct it*********** 
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
