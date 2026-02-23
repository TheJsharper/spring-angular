package com.jsharper.dyndns.server;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class Application {

    public static void main(String[] args) throws IOException {
        var url = System.getenv("NASA_VIDEO_URL");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1L));
        driver.get(url);

        var container = driver.findElements(By.cssSelector(".filterDiv"));


        var stream = container.stream().map(e -> {
            var video = new JSONObject();
            var description = e.findElement(By.cssSelector(".video_description"));
            var text = description.findElement(By.tagName("p")).getText();
            video.put("description", text);
            var title = description.findElement(By.cssSelector("em > u > b")).getText();
            video.put("title", title);
            var src = e.findElement(By.cssSelector(".video")).findElement(By.tagName("video")).getAttribute("src");
            video.put("url", src);
            try {
                var detailsVideo = e.findElement(By.cssSelector("div > p > a"));
                if (detailsVideo.getAttribute("href") != null) {
                    video.put("detailUrl", detailsVideo.getAttribute("href"));
                }
            } catch (Exception _) {
                video.put("detailUrl", "");
            }


            return video;
        });
        JSONObject array = new JSONObject();
        array.put("videos", stream.toList());
        var pathLocal = Paths.get("src", "main", "resources", "data", "videos.json");
        var fileWriter = new FileWriter(pathLocal.toFile());
        fileWriter.write(array.toString());
        driver.quit();
    }
}
