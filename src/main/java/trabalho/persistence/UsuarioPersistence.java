package trabalho.persistence;

import com.google.gson.reflect.TypeToken;

import trabalho.controller.GerenciadorSessao;
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
   
    public Boolean update(Number id, Usuario updatedUsuario) {
        List<Usuario> usuarios = findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                usuario.setNomeUsuario(updatedUsuario.getNomeUsuario());
                usuario.setSenha(updatedUsuario.getSenha());
                usuario.setPerfil(updatedUsuario.getPerfil());
                save(usuarios);
                return true;
            }
        }
        return false;
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

    public Usuario findByName(String nomeUsuario) {
        List<Usuario> usuarios = findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getNomeUsuario().equals(nomeUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> findAllExceptLoggedIn() {
        List<Usuario> allUsers = findAll();
        Usuario loggedInUser = GerenciadorSessao.getUsuarioLogado();

        allUsers.removeIf(usuario -> usuario.getNomeUsuario().equals(loggedInUser.getNomeUsuario()));

        return allUsers;
    }

    public void removeUsuario(Usuario usuario) {
        List<Usuario> usuarios = findAll();
        usuarios.removeIf(u -> u.getNomeUsuario().equals(usuario.getNomeUsuario()));
        save(usuarios);
    }

    public void deletarUsuarioLogado() {
        Usuario usuarioLogado = GerenciadorSessao.getUsuarioLogado();
        removeUsuario(usuarioLogado);
    }

    public int getNextId() {
        List<Usuario> usuarios = findAll();
        int maxId = 0;
        for (Usuario usuario : usuarios) {
            if (usuario.getId().intValue() > maxId) {
                maxId = usuario.getId().intValue();
            }
        }
        return maxId + 1;
    }
}