/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.structures;

public enum InstrumentMapper {

	Piano(0,"Piano 1", "Fortepian"),
	Harpsichord(6, "Harpsichord", "Klawesyn"),
	Vibraphone(11, "Vibraphone", "Wibrafon"),
	Marimba(12, "Marimba", "Marimba"),
	Organ(16, "Organ 1", "Organy"),
	Violin(40, "Violin", "Skrzypce"),
	Viola(41, "Viola", "Altówka"),
	Cello(42, "Cello", "Wiolonczela"),
	Contrabass(43, "Contrabass", "Kontrabas"),
	Choir(52, "Choir Aahs", "Chór"),
	Trumpet(56, "Trumpet", "Tr¹bka"),
	Trombone(57, "Trombone", "Puzon"),
	Alto_Sax(65, "Alto Sax", "Saksofon altowy"),
	Oboe(68, "Oboe", "Obój"),
	Bassoon(70, "Bassoon", "Fagot"),
	Clarinet(71, "Clarinet", "Klarnet"),
	Flute(73, "Flute", "Flet");

	private int index;
	private String englishName;
	private String polishName;

	private InstrumentMapper(int index, String englishName, String polishName){
		this.index = index;
		this.englishName = englishName;
		this.polishName = polishName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public String getPolishName() {
		return polishName;
	}

	public int getIndex(){
		return index;
	}

	public InstrumentMapper getByIndex(int index){
		for (InstrumentMapper instrumentMapper : InstrumentMapper.values()) {
			if (instrumentMapper.index == index)
				return instrumentMapper;
		}
		return Piano;
	}

	public static String getEnglishNameByPolish(String polish){
		for (InstrumentMapper instrumentMapper : InstrumentMapper.values()) {
			if (instrumentMapper.polishName.equals(polish.trim()))
				return instrumentMapper.englishName;
		}
		return "Piano I";
	}

	public static String getPolishNameByEnglish(String english){
		for (InstrumentMapper instrumentMapper : InstrumentMapper.values()) {
			if (instrumentMapper.englishName.equals(english.trim()))
				return instrumentMapper.polishName;
		}
		return "Fortepian";
	}
}
