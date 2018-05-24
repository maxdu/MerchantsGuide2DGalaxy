package cc.max.mg2dg.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import static cc.max.mg2dg.utils.Constants.*;

public enum RomeCurrencySymbol {

	I(1), V(5), X(10), L(50), C(100), D(500), M(1000);


	
	private int value;

	private RomeCurrencySymbol(int value) {
		this.value = value;
	}

	public static int calc(String input) throws Exception {
		legal(input);
		List<Integer> values = new ArrayList<>();
		char[] inputs = input.toCharArray();
		int i = 0, j = 1;
		if (inputs.length == 1) {
			values.add(maptoVal(inputs[0]));
		} else {
			while (i < inputs.length && j < inputs.length) {
				if (maptoVal(inputs[i]) < maptoVal(inputs[j])) {
					if (!isValidSub(inputs[i], inputs[j])) {
						throw new Exception(CONTAIN_ILLEGAL_SYMBOL_SEQUENCE);
					}
					values.add(maptoVal(inputs[j]) - maptoVal(inputs[i]));
					i += 2;
					j += 2;
				} else {
					values.add(maptoVal(inputs[i]));
					i += 1;
					j += 1;
				}
			}
			if (i < inputs.length && j >= inputs.length) {
				values.add(maptoVal(inputs[i]));
			}
		}
		return values.stream().reduce((sum, x) -> {
			sum += x;
			return sum;
		}).get();

	}

	private static void legal(String input) throws Exception {
		// Should have value
		if (StringUtils.isEmpty(input)) {
			throw new Exception(EMPTY_INPUT);
		}
		// The symbols "I", "X", "C", and "M" can be repeated three times in
		// succession, but no more. (They may appear four times if the third and
		// fourth are separated by a smaller value, such as XXXIX.) "D", "L",
		// and "V" can never be repeated.
		char[] inputs = input.toCharArray();
		int repeatCount = 0;
		char repeatCheck = ' ';
		for (char ei : inputs) {
			if (ei == repeatCheck) {
				repeatCount++;
				if (repeatCount > 3)
					throw new Exception(SYMBOL_REPEATED_MORE_THAN_THREE_TIMES_IN_SUCCESSION);
			} else {
				repeatCheck = ei;
				if (!isValidOrNot(repeatCheck))
					throw new Exception(CONTAIN_INVALID_SYMBOL);
				repeatCount = 1;
			}
		}
	}

	public static boolean isValidOrNot(char toValid) {
		boolean validOrNot = false;
		for (RomeCurrencySymbol rcs : RomeCurrencySymbol.values()) {
			if (Character.toLowerCase(toValid) == rcs.name().toLowerCase().charAt(0)) {
				validOrNot = true;
				break;
			}
		}
		return validOrNot;
	}

	private static int maptoVal(char symbol) {
		int val = 0;
		for (RomeCurrencySymbol rcs : RomeCurrencySymbol.values()) {
			if (Character.toLowerCase(symbol) == rcs.name().toLowerCase().charAt(0)) {
				val = rcs.value;
				break;
			}
		}
		return val;
	}

	// "I" can be subtracted from "V" and "X" only. "X" can be subtracted
	// from "L" and "C" only. "C" can be subtracted from "D" and "M" only.
	// "V", "L", and "D" can never be subtracted.
	private static boolean isValidSub(RomeCurrencySymbol sub, RomeCurrencySymbol from) {
		if (sub == I && from != V && from != X) {
			return false;
		}
		if (sub == X && from != L && from != C) {
			return false;
		}
		if (sub == C && from != D && from != M) {
			return false;
		}
		if (sub == V || sub == L || sub == D) {
			return false;
		}
		return true;
	}

	private static boolean isValidSub(char subChar, char fromChar) {
		RomeCurrencySymbol sub = RomeCurrencySymbol.valueOf(String.valueOf(Character.toUpperCase(subChar)));
		RomeCurrencySymbol from = RomeCurrencySymbol.valueOf(String.valueOf(Character.toUpperCase(fromChar)));
		return isValidSub(sub, from);
	}

}
