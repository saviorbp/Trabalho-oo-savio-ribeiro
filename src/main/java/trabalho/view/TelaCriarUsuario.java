package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import trabalho.controller.ValidarUsuario;
import trabalho.model.PerfilUsuario;
import trabalho.model.Usuario;
import trabalho.persistence.UsuarioPersistence;

public class TelaCriarUsuario {
    private JFrame frame;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<PerfilUsuario> campoPerfil;

    public TelaCriarUsuario() {
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
        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
                String nomeUsuario = campoUsuario.getText();
                String senha = new String(campoSenha.getPassword());

                if (!ValidarUsuario.validarNomeUsuario(nomeUsuario)) {
                    JOptionPane.showMessageDialog(null, "O nome de usuário deve conter apenas letras", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!ValidarUsuario.validarSenha(senha)) {
                    JOptionPane.showMessageDialog(null,
                            "A senha deve ter pelo menos 8 caracteres, conter pelo menos uma letra maiúscula e um caractere especial",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Usuario usuario = new Usuario();
                usuario.setNomeUsuario(campoUsuario.getText());
                usuario.setSenha(new String(campoSenha.getPassword()));
                usuario.setPerfil((PerfilUsuario) campoPerfil.getSelectedItem());

                List<Usuario> usuarios = usuarioPersistence.findAll();
                usuarios.add(usuario);

                usuarioPersistence.save(usuarios);

                campoUsuario.setText("");
                campoSenha.setText("");
                campoPerfil.setSelectedIndex(0);

                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

                frame.dispose();

                TelaLogin telaLogin = new TelaLogin();
                telaLogin.inicia();
                frame.setVisible(false);

            }
        });

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