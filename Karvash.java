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

    public static final String PASS_TIME = "pass_time";
    public static final String CAR_ARRIVAL = "car_arrival";
    public static final String GET_STAGE_STATUS = "get_stage_status";
    public static final String GET_WORKER_STATUS = "get_worker_status";
    public static final String GET_CAR_STATUS = "get_car_status";


    private Car find_car() {
        Car car_found;

        return car_found;
    }

    private Worker find_worker() {
        Worker worker_found;

        return worker_found;
    }

    private Stage find_stage(int stage_id) {
        Stage founded_stage;
        boolean is_found = false;
        Loop: for (Stage stage : this.stages) {
            if (stage.id == stage_id) {
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
        
    }
    
    private void add_car() {
        
    }

    private void get_car_status(String[] words) {

    }

    private void get_worker_status(String[] words) {

    }

    private void get_stage_status(String[] words) {

    }

    private void car_arrival(String[] words) {
        // length check

        Vector<Integer> values = new Vector<Integer>();
        for (String word : words) {
            try {
                int value = Integer.parseInt(word);
                values.add(value);
            } catch (NumberFormatException e) {
                System.err.println("Warning: Could not convert word '" + word + "' to integer.");
            }
        }
        Car car = Car(values, this.time);
        this.cars.add(car);
    }

    private void pass_time(String[] words) {
        if (words.length != 2) {

        }
    }

    private void add_stage(String[] values) {
        // check Stage class constructor *********************
        int id = Integer.parseInt(values[0]);
        int price = Integer.parseInt(values[1]);
        Stage stage = new Stage(id=id, price=price, time=time);
        this.stages.add(stage);
    }

    private void add_worker(String[] values) {
        // check Worker class constructor *********************
        int id = Integer.parseInt(values[0]);
        int stage_id = Integer.parseInt(values[1]);
        int time_to_finish = Integer.parseInt(values[2]);
        Worker worker = new Worker(id=id, stage_id=stage_id, time_to_finish=time_to_finish, time=time);
        this.workers.add(worker);
        stage = find_stage(stage_id);
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
        while (true) {
            String line = scanner.nextLine();
            String[] words = line.split("\\s+");  // split by whitespace
            String command = words[0];
            
            // searching for the command to execcute
            if (command == PASS_TIME) {
                pass_time(words);
            }
            else if (command == CAR_ARRIVAL) {

            }
            else if (command == GET_STAGE_STATUS) {

            }
            else if (command == GET_WORKER_STATUS) {

            }
            else if (command == GET_CAR_STATUS) {

            }
            else {
                System.err.println("NOT FOUND, not a correct request...");  // correct it*********** 
            }
        }
    }

    public void run() {
        System.out.println("Start of Karvash");

        get_request_from_user();

        System.gc();
    }
}
