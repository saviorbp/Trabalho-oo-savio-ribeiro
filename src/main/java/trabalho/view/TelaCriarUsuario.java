package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import trabalho.controller.ValidarUsuario;
import trabalho.model.Usuarios.PerfilUsuario;
import trabalho.model.Usuarios.Usuario;
import trabalho.persistence.UsuarioPersistence;

public class TelaCriarUsuario {
    private JFrame frame;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<PerfilUsuario> campoPerfil;

    public TelaCriarUsuario() {
        frame = new JFrame("Cadastro de Usuário");
        frame.setSize(350, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JLabel labelNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField();

        JLabel labelSobrenome = new JLabel("Sobrenome:");
        JTextField campoSobrenome = new JTextField();

        JLabel labelEmail = new JLabel("Email:");
        JTextField campoEmail = new JTextField();

        JLabel labelCpf = new JLabel("CPF:");
        JTextField campoCpf = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();

        JLabel labelPerfil = new JLabel("Perfil:");
        JComboBox<PerfilUsuario> campoPerfil = new JComboBox<>(PerfilUsuario.values());

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaLogin().setVisible(true);
                frame.dispose();
            }
        });
        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
                try {
                    Usuario novoUsuario = new Usuario();
                    novoUsuario.setId(usuarioPersistence.getNextId());
                    novoUsuario.setNome(campoNome.getText());
                    novoUsuario.setSobrenome(campoSobrenome.getText());
                    novoUsuario.setEmail(campoEmail.getText());
                    novoUsuario.setCpf(campoCpf.getText());
                    novoUsuario.setSenha(new String(campoSenha.getPassword()));
                    novoUsuario.setPerfil((PerfilUsuario) campoPerfil.getSelectedItem());

                    ValidarUsuario.validar(novoUsuario);

                    List<Usuario> usuarios = usuarioPersistence.findAll();
                    usuarios.add(novoUsuario);
                    usuarioPersistence.save(usuarios);
                    
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    new TelaLogin().setVisible(true);
                    frame.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        panel.add(labelNome);
        panel.add(campoNome);
        panel.add(labelSobrenome);
        panel.add(campoSobrenome);
        panel.add(labelEmail);
        panel.add(campoEmail);
        panel.add(labelCpf);
        panel.add(campoCpf);
        panel.add(labelSenha);
        panel.add(campoSenha);
        panel.add(labelPerfil);
        panel.add(campoPerfil);
        panel.add(botaoCadastrar);
        panel.add(botaoCancelar);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

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

}