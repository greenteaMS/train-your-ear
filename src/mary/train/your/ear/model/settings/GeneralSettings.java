package mary.train.your.ear.model.settings;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class GeneralSettings {

	public enum PlayType {
		Melodically, Harmonically
	}

	public enum TaskType {
		Intervals, Chords, Microtones, Scales
	}

	private PlayType playtype;
	private int space; // space between pitches in ms - melodical mode; between
						// chords - harmonical mode
	private int nrOfChords;
	private int instrumentIndex;
	private LinkedHashMap<Integer, Instrument> loadedInstruments;
	private int[] lowestPitchRange; // as midi pitch number; default C - c3
	private TaskType taskType;
	private boolean randomInstrument = true;

	LinkedList<Integer> indexes = new LinkedList<>();

	private Random random = new Random();
	private long pause;
	private int tempo;

	private static GeneralSettings instance;

	public static GeneralSettings getInstance() {
		if (instance == null)
			instance = new GeneralSettings();
		return instance;
	}

	private GeneralSettings() {
		playtype = PlayType.Melodically;
		nrOfChords = 1;
		space = 500;
		lowestPitchRange = new int[] { 48, 64 };
		instrumentIndex = 68;
		pause = 500;
		tempo = 120;
		setLoadedInstruments();
	}

	private void setLoadedInstruments() {
		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			System.out.println(synth.getDeviceInfo());
			loadedInstruments = new LinkedHashMap<Integer, Instrument>();
			Instrument[] list = synth.getAvailableInstruments();
			int[] index = new int[] { 0, 6, 11, 12, 16, 40, 41, 42, 43, 52, 56, 57, 65, 68, 70, 71, 73 };
			for (int i = 0; i < index.length; i++) {
				loadedInstruments.put(index[i], list[index[i]]);
				indexes.add(index[i]);
			}
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNrOfChords() {
		return nrOfChords;
	}

	public void setNrOfChords(int nrOfChords) {
		this.nrOfChords = nrOfChords;
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public PlayType getPlaytype() {
		return playtype;
	}

	public void setPlaytype(PlayType playtype) {
		this.playtype = playtype;
	}

	public int getInstrumentIndex() {
		if (randomInstrument)
			return indexes.get(random.ints(0, indexes.size()).findAny().getAsInt());
		else
			return instrumentIndex;
	}

	public void setInstrumentIndex(int instrumentIndex) {
		this.instrumentIndex = instrumentIndex;
	}

	public HashMap<Integer, Instrument> getLoadedInstruments() {
		return loadedInstruments;
	}

	public LinkedList<String> getLoadedInstrumentsNames() {
		LinkedList<String> list = new LinkedList<>();
		for (Instrument i : loadedInstruments.values())
			list.add(i.getName());
		return list;
	}

	public void setInstrument(String instr) {
		for (int i : loadedInstruments.keySet()) {
			if (loadedInstruments.get(i).getName().equals(instr)) {
				setInstrumentIndex(i);
				return;
			}
		}
	}

	public void setLoadedInstruments(LinkedHashMap<Integer, Instrument> loadedInstruments) {
		this.loadedInstruments = loadedInstruments;
	}

	public int[] getLowestPitchRange() {
		return lowestPitchRange;
	}

	public void setLowestPitchRange(int[] range) {
		this.lowestPitchRange = range;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public void setRandomInstrument(boolean selected) {
		System.out.println("randomInstrument " + selected);
		randomInstrument = selected;
	}

	public long getPauseLength() {
		return pause;
	}

	public void setPauseLength(long value){
		this.pause = value;
	}

	public void setTempo(int value) {
		this.tempo = value;
	}

	public int getTempo(){
		return tempo;
	}

	public static void main(String[] arg) throws MidiUnavailableException {
		Synthesizer synth = MidiSystem.getSynthesizer();
		System.out.println(synth.getDeviceInfo());
		Instrument[] list = synth.getAvailableInstruments();
		for (Instrument instrument : list) {
			System.out.println(instrument);
		}
	}

}
