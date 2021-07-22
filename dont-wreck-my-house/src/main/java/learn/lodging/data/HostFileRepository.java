package learn.lodging.data;

import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HostFileRepository implements HostRepository {

    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String filePath;

    public HostFileRepository (@Value("${hostDataFilePath}")String filePath){
        this.filePath = filePath;
    }

    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);

                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }

        } catch (IOException e) {
            //ignore
        }
        return result;
    }

    @Override
    public Host findById(String id){
        List<Host> all = findAll();
        Host host = new Host();
        for (Host h: all){
            if (h.getId().equals(id)){
                host = h;
            }
        }
        return host;
    }

    @Override
    public Host add(Host host) throws DataException {
        List<Host> hosts = findAll();
        host.setId(java.util.UUID.randomUUID().toString());
        hosts.add(host);
        writeAll(hosts);
        return host;
    }

    private void writeAll(List<Host> hosts) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)){
            writer.println(HEADER);
            for (Host h: hosts){
                writer.println(serialize(h));
            }
        }catch (FileNotFoundException ex){
            throw new DataException(ex);
        }

    }

    private String serialize(Host host){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                host.getId(),
                host.getLastName(),
                host.getEmail(),
                host.getPhoneNum(),
                host.getAddress(),
                host.getCity(),
                host.getState(),
                host.getPostalCode(),
                host.getStandardRate(),
                host.getWeekendRate());
    }

    private Host deserialize(String[] fields){
        Host host = new Host();
        host.setId(fields[0]);
        host.setLastName(fields[1]);
        host.setEmail(fields[2]);
        host.setPhoneNum(fields[3]);
        host.setAddress(fields[4]);
        host.setCity(fields[5]);
        host.setState(fields[6]);
        host.setPostalCode(fields[7]);
        host.setStandardRate(new BigDecimal(fields[8]));
        host.setWeekendRate(new BigDecimal(fields[9]));

        return host;
    }


}
