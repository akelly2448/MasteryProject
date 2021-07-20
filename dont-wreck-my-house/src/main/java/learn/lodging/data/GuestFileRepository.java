package learn.lodging.data;

import learn.lodging.models.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
