package API;

import com.github.kiulian.downloader.model.videos.formats.Format;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import API.JavaYoutubeDownloader;
import API.VideosDownloader;

public class VideoDownloader {

    private static void createFolder(String path) throws IOException {
        Path path_ = Paths.get(path);
        Files.createDirectory(path_);
    }

    public static void main(String[] args) throws TimeoutException {

        String link = "JmXjYi7FOVQ";
        String path = "videos";
        String channelId = "ValerioVelardoTheSoundofAI";

        try {
            createFolder(path);
        }
        catch (IOException e) {
            System.err.println("Failed to create directory " + e.getMessage());
        }

        VideosDownloader.getAllVideos(channelId);

        //JavaYoutubeDownloader.run(link, path);



    }
}
