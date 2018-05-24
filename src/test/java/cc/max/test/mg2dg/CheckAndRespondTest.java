package cc.max.test.mg2dg;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cc.max.mg2dg.utils.CheckAndRespond;
import static cc.max.mg2dg.utils.Constants.*;

import java.math.BigDecimal;
import java.util.HashMap;

public class CheckAndRespondTest {

	@Before
	public void initial() {
		CheckAndRespond.definedNumMapping = new HashMap<>();
		CheckAndRespond.creditsCosts = new HashMap<>();
	}

	@Test
	public void verifyDefinedNumMppingCorrent() throws Exception {
		String respond = CheckAndRespond.process("glob is I");
		Assert.assertTrue(RESPOND_MUTE.equals(respond));
		Assert.assertTrue("i".equals(CheckAndRespond.definedNumMapping.get("glob")));
	}

	@Test
	public void verifyDefinedNumMppingOverwrite() throws Exception {
		String respond = null;
		respond = CheckAndRespond.process("dfg is I");
		respond = CheckAndRespond.process("dfg is X");
		Assert.assertTrue(RESPOND_MUTE.equals(respond));
		Assert.assertTrue("x".equals(CheckAndRespond.definedNumMapping.get("dfg")));
	}

	@Test
	public void verifyDefinedNumMppingMixed() throws Exception {
		String respond = null;
		respond = CheckAndRespond.process("dfg is I");
		respond = CheckAndRespond.process("dfg is 3");
		Assert.assertTrue(RESPOND_HAVENOIDEA.equals(respond));
		Assert.assertTrue("i".equals(CheckAndRespond.definedNumMapping.get("dfg")));
	}

	@Test
	public void verifyDefinedNumMppingIncorrect() throws Exception {
		String respond = null;
		respond = CheckAndRespond.process("dfg is 3");
		Assert.assertTrue(RESPOND_HAVENOIDEA.equals(respond));
		Assert.assertTrue(null == CheckAndRespond.definedNumMapping.get("dfg"));
	}
	
	@Test
	public void verifyDefinedNumMppingMappingAndSetCredits() throws Exception {
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("glob is I")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("prok is V")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("pish is X")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("tegj is L")));
		Assert.assertTrue("i".equals(CheckAndRespond.definedNumMapping.get("glob")));
		Assert.assertTrue("v".equals(CheckAndRespond.definedNumMapping.get("prok")));
		Assert.assertTrue("x".equals(CheckAndRespond.definedNumMapping.get("pish")));
		Assert.assertTrue("l".equals(CheckAndRespond.definedNumMapping.get("tegj")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("glob glob Silver is 34 Credits")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("glob prok Gold is 57800 Credits")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("pish pish Iron is 3910 Credits")));
		Assert.assertTrue(new BigDecimal("17").equals(CheckAndRespond.creditsCosts.get("silver")));
		Assert.assertTrue(new BigDecimal("14450").equals(CheckAndRespond.creditsCosts.get("gold")));
		Assert.assertTrue(new BigDecimal("195.5").equals(CheckAndRespond.creditsCosts.get("iron")));
	}
	
	@Test
	public void verifyHowMuchIs() throws Exception {
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("glob is I")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("prok is V")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("pish is X")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("tegj is L")));
		Assert.assertTrue("pish tegj glob glob is 42".equals(CheckAndRespond.process("how much is pish tegj glob glob ?")));
	}	
	
	@Test
	public void verifyHowManyCreditsIs() throws Exception {
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("glob is I")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("prok is V")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("pish is X")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("tegj is L")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("glob glob Silver is 34 Credits")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("glob prok Gold is 57800 Credits")));
		Assert.assertTrue(RESPOND_MUTE.equals(CheckAndRespond.process("pish pish Iron is 3910 Credits")));
		Assert.assertTrue("glob prok Silver is 68 Credits".equals(CheckAndRespond.process("how many Credits is glob prok Silver ?")));
		Assert.assertTrue("glob prok Gold is 57800 Credits".equals(CheckAndRespond.process("how many Credits is glob prok Gold ?")));
		Assert.assertTrue("glob prok Iron is 782.0 Credits".equals(CheckAndRespond.process("how many Credits is glob prok Iron ?")));
	}	
}
