package eu.popowicz.splitpaymentcalculator;

public class FromBruttoToNetto {
	private double brutto;
	private double netto;
	private double vat;
	
	FromBruttoToNetto(String brutto, int vat) throws NumberFormatException{
		this.brutto = Double.valueOf(brutto);
		this.vat = vat;
		countNetto();
		countVat();
	}
	
	private void countNetto() {
		
		this.netto = brutto/((vat/100)+1);
	}
	
	private void countVat() {
		
		this.vat = brutto-netto;
	}
	
	public String getResults() {
		return "Kwota brutto: " + String.format("%1$,.2f", Math.abs(brutto)) + " zł." +
		"\nKwota netto: " + String.format("%1$,.2f", Math.abs(netto)) + " zł." +
		"\nKwota vat: " + String.format("%1$,.2f", Math.abs(vat)) + " zł.";
	}

}
