package com.eventbob.ui.admin;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.eventbob.dao.applicant.ApplicantDAO;
import com.eventbob.dao.event.EventDAO;
import com.eventbob.dao.join.JoinDAO;
import com.eventbob.dto.applicant.ApplicantDTO;
import com.eventbob.dto.event.EventDTO;
import com.eventbob.dto.join.JoinDTO;
import com.eventbob.util.common.CommonUtil;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AdminEventContents {

	private JFrame frame;
	private JTable tableEvent;

	String eventName;
	int eventUID;
	
	DefaultTableModel tableModel = null;
	
	public void open()
	{
		try {
			AdminEventContents window = new AdminEventContents(eventName, eventUID);
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the application.
	 */
	public AdminEventContents(String eventName, int eventUID) {
		
		this.eventName = eventName;
		this.eventUID = eventUID;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("\uB2F9\uCCA8\uC790 \uB9AC\uC2A4\uD2B8");
		frame.setBounds(100, 100, 428, 609);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uC774\uBCA4\uD2B8\uBA85");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel.setBounds(22, 26, 69, 49);
		frame.getContentPane().add(lblNewLabel);
		
		JButton buttonClose = new JButton("\uB2EB\uAE30");
		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});
		
		buttonClose.setForeground(UIManager.getColor("Button.light"));
		buttonClose.setBounds(149, 497, 127, 37);
		frame.getContentPane().add(buttonClose);
		
		//// J-TABLE
		
		///////////

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 103, 359, 372);
		frame.getContentPane().add(scrollPane);

		// 헤더
		String Column[] = {"이름","연락처", "당첨시각"};
		tableModel = new DefaultTableModel(Column, 0);

		tableEvent = new JTable(tableModel);
		scrollPane.setViewportView(tableEvent);

		scrollPane.setViewportView(tableEvent);
		
		tableRefresh();
		////////////////////////////////////
		
		
		JLabel labelEventName = new JLabel("");
		labelEventName.setFont(new Font("굴림", Font.BOLD, 15));
		labelEventName.setBounds(103, 26, 210, 49);
		frame.getContentPane().add(labelEventName);
		
		System.out.println(eventName);
		labelEventName.setText(eventName);
		
	}
	
	
	public void tableRefresh()
	{
		try
		{
			// 값
			JoinDAO joinDAO = new JoinDAO();
			ApplicantDAO applicantDAO = new ApplicantDAO();
			
			ArrayList<JoinDTO> dtoList = new ArrayList<JoinDTO>();
			ArrayList<ApplicantDTO> dtoApplicantList = new ArrayList<ApplicantDTO>();
			
			dtoList = joinDAO.selectByEvent(eventUID);

			tableModel.setNumRows(0);

			for (int i = 0; i < dtoList.size(); i++){

				int isWin = dtoList.get(i).getIsWin();
				if(isWin == 0)
					continue;
				
				int applicantUID = dtoList.get(i).getApplicantUID();
				dtoApplicantList = applicantDAO.selectByUID(applicantUID);
				
				String name = dtoApplicantList.get(0).getName();
				String tel = dtoApplicantList.get(0).getTel();

				
				String joinTime = dtoList.get(i).getJointime();
				
				Object[] data = {name, tel, joinTime};

				tableModel.addRow(data);	
				
			}
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
	}
}
