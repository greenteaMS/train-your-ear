/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.settings.TunerSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.FrequencyRatio;
import mary.train.your.ear.model.structures.dao.CustomChordDao;
import mary.train.your.ear.model.structures.dao.FrequencyRatioDao;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskDataManager.
 */
public class StructuresManager {

	/** The Constant LOG. */
	private static final Logger LOG = LogManager.getLogger(StructuresManager.class);

	/**
	 * Creates the chord.
	 *
	 * @param newChord the new chord
	 * @return true, if successful
	 */
	public static boolean createChord(CustomChord newChord) {
		LOG.info("Adding new chord: " + newChord + "...");
		try {
			CustomChordDao.saveCustomChord(newChord);
			ChordsSettings.getSettings().refresh();
			LOG.info("Adding new chord: " + newChord + " - SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Adding new chord:\n" + e);
			return false;
		}
		return true;
	}

	/**
	 * Removes the chord.
	 *
	 * @param chord the chord
	 * @return true, if successful
	 */
	public static boolean removeChord(CustomChord chord) {
		LOG.info("Removing chord: " + chord + "...");
		try {
			CustomChordDao.removeCustomChord(chord);
			ChordsSettings.getSettings().refresh();
			LOG.info("Removing chord: " + chord + " - SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Removing chord:\n" + e);
			return false;
		}
		return true;
	}

	/**
	 * Save chord hints.
	 *
	 * @param chord the chord
	 * @return true, if successful
	 */
	public static boolean saveChordHints(CustomChord chord) {
		LOG.info("Saving chord hints: " + chord + "...");
		try {

			CustomChordDao.updateChordHints(chord);
			ChordsSettings.getSettings().refresh();
			LOG.info("Save chord hints: " + chord + " - SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Save chord hints:\n" + e);
			return false;
		}
		return true;
	}

	/**
	 * Gets the chords.
	 *
	 * @return the chords
	 */
	public static HashMap<Integer, LinkedList<CustomChord>> getChords() {
		try {
			return CustomChordDao.getAllChords();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("getChords:",e);
			HashMap<Integer, LinkedList<CustomChord>> map = new HashMap<>();
			map.put(3, new LinkedList<>());
			map.put(4, new LinkedList<>());
			map.put(5, new LinkedList<>());
			return map;
		}
	}

	/**
	 * Gets the ratios.
	 *
	 * @return the ratios
	 */
	public static List<FrequencyRatio> getRatios() {
		try {
			return FrequencyRatioDao.loadRatios();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("getRatios:", e);
			return new LinkedList<>();
		}
	}

	/**
	 * Save new ratio.
	 *
	 * @param newRatio the new ratio
	 * @return true, if successful
	 */
	public static boolean saveNewRatio(FrequencyRatio newRatio) {
		LOG.info("Adding new ratio: " + newRatio + "...");
		try {
			List<FrequencyRatio> ratios = TunerSettings.getSettings().getAllRatios().stream()
					.filter(ratio -> ratio.isCustom() == true).collect(Collectors.toList());
			ratios.add(newRatio);
			FrequencyRatioDao.saveRatios(ratios);
			LOG.info("Adding new ratio: " + newRatio + " - SUCCESS");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Adding new ratio:\n",e);
			return false;
		}
	}

	public static boolean removeRatio(FrequencyRatio toDelete){
		LOG.info("Deleting ratio: " + toDelete + "...");
		try {
			List<FrequencyRatio> ratios = TunerSettings.getSettings().getAllRatios().stream()
					.filter(ratio -> ratio.isCustom() == true).collect(Collectors.toList());
			ratios.remove(toDelete);
			FrequencyRatioDao.saveRatios(ratios);
			LOG.info("Deleting ratio: " + toDelete + " - SUCCESS");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Deleting ratio:\n",e);
			return false;
		}
	}

}
