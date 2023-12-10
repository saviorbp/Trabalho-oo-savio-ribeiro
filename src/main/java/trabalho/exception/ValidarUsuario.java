//Sávio Ribeiro de Barros Pereira                                        
//201976013                                                               
package trabalho.exception;

import trabalho.model.Usuarios.Usuario;
import trabalho.persistence.UsuarioPersistence;
import java.util.List;

public class ValidarUsuario {
  public static boolean validarLogin(String email, String senha) throws ValidacaoException {
    UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
    List<Usuario> usuarios = usuarioPersistence.findAll();

    for (Usuario usuarioExistente : usuarios) {
      String emailExistente = usuarioExistente.getEmail();
      String senhaExistente = usuarioExistente.getSenha();
      if (emailExistente != null && emailExistente.equals(email) && senhaExistente != null
          && senhaExistente.equals(senha)) {
        return true;
      }
    }

    throw new ValidacaoException(
        "Usuário não encontrado, verifique o nome de usuário e a senha ou crie um novo usuário");
  }

  public static void validar(Usuario usuario) throws ValidacaoException {
    UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
    List<Usuario> usuarios = usuarioPersistence.findAll();

    validarNome(usuario.getNome());
    validarSobrenome(usuario.getSobrenome());
    validarEmail(usuario.getEmail());
    validarCpf(usuario.getCpf());
    validarSenha(usuario.getSenha());
    validarEmailExistente(usuario.getEmail(), usuarios);
    validarCpfExistente(usuario.getCpf(), usuarios);
  }

  public static void validarEdicao(Usuario usuario, List<Usuario> usuarios) throws ValidacaoException {
    validarNome(usuario.getNome());
    validarSobrenome(usuario.getSobrenome());
    validarEmail(usuario.getEmail());
    validarCpf(usuario.getCpf());
    validarSenha(usuario.getSenha());
    validarEmailExistente(usuario.getEmail(), usuarios);
    validarCpfExistente(usuario.getCpf(), usuarios);
  }

  public static void validarEmailExistente(String email, List<Usuario> usuarios) throws ValidacaoException {
    for (Usuario usuario : usuarios) {
      if (usuario.getEmail().equals(email)) {
        throw new ValidacaoException("Email já existe");
      }
    }
  }

  public static void validarCpfExistente(String cpf, List<Usuario> usuarios) throws ValidacaoException {
    for (Usuario usuario : usuarios) {
      if (usuario.getCpf().equals(cpf)) {
        throw new ValidacaoException("CPF já existe");
      }
    }
  }

  public static void validarNome(String nome) throws ValidacaoException {
    if (!nome.matches("^[a-zA-Z\\s]*$")) {
      throw new ValidacaoException("Nome deve conter apenas letras");
    }
    if (nome.trim().equals("")) {
      throw new ValidacaoException("Nome não pode ser vazio");
    }
  }

  public static void validarSobrenome(String sobrenome) throws ValidacaoException {
    if (!sobrenome.matches("^[a-zA-Z\\s]*$")) {
      throw new ValidacaoException("Sobrenome deve conter apenas letras");
    }
    if (sobrenome.trim().equals("")) {
      throw new ValidacaoException("Sobrenome não pode ser vazio");
    }
  }

  public static void validarEmail(String email) throws ValidacaoException {
    if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
      throw new ValidacaoException("Email inválido");
    }
    if (email.trim().equals("")) {
      throw new ValidacaoException("Email não pode ser vazio");
    }
  }

  public static void validarCpf(String cpf) throws ValidacaoException {
    if (!cpf.matches("^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}$")) {
      throw new ValidacaoException("CPF inválido");
    }
    if (cpf.trim().equals("")) {
      throw new ValidacaoException("CPF não pode ser vazio");
    }
  }

  public static void validarSenha(String senha) throws ValidacaoException {
    if (senha.length() < 8) {
      throw new ValidacaoException("Senha deve ter no mínimo 8 caracteres");
    }
    if (!senha.matches(".*[0-9].*")) {
      throw new ValidacaoException("Senha deve conter pelo menos um número");
    }
    if (!senha.matches(".*[a-z].*")) {
      throw new ValidacaoException("Senha deve conter pelo menos uma letra minúscula");
    }
    if (!senha.matches(".*[A-Z].*")) {
      throw new ValidacaoException("Senha deve conter pelo menos uma letra maiúscula");
    }
    if (!senha.matches(".*[!@#$%^&*()-+].*")) {
      throw new ValidacaoException("Senha deve conter pelo menos um caractere especial");
    }
  }
}