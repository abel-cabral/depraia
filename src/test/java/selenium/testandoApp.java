/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import junit.framework.Assert;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author leonardo
 */
public class testandoApp {
   //1
 @Test
  public static void postCadastraUsuario() throws InterruptedException {
    String info = "{\"admin\":true,\"cpf\":\"000.845.810-48\",\"email\":\"t@gmail\",\"endereco\":{\"bairro\":\"BA\",\"cep\":\"C1\",\"cidade\":\"CA\",\"rua\":\"RA\"},\"id\":0,\"nome\":\"NA\",\"senha\":\"SA\",\"tipoUsuario\":0}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(3).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(1).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(10000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(7000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      assertEquals("200",elementoTexto);
      bro.quit();
  }
  //2
  @Test
  public void postAutenticaUsuarios1() throws InterruptedException {
      String info = "{\"email\":\"t@gmail\",\"senha\":\"SA\"}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(3).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(2).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(10000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(3000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();
      assertEquals("200",elementoTexto);
      
  }
  //3
  @Test
  public void postAutenticaUsuarios2() throws InterruptedException {
      String info = "{\"email\":\"\",\"senha\":\"\"}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(3).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(2).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(10000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(3000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      assertEquals("404",elementoTexto);
      //bro.quit();
  }
  //4
  @Test
  public void getTodosOsUsuarios() throws InterruptedException {
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(3).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(3).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(3000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      assertEquals("200",elementoTexto);
      bro.quit();
  }
  //5
  @Test
  public void agendaControllerPost1() throws InterruptedException {
      String info = "{\"data\":\"01/01/2000\",\"praia\":{\"id\":5}\"vagas\":1000}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(0).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(3000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(3000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      assertEquals("200",elementoTexto);
      bro.quit();
  }
  //6
  @Test
  public void agendaControllerPost2() throws InterruptedException {
      String info = "";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(0).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(3000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(3000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      assertEquals("500",elementoTexto);
      bro.quit();
  }
  //7
  @Test
   public void getCadastradaId() throws InterruptedException {
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(1).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(3000);
      bro.findElement(By.tagName("input")).sendKeys("5");
      Thread.sleep(3000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      assertEquals("200",elementoTexto);
      bro.quit();
  }
   //8
  @Test
  public void agendaControllerPut1() throws InterruptedException {
      String info = "{\"data\":\"20/10/2000\",\"praia\":{\"id\":3},\"vagas\":100}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(2).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(5000);
      bro.findElement(By.tagName("input")).sendKeys("5");
      Thread.sleep(5000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      assertEquals("200",elementoTexto);
      bro.quit();
      
      
  }
  //9
  @Test
  public void agendaControllerPut2() throws InterruptedException {
      String info = "{\"data\":\"20/10/2000\",\"praia\":{\"id\":},\"vagas\":100}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(2).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(5000);
      bro.findElement(By.tagName("input")).sendKeys("10000");
      Thread.sleep(5000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("400\nUndocumented",elementoTexto);
  }
  //10
  @Test
  public void agendaControllerPut3() throws InterruptedException {
      String info = "{\"data\":\"\",\"praia\":{\"id\":},\"vagas\":100}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(2).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(5000);
      bro.findElement(By.tagName("input")).sendKeys("10000");
      Thread.sleep(5000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("400\nUndocumented",elementoTexto);
  }
  
  //16
  @Test
  public void putReserva1() throws InterruptedException {
    String info ="{\"agendaId\":5,\"userId\":6}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(5).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(5000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("200",elementoTexto);
  }
  //17
  // O ERRO 500 NAO É ENCONTRADO
  @Test
  public void putReserva2() throws InterruptedException {
    String info ="{\"agendaId\":1000,\"userId\":6}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(5).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(5000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("500\nUndocumented",elementoTexto);
  }
  //18
  // O ERRO 500 NAO É ENCONTRADO 
  @Test
  public void putReserva3() throws InterruptedException {
    String info ="{\"agendaId\":5,\"userId\":1000}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(5).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(5000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("500\nUndocumented",elementoTexto);
  }
  //19
  // O ERRO 500 NAO É ENCONTRADO 
  @Test
  public void putReserva4() throws InterruptedException {
      String info ="{}";
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(5).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      Thread.sleep(5000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("500\nUndocumented",elementoTexto);
  }
  //20
  @Test
  public void gerCadastradas() throws InterruptedException {
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(0).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(6).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(3000);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("200",elementoTexto);
  }
  //21
  @Test
  public void postCadastradasAnbulante1() throws InterruptedException {
      String info1 = "{\"praia\":{\"id\":3},";
      String info2 = "\"user\":{\"admin\":true,\"cpf\":\"a\",\"email\":\"a\",";
      String info3 = "\"endereco\":{\"bairro\":\"a\",\"cep\":\"a\",\"cidade\":\"a\",\"rua\":\"a\"},";
      String info4 = "\"id\":0,\"nome\":\"a\",\"senha\":\"a\",\"tipoUsuario\":0}}";
      String info =info1+info2+info3+info4 ;
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(1).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(0).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("200",elementoTexto);
  }
  //22
  @Test
  public void postCadastradasAnbulante2() throws InterruptedException {
      String info1 = "{\"praia\":{\"id\":10000},";
      String info2 = "\"user\":{\"admin\":true,\"cpf\":\"a\",\"email\":\"a\",";
      String info3 = "\"endereco\":{\"bairro\":\"a\",\"cep\":\"a\",\"cidade\":\"a\",\"rua\":\"a\"},";
      String info4 = "\"id\":0,\"nome\":\"a\",\"senha\":\"a\",\"tipoUsuario\":0}}";
      String info =info1+info2+info3+info4 ;
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(1).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(0).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("500\nUndocumented",elementoTexto);
  }
  //23
  @Test
  public void postCadastradasAnbulante3() throws InterruptedException {
      
      String info ="{\"praia\":{},\"user\":{}}" ;
      System.setProperty("webdriver.Chrome.driver", "chromedriver");
      WebDriver bro;
      bro = new ChromeDriver();
      bro.navigate().to("http://localhost:8080/swagger-ui.html#/");
      Thread.sleep(3000);
      bro.findElements(By.className("expand-operation")).get(1).click();
      Thread.sleep(3000);
      bro.findElements(By.className("opblock-summary-method")).get(0).click();
      Thread.sleep(3000);
      bro.findElement(By.className("try-out")).click();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).clear();
      Thread.sleep(2000);
      bro.findElement(By.className("body-param__text")).sendKeys(info);
      bro.findElement(By.className("execute")).click();
      Thread.sleep(10000);
      String elementoTexto = bro.findElements(By.className("response-col_status")).get(1).getText();
      Thread.sleep(3000);
      bro.quit();   
      assertEquals("500\nUndocumented",elementoTexto);
  }
  
}
