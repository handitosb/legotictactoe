package game;

//Field ist ein einzelnes Feld eines Spielbretts
public class Field {
	
	private int fieldValue; // 0 = Feld ist leer; 1 = Feld ist "X"; 2 Feld ist "O"

	//Konstruktor: Jedes neue Feld ist am Anfang leer, daher 0
	public Field() {
		setFieldValue(0);
	}
	
	//Methode gibt die Variable value des Feldes zurueck
	public int getFieldValue() {
		return fieldValue;
	}

	//Methode setzt uebergebenen int-Wert in Feld ein
	public void setFieldValue(int newfieldValue) {
		this.fieldValue = newfieldValue;
	}
	
	//liefert den Wahrheitswert zurueck, ob ein Feld frei ist
	public boolean isFree() {
		return (this.getFieldValue() == 0);
	}
	
	
}
