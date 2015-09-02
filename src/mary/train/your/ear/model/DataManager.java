package mary.train.your.ear.model;

import java.util.HashMap;
import java.util.LinkedList;

import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.structures.dao.CustomChordDao;

public class DataManager {

	public static boolean createChord(CustomChord newChord) {
		try {
			CustomChordDao.saveCustomChord(newChord);
			ChordsSettings.getSettings().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean removeChord(CustomChord chord) {
		try {
			CustomChordDao.removeCustomChord(chord);
			ChordsSettings.getSettings().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean saveChordHints(CustomChord chord) {
		try {
			CustomChordDao.updateChordHints(chord);
			ChordsSettings.getSettings().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static HashMap<Integer, LinkedList<CustomChord>> getChords() {
		try {
			return CustomChordDao.getDefaultChords();
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}

}
