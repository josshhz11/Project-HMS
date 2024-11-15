public class Medication {
    private String medicationID;
    private String name;
    private int quantity;
    private int replenishmentRequest;

    public Medication(String medicationID, String name, int quantity) {
        this.medicationID = medicationID;
        this.name = name;
        this.quantity = quantity;
        this.replenishmentRequest = 0;
    }

    public String getName() {
        return this.name;
    }

    public String getMedicationID() {
        return this.medicationID;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getReplenishmentRequest() {
        return this.replenishmentRequest;
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public void updateReplenishmentRequest(int newQuantity) {
        this.replenishmentRequest = newQuantity;
    }

}
