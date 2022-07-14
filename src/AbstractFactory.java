
interface AbstractFactory {
    TrashBin createTrashBin();
    Landfill createLandfill();
}

class MedicalFactory implements AbstractFactory {
    @Override
    public TrashBin createTrashBin() {
        return new MedicalTrashBin();
    }

    @Override
    public Landfill createLandfill() {
        return new MedicalWasteLandfill();
    }

}

class GeneralFactory implements AbstractFactory{
    @Override
    public TrashBin createTrashBin() {
        return new GeneralTrashBin();
    }

    @Override
    public Landfill createLandfill() {
        return new GeneralUseLandfill();
    }
}


abstract class TrashBin {
    private final double maxStorage = 100;
    private double trash;
    private Sensor sensor;
    protected boolean notified = false;

    public TrashBin() {
        this.trash=0;
    }

    public Sensor getSensor(){
        return sensor;
    }

    public void addTrash(double value){

        if (this.trash + value > this.maxStorage) {
        	System.out.println(this.trash+value-100 + " you can not add your trash to this bin");
            this.trash = this.maxStorage;
        }
        else {
        	this.trash += value;
        }

        double fillRate = (this.trash/maxStorage) * 100;
        //System.out.println(fillRate);

        if (!notified) {
            if (fillRate >= 80){
                sensor.Notify(1);
                notified = true;
            }
		}


    }
    public double emptyTrash(){
        double dummyTrash = this.trash;
        this.trash = 0;
        sensor.Notify(0);
        return dummyTrash;
    }

    public void setSensor(Sensor sensor){
        this.sensor = sensor;
    }

    abstract String getName();
}

class MedicalTrashBin extends TrashBin{

    public String getName() {
        return "Medical Trash Bin";
    }
}

class GeneralTrashBin extends TrashBin {

    public String getName(){
        return "General Trash Bin";
    }
}

abstract class Landfill{
    public final double maxStorage = 10000;
    protected double trash;

    public Landfill(){
        this.trash = 0;
    }
    
    public void dumpTrash(double value) {
    	trash += value;
    	print();
    }
    
	public void print() {}

}

class MedicalWasteLandfill extends Landfill{
	public void print() {
		System.out.println("Medical Landfill: " + trash);
	}

}

class GeneralUseLandfill extends Landfill{
	public void print() {
		System.out.println("General Landfill: " + trash);
	}
}
