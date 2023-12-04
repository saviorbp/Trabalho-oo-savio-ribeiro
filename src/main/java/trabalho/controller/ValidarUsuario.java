package trabalho.controller;

import trabalho.exception.ValidacaoException;
import trabalho.model.Usuario;
import trabalho.persistence.UsuarioPersistence;
import java.util.List;

public class ValidarUsuario {
  public static boolean validar(String nomeUsuario, String senha) throws ValidacaoException {
    UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
    List<Usuario> usuarios = usuarioPersistence.findAll();

    for (Usuario usuario : usuarios) {
      if (usuario.getNomeUsuario().equals(nomeUsuario) && usuario.getSenha().equals(senha)) {
        return true;
      }
    }

    throw new ValidacaoException(
        "Usuário não encontrado, verifique o nome de usuário e a senha ou crie um novo usuário");
  }

  public static void validarUsuarioExistente(String nomeUsuario, List<Usuario> usuarios) throws ValidacaoException {
    for (Usuario usuario : usuarios) {
      if (usuario.getNomeUsuario().equals(nomeUsuario)) {
        throw new ValidacaoException("O nome de usuário já está em uso");
      }
    }
  }

  public static void validarNomeUsuario(String nomeUsuario) throws ValidacaoException {
    if (nomeUsuario.length() < 5) {
      throw new ValidacaoException("O nome de usuário deve conter no mínimo 5 caracteres");
    }
  }

  public static void validarSenha(String senha) throws ValidacaoException {
    if (senha.length() < 8 || !senha.matches(".*[A-Z].*") || !senha.matches(".*\\W.*")) {
      throw new ValidacaoException(
          "A senha deve ter pelo menos 8 caracteres, conter pelo menos uma letra maiúscula e um caractere especial");
    }
  }
}