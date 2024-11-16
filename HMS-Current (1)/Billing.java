import java.util.ArrayList;
import java.util.List;

public class Billing{
    private int patientID;
    private List<BillingItem> billingItems;
    private double totalAmount;
  
    //GST percentage
    private static final int CONST_TAX = 9;

    //consultation fee
    private static final int CONST_CONSUL = 10;

    //constructor
    public Billing(int patientID){
      this.patientID = patientID;
      this.billingItems = new ArrayList<>();
      this.totalAmount = 0.0;
    }

    //include 9% gst
    public double calculatetotalAmount(){
      return (totalAmount+CONST_CONSUL) * (1 + CONST_TAX/100.0);
    }

    //print final bill
    public void displayPrintingSummary(){//print final bill
      System.out.println("Invoice for Patient ID: " + patientID);
      System.out.println("------------------------------");
      for (BillingItem item : billingItems){
        System.out.println(item);
      }
      System.out.println("Consultation Fee: $" + CONST_CONSUL);
      System.out.println("Total (excluding GST): $" + (totalAmount + CONST_CONSUL));
      System.out.println("Total (including GST): $" + calculatetotalAmount());
    }

    //add new billing item
    public void addBillingItem(String description, double cost){
      BillingItem item = new BillingItem(description, cost);
      billingItems.add(item);
      totalAmount += cost;

    private class BillingItem{
    	private String description;
    	private double cost;
    	
    	public BillingItem(String description, double cost) {
    		this.description = description;
    		this.cost = cost;
    	}
        @Override
        public String toString() {
            return description + ": $" + cost;
        }

    }
    


    

}