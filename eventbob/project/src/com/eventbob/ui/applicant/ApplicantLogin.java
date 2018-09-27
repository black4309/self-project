package com.eventbob.ui.applicant;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.eventbob.dao.applicant.ApplicantDAO;
import com.eventbob.dto.applicant.ApplicantDTO;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ApplicantLogin {

	private JFrame frame;
	private JTextField textName;
	private JTextField textTel;

	
	
	public void open()
	{
		try {
			ApplicantLogin window = new ApplicantLogin();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ApplicantLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\uC774\uBCA4\uD2B8 \uC751\uBAA8 ");
		frame.setBounds(100, 100, 359, 267);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("\uC774\uB984 :");
		lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(lblNewLabel.getFont().getStyle() | Font.BOLD));
		lblNewLabel.setToolTipText("\u314E\u314E\u314E");
		lblNewLabel.setBounds(51, 46, 57, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel label = new JLabel("\uC5F0\uB77D\uCC98 :");
		label.setFont(new Font("굴림", Font.BOLD, 12));
		label.setBounds(36, 95, 57, 23);
		frame.getContentPane().add(label);

		textName = new JTextField();
		textName.setBounds(114, 38, 205, 31);
		frame.getContentPane().add(textName);
		textName.setColumns(10);

		textTel = new JTextField();
		textTel.setColumns(10);
		textTel.setBounds(114, 91, 205, 31);
		frame.getContentPane().add(textTel);

		JButton buttonLogin = new JButton("\uCC38\uC5EC");
		buttonLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try
				{
					ApplicantDAO applicantDAO = new ApplicantDAO();
					ApplicantDTO applicantDTO = new ApplicantDTO();

					applicantDTO.setName(textName.getText());
					applicantDTO.setTel(textTel.getText());

					boolean isExist = applicantDAO.isExist(applicantDTO);

					if(isExist == true)
					{
						JOptionPane.showMessageDialog(null, "참여한 이력이 존재합니다 ! 응모화면으로 이동합니다 ");
						
						ArrayList<ApplicantDTO> dtoList = applicantDAO.selectByNameAndTel(textName.getText(), textTel.getText());
						
						int uid = dtoList.get(0).getUid();
						String name = dtoList.get(0).getName();
						String tel = dtoList.get(0).getTel();
						
						ApplicantEventMain applicantEventMain = new ApplicantEventMain(uid, name, tel);
						applicantEventMain.open();
						
						frame.setVisible(false);
						
					}
					if(isExist == false)
					{
						int result = JOptionPane.showConfirmDialog(null, "새로운 응모자시군요. 정보를 등록하시겠습니까?", "응모자 등록", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						if (result == JOptionPane.YES_OPTION)
						{
							applicantDAO.insert(applicantDTO);
							
							ArrayList<ApplicantDTO> dtoList = applicantDAO.selectByNameAndTel(textName.getText(), textTel.getText());
							
							int uid = dtoList.get(0).getUid();
							String name = dtoList.get(0).getName();
							String tel = dtoList.get(0).getTel();
							
							ApplicantEventMain applicantEventMain = new ApplicantEventMain(uid, name, tel);
							applicantEventMain.open();
							
							frame.setVisible(false);
						}
						else
						{

						}

					}

				}
				catch (Exception e)
				{

				}

			}
		});
		buttonLogin.setBounds(114, 169, 125, 31);
		frame.getContentPane().add(buttonLogin);
	}
}
