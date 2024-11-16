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
