//Sávio Ribeiro de Barros Pereira                                         
//201976013                                                            
package trabalho.model.Usuarios;

public class Usuario {

  private Integer id;
  private String nome;
  private String sobrenome;
  private String cpf;
  private String email;
  private String senha;
  private PerfilUsuario perfil;

  public Usuario(Integer id, String senha, PerfilUsuario perfil, String nome, String sobrenome, String email,
      String cpf) {
    this.id = id;
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.cpf = cpf;
    this.email = email;
    this.senha = senha;
    this.perfil = perfil;
  }

  public Usuario() {
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PerfilUsuario getPerfil() {
    return perfil;
  }

  public void setPerfil(PerfilUsuario perfil) {
    this.perfil = perfil;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setSobrenome(String sobrenome) {
    this.sobrenome = sobrenome;
  }

  public String getSobrenome() {
    return sobrenome;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getCpf() {
    return cpf;
  }

}