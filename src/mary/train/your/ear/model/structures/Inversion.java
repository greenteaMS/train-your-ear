package mary.train.your.ear.model.structures;

public enum Inversion {

	None("None", 0), First("I", 1), Second("II", 2), Third("III", 3), Fourth("IV", 4);

	private String text;
	private int index;

	private Inversion(String text, int index) {
		this.text = text;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public String getText() {
		return text;
	}

	public static Inversion getByIndex(int i){
		for (Inversion inv : values())
			if (inv.getIndex() == i)
				return inv;
		return None;
	}


}
