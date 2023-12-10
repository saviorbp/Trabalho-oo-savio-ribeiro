//Sávio Ribeiro de Barros Pereira                                
//201976013                                                      
package trabalho.controller;

import trabalho.model.Usuarios.PerfilUsuario;
import trabalho.model.Usuarios.Usuario;

public class GerenciadorSessao {
    private static Usuario usuarioLogado;

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static Integer getIdUsuarioLogado() {
        return usuarioLogado.getId();
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }

    public static boolean isUsuarioLogado() {
        return usuarioLogado != null;
    }

    public static PerfilUsuario getPerfilUsuarioLogado() {
        return usuarioLogado.getPerfil();
    }
}
