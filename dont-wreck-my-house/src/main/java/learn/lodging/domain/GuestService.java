package learn.lodging.domain;

import learn.lodging.data.GuestFileRepository;
import learn.lodging.models.Guest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestFileRepository repository;

    public GuestService(GuestFileRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findByLastName(String prefix) {
        return repository.findAll().stream()
                .filter(i -> i.getLastName().startsWith(prefix))
                .collect(Collectors.toList());
    }
}
