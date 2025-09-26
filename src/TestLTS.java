public class TestLTS {
    public static void main(String[] args) {
        System.out.println("===== Testing default constructor =====");
        LTS lts1 = new LTS();
        System.out.println(lts1 + "\n");

        System.out.println("===== Testing 2 arguments constructor =====");
        LTS lts2 = new LTS(1000000, 5000);
        System.out.println(lts2 + "\n");

        System.out.println("===== Testing setters and getters =====");
        lts2.setManufacturer("New Manufacturer Inc.");
        lts2.setFuelMass(2500000);
        lts2.setCargoMass(10000);
        System.out.println(lts2 + "\n");


        /*If you don't set the fuel mass to a higher value after initializing the LTS always run lout of fuel
        before you can deploy, We assume that it will keep moving forward once it reaches the orbit*/
        System.out.println("===== Testing increase mission and fuel status without setting new values =====");
        LTS.FuelStatus fuelStatus = LTS.FuelStatus.HAS_FUEL;

        while (lts1.getMissionTime() < 240 && fuelStatus == LTS.FuelStatus.HAS_FUEL) {
            fuelStatus = lts1.increaseMissionTime();
        }

        System.out.println("Mission time: " + lts1.getMissionTime() + " seconds");
        System.out.println(fuelStatusMessage(fuelStatus));

        /*In this test case the LTS does not run out of fuel before deployment,
         that's because you set fuel mass to a higher value than the initial one*/
        System.out.println("\n===== Testing increase mission and fuel status");

        fuelStatus = LTS.FuelStatus.HAS_FUEL;
        while (lts2.getMissionTime() < 240 && fuelStatus == LTS.FuelStatus.HAS_FUEL) {

            fuelStatus = lts2.increaseMissionTime();
        }

        System.out.println("Mission time: " + lts2.getMissionTime() + " seconds");
        System.out.println(fuelStatusMessage(fuelStatus));

        System.out.println(/*lts2 + */"\n");

        System.out.println("===== Testing deploy cargo before 200 seconds =====");
        LTS lts3 = new LTS(1000000, 5000);
        String cargoStatusEarly = cargoStatusMessage(lts3.deployCargo());
        System.out.println("Attempted deploy at " + lts3.getMissionTime() + " seconds: " + cargoStatusEarly);
        System.out.println(lts3 + "\n");

        System.out.println("===== Testing deploy cargo after 200 seconds =====");
        while (lts3.getMissionTime() < 210)
            lts3.increaseMissionTime();

        String cargoStatusSuccess = cargoStatusMessage(lts3.deployCargo());
        System.out.println("Attempted deploy at " + lts3.getMissionTime() + " seconds: " + cargoStatusSuccess);
        System.out.println(lts3 + "\n");

        System.out.println("===== Testing deploy cargo again =====");
        String cargoStatusNoCargo = cargoStatusMessage(lts3.deployCargo());
        System.out.println("Attempted second deploy: " + cargoStatusNoCargo);
        System.out.println(lts3);

    }

    public static String cargoStatusMessage(LTS.CargoStatus cargoStatus) {

        return switch (cargoStatus) {
            case LTS.CargoStatus.TOO_EARLY -> "Deployed too early";
            case LTS.CargoStatus.SUCCESS -> "Successful deploy";
            case LTS.CargoStatus.NO_CARGO -> "There is no cargo to deploy";

        };
    }

    public static String fuelStatusMessage(LTS.FuelStatus fuelStatus) {
        return switch (fuelStatus) {
            case HAS_FUEL -> "LTS has fuel";
            case NO_FUEL -> "LTS is out of fuel";
        };
    }

}