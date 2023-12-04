package trabalho.controller;
import trabalho.model.Usuario;
public class GerenciadorSessao {
    private static Usuario usuarioLogado;

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }

    public static boolean isUsuarioLogado() {
        return usuarioLogado != null;
    }
}
