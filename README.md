# DesignPatterns Project

**General Flow Of Our Waste Collection System Project**

In our scenario, we have one City and city has neighborhoods and streets. In the City, there are
two factories. One of them is medical factory and the other one is general factory. The job of
medical factory is creating medical trash bins and medical Landfill. The job of general Landfill is
creating general trash bins and general Landfill. There are two general landfill and one medical
landfills in our program. Also, general landfill cannot contain medical trash and medical landfill
cannot contain general trash. The city also has municipality and waste collection department.
The job of the municipality is creating sensors and attach those sensors into the trash bins.
After they create and attach those sensors, they register those sensors ids and their states into
the waste collection departments repository so that waste collection department can track the
states of the sensors. Another job of the waste collection department is creating the collection
order. Collection order is created after enough bins got full and notified the waste collection
department. In our scenario, after 3 trash bins got full, the collection order is created. After
that, man in charge gets the collection order and invoke the truck driver to empty those trash
bins. Truck driver starts executing the commands. However, he has to navigate through the city
somehow. Luckly, he has a special GPS for navigate through the city. He enters the sensor id in
his GPS and GPS finds the corresponding trash bins for the sensor id. After that, he goes to the
trash bin and emptied it. If the trash bin is medical trash bin, he uses his medical trash storage
to dump the trash. If the trash bin is the general one, he uses general trash storage for dumping
the trash. When the trash bins got emptied, the sensors also notified the waste collection
department to update their state back. After truck driver finishes his job, he/she dump the
trash in the corresponding landfill. 

*PATTERN AND CLASS EXPLANATIONS*

**Command Pattern**
1. Why we use command pattern
The reason why we use this pattern is that after it is determined that there is enough trash bins,
the responsible of the collection department must create a collection order and deliver it to the
truck driver, and what needs to be done is to decoupled the invoker which is ManInCharge and
the reciver which is TruckDriver from each other.
2. Mapping with original Command structure <br/>
a. Command (Command) <br/>
b. ConcreteCommand (CollectTrashBinsCommand) <br/>
c. Invoker(ManInCharge) <br/>
d. Reciver (TruckDriver) <br/>
e. Client (WasteCollectionDepartment)

**Singleton Pattern**
1. Why we use singleton pattern
The reason why we use this pattern is that each city has one waste collection department so we need
to only one Waste Collection Department Instance.
2. Mapping with original Command structure
Singleton (WasteCollectionDepartment)

**Observer Pattern**
1. Why we use observer pattern
The reason why we use this pattern is that we want to define a one-to-many dependency between
wasteCollectionDepartment and Sensors that when one sensor changes state whics is fill rate , all its
dependents are notified and updated automatically.
2. Mapping with original Observer structure <br/>
a. Observer (Observer) <br/>
b. Subject (Subject) <br/>
c. ConcreteSubject(Sensor) <br/>
d. ConcreteObserver (WasteCollectionDepartment) 

**Composite Pattern**
1. Why we use composite pattern
We use composite pattern since Neighborhood-Street elements represent a partwhole hierarchy due to the fact that neighborhoods consist of streets. We also set
our City as a city element because we wanted to use it as our root component. So
hierarchy goes as: City-> Neighborhood -> Street. Also, we chose the safe method
because the leaf node (Street) has its own trashBin methods that is not related with
our composite node.
2. Mapping with original Composite Structure <br/>
a. Component (CityElement) <br/>
b. Composite (Neighborhood, City) <br/>
c. Leaf (Street) <br/>
d. Client (City)


**Abstract Factory Pattern**
1. Why we use abstract factory pattern
We use abstract factory because each city has three landfill and one landfill is for medical waste the
other two are for general use.
2. Mapping with original Command structure <br/>
a. AbstractFactory (AbstractFactory) <br/>
b. ConcreteFactory1 (MedicalFactory) <br/>
c. ConcreteFactory2 (GeneralFactory) <br/>
d. AbstractProductA(TrashBin) <br/>
e. AbstractProductB(Landfill) <br/>
f. ConcreteProductA1(MedicalTrashBin) <br/>
g. ConcreteProductA2(GeneralTrashBin) <br/>
h. ConcreteProductB1(MedicalWasteLandfill) <br/>
i. ConcreteProductB2(GeneralUseLandFill) <br/>
j. Client(City)

**Iterator Pattern**
1. Why we use Iterator pattern
We use abstract factory because each city has three landfill and one landfill is for medical waste the
other two are for general use.
2. Mapping with original Iterator structure <br/>
a. AbstractIterator (AbstractIterator) <br/>
b. ConcreteIterator (GPS) <br/>
c. Aggregate (Aggregate) <br/>
d. ConcreteAggregate (City)
