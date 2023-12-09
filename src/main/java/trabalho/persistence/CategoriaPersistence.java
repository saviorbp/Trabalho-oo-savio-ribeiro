package trabalho.persistence;

import com.google.gson.reflect.TypeToken;
import trabalho.model.Tickets.Categoria;
import trabalho.util.Arquivo;
import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoriaPersistence implements Persistence<Categoria> {

    private static final String PATH = DIRECTORY + File.separator + "categorias.json";

    @Override
    public void save(List<Categoria> categorias) {
        Gson gson = new Gson();
        String json = gson.toJson(categorias);

        File diretorio = new File(DIRECTORY);
        if (!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Categoria> findAll() {
        Gson gson = new Gson();

        String json = Arquivo.ler(PATH);

        List<Categoria> categorias = new ArrayList<>();
        if (!json.trim().equals("")) {

            Type tipoLista = new TypeToken<List<Categoria>>() {
            }.getType();
            categorias = gson.fromJson(json, tipoLista);

            if (categorias == null)
                categorias = new ArrayList<>();
        }

        return categorias;
    }

    public Boolean update(Number id, Categoria updatedCategoria) {
        List<Categoria> categorias = findAll();
        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(id)) {
                categoria.setNome(updatedCategoria.getNome());
                save(categorias);
                return true;
            }
        }
        return false;
    }

    public int getNextId() {
        List<Categoria> categorias = findAll();
        int maxId = 0;
        for (Categoria categoria : categorias) {
            if (categoria.getId().intValue() > maxId) {
                maxId = categoria.getId().intValue();
            }
        }
        return maxId + 1;
    }
}