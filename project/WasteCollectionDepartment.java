import java.util.ArrayList;
import java.util.HashMap;

public class WasteCollectionDepartment implements Observer {
    private static WasteCollectionDepartment uniqueInstance = null;
    private final HashMap<Integer, Boolean> sensorIdStates = new HashMap<Integer, Boolean>();
    private final ArrayList<Integer> collectionOrder = new ArrayList<>();
    private ManInCharge manInCharge;

    //Singleton
    public static WasteCollectionDepartment getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new WasteCollectionDepartment();
        }
        return uniqueInstance;
    }

    private WasteCollectionDepartment() {}


    public void addToHashMap(int sensorId, boolean state) {
        sensorIdStates.put(sensorId, state);
    }

    private TruckDriver truckDriver;
    private int orderCount;


    public void Update(Sensor sensor) {
        if (!sensor.sensorState) { // TrashBin got empty
            for (int sensorId : sensorIdStates.keySet()) {
                if (sensorId == sensor.getSensorId()) {
                    sensorIdStates.put(sensorId, sensor.sensorState);
                }
            }
        } else { // TrashBin got full
            for (int sensorId : sensorIdStates.keySet()) {
                if (sensorId == sensor.getSensorId()) {
                    sensorIdStates.put(sensorId, sensor.sensorState);
                    System.out.println("get notified!!!!");
                    collectionOrder.add(sensorId);
                    orderCount++;
                }
            }
        }

        if (orderCount == 3) {
            orderCount = 0;
            createCollectionOrder();
            manInCharge.giveOrder();
        }
    }

    public void createCollectionOrder() {
        for (Integer sensorID : collectionOrder) {
            manInCharge.addCommandToCollectionOrder(new collectTrashBinsCommand(truckDriver, sensorID));
        }
        System.out.println("Collection Order Created");
    }

    public void setManInCharge(ManInCharge manInCharge) {
        this.manInCharge = manInCharge;
    }

    public void setTruckDriver(TruckDriver truckDriver) {
        this.truckDriver = truckDriver;
    }

    void print() {
        for (int x : sensorIdStates.keySet()) {
            System.out.println(sensorIdStates.get(x)+" :"+x);
        }
    }

}
class Municipality {
    private ArrayList<TrashBin> trashBins;
    private ArrayList<Sensor> sensors;
    private WasteCollectionDepartment wasteCollectionDepartment = WasteCollectionDepartment.getInstance();

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Municipality(ArrayList<TrashBin> trashBins) {
        this.trashBins = trashBins;
        sensors = new ArrayList<>();
    }

    public void createSensors() {
        // Created the sensors and added their states Waste Collection Dep.'s database (Hashmap). Database has information about sensor ids and their states.
        for (int i = 0; i < trashBins.size(); i++) {
            sensors.add(new Sensor());
            wasteCollectionDepartment.addToHashMap(sensors.get(i).getSensorId(), sensors.get(i).sensorState);
        }
        // The municipality installs sensors on each trash bin
        for (int i = 0; i < trashBins.size();i++){
            trashBins.get(i).setSensor(sensors.get(i));
        }
        // Attach sensors to Waste Collection Dep.
        for (Sensor sensor : sensors) {
            sensor.Attach(wasteCollectionDepartment);
        }
    }

    public void print(){
        System.out.println(trashBins);
    }

}

