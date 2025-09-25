import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;

public class LTS {

    //Constants and default values
    private static final double DEFAULT_DRY_MASS = 620500;
    private static final double DEFAULT_FUEL_MASS = 3800000;
    private static final double DEFAULT_CARGO_MASS = 0;
    private static final int MIN_MISSION_TIME = 200;
    private static final int MISSION_INCREMENT = 5;
    private static final double FUEL_CONSUMPTION_RATE = 0.01;

    //Static helper properties
    private static final Random idGenerator = new Random();
    private static final HashSet<Integer> usedIds = new HashSet<>();

    //Constant private fields
    private final int ltsId;
    private final LocalDate dateManufactured;
    private final double dryMass;
    private final double initialFuelMass;

    //Fields
    private double fuelMass;
    private double cargoMass;
    private double grossMass;
    private String manufacturer;
    private int missionTime;


    //Helper Enums that represent status for fuel and cargo
    public enum FuelStatus {HAS_FUEL, NO_FUEL}
    public enum CargoStatus { TOO_EARLY, NO_CARGO , SUCCESS }

    //Empty constructor
    public LTS(){
        this(DEFAULT_FUEL_MASS, DEFAULT_CARGO_MASS);
    }

    //Two parameters constructor for fuelMass and cargoMass
    public LTS(double fuelMass, double cargoMass) {

        this.initialFuelMass = (fuelMass >= 0) ? fuelMass : 0;
        setFuelMass(fuelMass);
        setCargoMass(cargoMass);
        this.dryMass = DEFAULT_DRY_MASS;
        this.ltsId = generateUniqueRandomId();
        this.missionTime = 0;
        updateGrossMass();
        this.manufacturer = "Celestial Transport Technologies";
        this.dateManufactured = LocalDate.now();

    }

    //Helper methods
    private void updateGrossMass() {
        this.grossMass = dryMass + fuelMass + cargoMass;
    }

    private int generateUniqueRandomId() {

        if (usedIds.size() >= 900000) {
            throw new RuntimeException("No more unique IDs available");
        }

        int id;

        // If the HashSet is more than 80% full, switch to sequential id search which is more efficient
        if (usedIds.size() > 800000) {
            for (int i = 100000; i <= 999999; i++) {
                if (!usedIds.contains(i)) {
                    usedIds.add(i);
                    return i;
                }
            }
        }

        //otherwise use random ID generation
        do {
            id = idGenerator.nextInt(900000) + 100000; // random 6 digits id
        } while (usedIds.contains(id));
        usedIds.add(id);

        return id;
    }

    private boolean isCargoDeployed() {
        return cargoMass <= 0;
    }

    private boolean hasFuel(double fuelConsumed){
        return fuelMass >= fuelConsumed;
    }

    //Properties getters
    public String getManufacturer() {
        return manufacturer;
    }

    public double getFuelMass() {
        return fuelMass;
    }

    public double getCargoMass() {
        return cargoMass;
    }

    public int getMissionTime() {
        return missionTime;
    }

    public double getDryMass() {
        return dryMass;
    }

    public double getGrossMass() {
        return dryMass + fuelMass + cargoMass;
    }

    public int getLtsId() {
        return ltsId;
    }

    public LocalDate getDateManufactured() {
        return dateManufactured;
    }


    //Properties setters
    public void setManufacturer(String manufacturer) {
        if(manufacturer != null && !manufacturer.isBlank()){
            this.manufacturer = manufacturer;
        }
        else
            System.out.println("Manufacturer can't be null or empty string");

    }

    public void setFuelMass(double fuelMass) {
        if (fuelMass < 0) {
            System.out.println("Fuel mass can't be negative, defaulting to 0");
            this.fuelMass = 0;
        }

        else
            this.fuelMass = fuelMass;
        updateGrossMass();
    }

    public void setCargoMass(double cargoMass) {

        if (cargoMass < 0) {
            System.out.println("Cargo mass can't be negative, defaulting to 0");
            this.cargoMass = 0;
        }

        else
            this.cargoMass = cargoMass;

        updateGrossMass();
    }

    //LTS main functions
    public FuelStatus increaseMissionTime() {

        double fuelConsumed = MISSION_INCREMENT * (initialFuelMass * FUEL_CONSUMPTION_RATE);
        missionTime += MISSION_INCREMENT;

        if (hasFuel(fuelConsumed)) {
            fuelMass -= fuelConsumed;

        } else {
            fuelMass = 0;
        }

        updateGrossMass();
        return (fuelMass > 0) ? FuelStatus.HAS_FUEL : FuelStatus.NO_FUEL;

    }

    public CargoStatus deployCargo() {

        if (missionTime < MIN_MISSION_TIME) {
            return CargoStatus.TOO_EARLY;
        }

        if (isCargoDeployed()) {
            return CargoStatus.NO_CARGO;
        }

        cargoMass = 0;
        updateGrossMass();
        //System.out.println("Cargo deployed successfully!");
        return CargoStatus.SUCCESS;
    }


    //Override string so you display the state of the object
    @Override
    public String toString() {
        return "LTS ID: " + ltsId + "\n" +
                "Date Manufactured: " + dateManufactured + "\n" +
                "Dry Mass: " + dryMass + "\n" +
                "Fuel Mass: " + fuelMass + "\n" +
                "Cargo Mass: " + cargoMass + "\n" +
                "Gross Mass: " + grossMass + "\n" +
                "Mission Time: " + missionTime + "\n" +
                "Manufacturer: " + manufacturer;
    }

}
