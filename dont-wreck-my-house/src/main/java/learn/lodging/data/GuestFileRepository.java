package learn.lodging.data;

import learn.lodging.models.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuestFileRepository implements GuestRepository {

    private final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private String filePath;

    public GuestFileRepository(@Value("${guestDataFilePath}")String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll(){
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()){
                String[] fields = line.split(",",-1);

                if (fields.length == 6){
                    result.add(deserialize(fields));
                }
            }

        } catch (IOException e) {
            //ignore
        }
        return result;
    }

    @Override
    public Guest findById(int id){
        List<Guest> all = findAll();
        Guest guest = new Guest();
        for (Guest g: all){
            if (g.getId() == id){
                guest = g;
            }
        }
        return guest;
    }
    /*
    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHostId(reservation.getHostId());
        int nextId = 0;
        for (Reservation r: reservations){
            nextId = Math.max(nextId,r.getId());
        }
        nextId++;
        reservation.setId(nextId);
        reservations.add(reservation);
        writeAll(reservations, reservation.getHostId());
        return reservation;
    }
     */
    @Override
    public Guest add(Guest guest) throws DataException {
        List<Guest> guests = findAll();
        int nextId = 0;
        for (Guest g: guests){
            nextId = Math.max(nextId,g.getId());
        }
        nextId++;
        guest.setId(nextId);
        guests.add(guest);
        writeAll(guests);
        return guest;
    }

    @Override
    public boolean update(Guest guest) throws DataException {
        if (guest == null){
            return false;
        }
        List<Guest> guests = findAll();
        for (int i = 0; i < guests.size(); i++){
            if (guests.get(i).getId() == guest.getId()){
                guests.set(i,guest);
                writeAll(guests);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<Guest> guests) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)){
            writer.println(HEADER);
            for (Guest g: guests){
                writer.println(serialize(g));
            }
        }catch (FileNotFoundException ex){
            throw new DataException(ex);
        }
    }

    private String serialize(Guest guest){
        return String.format("%s,%s,%s,%s,%s,%s",
                guest.getId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getEmail(),
                guest.getPhoneNum(),
                guest.getState());
    }

    private Guest deserialize(String[] fields){
        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[0]));
        guest.setFirstName(fields[1]);
        guest.setLastName(fields[2]);
        guest.setEmail(fields[3]);
        guest.setPhoneNum(fields[4]);
        guest.setState(fields[5]);
        return guest;
    }
}
