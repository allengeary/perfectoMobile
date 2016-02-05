package com.perfectoMobile.page.keyWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.keyWord.KeyWordStep.StepFailure;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;
import com.perfectoMobile.page.keyWord.step.KeyWordStepFactory;

public class KeyWordStepTest 
{
	@BeforeClass
	public void setUp()
	{
		KeyWordStepFactory.instance().addKeyWord( "PASS", Pass.class );
		KeyWordStepFactory.instance().addKeyWord( "FAIL", Fail.class );
		KeyWordStepFactory.instance().addKeyWord( "ERROR", Error.class );
		KeyWordStepFactory.instance().addKeyWord( "UERROR", UncheckedError.class );
	}
	
	@Test()
	  public void executeStepPass() throws Throwable
	  {
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "PASS", null, false, StepFailure.ERROR, false, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
	    Assert.assertFalse( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "PASS", null, false, StepFailure.ERROR, true, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
	    
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "PASS", null, false, StepFailure.IGNORE, false, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "PASS", null, false, StepFailure.IGNORE, true, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );

	  }
	
	@Test()
	  public void executeStepFail() throws Throwable
	  {
		
	    Assert.assertFalse( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "FAIL", null, false, StepFailure.ERROR, false, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "FAIL", null, false, StepFailure.ERROR, true, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
	    
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "FAIL", null, false, StepFailure.IGNORE, true, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "FAIL", null, false, StepFailure.IGNORE, false, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );

	  }
	
	@Test
	  public void executeStepError() throws Throwable
	  {
		
		try
		{
			Assert.assertFalse( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "ERROR", null, false, StepFailure.ERROR, false, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
		}
		catch( Exception e )
		{
			
		}
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "ERROR", null, false, StepFailure.ERROR, true, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "ERROR", null, false, StepFailure.IGNORE, true, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
		Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "ERROR", null, false, StepFailure.IGNORE, true, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );

	  }
	
	@Test
	  public void executeStepUError() throws Throwable
	  {
		
		try
		{
			Assert.assertFalse( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "UERROR", null, false, StepFailure.ERROR, false, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
		}
		catch( Exception e )
		{
			
		}
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "UERROR", null, false, StepFailure.ERROR, true, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
	    Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "UERROR", null, false, StepFailure.IGNORE, true, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );
		Assert.assertTrue( KeyWordStepFactory.instance().createStep("stepOne", "page", true, "UERROR", null, false, StepFailure.IGNORE, false, null ).executeStep(new MyPage(), new MyWebDriver(), new HashMap<String,Object>(10), new HashMap<String,PageData>( 10 ) ) );

	  }
	
	public class MyPage implements Page
	{

		@Override
		public Element getElement(String elementName) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Element getElement(String pageName, String elementName) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void initializePage() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setDriver(Object webDriver) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static class Pass extends AbstractKeyWordStep
	{

		@Override
		protected boolean _executeStep(Page pageObject, WebDriver webDriver, Map<String, Object> contextMap,
				Map<String, PageData> dataMap) throws Exception {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	
	public static class Fail extends AbstractKeyWordStep
	{

		@Override
		protected boolean _executeStep(Page pageObject, WebDriver webDriver, Map<String, Object> contextMap,
				Map<String, PageData> dataMap) throws Exception {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	public static class Error extends AbstractKeyWordStep
	{

		@Override
		protected boolean _executeStep(Page pageObject, WebDriver webDriver, Map<String, Object> contextMap,
				Map<String, PageData> dataMap) throws Exception {
			// TODO Auto-generated method stub
			throw new NullPointerException();
		}
		
	}
	
	public static class UncheckedError extends AbstractKeyWordStep
	{

		@Override
		protected boolean _executeStep(Page pageObject, WebDriver webDriver, Map<String, Object> contextMap,
				Map<String, PageData> dataMap) throws Exception {
			// TODO Auto-generated method stub
			throw new IllegalStateException();
		}
		
	}
	
	public class MyWebDriver implements WebDriver
	{

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public WebElement findElement(By arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<WebElement> findElements(By arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void get(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getCurrentUrl() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPageSource() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getTitle() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getWindowHandle() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<String> getWindowHandles() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Options manage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Navigation navigate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void quit() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public TargetLocator switchTo() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
  
}
