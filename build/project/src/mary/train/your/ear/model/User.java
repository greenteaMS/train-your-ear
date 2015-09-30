/*
 *
 */
package mary.train.your.ear.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.settings.GeneralTaskSettings;
import mary.train.your.ear.model.settings.IntervalComparatorSettings;
import mary.train.your.ear.model.settings.IntervalsSettings;
import mary.train.your.ear.model.settings.TunerSettings;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
@Root
public class User {

	/** The name. */
	@Element
	private String name;

	/** The general task settings. */
	@Element
	private GeneralTaskSettings generalTaskSettings;

	/** The chords settings. */
	@Element
	private ChordsSettings chordsSettings;

	/** The intervals settings. */
	@Element
	private IntervalsSettings intervalsSettings;

	/** The tuner settings. */
	@Element
	private TunerSettings tunerSettings;

	@Element
	private IntervalComparatorSettings intervalComparatorSettings;

	/**
	 * Instantiates a new user.
	 */
	public User() {
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param name the name
	 */
	public User(String name) {
		setName(name);
		setGeneralTaskSettings(new GeneralTaskSettings());
		setChordsSettings(new ChordsSettings());
		setIntervalsSettings(new IntervalsSettings());
		setTunerSettings(new TunerSettings());
		setIntervalComparatorSettings(new IntervalComparatorSettings());
	}

	/**
	 * Gets the intervals settings.
	 *
	 * @return the intervals settings
	 */
	public IntervalsSettings getIntervalsSettings() {
		return intervalsSettings;
	}

	/**
	 * Sets the intervals settings.
	 *
	 * @param intervalsSettings the new intervals settings
	 */
	public void setIntervalsSettings(IntervalsSettings intervalsSettings) {
		this.intervalsSettings = intervalsSettings;
	}

	/**
	 * Gets the tuner settings.
	 *
	 * @return the tuner settings
	 */
	public TunerSettings getTunerSettings() {
		return tunerSettings;
	}

	/**
	 * Sets the tuner settings.
	 *
	 * @param tunerSettings the new tuner settings
	 */
	public void setTunerSettings(TunerSettings tunerSettings) {
		this.tunerSettings = tunerSettings;
	}

	/**
	 * Gets the chords settings.
	 *
	 * @return the chords settings
	 */
	public ChordsSettings getChordsSettings() {
		return chordsSettings;
	}

	/**
	 * Sets the chords settings.
	 *
	 * @param chordsSettings the new chords settings
	 */
	public void setChordsSettings(ChordsSettings chordsSettings) {
		this.chordsSettings = chordsSettings;
	}

	/**
	 * Gets the general task settings.
	 *
	 * @return the general task settings
	 */
	public GeneralTaskSettings getGeneralTaskSettings() {
		return generalTaskSettings;
	}

	/**
	 * Sets the general task settings.
	 *
	 * @param generalTaskSettings the new general task settings
	 */
	public void setGeneralTaskSettings(GeneralTaskSettings generalTaskSettings) {
		this.generalTaskSettings = generalTaskSettings;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString(){
		return name;
	}

	public IntervalComparatorSettings getIntervalComparatorSettings() {
		return intervalComparatorSettings;
	}

	public void setIntervalComparatorSettings(IntervalComparatorSettings intervalComparatorSettings) {
		this.intervalComparatorSettings = intervalComparatorSettings;
	}

}
