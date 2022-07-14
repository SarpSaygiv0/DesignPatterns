import java.util.ArrayList;

interface Command {
    void execute();
}

// Receiver for Command Client for Iterator
class TruckDriver {
    private City city;
    private AbstractIterator gps;

    private double medicalTrashStorage;
    private double generalTrashStorage;

    public TruckDriver(City city) {
        this.city = city;
        this.gps = city.createIterator();
        this.generalTrashStorage = 0;
        this.medicalTrashStorage = 0;
    }

    public TrashBin goToCorrespondingTrashBin(int SensorId) {
        for (gps.first(); !gps.isDone(); gps.next()) {
            int id = gps.currentItem().getSensor().getSensorId();
            if (id == SensorId) {
                return gps.currentItem();
            }
        }
        return null;
    }

    public void emptyTrashBin(int sensorId) {
        System.out.println("ID:" + sensorId + " is emptied");
        TrashBin trashBin = goToCorrespondingTrashBin(sensorId);

        if (trashBin.getName().equals("Medical Trash Bin")) medicalTrashStorage += trashBin.emptyTrash();
        else generalTrashStorage += trashBin.emptyTrash();

    }
    
    public void dumpTrashToLandfill(Landfill landfill) {
    	System.out.println("*************");
    	System.out.println("medical storage " + medicalTrashStorage);
    	System.out.println("general storage " +generalTrashStorage);
    	System.out.println("*************");
    	
    	if (landfill instanceof MedicalWasteLandfill) {
			landfill.dumpTrash(medicalTrashStorage);
			this.medicalTrashStorage = 0;
		}else if (landfill instanceof GeneralUseLandfill) {
			landfill.dumpTrash(generalTrashStorage);
			this.generalTrashStorage = 0;
		}
    	
    	System.out.println("*************");
    	System.out.println("medical storage " + medicalTrashStorage);
    	System.out.println("general storage " +generalTrashStorage);
    	System.out.println("*************");
    }
    
    public void print(){
        System.out.println("med storage: "+medicalTrashStorage);
        System.out.println("gen storage: "+generalTrashStorage);
    }

    public double getMedicalTrashStorage() {
        return medicalTrashStorage;
    }

    public void setMedicalTrashStorage(double medicalTrashStorage) {
        this.medicalTrashStorage = medicalTrashStorage;
    }

    public double getGeneralTrashStorage() {
        return generalTrashStorage;
    }

    public void setGeneralTrashStorage(double generalTrashStorage) {
        this.generalTrashStorage = generalTrashStorage;
    }
    
}

// Invoker
class ManInCharge {
    private final ArrayList<Command> collectionOrder = new ArrayList<Command>();

    public void giveOrder() {
        System.out.println(collectionOrder);
        for (Command command : collectionOrder) {
            command.execute();
        }
    }

    public void addCommandToCollectionOrder(Command c) {
        collectionOrder.add(c);
    }
}

// Concrete Command
class collectTrashBinsCommand implements Command {
    private TruckDriver truckDriver;
    private int sensorId;

    public collectTrashBinsCommand(TruckDriver truckDriver, int sensorId) {
        this.truckDriver = truckDriver;
        this.sensorId = sensorId;
    }

    public void execute() {
        truckDriver.emptyTrashBin(this.sensorId);
    }

}
