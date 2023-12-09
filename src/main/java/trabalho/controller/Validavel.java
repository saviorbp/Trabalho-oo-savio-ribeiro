package trabalho.controller;

import trabalho.exception.ValidacaoException;

public interface Validavel {
  void validar() throws ValidacaoException;
}
