package trabalho.view;

import javax.swing.*;
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
                System.out.println("Usuário: " + campoUsuario.getText());
                System.out.println("Senha: " + new String(campoSenha.getPassword()));
            }
        });
        botaoCriarUsuario = new JButton("Criar Usuário");
        botaoCriarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaUsuario telaUsuario = new TelaUsuario();
                telaUsuario.exibir();
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