package cc.max.test.mg2dg;

import org.junit.Assert;
import org.junit.Test;

import cc.max.mg2dg.utils.RomeCurrencySymbol;
import static cc.max.mg2dg.utils.Constants.*;

public class RomeCurrencySymbolTest {

	@Test
	public void verifyInputsEmpty() throws Exception {
		try {
			RomeCurrencySymbol.calc("");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(EMPTY_INPUT));
		}

	}

	@Test
	public void verifyInputsMoreThan3() throws Exception {
		try {
			RomeCurrencySymbol.calc("XXXXX");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(SYMBOL_REPEATED_MORE_THAN_THREE_TIMES_IN_SUCCESSION));
		}

	}

	@Test
	public void verifyInputsInvalidSymbol() throws Exception {
		try {
			RomeCurrencySymbol.calc("XX XX");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(CONTAIN_INVALID_SYMBOL));
		}

	}

	@Test
	public void verifyInputsWrongSeqSymbol() throws Exception {
		try {
			RomeCurrencySymbol.calc("IM");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(CONTAIN_ILLEGAL_SYMBOL_SEQUENCE));
		}

	}

	@Test
	public void verifySumX() throws Exception {
		int sum = RomeCurrencySymbol.calc("x");
		Assert.assertTrue(sum == 10);
	}

	@Test
	public void verifySumMCMXLIV() throws Exception {
		int sum = RomeCurrencySymbol.calc("MCMXLIV");
		Assert.assertTrue(sum == 1944);
	}

	@Test
	public void verifySumMMCMXLIV() throws Exception {
		int sum = RomeCurrencySymbol.calc("MMCMXLIV");
		Assert.assertTrue(sum == 2944);
	}

	@Test
	public void verifySumMCMXLIUV() throws Exception {
		int sum = RomeCurrencySymbol.calc("MCMXLIIV");
		Assert.assertTrue(sum == 1945);
	}

	@Test
	public void verifySumMore() throws Exception {
		Assert.assertTrue(RomeCurrencySymbol.calc("MDCCLXXVI") == 1776);
		Assert.assertTrue(RomeCurrencySymbol.calc("MCMLIV") == 1954);
		Assert.assertTrue(RomeCurrencySymbol.calc("MCMXC") == 1990);
		Assert.assertTrue(RomeCurrencySymbol.calc("MMXIV") == 2014);
	}

	@Test
	public void verifySumLinear() throws Exception {
		Assert.assertTrue(RomeCurrencySymbol.calc("I") == 1);
		Assert.assertTrue(RomeCurrencySymbol.calc("II") == 2);
		Assert.assertTrue(RomeCurrencySymbol.calc("III") == 3);
		Assert.assertTrue(RomeCurrencySymbol.calc("IV") == 4);
		Assert.assertTrue(RomeCurrencySymbol.calc("V") == 5);
		Assert.assertTrue(RomeCurrencySymbol.calc("VI") == 6);
		Assert.assertTrue(RomeCurrencySymbol.calc("VII") == 7);
		Assert.assertTrue(RomeCurrencySymbol.calc("VIII") == 8);
		Assert.assertTrue(RomeCurrencySymbol.calc("VIV") == 9);
		Assert.assertTrue(RomeCurrencySymbol.calc("X") == 10);
	}
}
