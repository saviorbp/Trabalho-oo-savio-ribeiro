package trabalho.view;

import javax.swing.*;
import java.awt.*;
import trabalho.model.PerfilUsuario;
import trabalho.controller.AdicionarUsuario;

public class TelaUsuario {
    private JFrame frame;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<PerfilUsuario> campoPerfil;

    public TelaUsuario() {
        frame = new JFrame("Cadastro de Usuário");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        JLabel labelPerfil = new JLabel("Perfil:");
        campoPerfil = new JComboBox<>(PerfilUsuario.values());

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.addActionListener(new AdicionarUsuario(this));

        panel.add(labelUsuario);
        panel.add(campoUsuario);
        panel.add(labelSenha);
        panel.add(campoSenha);
        panel.add(labelPerfil);
        panel.add(campoPerfil);
        panel.add(botaoCadastrar);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    public void exibir() {
        frame.setVisible(true);
    }

    public String getNomeUsuario() {
        return campoUsuario.getText();
    }

    public String getSenha() {
        return new String(campoSenha.getPassword());
    }

    public PerfilUsuario getPerfil() {
        return (PerfilUsuario) campoPerfil.getSelectedItem();
    }

    public void adicionarUsuario(String nomeUsuario, String senha, PerfilUsuario perfil) {
        System.out.println("Usuário: " + nomeUsuario);
        System.out.println("Senha: " + senha);
        System.out.println("Perfil: " + perfil);
    }
}