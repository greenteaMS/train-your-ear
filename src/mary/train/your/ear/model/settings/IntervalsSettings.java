package mary.train.your.ear.model.settings;

import java.util.LinkedHashMap;

import mary.train.your.ear.model.structures.Interval;

public class IntervalsSettings {

	private static IntervalsSettings settings;

	public static IntervalsSettings getSettings() {
		if (settings == null)
			settings = new IntervalsSettings();
		return settings;
	}

	private LinkedHashMap<Interval, Boolean> intervalsAvailability;
	private boolean simple;
	private boolean compound;

	private IntervalsSettings() {
		intervalsAvailability = new LinkedHashMap<>();
		for (Interval interval : Interval.values())
			intervalsAvailability.put(interval, true);

		setSimple(true);
		setCompound(false);
	}

	public LinkedHashMap<Interval, Boolean> getIntervalsAvailability() {
		return intervalsAvailability;
	}

	private boolean anyAvailable = false;
	public boolean checkAvailability() {
		anyAvailable = false;
		if (simple)
			intervalsAvailability.keySet().stream().filter(p -> p.getJfugueRep() < 13).forEach(i -> {
				if (intervalsAvailability.get(i))
					anyAvailable =  true;
			});
		if (compound)
			intervalsAvailability.keySet().stream().filter(p -> p.getJfugueRep() > 12).forEach(i -> {
				if (intervalsAvailability.get(i))
					anyAvailable =  true;
			});
		return anyAvailable;

	}

	public void setIntervalsAvailability(LinkedHashMap<Interval, Boolean> intervalsAvailability) {
		this.intervalsAvailability = intervalsAvailability;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

	public boolean isCompound() {
		return compound;
	}

	public void setCompound(boolean complex) {
		this.compound = complex;
	}

}
