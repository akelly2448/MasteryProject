package learn.lodging.domain;

import learn.lodging.data.HostRepositoryDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service;

    @BeforeEach
    void setup(){
        HostRepositoryDouble repository = new HostRepositoryDouble();
        service = new HostService(repository);
    }



    @Test
    void findById() {
    }

    @Test
    void findByLastName() {
    }
}