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

    public static void downloadVideo(String video) {

    }

    public static void downloadVideos(List<String> list) throws TimeoutException {
        for (String videoId : list) {
            VideoDownloader.download(videoId, "./videos");
        }
    }

    //todo: parse input
    public static String[] parseInput(String[] args){
        return null;
    }

    public static boolean getInfo(String id, boolean isChannel) {
        if (isChannel) {
            // gets channel info
            return true;
        }
        // gets video info
        return true;
    }

    /*
    todo: parse input
    input:
        * --info              - retrieves info about video or channel
        * --download          - downloads video or all the videos
        * --folder=[path]     - Folder where the videos are stored
        * --video=[video Id]  - Downloads specific video to folder
        * --channel=[channel] - Downloads all the videos from the channel
     */
    public static void main(String[] args) throws TimeoutException {

        List<String> list = VideosDownloader.getAllVideos("ValerioVelardoTheSoundofAI");
        downloadVideos(list);
    }
}
