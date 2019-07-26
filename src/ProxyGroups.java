import java.util.ArrayList;

public class ProxyGroups {

    public boolean found;
    public ArrayList<HostGroup> allGroups = new ArrayList<>();

    public void add(HostGroup newGroup) {
        if (!allGroups.contains(newGroup)) {
            allGroups.add(newGroup);
            System.out.println("group added successfully");
            return;
        }

        System.out.println("unable to add group, this group already exists!");
    }

    public void deleteGroup(String group) {
        for(HostGroup hg : allGroups){
            if(hg.groupName.equals(group)){
                allGroups.remove(hg);
                System.out.println("group deleted successfully");
                return;
            }
        }

        System.out.println("unable to delete group, this group doesn't exists!");
    }

    public void printGroups() {
        if(allGroups.size() == 0){
            System.out.println("no group assigned yet");
            return;
        }
        System.out.println("#group\tgroupName\tstatus\tnumberOfHosts");
        for (HostGroup hg : allGroups) {
            System.out.println(hg.groupNumber + "\t\t" + hg.groupName + "\t\t" + hg.status+"\t\t" + hg.hosts.size());
        }
        System.out.println("----------------------------------------------------------");
    }

    public HostGroup search(String groupName) {
        for (HostGroup hg : allGroups) {
            if (hg.groupName.equals(groupName)) {
                return hg;
            }
        }

        return null;
    }
}
