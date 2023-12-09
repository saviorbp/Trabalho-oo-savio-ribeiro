package trabalho.persistence;

import com.google.gson.reflect.TypeToken;

import trabalho.controller.GerenciadorSessao;
import trabalho.model.Usuarios.Usuario;
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
                usuario.setNome(updatedUsuario.getNome());
                usuario.setSobrenome(updatedUsuario.getSobrenome());
                usuario.setEmail(updatedUsuario.getEmail());
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

    public Usuario findByName(String nome) {
        List<Usuario> usuarios = findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario findByEmail(String email) {
        List<Usuario> usuarios = findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario findByCpf(String cpf) {
        List<Usuario> usuarios = findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario findById(Number id) {
        List<Usuario> usuarios = findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> findAllExceptLoggedIn() {
        List<Usuario> allUsers = findAll();
        Usuario loggedInUser = GerenciadorSessao.getUsuarioLogado();

        allUsers.removeIf(usuario -> usuario.getNome().equals(loggedInUser.getNome()));

        return allUsers;
    }

    public void removeUsuario(Usuario usuario) {
        List<Usuario> usuarios = findAll();
        usuarios.removeIf(u -> u.getNome().equals(usuario.getNome()));
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