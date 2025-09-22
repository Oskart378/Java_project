import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;

public class LTS {

    private static final double DEFAULT_DRY_MASS = 620500;
    private static final double DEFAULT_FUEL_MASS = 3800000;
    private static final double DEFAULT_CARGO_MASS = 0;
    private static final int MIN_MISSION_TIME = 200;
    private static final int MISSION_INCREMENT = 5;
    private static final double FUEL_CONSUMPTION_RATE = 0.01;

    private final int ltsId;
    private final LocalDate dateManufactured;
    private final double dryMass;

    private double fuelMass;
    private double cargoMass;
    //private double grossMass;
    private String manufacturer;
    private int missionTime;

    private final double initialFuelMass;

    private static final HashSet<Integer> usedIds = new HashSet<>();

    public LTS(){
        this(DEFAULT_FUEL_MASS, DEFAULT_CARGO_MASS);
    }

    public LTS(double fuelMass, double cargoMass) {

        this.initialFuelMass = (fuelMass >= 0) ? fuelMass : DEFAULT_FUEL_MASS;
        setFuelMass(fuelMass);
        setCargoMass(cargoMass);
        this.dryMass = DEFAULT_DRY_MASS;
        this.ltsId = generateUniqueRandomId();
        this.missionTime = 0;
        //this.grossMass = this.dryMass + this.fuelMass + this.cargoMass;
        this.manufacturer = "Celestial Transport Technologies";
        this.dateManufactured = LocalDate.now();

    }

    /*private void updateGrossMass() {
        this.grossMass = dryMass + fuelMass + cargoMass;
    }*/

    private int generateUniqueRandomId() {

        if (usedIds.size() >= 900000) {
            throw new RuntimeException("No more unique IDs available");
        }

        Random idGenerator = new Random();
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
            id = idGenerator.nextInt(900000) + 100000; // random 6-digit
        } while (usedIds.contains(id));
        usedIds.add(id);

        return id;
    }

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

    public void setManufacturer(String manufacturer) {
        if(manufacturer != null && !manufacturer.isBlank())
            this.manufacturer = manufacturer;
    }

    public void setFuelMass(double fuelMass) {
        if (fuelMass < 0) {
            System.out.println("Fuel mass can't be negative, defaulting to " + DEFAULT_FUEL_MASS);
            this.fuelMass = DEFAULT_FUEL_MASS;
        }

        else
            this.fuelMass = fuelMass;
        //updateGrossMass();
    }

    public void setCargoMass(double cargoMass) {

        if (cargoMass < 0) {
            System.out.println("Cargo mass can't be negative, defaulting to " + DEFAULT_CARGO_MASS);
            this.cargoMass = DEFAULT_CARGO_MASS;
        }

        else
            this.cargoMass = cargoMass;
        //updateGrossMass();
    }

    public void increaseMissionTime() {

        double fuelConsumed = MISSION_INCREMENT * initialFuelMass * FUEL_CONSUMPTION_RATE;
        missionTime += MISSION_INCREMENT;

        if (fuelMass >= fuelConsumed) {
            fuelMass -= fuelConsumed;
        } else {
            fuelMass = 0;
            System.out.println("Warning: you ran out of fuel!");
        }

        //updateGrossMass();
    }

    public boolean deployCargo() {

        if (missionTime < MIN_MISSION_TIME) {
            System.out.println("Cannot deploy yet, only after 200 seconds");
            return false;
        }

        if (cargoMass <= 0) {
            System.out.println("No cargo to deploy");
            return false;
        }

        cargoMass = 0;
        //updateGrossMass();

        System.out.println("Cargo deployed successfully!");
        return true;
    }

}
