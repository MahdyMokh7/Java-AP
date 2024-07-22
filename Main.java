public class Main{
    public static void main(String [] args) {
        Karvash karvash = new Karvash();

        String stages_csv_file = args[0];
        String workers_csv_file = args[1];

        karvash.store_stages_from_csv(stages_csv_file);
        karvash.store_workers_from_csv(workers_csv_file);

        karvash.run();
    }
}