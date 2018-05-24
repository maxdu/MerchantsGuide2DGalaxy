
package cc.max.mg2dg.utils;

import static cc.max.mg2dg.utils.Constants.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class CheckAndRespond {

	public static final Logger logger = (Logger) LogManager.getLogger(CheckAndRespond.class);

	public static Map<String, String> definedNumMapping = new HashMap<>();
	public static Map<String, BigDecimal> creditsCosts = new HashMap<>();

	public static String process(String input) {
		if (StringUtils.isBlank(input))
			return RESPOND_HAVENOIDEA;
		String[] inputSplits = input.trim().toLowerCase().split(" ");
		try {
			if (isDefineNumericMapping(inputSplits))
				return RESPOND_MUTE;
			if (isSetCostCredits(inputSplits))
				return RESPOND_MUTE;
		} catch (Exception e) {
			logger.debug("some fail inside isDefineNumericMapping or isSetCostCredits", e);
			return RESPOND_HAVENOIDEA;
		}
		String respond = isAskingCostCredits(inputSplits);
		if (RESPOND_HAVENOIDEA.equals(respond)) {
			respond = isAskingActualNunmbers(inputSplits);
		}

		return respond;
	}

	private static boolean isDefineNumericMapping(String[] splits) throws Exception {
		// check length should be 3, fixed value 'is', last splits size 1
		if (splits.length != DEFINE_NUMERIC_MAPPING.length || !splits[1].equals(DEFINE_NUMERIC_MAPPING[1])
				|| splits[2].toCharArray().length != 1) {
			return false;
		}
		logger.debug("should be DefineNumericMapping...");
		int num = -1;
		try {
			num = RomeCurrencySymbol.calc(splits[2]);
		} catch (Exception e) {
			logger.debug(String.format("not a valid roman %s", splits[splits.length - 1]), e);
		}
		if (num != -1) {
			definedNumMapping.put(splits[0], splits[2]);
			return true;
		} else {
			throw new Exception(RESPOND_HAVENOIDEA);
		}
	}

	private static boolean isSetCostCredits(String[] splits) throws Exception {
		// check length at least 5
		if (splits.length < SET_COST_CREDITS.length) {
			return false;
		}
		// check fixed value 'credits' & 'is'
		if (!SET_COST_CREDITS[4].equals(splits[splits.length - 1])
				|| !SET_COST_CREDITS[2].equals(splits[splits.length - 3])) {
			return false;
		}
		// check '[0-9]+'
		if (!splits[splits.length - 2].matches(SET_COST_CREDITS[3])) {
			return false;
		}
		logger.debug("should be SetCostCredits...");
		// metal name
		String metal = splits[splits.length - 4];
		// defined quantity, to roman
		StringBuilder romans = new StringBuilder();
		for (int i = 0; i < splits.length - 4; i++) {
			String roman = definedNumMapping.get(splits[i]);
			if (roman == null) {
				logger.error(String.format("Mapping of %s not found", splits[i]));
				throw new Exception(RESPOND_HAVENOIDEA);
			}
			romans.append(roman);
		}
		// roman calc
		int metalQuantity = -1;
		try {
			metalQuantity = RomeCurrencySymbol.calc(romans.toString());
		} catch (Exception e) {
			logger.error(String.format("not a valid roman %s", romans.toString()), e);
			throw new Exception(RESPOND_HAVENOIDEA);
		}
		if (metalQuantity > 0) {
			BigDecimal matelCredits = new BigDecimal(splits[splits.length - 2]);
			creditsCosts.put(metal, matelCredits.divide(new BigDecimal(metalQuantity)));
			return true;
		} else {
			throw new Exception(RESPOND_HAVENOIDEA);
		}
	}

	private static String isAskingCostCredits(String[] splits) {
		// check length at least 7
		if (splits.length < ASK_COST_CREDITS.length) {
			return RESPOND_HAVENOIDEA;
		}
		// check fixed value, first 4 : "how", "many", "credits", "is"
		for (int i = 0; i < 4; i++) {
			if (!ASK_COST_CREDITS[i].equals(splits[i])) {
				return RESPOND_HAVENOIDEA;
			}
		}
		// check end with "?"
		if (!ASK_COST_CREDITS[6].equals(splits[splits.length - 1])) {
			return RESPOND_HAVENOIDEA;
		}
		// check metal
		String metal = splits[splits.length - 2];
		if (creditsCosts.get(metal) == null) {
			return RESPOND_HAVENOIDEA;
		}
		logger.debug("should be AskingCostCredits...");
		// convert to the actual quantity be asking for
		StringBuilder metalStr = new StringBuilder();
		StringBuilder romans = new StringBuilder();
		for (int i = 4; i < splits.length - 2; i++) {
			String roman = definedNumMapping.get(splits[i]);
			if (roman == null) {
				logger.error(String.format("Mapping of %s not found", splits[i]));
				return RESPOND_HAVENOIDEA;
			}
			romans.append(roman);
			metalStr.append(SPACE).append(splits[i]);
		}
		metalStr.append(SPACE).append(StringUtils.capitalize(metal));
		// roman calc
		int metalQuantity = -1;
		try {
			metalQuantity = RomeCurrencySymbol.calc(romans.toString());
		} catch (Exception e) {
			logger.error(String.format("not a valid roman %s", romans.toString()), e);
			return RESPOND_HAVENOIDEA;
		}
		if (metalQuantity > 0) {
			BigDecimal unitCredit = creditsCosts.get(metal);
			BigDecimal res = unitCredit.multiply(new BigDecimal(metalQuantity));
			return String.format(RESPOND_CREDITS, metalStr.toString().trim(), res);
		} else {
			return RESPOND_HAVENOIDEA;
		}

	}

	private static String isAskingActualNunmbers(String[] splits) {
		// check length at least 5
		if (splits.length < ASK_ACTUAL_NUMRICS.length) {
			return RESPOND_HAVENOIDEA;
		}
		// check fixed value, first 3 : "how", "much", "is"
		for (int i = 0; i < 3; i++) {
			if (!ASK_ACTUAL_NUMRICS[i].equals(splits[i])) {
				return RESPOND_HAVENOIDEA;
			}
		}
		// check end with "?"
		if (!ASK_ACTUAL_NUMRICS[4].equals(splits[splits.length - 1])) {
			return RESPOND_HAVENOIDEA;
		}
		logger.debug("should be AskingActualNunmbers...");
		// convert to the actual quantity be asking for
		StringBuilder qtyStr = new StringBuilder();
		StringBuilder romans = new StringBuilder();
		for (int i = 3; i < splits.length - 1; i++) {
			String roman = definedNumMapping.get(splits[i]);
			if (roman == null) {
				logger.error(String.format("Mapping of %s not found", splits[i]));
				return RESPOND_HAVENOIDEA;
			}
			romans.append(roman);
			qtyStr.append(SPACE).append(splits[i]);
		}
		// roman calc
		int actualQty = -1;
		try {
			actualQty = RomeCurrencySymbol.calc(romans.toString());
		} catch (Exception e) {
			logger.error(String.format("not a valid roman %s", romans.toString()), e);
			return RESPOND_HAVENOIDEA;
		}
		if (actualQty > 0) {
			return String.format(RESPOND_NUMERIC, qtyStr.toString().trim(), actualQty);
		} else {
			return RESPOND_HAVENOIDEA;
		}
	}

}
