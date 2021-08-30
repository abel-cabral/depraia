/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.util.List;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 *
 * @author leonardo
 */

public class teste {
    
  @Test
  public void hello() {
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      bro.quit();   
  }
  
  @Test
  public void click() throws InterruptedException {
      String a ="{\"data\":\"string\",\"praia\":{\"id\":1},\"vagas\":2}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElement(By.className("expand-operation")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("opblock-summary-method")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("body-param__text")).clear();
      bro.findElement(By.className("body-param__text")).sendKeys(a);
      Thread.sleep(10000);
      bro.quit();   
  }
  
  @Test
  public void cadastro() throws InterruptedException {
      String a ="{\"endereco\":{\"bairro\":\"BA\",\"cep\":\"1\",\"cidade\":\"CA\",\"rua\":\"RA\"},\"nome\":\"NA\"}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(4).click();
      Thread.sleep(3000);
      bro.findElement(By.className("opblock-summary-method")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("body-param__text")).clear();
      bro.findElement(By.className("body-param__text")).sendKeys(a);
      Thread.sleep(10000);
      bro.findElement(By.className("execute")).click();
      bro.quit();   
  }
  @Test
  public void recuperar() throws InterruptedException {
      String a ="{\"endereco\":{\"bairro\":\"BA\",\"cep\":\"1\",\"cidade\":\"CA\",\"rua\":\"RA\"},\"nome\":\"NA\"}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(4).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(4).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(3000);
      //bro.findElement(By.className("body-param__text")).clear();
      //bro.findElement(By.className("body-param__text")).sendKeys(a);
      Thread.sleep(10000);
      bro.findElement(By.className("execute")).click();
      //bro.quit();   
  } 
  
}
