package mary.train.your.ear.model.util;

import java.util.HashMap;

public class AbcMapper {

	public final static HashMap<Integer, String> NOTES_MAPPER;
	public final static String QUARTER = "L:1/4";
	public final static String EIGHT = "L:1/8";
	public final static String OPEN_BRACKET = "[";
	public final static String CLOSE_BRACKET = "]";
	public final static String BARLINE = " | ";
	public final static String DOUBLE_LINE = " |]";
	public final static String BLACKSLASH ="\\";
	public static final String WHOLE_NOTE = "L:1/1";


	static {
		NOTES_MAPPER = new HashMap<>();
		NOTES_MAPPER.put(0, "C,,,,,");
		NOTES_MAPPER.put(1, "^C,,,,,");
		NOTES_MAPPER.put(2, "D,,,,,");
		NOTES_MAPPER.put(3, "_E,,,,,");
		NOTES_MAPPER.put(4, "E,,,,,");
		NOTES_MAPPER.put(5, "F,,,,,");
		NOTES_MAPPER.put(6, "^F,,,,,");
		NOTES_MAPPER.put(7, "G,,,,,");
		NOTES_MAPPER.put(8, "_A,,,,,");
		NOTES_MAPPER.put(9, "A,,,,,");
		NOTES_MAPPER.put(10, "_B,,,,,");
		NOTES_MAPPER.put(11, "B,,,,,");

		NOTES_MAPPER.put(12, "C,,,,");
		NOTES_MAPPER.put(13, "^C,,,,");
		NOTES_MAPPER.put(14, "D,,,,");
		NOTES_MAPPER.put(15, "_E,,,,");
		NOTES_MAPPER.put(16, "E,,,,");
		NOTES_MAPPER.put(17, "F,,,,");
		NOTES_MAPPER.put(18, "^F,,,,");
		NOTES_MAPPER.put(19, "G,,,,");
		NOTES_MAPPER.put(20, "_A,,,,");
		NOTES_MAPPER.put(21, "A,,,,");
		NOTES_MAPPER.put(22, "_B,,,,");
		NOTES_MAPPER.put(23, "B,,,,");

		NOTES_MAPPER.put(24, "C,,,");
		NOTES_MAPPER.put(25, "^C,,,");
		NOTES_MAPPER.put(26, "D,,,");
		NOTES_MAPPER.put(27, "_E,,,");
		NOTES_MAPPER.put(28, "E,,,");
		NOTES_MAPPER.put(29, "F,,,");
		NOTES_MAPPER.put(30, "^F,,,");
		NOTES_MAPPER.put(31, "G,,,");
		NOTES_MAPPER.put(32, "_A,,,");
		NOTES_MAPPER.put(33, "A,,,");
		NOTES_MAPPER.put(34, "_B,,,");
		NOTES_MAPPER.put(35, "B,,,");

		NOTES_MAPPER.put(36, "C,,");
		NOTES_MAPPER.put(37, "^C,,");
		NOTES_MAPPER.put(38, "D,,");
		NOTES_MAPPER.put(39, "_E,,");
		NOTES_MAPPER.put(40, "E,,");
		NOTES_MAPPER.put(41, "F,,");
		NOTES_MAPPER.put(42, "^F,,");
		NOTES_MAPPER.put(43, "G,,");
		NOTES_MAPPER.put(44, "_A,,");
		NOTES_MAPPER.put(45, "A,,");
		NOTES_MAPPER.put(46, "_B,,");
		NOTES_MAPPER.put(47, "B,,");

		NOTES_MAPPER.put(48, "C,");
		NOTES_MAPPER.put(49, "^C,");
		NOTES_MAPPER.put(50, "D,");
		NOTES_MAPPER.put(51, "_E,");
		NOTES_MAPPER.put(52, "E,");
		NOTES_MAPPER.put(53, "F,");
		NOTES_MAPPER.put(54, "^F,");
		NOTES_MAPPER.put(55, "G,");
		NOTES_MAPPER.put(56, "_A,");
		NOTES_MAPPER.put(57, "A,");
		NOTES_MAPPER.put(58, "_B,");
		NOTES_MAPPER.put(59, "B,");

		NOTES_MAPPER.put(60, "C");
		NOTES_MAPPER.put(61, "^C");
		NOTES_MAPPER.put(62, "D");
		NOTES_MAPPER.put(63, "_E");
		NOTES_MAPPER.put(64, "E");
		NOTES_MAPPER.put(65, "F");
		NOTES_MAPPER.put(66, "^F");
		NOTES_MAPPER.put(67, "G");
		NOTES_MAPPER.put(68, "_A");
		NOTES_MAPPER.put(69, "A");
		NOTES_MAPPER.put(70, "_B");
		NOTES_MAPPER.put(71, "B");

		NOTES_MAPPER.put(72, "c");
		NOTES_MAPPER.put(73, "^c");
		NOTES_MAPPER.put(74, "d");
		NOTES_MAPPER.put(75, "_e");
		NOTES_MAPPER.put(76, "e");
		NOTES_MAPPER.put(77, "f");
		NOTES_MAPPER.put(78, "^f");
		NOTES_MAPPER.put(79, "g");
		NOTES_MAPPER.put(80, "_a");
		NOTES_MAPPER.put(81, "a");
		NOTES_MAPPER.put(82, "_b");
		NOTES_MAPPER.put(83, "b");

		NOTES_MAPPER.put(84, "c'");
		NOTES_MAPPER.put(85, "^c'");
		NOTES_MAPPER.put(86, "d'");
		NOTES_MAPPER.put(87, "_e'");
		NOTES_MAPPER.put(88, "e'");
		NOTES_MAPPER.put(89, "f'");
		NOTES_MAPPER.put(90, "^f'");
		NOTES_MAPPER.put(91, "g'");
		NOTES_MAPPER.put(92, "_a'");
		NOTES_MAPPER.put(93, "a'");
		NOTES_MAPPER.put(94, "_b'");
		NOTES_MAPPER.put(95, "b'");

		NOTES_MAPPER.put(96, "c''");
		NOTES_MAPPER.put(97, "^c''");
		NOTES_MAPPER.put(98, "d''");
		NOTES_MAPPER.put(99, "_e''");
		NOTES_MAPPER.put(100, "e''");
		NOTES_MAPPER.put(101, "f''");
		NOTES_MAPPER.put(102, "^f''");
		NOTES_MAPPER.put(103, "g''");
		NOTES_MAPPER.put(104, "_a''");
		NOTES_MAPPER.put(105, "a''");
		NOTES_MAPPER.put(106, "_b''");
		NOTES_MAPPER.put(107, "b''");

		NOTES_MAPPER.put(108, "c'''");
		NOTES_MAPPER.put(109, "^c'''");
		NOTES_MAPPER.put(110, "c'''");
		NOTES_MAPPER.put(111, "_e'''");
		NOTES_MAPPER.put(112, "e'''");
		NOTES_MAPPER.put(113, "f'''");
		NOTES_MAPPER.put(114, "^f'''");
		NOTES_MAPPER.put(115, "g'''");
		NOTES_MAPPER.put(116, "_a'''");
		NOTES_MAPPER.put(117, "a'''");
		NOTES_MAPPER.put(118, "_b'''");
		NOTES_MAPPER.put(119, "b'''");

		NOTES_MAPPER.put(120, "c''''");
		NOTES_MAPPER.put(121, "^c''''");
		NOTES_MAPPER.put(122, "d''''");
		NOTES_MAPPER.put(123, "_e''''");
		NOTES_MAPPER.put(124, "e''''");
		NOTES_MAPPER.put(125, "f''''");
		NOTES_MAPPER.put(126, "^f''''");
		NOTES_MAPPER.put(127, "g''''");
	}


}
