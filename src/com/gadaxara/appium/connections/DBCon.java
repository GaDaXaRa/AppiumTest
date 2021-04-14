package com.gadaxara.appium.connections;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class DBCon {
	private AppiumDriver<MobileElement> driver;
	
	public DBCon(AppiumDriver<MobileElement> driver){
		this.driver = driver;
	}

	public MobileElement etCod() { 
		return (MobileElement) driver.findElement(By.id("etCod"));
		}
	public MobileElement etDescri() { 
		return (MobileElement) driver.findElement(By.id("etDescri"));
		}
	public MobileElement etPrecio() { 
		return (MobileElement) driver.findElement(By.id("etPrecio"));
		}
	public MobileElement btGrabar() { 
		return (MobileElement) driver.findElement(By.id("button"));
		}
	public MobileElement btBuscar() { 
		return (MobileElement) driver.findElement(By.id("button2"));
		}
	public MobileElement btModificar() { 
		return (MobileElement) driver.findElement(By.id("button3"));
		}
	public MobileElement btEliminar() { 
		return (MobileElement) driver.findElement(By.id("button4"));
		}
	public MobileElement msje() { 
		return (MobileElement) driver.findElementByXPath("//*[@class=\"android.widget.Toast\"]");
	}
}
