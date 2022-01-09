package API;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestChannelUploads;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class VideosDownloader {

    List<String> downloadList = new ArrayList<String>();

    public static List<String> getAllVideos(String channel) {

        List<String> output = new ArrayList<String>();

        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestChannelUploads request = new RequestChannelUploads(channel);
        Response<PlaylistInfo> response = downloader.getChannelUploads(request);
        PlaylistInfo playlistInfo = response.data();

        List<PlaylistVideoDetails> videos = playlistInfo.videos();

        for (PlaylistVideoDetails video : videos) {
            Integer index = video.index();
            String title = video.title();
            String videoId = video.videoId();

            if (video.isPlayable()) {
                System.out.println(index + " " + videos.size() + " " + title + " " + videoId);
                output.add(videoId);
            }
        }
        return output;
    }


    public static void downloadVideos(List<String> list) throws TimeoutException {
        for (String videoId : list) {
            JavaYoutubeDownloader.run(videoId, "./videos");
        }
    }

    public static void main(String[] args) throws TimeoutException {
        List<String> list = VideosDownloader.getAllVideos("ValerioVelardoTheSoundofAI");
        downloadVideos(list);
    }
}
