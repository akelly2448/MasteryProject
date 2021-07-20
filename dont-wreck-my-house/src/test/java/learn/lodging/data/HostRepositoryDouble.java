package learn.lodging.data;

import learn.lodging.models.Host;

import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository{

    public final static Host HOST = makeHost();
    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble(){
        hosts.add(HOST);
    }
    @Override
    public List<Host> findAll() {
        return null;
    }

    @Override
    public Host findById(String id) {
        return null;
    }

    private static Host makeHost(){
        Host host = new Host();
        //set host credentials
        return host;
    }
}
