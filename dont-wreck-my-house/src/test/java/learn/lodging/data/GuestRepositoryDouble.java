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
        return null;
    }

    @Override
    public Guest findById(int id) {
        return null;
    }

    private static Guest makeGuest(){
        Guest guest = new Guest();
        //set guest credentials
        return guest;
    }
}
