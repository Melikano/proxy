import java.util.ArrayList;

public class HostGroup {

    int groupNumber;
    String groupName;
    boolean isActive;
    String status;
    ArrayList<String> hosts = new ArrayList<>();

    public HostGroup(int groupNumber, String groupName, boolean isActive) {
        this.groupName = groupName;
        this.groupNumber = groupNumber;
        this.isActive = isActive;
        if (isActive) {
            status = "ACTIVE";
        } else {
            status = "NOT ACTIVE";
        }
    }

    public void addHost(String host){
        if(!hosts.contains(host)) {
            hosts.add(host);
            System.out.println("host added successfully");
        }else {
            System.out.println("unable to add host, this host already exists in this group");
        }
    }

    public void deleteHost(String host){
        if(hosts.contains(host)) {
            hosts.remove(host);
            System.out.println("host removed successfully");
        }else {
            System.out.println("unable to remove host, this host doesn't exist in this group");
        }
    }


    public boolean searchHost(String host){
        for(String hst : hosts){
            if(hst.equals(host)){
                return true;
            }
        }

        return false;
    }
}
