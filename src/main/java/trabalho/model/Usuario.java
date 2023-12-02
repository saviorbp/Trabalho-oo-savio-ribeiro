package trabalho.model;

public class Usuario {

  private String nomeUsuario;
  private String senha;
  private PerfilUsuario perfil;

  public Usuario() {
  }

  public Usuario(String nomeUsuario, String senha, PerfilUsuario perfil) {
    this.nomeUsuario = nomeUsuario;
    this.senha = senha;
    this.perfil = perfil;
  }

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public PerfilUsuario getPerfil() {
    return perfil;
  }

  public void setPerfil(PerfilUsuario perfil) {
    this.perfil = perfil;
  }
}