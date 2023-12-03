package trabalho.persistence;

import com.google.gson.reflect.TypeToken;
import trabalho.model.Usuario;
import trabalho.util.Arquivo;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class UsuarioPersistence implements Persistence<Usuario> {

    private static final String PATH = DIRECTORY + File.separator + "usuarios.json";

    @Override
    public void save(List<Usuario> usuarios) {
        Gson gson = new Gson();
        String json = gson.toJson(usuarios);

        File diretorio = new File(DIRECTORY);
        if (!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Usuario> findAll() {
        Gson gson = new Gson();

        String json = Arquivo.ler(PATH);

        List<Usuario> usuarios = new ArrayList<>();
        if (!json.trim().equals("")) {

            Type tipoLista = new TypeToken<List<Usuario>>() {
            }.getType();
            usuarios = gson.fromJson(json, tipoLista);

            if (usuarios == null)
                usuarios = new ArrayList<>();
        }

        return usuarios;
    }
}