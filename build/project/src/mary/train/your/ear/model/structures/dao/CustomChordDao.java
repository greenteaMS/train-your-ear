/*
 *
 */
package mary.train.your.ear.model.structures.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import mary.train.your.ear.model.structures.CustomChord;

// TODO: Auto-generated Javadoc
/**
 * CustomChordDao class is responsible for managing chords - loading default
 * chords, adding, removing, updating custom chords.
 */
public class CustomChordDao {

	/** The Constant LOG. */
	private static final Logger LOG = LogManager.getLogger(CustomChordDao.class);

	/** The Constant CHORDS_FILENAME. */
	private static final String CHORDS_FILENAME = "./files/chords.xml";

	/** The Constant CUSTOM_CHORDS_FILENAME. */
	private static final String CUSTOM_CHORDS_FILENAME = "./files/customChords.xml";

	/** The def chords. */
	private static HashMap<Integer, LinkedList<CustomChord>> defChords;

	/** The users chords. */
	private static HashMap<Integer, LinkedList<CustomChord>> usersChords;

	/**
	 * Gets the default chords.
	 *
	 * @return the default chords
	 * @throws Exception
	 *             the exception
	 */
	public static HashMap<Integer, LinkedList<CustomChord>> getAllChords() throws Exception {
		defChords = loadChords(CHORDS_FILENAME);
		usersChords = loadChords(CUSTOM_CHORDS_FILENAME);
		return mergeLists(defChords, usersChords);
	}

	/**
	 * Merge lists.
	 *
	 * @param defChords
	 *            the def chords
	 * @param usersChords
	 *            the users chords
	 * @return the hash map
	 */
	private static HashMap<Integer, LinkedList<CustomChord>> mergeLists(
			HashMap<Integer, LinkedList<CustomChord>> defChords,
			HashMap<Integer, LinkedList<CustomChord>> usersChords) {
		HashMap<Integer, LinkedList<CustomChord>> map = new HashMap<>();
		defChords.forEach((key, list) -> {
			map.put(key, list);
		});
		usersChords.forEach((key, list) -> {
			list.forEach(c -> c.setCustom(true));
			if (map.containsKey(key))
				map.get(key).addAll(list);
			else
				map.put(key, list);
		});
		return map;
	}

	/**
	 * Load chords.
	 *
	 * @param filename
	 *            the filename
	 * @return the hash map
	 * @throws Exception
	 *             the exception
	 */
	private static HashMap<Integer, LinkedList<CustomChord>> loadChords(String filename) throws Exception {

		Serializer reader = new Persister();
		InputStream source;
		ChordsList example;
		try {
			source = new FileInputStream(filename);
			example = reader.read(ChordsList.class, source);
			source.close();
		} catch (FileNotFoundException e) {
			LOG.error("loadChords for file " + filename + " failed\n", e);
			example = new ChordsList();
		}

		HashMap<Integer, LinkedList<CustomChord>> chords = new HashMap<>();
		chords.put(3, example.triads);
		chords.put(4, example.tetrads);
		chords.put(5, example.pentads);
		chords.put(6, example.hexads);

		return chords;
	}

	/**
	 * Save chords.
	 *
	 * @param filename
	 *            the filename
	 * @param chords
	 *            the chords
	 * @throws Exception
	 *             the exception
	 *//*
	@SuppressWarnings("unused")
	private static void saveChords(String filename, HashMap<Integer, LinkedList<CustomChord>> chords) throws Exception {
		ChordsList list = new ChordsList();
		chords.forEach((number, chordsList) -> {
			chordsList.forEach(chord -> list.addChord(chord));
		});
		Serializer serializer = new Persister();
		OutputStream file = new FileOutputStream(filename);

		serializer.write(list, file);
		file.close();
	}*/

	/**
	 * Save custom chord.
	 *
	 * @param cc
	 *            the cc
	 * @throws Exception
	 *             the exception
	 */
	public static void saveCustomChord(CustomChord cc) throws Exception {
		if (usersChords.containsKey(cc.getStructure().size() + 1))
			usersChords.get(cc.getStructure().size() + 1).add(cc);
		else {
			LinkedList<CustomChord> list = new LinkedList<CustomChord>();
			list.add(cc);
			usersChords.put(cc.getStructure().size() + 1, list);
		}
		serializeChords(usersChords, CUSTOM_CHORDS_FILENAME);
	}

	/**
	 * Creates the custom chord.
	 *
	 * @param name
	 *            the name
	 * @param intervals
	 *            the intervals
	 * @param hints
	 *            the hints
	 * @param hasInversions
	 *            the has inversions
	 * @return the custom chord
	 *//*
	public static CustomChord createCustomChord(String name, Intervals intervals, String hints, boolean hasInversions) {
		CustomChord ch = new CustomChord();
		ch.setName(name);
		LinkedList<Interval> ints = new LinkedList<>();
		for (int i = 1; i < intervals.toHalfstepArray().length; i++)
			ints.add(Interval.find(intervals.toHalfstepArray()[i]));
		ch.setStructure(ints);
		ch.setHints(hints);
		ch.setHasInversions(hasInversions);
		return ch;
	}*/

	/**
	 * Removes the custom chord.
	 *
	 * @param chord
	 *            the chord
	 * @throws Exception
	 *             the exception
	 */
	public static void removeCustomChord(CustomChord chord) throws Exception {
		if (usersChords.containsKey(chord.getStructure().size() + 1)) {
			usersChords.get(chord.getStructure().size() + 1).remove(chord);
			serializeChords(usersChords, CUSTOM_CHORDS_FILENAME);
		}

	}

	/**
	 * Update chord hints.
	 *
	 * @param chord
	 *            the chord
	 * @throws Exception
	 *             the exception
	 */
	public static void updateChordHints(CustomChord chord) throws Exception {
		int size = chord.getStructure().size() + 1;
		if (chord.isCustom()) {
			usersChords.get(size).forEach(l -> {
				if (l.getName().equals(chord.getName())) {
					l.setHints(chord.getHints());
				}
			});
			serializeChords(usersChords, CUSTOM_CHORDS_FILENAME);
		} else {
			defChords.get(size).forEach(l -> {
				if (l.getName().equals(chord.getName())) {
					l.setHints(chord.getHints());
				}
			});
			serializeChords(defChords, CHORDS_FILENAME);
		}
	}

	/**
	 * Serialize chords.
	 *
	 * @param map
	 *            the map
	 * @param fileName
	 *            the file name
	 * @throws Exception
	 *             the exception
	 */
	private static void serializeChords(HashMap<Integer, LinkedList<CustomChord>> map, String fileName)
			throws Exception {
		ChordsList list = new ChordsList();
		map.forEach((number, chordsList) -> {
			chordsList.forEach(c -> list.addChord(c));
		});
		Serializer serializer = new Persister();

		OutputStream file = new FileOutputStream(fileName);
		serializer.write(list, file);
		file.close();
	}

}

@Root
class ChordsList {

	@ElementList
	LinkedList<CustomChord> triads = new LinkedList<>();
	@ElementList
	LinkedList<CustomChord> tetrads = new LinkedList<>();
	@ElementList
	LinkedList<CustomChord> pentads = new LinkedList<>();
	@ElementList
	LinkedList<CustomChord> hexads = new LinkedList<>();

	void addChord(CustomChord chord) {
		switch (chord.getStructure().size() + 1) {
		case 3:
			triads.add(chord);
			break;
		case 4:
			tetrads.add(chord);
			break;
		case 5:
			pentads.add(chord);
			break;
		case 6:
			hexads.add(chord);
			break;
		}
	}

}
