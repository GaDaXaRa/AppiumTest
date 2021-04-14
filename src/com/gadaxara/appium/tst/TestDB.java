package com.gadaxara.appium.tst;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gadaxara.appium.connections.DBCon;
import com.gadaxara.appium.model.Article;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

class TestDB {
	
	public static AppiumDriver<MobileElement> driver;
	static DBCon pageObject;
	
	final String altaOk = "Registro grabado correactamente";
	final String faltaCampos = "Se han de completar todos los campos";
	final String articleNotFound = "Registro no encontrado";
	final String articleCode = "43242424";
	
	@BeforeAll
	static void setUp() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("deviceName", "emulator-5554");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("allowTestPackages", "true");

		capabilities.setCapability("app", "/Users/msantiago/Downloads/TestBd/apk-debug/app-debug.apk");

		//Creacion de la sesion
		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Sesion creada");
		pageObject = new DBCon(driver);
	}
	
	@BeforeEach
	void setUpDB() {
		Article article = new Article();
		article.description = "description";
		article.code = articleCode;
		article.value = 32424;
		createArticle(article);
	}
	
	@AfterEach
	void clearDB() {
		removeArticle(articleCode);
		clearFields();
	}
	
	@AfterAll
	static void tearDown() {
		driver.quit();
	}
	
	@Test
	void testNewRegistry() {
		Article article = findArticle(articleCode);
		Assert.assertEquals(article.code, articleCode);
	}
	
	@Test
	void updateRegistry() {
		Article newArticle = new Article();
		newArticle.code = articleCode;
		String newDescription = "Nueva descripcion";
		newArticle.description = newDescription;
		newArticle.value = 3323;
		updateArticle(articleCode, newArticle);
		Article foundArticle = findArticle(articleCode);
		Assert.assertEquals(foundArticle.description, newDescription);
	}
	
	@Test
	void failSearch() {
		findArticle("343");
		System.out.println(pageObject.msje().getText());
		Assert.assertEquals(articleNotFound, pageObject.msje().getText());
	}
	
	private boolean createArticle(Article article) {
		fillArticle(article);
		pageObject.btGrabar().click();
		System.out.println(pageObject.msje().getText());
		return pageObject.msje().getText().equals(altaOk);
		
	}
	
	private void fillArticle(Article article) {
		pageObject.etCod().sendKeys(article.code);
		pageObject.etDescri().sendKeys(article.description);
		pageObject.etPrecio().sendKeys(Double.toString(article.value));
	}
	
	private Article findArticle(String code) {
		pageObject.etCod().sendKeys(code);
		pageObject.btBuscar().click();
		return readArticle();
	}
	
	private void removeArticle(String code) {
		pageObject.etCod().sendKeys(code);
		pageObject.btEliminar().click();
		System.out.println(pageObject.msje().getText());
	}
	
	private void updateArticle(String code, Article newArticle) {
		findArticle(articleCode);
		fillArticle(newArticle);
		pageObject.btModificar().click();
		System.out.println(pageObject.msje().getText());
	}
	
	private void clearFields() {
		pageObject.etCod().sendKeys("");
		pageObject.etDescri().sendKeys("");
		pageObject.etPrecio().sendKeys("");
	}
	
	private Article readArticle() {
		Article article = new Article();
		article.code = pageObject.etCod().getText();
		article.description = pageObject.etDescri().getText();
		String precio = pageObject.etPrecio().getText();
		article.value = 0;
		if (precio.length() > 0) {
			article.value = Double.parseDouble(precio);
		}
		
		return article;
	}

}
