package trabalho.controller;

import trabalho.model.Usuario;

public class ValidaTicket {

  public static void validaTitulo(String titulo) {
    if (titulo == null || titulo.trim().isEmpty()) {
      throw new IllegalArgumentException("O título do ticket não pode ser vazio");
    }
  }

  public static void validaDescricao(String descricao) {
    if (descricao == null || descricao.trim().isEmpty()) {
      throw new IllegalArgumentException("A descrição do ticket não pode ser vazia");
    }
  }

  public static void validaUsuario(Usuario usuario) {
    if (usuario == null) {
      throw new IllegalArgumentException("O usuário do ticket não pode ser em vazia");
    }
  }

  public static void ticketNaoEncontrado(Number id) {
    if (id == null) {
      throw new IllegalArgumentException("Ticket não encontrado");
    }
    return;
  }
  public static void editarTicketError(String ex) {
    if (ex != null) {
      throw new IllegalArgumentException("Erro ao editar o ticket" + ex);
    }
    return;
  }
}
