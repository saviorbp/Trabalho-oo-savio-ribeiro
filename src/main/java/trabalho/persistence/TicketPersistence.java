package trabalho.persistence;

import com.google.gson.reflect.TypeToken;
import trabalho.model.Ticket;
import trabalho.util.Arquivo;
import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TicketPersistence implements Persistence<Ticket> {

    private static final String PATH = DIRECTORY + File.separator + "tickets.json";

    @Override
    public void save(List<Ticket> tickets) {
        Gson gson = new Gson();
        String json = gson.toJson(tickets);

        File diretorio = new File(DIRECTORY);
        if (!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Ticket> findAll() {
        Gson gson = new Gson();

        String json = Arquivo.ler(PATH);

        List<Ticket> tickets = new ArrayList<>();
        if (!json.trim().equals("")) {

            Type tipoLista = new TypeToken<List<Ticket>>() {
            }.getType();
            tickets = gson.fromJson(json, tipoLista);

            if (tickets == null)
                tickets = new ArrayList<>();
        }

        return tickets;
    }

    public void update(Ticket ticket) {
        List<Ticket> tickets = findAll();
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getId() == ticket.getId()) {
                tickets.set(i, ticket);
                break;
            }
        }
        save(tickets);
    }

    public int getNextId() {
        List<Ticket> tickets = findAll();
        int maxId = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getId().intValue() > maxId) {
                maxId = ticket.getId().intValue();
            }
        }
        return maxId + 1;
    }
}