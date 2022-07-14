import java.util.ArrayList;


interface Subject{
    void Attach(Observer observer);
    void Detach(Observer observer);
    void Notify(int fullness);
}
interface Observer{
    void Update(Sensor sensor);
}

//ConcreteSubject
class Sensor implements Subject {
    private int sensorId;
    boolean sensorState = false;
    private static int sensorIdCount = 1000;
    private final ArrayList<Observer> observers = new ArrayList<>();

    public Sensor() {
        this.sensorId = sensorIdCount;
        sensorIdCount++;
    }


    @Override
    public void Attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void Detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void Notify(int fullness) {
        if (fullness == 0){
            setSensorState(false);
            for (Observer o: observers){
                o.Update(this);
            }
        }
        else {
            setSensorState(true);
            for (Observer o: observers){
                o.Update(this);
            }
        }
    }

    public int getSensorId() {
        return sensorId;
    }

    public boolean getSensorState() {
        return sensorState;
    }

    public void setSensorState(boolean sensorState) {
        this.sensorState = sensorState;
    }

    public static int getSensorIdCount() {
        return sensorIdCount;
    }


}

