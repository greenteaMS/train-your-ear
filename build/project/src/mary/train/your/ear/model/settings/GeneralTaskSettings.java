/*
 *
 */
package mary.train.your.ear.model.settings;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.structures.InstrumentMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneralTaskSettings.
 */
@Root
public class GeneralTaskSettings {

	/** The Constant LOG. */
	private static final Logger LOG = LogManager.getLogger(GeneralTaskSettings.class);

	/**
	 * The Enum PlayType.
	 */
	public enum PlayType {

		/** The Melodically. */
		Melodically, /** The Harmonically. */
		Harmonically, MelUp, MelDown, MelRandom
	}

	/** The playtype. */
	@Element
	private PlayType playtype;

	@Element
	private PlayType melPlaytype;

	/** The nr of chords. */
	@Element
	private int nrOfChords;

	/** The lowest pitch range. */
	@ElementArray
	private int[] lowestPitchRange; // as midi pitch number; default C - c3

	/** The random instrument. */
	@Element
	private boolean randomInstrument = true;

	/** The pause. */
	@Element
	private int pause;

	/** The tempo. */
	@Element
	private int tempo;

	/** The indexes. */
	private static LinkedList<Integer> indexes = new LinkedList<>();

	/** The instrument index. */
	@Element
	private int instrumentIndex;

	/** The loaded instruments. */
	private static LinkedHashMap<Integer, Instrument> loadedInstruments;

	/** The random. */
	private static Random random = new Random();

	/** The instance. */
	private static GeneralTaskSettings instance;

	/**
	 * Gets the single instance of GeneralTaskSettings.
	 *
	 * @return single instance of GeneralTaskSettings
	 */
	public static GeneralTaskSettings getSettings() {
		if (instance == null)
			instance = new GeneralTaskSettings();
		return instance;
	}

	static {
		loadInstruments();
	}

	/**
	 * Sets the instance.
	 *
	 * @param i
	 *            the new instance
	 */
	public static void setInstance(GeneralTaskSettings i) {
		instance = i;
	}

	/**
	 * Instantiates a new general task settings.
	 */
	public GeneralTaskSettings() {
		playtype = PlayType.Melodically;
		melPlaytype = PlayType.MelUp;
		nrOfChords = 1;
		lowestPitchRange = new int[] { 48, 64 };
		instrumentIndex = 0;
		pause = 500;
		tempo = 120;
	}

