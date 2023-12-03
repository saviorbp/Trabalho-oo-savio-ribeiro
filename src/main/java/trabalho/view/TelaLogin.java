package trabalho.view;

import javax.swing.*;

import trabalho.controller.GerenciadorSessao;
import trabalho.model.Usuario;
import trabalho.controller.ValidarUsuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin {
    private JFrame frame;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private JButton botaoCriarUsuario;

    public TelaLogin() {
        frame = new JFrame("Tela de Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        botaoLogin = new JButton("Login");
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                boolean isValido = ValidarUsuario.validar(nomeUsuario, senha);
                if (isValido) {
                    Usuario usuario = new Usuario(nomeUsuario, senha, null);
                    GerenciadorSessao.setUsuarioLogado(usuario);

                    JOptionPane.showMessageDialog(null, "Logado com sucesso", "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Nome de usuário ou senha incorretos", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        botaoCriarUsuario = new JButton("Criar Usuário");
        botaoCriarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCriarUsuario TelaCriarUsuario = new TelaCriarUsuario();
                TelaCriarUsuario.exibir();
                frame.setVisible(false);
            }
        });
        panel.add(labelUsuario);
        panel.add(campoUsuario);
        panel.add(labelSenha);
        panel.add(campoSenha);
        panel.add(botaoLogin);
        panel.add(botaoCriarUsuario);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    public void inicia() {
        frame.setVisible(true);
    }
}