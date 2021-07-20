package learn.lodging.data;

import learn.lodging.models.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();
}
