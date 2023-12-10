//Sávio Ribeiro de Barros Pereira                                
//201976013                                                     
package trabalho.model.Tickets;

import java.util.List;

public class Categoria {
  private Number id;
  private String nome;

  public List<String> getSubcategorias() {
    return this.subcategorias;
  }

  private List<String> subcategorias;

  public Categoria() {
  }

  public Categoria(int id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public Categoria(int id, String nome, List<String> subcategorias) {
    this.id = id;
    this.nome = nome;
    this.subcategorias = subcategorias;
  }

  public Number getId() {
    return id;
  }

  public void setId(Number id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @Override
  public String toString() {
    return this.nome;
  }
}
