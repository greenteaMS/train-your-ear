/*
 * @author Maria Sotor
 * @date September 2015
 * @version 1.2
 */
package mary.train.your.ear.model.structures.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import mary.train.your.ear.model.structures.FrequencyRatio;

// TODO: Auto-generated Javadoc
/**
 * FrequencyRatioDao is responsible for loading and saving FrequencyRatio
 * objects.
 */
public class FrequencyRatioDao {

	private static Logger LOG = LogManager.getLogger(FrequencyRatioDao.class);

	/** The Constant RATIOS_FILENAME. */
	private static final String RATIOS_FILENAME = "./files/ratios.xml";

	/**
	 * Load ratios.
	 *
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public static List<FrequencyRatio> loadRatios() throws Exception {
		Serializer reader = new Persister();
		InputStream source;
		RatioList loadedRatios;
		try{
			source = new FileInputStream(RATIOS_FILENAME);
			loadedRatios = reader.read(RatioList.class, source);
			loadedRatios.ratios.forEach(ratio -> ratio.setCustom(true));
			source.close();
		} catch (FileNotFoundException e){
			LOG.error("loadRatios", e);
			saveRatios(new LinkedList<>());
			loadedRatios = new RatioList();
		}
		return loadedRatios.ratios;
	}

	/**
	 * Save ratios.
	 *
	 * @param ratios
	 *            the ratios
	 * @throws Exception
	 *             the exception
	 */
	public static void saveRatios(List<FrequencyRatio> ratios) throws Exception {
		serializeRatios(ratios, RATIOS_FILENAME);
	}

	/**
	 * Serialize ratios.
	 *
	 * @param ratios
	 *            the ratios
	 * @param fileName
	 *            the file name
	 * @throws Exception
	 *             the exception
	 */
	private static void serializeRatios(List<FrequencyRatio> ratios, String fileName) throws Exception {
		RatioList list = new RatioList();
		list.ratios = ratios;
		Serializer serializer = new Persister();
		OutputStream file = new FileOutputStream(fileName);
		serializer.write(list, file);
		file.close();
	}

}

@Root
class RatioList {

	@ElementList
	List<FrequencyRatio> ratios = new LinkedList<>();

	void addFrequencyRatio(FrequencyRatio ratio) {
		ratios.add(ratio);
	}

}
