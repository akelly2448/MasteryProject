package learn.lodging.domain;

import learn.lodging.data.HostFileRepository;
import learn.lodging.models.Host;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostService {

    private final HostFileRepository repository;

    public HostService(HostFileRepository repository) {
        this.repository = repository;
    }

    public List<Host> findByLastName(String prefix) {
        return repository.findAll().stream()
                .filter(i -> i.getLastName().startsWith(prefix))
                .collect(Collectors.toList());
    }
}
