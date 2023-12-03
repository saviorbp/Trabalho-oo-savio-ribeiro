package trabalho.controller;

import trabalho.model.Usuario;
import trabalho.persistence.UsuarioPersistence;
import java.util.List;

public class ValidarUsuario {
  public static boolean validar(String nomeUsuario, String senha) {
    UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
    List<Usuario> usuarios = usuarioPersistence.findAll();

    for (Usuario usuario : usuarios) {
      if (usuario.getNomeUsuario().equals(nomeUsuario) && usuario.getSenha().equals(senha)) {
        return true;
      }
    }

    return false;
  }

  public static boolean validarNomeUsuario(String nomeUsuario) {
    return nomeUsuario.matches("[a-zA-Z]+");
  }

  public static boolean validarSenha(String senha) {
    return senha.length() >= 8 && senha.matches(".*[A-Z].*") && senha.matches(".*\\W.*");
  }
}