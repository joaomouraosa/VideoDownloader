package API;

public class Operation {
    private Boolean isChannel=null; // info or download
    private Boolean isDownload=null; //otherwise info
    private Boolean isInfo=null;
    private String id=null;
    private String folder="~/Videos";
    private boolean validInstance=false;

    Operation() {}

    public boolean isChannel() {
        return this.isChannel;
    }

    public boolean isDownload() {
        return this.isDownload;
    }

    public boolean isInfo() {
        return this.isInfo;
    }

    public String getId() {
        return this.id;
    }

    public String getFolder() {
        return this.folder;
    }

    void setIsChannel(Boolean value) {
        this.isChannel = value;
    }

    void setIsInfo(Boolean value) {
        this.isInfo = value;
        this.isDownload = !value;
    }

    void setFolder(String value) {
        this.folder = value;
    }

    void setId(String value) {
        this.id = value;
    }

    public boolean isValid() {
        return (this.isChannel!= null && this.isDownload==!this.isInfo && this.id!=null && this.folder!=null);
    }

    @Override
    public String toString() {

        return "Channel " + this.isChannel + "\n" +
        "Download " + this.isDownload + "\n"+
        "Info " + this.isInfo + "\n"+
        "Id " + this.getId() + "\n"+
        "Folder " + this.getFolder() + "\n"+
        "Valid " + this.isValid();
    }
}