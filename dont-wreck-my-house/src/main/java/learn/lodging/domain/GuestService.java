package learn.lodging.domain;

import learn.lodging.data.DataException;
import learn.lodging.data.GuestRepository;
import learn.lodging.models.Guest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestRepository repository;
    private final ArrayList<String> ALL_STATES_ACRONYMS = new ArrayList<>(List.of("AK", "AL", "AR", "AS", "AZ", "CA", "CO",
            "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME",
            "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR",
            "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"));

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findById(int id){
        return repository.findById(id);
    }

    public List<Guest> findByLastName(String prefix) {
        return repository.findAll().stream()
                .filter(i -> i.getLastName().startsWith(prefix))
                .collect(Collectors.toList());
    }

    public Result<Guest> add(Guest guest) throws DataException {
        Result<Guest> result = validate(guest);
        if (!result.isSuccess()){
            return result;
        }
        guest.setPhoneNum(formatPhoneNumber(guest.getPhoneNum()));
        result.setPayload(repository.add(guest));
        return result;
    }

    public Result<Guest> update(Guest guest) throws DataException {
        Result<Guest> result = validate(guest);
        if (!result.isSuccess()){
            return result;
        }
        List<Guest> guests = repository.findAll();
        Guest existing = null;
        for (Guest g: guests){
            if (g.getId() == guest.getId()){
                existing = g;
            }
        }
        if (existing == null){
            result.addErrorMessage("Guest not found.");
            return result;
        }
        guest.setPhoneNum(formatPhoneNumber(guest.getPhoneNum()));
        boolean success = repository.update(guest);
        if (!success){
            result.addErrorMessage("Guest not found.");
        }
        return result;
    }

    private Result<Guest> validate(Guest guest){
        Result<Guest> result = validateNulls(guest);
        if (!result.isSuccess()){
            return result;
        }
        validateInputs(guest,result);

        return result;
    }

    private Result<Guest> validateNulls(Guest guest){
        Result<Guest> result = new Result<>();
        if (guest == null){
            result.addErrorMessage("Nothing to save.");
            return result;
        }
        if (guest.getFirstName() == null){
            result.addErrorMessage("First Name is required.");
        }
        if (guest.getLastName() == null){
            result.addErrorMessage("Last Name is required.");
        }
        if (guest.getEmail() == null){
            result.addErrorMessage("Email Address is required.");
        }
        if (guest.getPhoneNum() == null){
            result.addErrorMessage("Phone # is required.");
        }
        if (guest.getState() == null){
            result.addErrorMessage("State is required.");
        }
        return result;
    }

    private void validateInputs(Guest guest, Result<Guest> result){

        if (!(guest.getEmail().contains("@") && guest.getEmail().contains("."))){
            result.addErrorMessage("Email must be a valid email address (containing '@' and '.').");
        }

        boolean isValidNum = true;
        for (int i = 0; i < guest.getPhoneNum().length(); i++){
            if (!Character.isDigit(guest.getPhoneNum().charAt(i))){
                isValidNum = false;
            }
        }
        if (!isValidNum || guest.getPhoneNum().length() != 10){
            result.addErrorMessage("Phone # must a valid phone #.");
        }

        if (!ALL_STATES_ACRONYMS.contains(guest.getState())){
            result.addErrorMessage("State must be a valid US state acronym.");
        }

    }

    private String formatPhoneNumber(String phoneNum){
        String a = phoneNum.substring(0,3);
        String b = phoneNum.substring(3);
        return "("+a+") "+b;
    }

    //check for unique?
    /*
        private void validateUnique(Forager forager, Result<Forager> result){
        List<Forager> all = repository.findAll();

        for (Forager f: all){
            if (f.getFirstName().equals(forager.getFirstName()) &&
                f.getLastName().equals(forager.getLastName()) &&
                f.getState().equals(forager.getState())){

                result.addErrorMessage("Forager already exists.");
            }
        }

    }
     */
}
