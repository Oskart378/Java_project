public class Main {
    public static void main(String[] args) {

        LTS defaultLTS = new LTS();
        System.out.println("Default Constructor Test: ");
        System.out.println("ID: " + defaultLTS.getLtsId());
        System.out.println("Dry Mass: " + defaultLTS.getDryMass());
        System.out.println("Fuel Mass: " + defaultLTS.getFuelMass());
        System.out.println("Cargo Mass: " + defaultLTS.getCargoMass());


        // Testing increaseMissionTime
        defaultLTS.increaseMissionTime();
        System.out.println("Mission Time: " + defaultLTS.getMissionTime());
        System.out.println("Fuel after burn: " + defaultLTS.getFuelMass());


        // Test cargo development
        defaultLTS.deployCargo(); // might print error if < 200 seconds
    }

    public static void showLTSInfo() {

    }

}