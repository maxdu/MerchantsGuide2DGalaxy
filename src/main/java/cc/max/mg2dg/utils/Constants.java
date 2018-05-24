package cc.max.mg2dg.utils;

public class Constants {
	
	public static final String CONTAIN_INVALID_SYMBOL = "contain invalid symbol";
	public static final String SYMBOL_REPEATED_MORE_THAN_THREE_TIMES_IN_SUCCESSION = "symbol repeated more than three times in succession";
	public static final String EMPTY_INPUT = "empty input";
	public static final String CONTAIN_ILLEGAL_SYMBOL_SEQUENCE = "contain illegal symbol sequence";

	public static final String QUANTITY = "%quantity";
	public static final String METAL = "%metal";
	public static final String ROMAN = "%roman";
	public static final String DECIMAL = "%decimal";
	public static final String REPEATABLE = "%N";
	public static final String QUESTIONMARK = "?";
	public static final String SPACE = " ";

	public static final String[] DEFINE_NUMERIC_MAPPING = { "%quantity", "is", "%roman" };
	public static final String[] SET_COST_CREDITS = { "%quantity%N", "%metal", "is", "[0-9]+", "credits" };
	public static final String[] ASK_COST_CREDITS = { "how", "many", "credits", "is", "%quantity%N", "%metal", "?" };
	public static final String[] ASK_ACTUAL_NUMRICS = { "how", "much", "is", "%quantity%N", "?" };

	public static final String RESPOND_HAVENOIDEA = "I have no idea what you are talking about";
	public static final String RESPOND_NUMERIC = "%s is %s";
	public static final String RESPOND_CREDITS = "%s is %s Credits";
	public static final String RESPOND_MUTE = "";
}
