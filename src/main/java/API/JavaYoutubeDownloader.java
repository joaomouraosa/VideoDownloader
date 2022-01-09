package API;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeCallback;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import java.io.File;
import java.util.concurrent.TimeoutException;

public class JavaYoutubeDownloader {

    public static void run(String link, String path) throws TimeoutException {
        YoutubeDownloader downloader = new YoutubeDownloader();
        String videoId = link; // for url https://www.youtube.com/watch?v=abc12345

        RequestVideoInfo request = new RequestVideoInfo(videoId).callback(new YoutubeCallback<VideoInfo>() {
            @Override
            public void onFinished(VideoInfo videoInfo) {
                System.out.println("Finished parsing");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error: " + throwable.getMessage());
            }
        }).async();
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data(); // will block thread

        // video details
        VideoDetails details = video.details();
        System.out.println(details.title());

        String video_name = details.title().replaceAll(" ", "_").toLowerCase();

        VideoFormat bestFormat = video.bestVideoWithAudioFormat();
        RequestVideoFileDownload request2 = new RequestVideoFileDownload(bestFormat)
        .saveTo(new File(path)) // by default "videos" directory
                .renameTo(video_name) // by default file name will be same as video title on youtube
                .overwriteIfExists(true) // if false and file with such name already exits sufix will be added video(1).mp4
                .callback(new YoutubeProgressCallback<File>() {
                    @Override
                    public void onDownloading(int progress) {
                        System.out.printf("Downloaded %d%%\n", progress);
                    }

                    @Override
                    public void onFinished(File videoInfo) {
                        System.out.println("Finished file: " + videoInfo);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Error: " + throwable.getLocalizedMessage());
                    }
                })
                .async();
        Response<File> response2 = downloader.downloadVideoFile(request2);
        File data = response2.data(); // will block current thread
    }

    public static void main(String link, String path) throws TimeoutException {
    }
}