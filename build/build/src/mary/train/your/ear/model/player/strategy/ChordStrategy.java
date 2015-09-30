/*
 *
 */
package mary.train.your.ear.model.player.strategy;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import mary.train.your.ear.StringConstants;
import mary.train.your.ear.model.player.response.TaskResponse;
import mary.train.your.ear.model.player.response.TaskResponse.Type;
import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.util.PatternCreator;
import mary.train.your.ear.model.util.StructuresFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class ChordStrategy.
 */
public class ChordStrategy extends PlayStrategy {

	public ChordStrategy(){
		LOG = LogManager.getLogger(ChordStrategy.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mary.train.your.ear.model.player.PlayStrategy#play()
	 */
	@Override
	public TaskResponse play() {
		if (!validateData())
			return new TaskResponse(Type.ChordResponse);
		response = new TaskResponse(Type.ChordResponse);
		LinkedList<CustomChord> structures = StructuresFactory.createChords();
		prepareAnswer(structures);

		response.setReadyToPlay(PatternCreator.createPatternsForChords(structures));
		response.setChords(structures);
		doPlay();

		return response;
	}

	private boolean validateData() {
		if (ChordsSettings.getSettings().getChords().stream()
				.filter(p -> ChordsSettings.getSettings().getChordsAvailability().get(p.getName()) == true)
				.count() == 0)
			return false;
		if (!ChordsSettings.getSettings().getEnabledInvertions().containsValue(true))
			return false;
		return true;
	}

	protected void prepareAnswer(LinkedList<CustomChord> structures) {
		structures.forEach(struct -> {
			StringBuilder sb = new StringBuilder();
			sb.append(struct.getName());
			if (struct.hasInversions()) {
				sb.append(StringConstants.SPACE);
				sb.append(struct.getInversion().getText());
			}
			response.getAnswer().add(sb.toString());
		});

	}
}
