package com.eventbob.ui.admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import com.eventbob.dao.admin.AdminDAO;
import com.eventbob.dto.admin.AdminDTO;
import com.eventbob.ui.applicant.ApplicantEventMain;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminLogin {

	private JFrame frame;
	private JTextField textId;
	private JTextField textPassWord;

	/**
	 * Launch the application.
	 */
	
	
	public void open() {
	
		try {
			AdminLogin window = new AdminLogin();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public AdminLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\uAD00\uB9AC\uC790 \uB85C\uADF8\uC778");
		frame.setBounds(100, 100, 319, 286);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\uC544\uC774\uB514 :");
		label.setFont(new Font("굴림", Font.BOLD, 14));
		label.setBounds(38, 55, 57, 15);
		frame.getContentPane().add(label);
		
		JLabel lblPassword = new JLabel("\uD328\uC2A4\uC6CC\uB4DC :");
		lblPassword.setFont(new Font("굴림", Font.BOLD, 14));
		lblPassword.setBounds(22, 100, 93, 15);
		frame.getContentPane().add(lblPassword);
		
		textId = new JTextField();
		textId.setBounds(107, 53, 149, 21);
		frame.getContentPane().add(textId);
		textId.setColumns(10);
		
		textPassWord = new JTextField();
		textPassWord.setColumns(10);
		textPassWord.setBounds(107, 98, 149, 21);
		frame.getContentPane().add(textPassWord);
		
		JButton buttonLogin = new JButton("\uB85C\uADF8\uC778");
		buttonLogin.addActionListener(new ActionListener() {
			
			// 구현 
			public void actionPerformed(ActionEvent arg0) {
			
				try {
					
				AdminDAO adminDAO = new AdminDAO();
				AdminDTO adminDTO = new AdminDTO();
			
				
				adminDTO.setId(textId.getText());
				adminDTO.setPassword(textPassWord.getText());
		
					
					boolean isExist = adminDAO.isExist(adminDTO);
					
					
				
				if(isExist == true)
				{
					JOptionPane.showMessageDialog(null, "안녕하세요 관리자님 :) ");
					
					AdminEventMain adminEventMain = new AdminEventMain();
					adminEventMain.open();
					
					frame.setVisible(false);
					
				}
						else
						{
							int result =JOptionPane.showConfirmDialog(frame, "아이디 혹은 패스워드가 틀렸습니다 다시 로그인하세요!", null, JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					
						}
					} catch (Exception e) {
					
						e.printStackTrace();
					}
				}
				
		});
		buttonLogin.setBounds(75, 159, 154, 43);
		frame.getContentPane().add(buttonLogin);
	}

}
