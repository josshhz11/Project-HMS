import.util.ArrayList;
import java.util.List

public class Billing{
    private int patientID;
    private List<BillingItem> billingItems;
    private double totalAmount;
  
    private static final int CONST_TAX = 9;

    //constructor
    public Billing(int patientID){
      this.patientID = patientID;
      this.billingItems = new ArrayList<>();
      this.totalAmount = 0.0;
    }

    //include 9% gst
    public double calculatetotalAmount(double totalAmount){
      return totalAmount * (1 + CONST_TAX/100);

    public void displayPrintingSummary(){//print final bill
      System.out.println("Invoice for ID: " + patientID);
      System.out.println("------------------------------");
      for (BillingItem item : billingItems){
        System.out.println(item);
      }
      System.out.println("Total (excluding GST): $" + totalAmount);
      System.out.println("Total (including GST): $" + calculatetotalAmount);
    }

    //has a; aggregation?
    public class BillingItem(){
      
    }

    public void addBillingItem(item, cost){
      BillingItem item = new BillingItem(item, cost);
      billingItems.add(item);
      totalAmount += cost;
    

}
