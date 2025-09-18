import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class LTS {

    private int itsId;
    private LocalDate dateManufactured;
    private double dryMass;
    private double fuelMass;
    private double cargoMass;
    private double grossMass;
    private String manufacturer;
    private int missionTime;

    public LTS(){
        this(3800000, 0);
    }

    public LTS(double fuelMass, double cargoMass) {
        Random idGenerator = new Random();

        this.fuelMass = fuelMass;
        this.cargoMass = cargoMass;
        this.dryMass = 620500;
        this.itsId = 100000 + idGenerator.nextInt(900000);
        this.missionTime = 0;
        this.cargoMass = 0;
        this.manufacturer = "Celestial Transport Technologies";
        this.dateManufactured = LocalDate.now();

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
        return grossMass;
    }

    public int getItsId() {
        return itsId;
    }

    public LocalDate getDateManufactured() {
        return dateManufactured;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setFuelMass(double fuelMass) {
        this.fuelMass = fuelMass;
    }

    public void setCargoMass(double cargoMass) {
        this.cargoMass = cargoMass;
    }

    public void increaseMissionTime() {

    }

    public void deployCargo() {

    }

}
