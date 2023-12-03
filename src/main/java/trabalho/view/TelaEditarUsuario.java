package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import trabalho.model.Usuario;
import trabalho.controller.GerenciadorSessao;
import trabalho.model.PerfilUsuario;
import trabalho.persistence.UsuarioPersistence;

public class TelaEditarUsuario {
    private JFrame frame;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<PerfilUsuario> campoPerfil;

    public TelaEditarUsuario() {
        Usuario usuarioLogado = GerenciadorSessao.getUsuarioLogado();

        frame = new JFrame("Edição de Usuário");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        campoUsuario = new JTextField(usuarioLogado.getNomeUsuario());
        campoSenha = new JPasswordField(usuarioLogado.getSenha());
        campoPerfil = new JComboBox<>(PerfilUsuario.values());
        campoPerfil.setSelectedItem(usuarioLogado.getPerfil());
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}