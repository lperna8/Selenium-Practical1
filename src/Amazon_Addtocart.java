import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import javax.lang.model.element.Element;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Amazon_Addtocart {
    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:/Users/lperna/Downloads/Drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.amazon.com/");
        driver.findElement(By.id("twotabsearchtextbox")).click();
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("appium Book");        // ratings = String.valueOf(driver.findElement(By.xpath("//div[contains(@class,'a-section a-spacing-medium')]//div/a[contains(text(),'Paperback')]/../../..//div[@class='a-row a-size-small']")));

        driver.findElement(By.xpath("//div[@class='nav-search-submit nav-sprite']//input[@class='nav-input']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

//      driver.findElement(By.xpath("//img[@data-image-index='10']")).click();

        //System.out.println(ratings.get(0));

        List<WebElement> items = driver.findElements(By.xpath("//div[contains(@class,'a-section a-spacing-medium')]//div/a[contains(text(),'Paperback')]"));
        System.out.println("Total paperback items found are: "+items.size());

        WebElement tenthBook = driver.findElement(By.xpath("//*[@id='search']//div[@data-component-type='s-search-result'][//a[not(contains(text(), 'Kindle Edition'))]][10]//a[not(contains(text(), 'Kindle Edition'))]"));
//         Focusing on paperback and hardback books since Kindle versions do NOT have an Add to Cart option
        List<WebElement>  tenthResultRatings = driver.findElements(By.xpath("//*[@id='search']//div[@data-component-type='s-search-result'][//a[not(contains(text(), 'Kindle Edition'))]][10][//a[not(contains(text(), 'Kindle Edition'))] and //span[contains(text(),'out of 5 stars')]]"));
        if (tenthResultRatings.size()>0) {
            String ratingOfBook = driver.findElement(By.xpath("//*[@id='search']//div[@data-component-type='s-search-result'][//a[not(contains(text(), 'Kindle Edition'))]][10]//span[contains(text(),'out of 5 stars')]")).getAttribute("innerText");
            System.out.println("The 10th book does have a rating and it's " + ratingOfBook + ".");
            tenthBook.click();
        } else {
            List<WebElement>  resultsWithRatingsAndNotKindleVersionOnly = driver.findElements(By.xpath("//*[@id='search']//div[@data-component-type='s-search-result'][//a[contains(text(), 'back')] and //span[contains(text(),'out of 5 stars')]]"));
            int number = new Random().nextInt(resultsWithRatingsAndNotKindleVersionOnly.size());
            System.out.println("The 10th book does NOT have a rating. Picking one of the " + resultsWithRatingsAndNotKindleVersionOnly.size() + " books with ratings at random. Picking number " + number + ".");
            WebElement  randomResultWithRatings = driver.findElement(By.xpath("//*[@id='search']//div[@data-component-type='s-search-result'][//a[contains(text(), 'back')] and //span[contains(text(),'out of 5 stars')]][" + number + "]//a[contains(text(), 'back')]"));
            String ratingOfRandomBook = driver.findElement(By.xpath("//*[@id='search']//div[@data-component-type='s-search-result'][//a[contains(text(), 'back')] and //span[contains(text(),'out of 5 stars')]][" + number + "]//span[contains(text(),'out of 5 stars')]")).getAttribute("innerText");
            System.out.println("The rating of our random book is " + ratingOfRandomBook + ".");
            randomResultWithRatings.click();
        }



        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(By.id("add-to-cart-button")).click();

        String bookprice = driver.findElement(By.xpath("//span[@class='a-size-base a-color-price a-text-bold']")).getText();
        System.out.println("The sub-total is " + bookprice);
    }

}
