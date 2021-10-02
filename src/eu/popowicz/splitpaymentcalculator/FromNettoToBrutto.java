package eu.popowicz.splitpaymentcalculator;

public class FromNettoToBrutto {
	private double brutto;
	private double netto;
	private double vat;
	
	
	FromNettoToBrutto(String netto, int vat) throws NumberFormatException{
		
		this.netto = Double.valueOf(netto);
		this.vat = vat;
		countNetto();
		countVat();
		
	}
private void countNetto() {
		
		this.brutto = netto*((vat/100)+1);
	}
	
	private void countVat() {
		
		this.vat = brutto-netto;
	}
	
	public String getResults() {
		return "Kwota netto: " + String.format("%1$,.2f", Math.abs(netto)) + " zł." +
				"\nKwota brutto: " + String.format("%1$,.2f", Math.abs(brutto)) + " zł." +
				"\nKwota vat: " + String.format("%1$,.2f", Math.abs(vat)) + " zł.";
	}
}
