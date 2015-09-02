package mary.train.your.ear.model.strategies;

import java.util.LinkedList;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import org.jfugue.player.Player;

import mary.train.your.ear.model.TaskResponse;
import mary.train.your.ear.model.TaskResponse.Type;
import mary.train.your.ear.model.settings.ChordsSettings;
import mary.train.your.ear.model.settings.GeneralSettings;
import mary.train.your.ear.model.structures.CustomChord;
import mary.train.your.ear.model.util.PatternCreator;
import mary.train.your.ear.model.util.StructuresFactory;

public class ChordStrategy implements PlayStrategy {

	private TaskResponse response;

	@Override
	public TaskResponse play() {
		if (ChordsSettings.getSettings().getChords().stream()
				.filter(p -> ChordsSettings.getSettings().getChordsAvailability().get(p.getName()) == true)
				.count() == 0)
			return null;
		response = new TaskResponse(Type.ChordResponse);
		LinkedList<CustomChord> structures = StructuresFactory.createChords();
		structures.forEach(struct -> {
			StringBuilder sb = new StringBuilder();
			sb.append(struct.getName());
			if (struct.hasInversions()){
				sb.append(" ");
				sb.append(struct.getInversion().getText());
			}
			response.getAnswer().add(sb.toString());
		});
		response.setReadyToPlay(PatternCreator.createPatternsForChords(structures));

		response.getReadyToPlay().forEach(pattern -> {
			Player player = new Player();
			player.play(pattern);
			System.out.println("*** pattern " + pattern);
			try {
				Thread.sleep(GeneralSettings.getInstance().getPauseLength());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return response;
	}

	@Override
	public void repeat() {
		if (response == null)
			return;
		response.getReadyToPlay().forEach(pattern -> {
			Player player = new Player();
			player.play(pattern);
			System.out.println(pattern);
		});
	}

	@Override
	public String show() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		response = null;
	}

	public static void main(String[] args) throws MidiUnavailableException{
		System.out.println(MidiSystem.getSequencer());
		for (Info string : MidiSystem.getMidiDeviceInfo()) {
			System.out.println(string + " " + MidiSystem.getMidiDevice(string).getClass());
		}
		MidiDevice device = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[2]);
		System.out.println(device.getReceiver());
		TaskResponse response = new TaskResponse(Type.ChordResponse);
		LinkedList<CustomChord> structures = StructuresFactory.createChords();
		structures.forEach(struct -> {
			StringBuilder sb = new StringBuilder();
			sb.append(struct.getName());
			if (struct.hasInversions()){
				sb.append(" ");
				sb.append(struct.getInversion().getText());
			}
			response.getAnswer().add(sb.toString());
		});
		response.setReadyToPlay(PatternCreator.createPatternsForChords(structures));

		response.getReadyToPlay().forEach(pattern -> {
			Player player = new Player();
			player.getSequence(pattern);
			player.play(pattern);
			System.out.println(pattern);
			try {
				Thread.sleep(GeneralSettings.getInstance().getPauseLength());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
