package learn.lodging.data;

import learn.lodging.models.Guest;
import learn.lodging.models.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    Host findById(String id);

    Host add(Host host) throws DataException;
}
