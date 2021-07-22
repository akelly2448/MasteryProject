package learn.lodging.data;

import learn.lodging.models.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository{

    public final static Guest GUEST = makeGuest();
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble(){
        guests.add(GUEST);
    }
    @Override
    public List<Guest> findAll() {
        return guests;
    }

    @Override
    public Guest findById(int id) {
        return guests.stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest add(Guest guest) throws DataException {
        guests.add(guest);
        return guest;
    }

    private static Guest makeGuest(){
        Guest guest = new Guest();
        //set guest credentials
        guest.setId(12);
        guest.setFirstName("Bojack");
        guest.setLastName("Horseman");
        guest.setEmail("bhorseman@website.com");
        guest.setPhoneNum("(333) 2224444");
        guest.setState("CA");
        return guest;
    }
}
