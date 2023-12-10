//Sávio Ribeiro de Barros Pereira                                      
//201976013                                                        
package trabalho.model.Tickets;

public class Subcategoria {
  private Number id;
  private String nome;
  private Categoria categoria;

  public Subcategoria() {
  }

  public Subcategoria(Number id, String nome, Categoria categoria) {
    this.id = id;
    this.nome = nome;
    this.categoria = categoria;
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

  public void setNome(String nomeSubcategoria) {
    this.nome = nomeSubcategoria;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoriaPai) {
    this.categoria = categoriaPai;
  }

}
