public class Time {
    private int time;

    // Constructor with default value of 0
    public Time() {
        this.time = 0;
    }

    // Copy Constructor
    public Time(Time other) {
        this.time = other.time;
    }

    // Getter for the time attribute
    public int getTime() {
        return time;
    }

    // Method to increment the time
    public void increment() {
        time++;
    }
}