import java.util.ArrayList;
import java.util.Random;


//Component (Safe Design)
interface CityElement {
    void display(int indent);

    ArrayList<CityElement> getElements();
}

//Composite
class Neighborhood implements CityElement {
    //Fields
    private final String name;
    private final ArrayList<CityElement> streets = new ArrayList<>();

    //Constructor
    public Neighborhood(String name) {
        this.name = name;
    }

    //Methods
    public void add(CityElement c) {
        streets.add(c);
    }

    public void remove(CityElement c) {
        streets.remove(c);
    }

    public String getName() {
        return name;
    }

    @Override
    public ArrayList<CityElement> getElements() {
        return streets;
    }

    @Override
    public void display(int indent) {
        for (int i = 0; i <= indent; i++) {
            System.out.print("-");
        }
        System.out.println("+ " + getName());
        for (int i = 0; i < streets.size(); i++) {
            streets.get(i).display(indent + 1);
        }
    }

}

//Leaf
class Street implements CityElement {
    //Fields
    ArrayList<TrashBin> trashBins = new ArrayList<>();
    private final String name;

    //Constructor
    public Street(String name) {
        this.name = name;
    }


    //Methods
    public void addTrashBin(AbstractFactory factory) {
        trashBins.add(factory.createTrashBin());
    }

    public void removeTrashBin(TrashBin t) {
        trashBins.remove(t);
    }

    public String getName() {
        return name;
    }


    public ArrayList<TrashBin> getTrashBins() {
        return trashBins;
    }

    @Override
    public ArrayList<CityElement> getElements() {
        return null;
    }

    @Override
    public void display(int indent) {
        for (int i = 0; i <= indent; i++) {
            System.out.print("-");
        }
        System.out.println("- " + getName() + " has " + trashBins.size() + (trashBins.size() > 1 ? " Trash Bins" : " Trash Bin"));
        for (TrashBin trashBin : trashBins) {
            System.out.println("------ " + trashBin.getName());
        }
    }
}

class City implements CityElement, Aggregate {
    //Fields
    private final AbstractFactory medicalFactory;
    private final AbstractFactory generalFactory;
    private final ArrayList<TrashBin> trashBins = new ArrayList<>();
    private final ArrayList<CityElement> cityElements = new ArrayList<>();
    private final String name;

    //Constructor
    public City(String name, AbstractFactory medFactory, AbstractFactory genFactory) {
        this.name = name;
        this.medicalFactory = medFactory;
        this.generalFactory = genFactory;
    }

    //Methods
    public void createCity() {
        Neighborhood neighborhood1 = new Neighborhood("balcova");
        Neighborhood neighborhood2 = new Neighborhood("yali");

        Street street1 = new Street("101 street");
        addTrashBins(street1);
        Street street2 = new Street("102 street");
        addTrashBins(street2);
        Street street3 = new Street("103 street");
        addTrashBins(street3);
        Street street4 = new Street("104 street");
        addTrashBins(street4);

        neighborhood1.add(street1);
        neighborhood1.add(street2);

        neighborhood2.add(street3);
        neighborhood2.add(street4);

        this.add(neighborhood1);
        this.add(neighborhood2);

        initializeTrashBins(this);

    }

    public void addTrashBins(Street c) {
        Random rand = new Random();
        int random_trashBin = rand.nextInt(5) + 1;
        for (int i = 0; i < random_trashBin; i++) {
            int choice = rand.nextInt(2);
            // Using the polymorphism of Abstract Factory
            AbstractFactory factory = (choice == 0) ? medicalFactory : generalFactory;
            c.addTrashBin(factory);
        }
    }

    public void initializeTrashBins(CityElement g) {
        ArrayList<TrashBin> dummyTrashBins;

        for (CityElement x : g.getElements()) {
            if (x instanceof Neighborhood) {
                initializeTrashBins(x);
            } else {
                Street y = (Street) x;
                dummyTrashBins = (ArrayList<TrashBin>) y.getTrashBins().clone();
                this.trashBins.addAll(dummyTrashBins);
                dummyTrashBins.clear();
            }
        }
    }

    public String getName() {
        return name;
    }

    //This method different than getTrashBin method in the leaf. You can not add or remove trash bin in the city.
    //trashbins array in the city class contains all the trash bins in the city. However, in leaf, trashbins array contains only tras bins in the streets.
    public ArrayList<TrashBin> getTrashBins() {
        return trashBins;
    }

    //Composite Pattern Methods
    public void add(CityElement c) {
        cityElements.add(c);
    }

    public void remove(CityElement c) {
        cityElements.remove(c);
    }


    @Override
    public ArrayList<CityElement> getElements() {
        return cityElements;
    }

    @Override
    public void display(int indent) {

        System.out.println(getName());

        for (int i = 0; i < cityElements.size(); i++) {
            cityElements.get(i).display(indent + 1);
        }
    }

    //Iterator Pattern methods. City is the Aggregate.
    @Override
    public AbstractIterator createIterator() {
        return new Gps(this);
    }

    @Override
    public TrashBin get(int index) {
        return trashBins.get(index);
    }

    @Override
    public int getCount() {
        return trashBins.size();
    }

}

class Test {
    public static void main(String[] args) {
        AbstractFactory medicalFactory = new MedicalFactory();
        AbstractFactory generalFactory = new GeneralFactory();

        Landfill medicaLandfill = medicalFactory.createLandfill();
        Landfill generalandfill1 = generalFactory.createLandfill();
        Landfill generalandfill2 = generalFactory.createLandfill();


        WasteCollectionDepartment wasteCollectionDepartment = WasteCollectionDepartment.getInstance();
        City city = new City("Izmir", medicalFactory, generalFactory);

        TruckDriver truckDriver = new TruckDriver(city);
        ManInCharge manInCharge = new ManInCharge();
        wasteCollectionDepartment.setManInCharge(manInCharge);
        wasteCollectionDepartment.setTruckDriver(truckDriver);
        city.createCity();

        Municipality municipality = new Municipality(city.getTrashBins());
        municipality.createSensors();

        //city.display(1);
        //municipality.print();

        city.getTrashBins().get(0).addTrash(80);
        city.getTrashBins().get(0).addTrash(80);
        city.getTrashBins().get(0).addTrash(80);

        city.getTrashBins().get(1).addTrash(80);
        city.getTrashBins().get(1).addTrash(80);
        city.getTrashBins().get(1).addTrash(80);

        city.getTrashBins().get(2).addTrash(40);
        city.getTrashBins().get(2).addTrash(40);
        city.getTrashBins().get(2).addTrash(20);

        wasteCollectionDepartment.print();


        truckDriver.dumpTrashToLandfill(generalandfill1);
        truckDriver.dumpTrashToLandfill(medicaLandfill);


        //wasteCollectionDepartment.print();
        //truckDriver.print();

    }
}