	/**
	 * Sets the loaded instruments.
	 */
	private static void loadInstruments() {
		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			LOG.info("Synthesizer: " + synth.getDeviceInfo());
			loadedInstruments = new LinkedHashMap<Integer, Instrument>();
			Instrument[] list = synth.getAvailableInstruments();
			for (InstrumentMapper instr : InstrumentMapper.values()) {
				loadedInstruments.put(instr.getIndex(), list[instr.getIndex()]);
				indexes.add(instr.getIndex());
			}
			LOG.info("Instruments loaded: " + loadedInstruments);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			LOG.error(e);
		}
	}

	/**
	 * Gets the nr of chords.
	 *
	 * @return the nr of chords
	 */
	public int getNrOfChords() {
		return nrOfChords;
	}

	/**
	 * Sets the nr of chords.
	 *
	 * @param nrOfChords
	 *            the new nr of chords
	 */
	public void setNrOfChords(int nrOfChords) {
		this.nrOfChords = nrOfChords;
	}

	/**
	 * Gets the playtype.
	 *
	 * @return the playtype
	 */
	public PlayType getPlaytype() {
		return playtype;
	}

	/**
	 * Sets the playtype.
	 *
	 * @param playtype
	 *            the new playtype
	 */
	public void setPlaytype(PlayType playtype) {
		this.playtype = playtype;
	}

	/**
	 * Gets the instrument index.
	 *
	 * @return the instrument index
	 */
	public int getInstrumentIndex() {
		if (randomInstrument)
			return indexes.get(random.ints(0, indexes.size()).findAny().getAsInt());
		else
			return instrumentIndex;
	}

	/**
	 * Sets the instrument index.
	 *
	 * @param instrumentIndex
	 *            the new instrument index
	 */
	public void setInstrumentIndex(int instrumentIndex) {
		this.instrumentIndex = instrumentIndex;
	}

	/**
	 * Gets the loaded instruments.
	 *
	 * @return the loaded instruments
	 */
	public static HashMap<Integer, Instrument> getLoadedInstruments() {
		return loadedInstruments;
	}

	/**
	 * Gets the loaded instruments names.
	 *
	 * @return the loaded instruments names
	 */
	public static LinkedList<String> getLoadedInstrumentsNames() {
		LOG.debug("getLoadedInstrumentsNames " + loadedInstruments);
		LinkedList<String> list = new LinkedList<>();
		for (Instrument i : loadedInstruments.values())
			list.add(InstrumentMapper.getPolishNameByEnglish(i.getName()));
		return list;
	}

	/**
	 * Sets the instrument.
	 *
	 * @param instr
	 *            the new instrument
	 */
	public void setInstrument(String instr) {
		String eng = InstrumentMapper.getEnglishNameByPolish(instr);
		for (int i : loadedInstruments.keySet()) {
			if (loadedInstruments.get(i).getName().trim().equals(eng)) {
				setInstrumentIndex(i);
				return;
			}
		}
	}

	/**
	 * Sets the loaded instruments.
	 *
	 * @param loadedInstruments
	 *            the loaded instruments
	 */
	/*
	 * public void setLoadedInstruments(LinkedHashMap<Integer, Instrument>
	 * loadedInstruments) { this.loadedInstruments = loadedInstruments; }
	 */

	/**
	 * Gets the lowest pitch range.
	 *
	 * @return the lowest pitch range
	 */
	public int[] getLowestPitchRange() {
		return lowestPitchRange;
	}

	/**
	 * Sets the lowest pitch range.
	 *
	 * @param range
	 *            the new lowest pitch range
	 */
	public void setLowestPitchRange(int[] range) {
		this.lowestPitchRange = range;
	}

	/**
	 * Sets the random instrument.
	 *
	 * @param selected
	 *            the new random instrument
	 */
	public void setRandomInstrument(boolean selected) {
		randomInstrument = selected;
	}

	/**
	 * Checks if is random instrument.
	 *
	 * @return true, if is random instrument
	 */
	public boolean isRandomInstrument() {
		return randomInstrument;
	}

	/**
	 * Gets the pause length.
	 *
	 * @return the pause length
	 */
	public int getPauseLength() {
		return pause;
	}

	/**
	 * Sets the pause length.
	 *
	 * @param value
	 *            the new pause length
	 */
	public void setPauseLength(int value) {
		System.out.println(value);
		this.pause = value;
	}

	/**
	 * Sets the tempo.
	 *
	 * @param value
	 *            the new tempo
	 */
	public void setTempo(int value) {
		this.tempo = value;
	}

	/**
	 * Gets the tempo.
	 *
	 * @return the tempo
	 */
	public int getTempo() {
		return tempo;
	}

	/**
	 * The main method.
	 *
	 * @param arg
	 *            the arguments
	 * @throws MidiUnavailableException
	 *             the midi unavailable exception
	 */
	public static void main(String[] arg) throws MidiUnavailableException {
		Synthesizer synth = MidiSystem.getSynthesizer();
		System.out.println(synth.getDeviceInfo());
		Instrument[] list = synth.getAvailableInstruments();
		int[] index = new int[] { 0, 6, 11, 12, 16, 40, 41, 42, 43, 52, 56, 57, 65, 68, 70, 71, 73 };
		for (int i = 0; i< index.length; i++){
			System.out.println(index[i] + " " + list[index[i]].getName());
		}
	}

	public void setMelPlaytype(PlayType type) {
		melPlaytype = type;
	}

	public PlayType getMelPlayType() {
		return melPlaytype;
	}

}
