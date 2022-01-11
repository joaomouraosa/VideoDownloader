package API;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class EntryPoint {

    public static boolean go(Operation op) throws TimeoutException {

        if (!op.isValid())
            return false;

        if (op.isChannel()) {
            List<String> ids = VideoDownloader.infoChannel(op.getId(), true);

            if (op.isDownload()) {
                for (String videoId : ids) {
                    if (!VideoDownloader.download(videoId, op.getFolder(), op.isDownload()))
                        return false;
                }
                return true;
            }
            return true;
        }
        return VideoDownloader.download(op.getId(), op.getFolder(), op.isDownload());
    }


    private static String getId(String str) {
        if (str.contains("/")) {
            String[] tmp = str.split("/");
            return tmp[tmp.length-1];
        }
        return str;
    }


    public static Operation parseInput(String[] args){

        Operation mode = new Operation();

        for (String arg : args) {

            if (arg.contains("=")) {
                try {
                    String[] tmp = arg.split("=");
                    String key = tmp[0];
                    String value = tmp[1];

                    if (key.equals("--folder")  || key.equals("-f")) {
                        mode.setFolder(value);
                    }
                    if (key.equals("--channel") || key.equals("-c")) {
                        //todo https://www.youtube.com/channel/UCa3SDs8cPme7tD9S8BIVYrw -> UCa3SDs8cPme7tD9S8BIVYrw
                        //todo https://www.youtube.com/c/ValerioVelardoTheSoundofAI -> ValerioVelardoTheSoundofAI

                        mode.setId(getId(value));
                        mode.setIsChannel(true);
                    }
                    if (key.equals("--video")   || key.equals("-v")) {

                        //todo https://www.youtube.com/watch?v=c4YGsyiYSLM -> c4YGsyiYSLM
                        mode.setId(getId(value));
                        mode.setIsChannel(false);
                    }

                } catch (Exception e) {
                    System.out.println("couldn't parse ");
                }
            }

            if (arg.equals("--info") || arg.equals("-i")) {
                mode.setIsInfo(true);
            }

            if (arg.equals("--download") || arg.equals("-d")) {
                mode.setIsInfo(false);
            }
        }

        return mode;
    }

    public static void help() {
        System.out.println("Options:");
        System.out.println(" --info\t\t\t\t - retrieves info about video or channel");
        System.out.println(" --download\t\t\t - downloads video or all the videos");
        System.out.println(" --folder=[path]\t\t - Folder where the videos are stored");
        System.out.println(" --video=[video Id]\t - Downloads specific video to folder");
        System.out.println(" --channel=[channel]\t - Downloads all the videos from the channel");
        System.out.println("\nExample:\n--download --video=c4YGsyiYSLM --folder=~/Videos");
    }

    public static void main(String[] args) throws TimeoutException {

        help();
        Operation op = parseInput(args);
    //    System.out.println(op.toString());
        go(op);
    }
}
