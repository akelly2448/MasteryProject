package learn.lodging.data;

import learn.lodging.models.Host;

import java.math.BigDecimal;
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
        return hosts;
    }

    @Override
    public Host findById(String id) {
        return hosts.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host add(Host host) throws DataException {
        hosts.add(host);
        return host;
    }

    private static Host makeHost(){
        Host host = new Host();
        //set host credentials
        host.setId("test-host-id");
        host.setLastName("Kelly");
        host.setEmail("akelly@website.com");
        host.setPhoneNum("(215) 3334444");
        host.setAddress("10 Alex Way");
        host.setCity("Philly");
        host.setState("PA");
        host.setPostalCode("19840");
        host.setStandardRate(new BigDecimal("100.00"));
        host.setWeekendRate(new BigDecimal("200.00"));
        return host;
    }
}
