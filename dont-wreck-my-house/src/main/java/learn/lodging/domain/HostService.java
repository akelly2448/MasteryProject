package learn.lodging.domain;

import learn.lodging.data.DataException;
import learn.lodging.data.HostRepository;
import learn.lodging.models.Host;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostService {

    private final HostRepository repository;
    private final ArrayList<String> ALL_STATES_ACRONYMS = new ArrayList<>(List.of("AK", "AL", "AR", "AS", "AZ", "CA", "CO",
            "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME",
            "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR",
            "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"));

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findById(String id){
        return repository.findById(id);
    }

    public List<Host> findByLastName(String prefix) {
        return repository.findAll().stream()
                .filter(i -> i.getLastName().startsWith(prefix))
                .collect(Collectors.toList());
    }

    public Result<Host> add(Host host) throws DataException {
        Result<Host> result = validate(host);
        if (!result.isSuccess()){
            return result;
        }
        host.setPhoneNum(formatPhoneNumber(host.getPhoneNum()));
        result.setPayload(repository.add(host));
        return result;
    }

    private Result<Host> validate(Host host){
        Result<Host> result = validateNulls(host);
        if (!result.isSuccess()){
            return result;
        }
        validateInputs(host,result);
        return result;
    }

    private Result<Host> validateNulls(Host host){
        Result<Host> result = new Result<>();
        if (host == null){
            result.addErrorMessage("Nothing to save.");
            return result;
        }
        if (host.getLastName() == null){
            result.addErrorMessage("Last Name is required.");
        }
        if (host.getEmail() == null){
            result.addErrorMessage("Email Address is required.");
        }
        if (host.getState() == null){
            result.addErrorMessage("State is required.");
        }
        if (host.getCity() == null){
            result.addErrorMessage("City is required.");
        }
        if (host.getAddress() == null){
            result.addErrorMessage("Address is required.");
        }
        if (host.getPostalCode() == null){
            result.addErrorMessage("Postal Code is required.");
        }
        if (host.getPhoneNum() == null){
            result.addErrorMessage("Phone # is required.");
        }
        if (host.getStandardRate() == null){
            result.addErrorMessage("Standard Rate is required.");
        }
        if (host.getWeekendRate() == null){
            result.addErrorMessage("Weekend Rate is required.");
        }
        return result;
    }

    private void validateInputs(Host host, Result<Host> result){
        if (!(host.getEmail().contains("@") && host.getEmail().contains("."))){
            result.addErrorMessage("Email must be a valid email address (containing '@' and '.').");
        }

        if (!ALL_STATES_ACRONYMS.contains(host.getState())){
            result.addErrorMessage("State must be a valid US state acronym.");
        }

        boolean isValidPostal = true;
        for (int i = 0; i < host.getPostalCode().length(); i++){
            if (!Character.isDigit(host.getPostalCode().charAt(i))){
                isValidPostal = false;
            }
        }
        if (!isValidPostal || host.getPostalCode().length() != 5){
            result.addErrorMessage("Postal Code must be valid (5 integers).");
        }

        boolean isValidPhone = true;
        for (int i = 0; i < host.getPhoneNum().length(); i++){
            if (!Character.isDigit(host.getPhoneNum().charAt(i))){
                isValidPhone = false;
            }
        }
        if (!isValidPhone || host.getPhoneNum().length() != 10){
            result.addErrorMessage("Phone # must a valid phone # (10 integers).");
        }

        if (host.getStandardRate().compareTo(BigDecimal.ZERO) < 0){
            result.addErrorMessage("Standard Rate must be positive.");
        }

        if (host.getWeekendRate().compareTo(BigDecimal.ZERO) < 0){
            result.addErrorMessage("Weekend Rate must be positive.");
        }
    }

    private String formatPhoneNumber(String phoneNum){
        String a = phoneNum.substring(0,3);
        String b = phoneNum.substring(3);
        return "("+a+") "+b;
    }

}
