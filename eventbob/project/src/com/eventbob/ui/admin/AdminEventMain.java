package com.eventbob.ui.admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.eventbob.dao.event.EventDAO;
import com.eventbob.dao.join.JoinDAO;
import com.eventbob.dto.event.EventDTO;
import com.eventbob.util.common.CommonUtil;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class AdminEventMain{

	private JFrame frame;
	private JTextField textEventName;
	private JLabel label_1;
	private JTextField textStartDate;
	private JLabel label_2;
	private JTextField textStartHour;
	private JLabel label_3;
	private JTextField textQuantity;
	private JButton buttonData;
	private JLabel lblNewLabel;
	private JLabel label_4;
	private JScrollPane scrollPane;
	private JTable tableEvent;
	DefaultTableModel tableModel = null;

	int buttonDataEventUID = 0;
	
	
	
	public void open()
	{
		try {
			AdminEventMain window = new AdminEventMain();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public AdminEventMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\uAD00\uB9AC\uC790 \uD398\uC774\uC9C0");
		frame.setBounds(100, 100, 702, 616);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("\uC774\uBCA4\uD2B8\uBA85 :");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label.setBounds(29, 42, 72, 15);
		frame.getContentPane().add(label);

		textEventName = new JTextField();
		textEventName.setBounds(113, 40, 421, 21);
		frame.getContentPane().add(textEventName);
		textEventName.setColumns(10);

		label_1 = new JLabel("\uB0A0\uC9DC :");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_1.setBounds(58, 71, 49, 19);
		frame.getContentPane().add(label_1);

		textStartDate = new JTextField();
		textStartDate.setBounds(113, 71, 84, 21);
		frame.getContentPane().add(textStartDate);
		textStartDate.setColumns(10);

		label_2 = new JLabel("\uC2DC\uAC01(\uC2DC) :");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_2.setBounds(209, 73, 72, 15);
		frame.getContentPane().add(label_2);

		textStartHour = new JTextField();
		textStartHour.setBounds(293, 71, 84, 21);
		frame.getContentPane().add(textStartHour);
		textStartHour.setColumns(10);

		label_3 = new JLabel("\uC218\uB7C9 :");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_3.setBounds(389, 73, 49, 15);
		frame.getContentPane().add(label_3);

		textQuantity = new JTextField();
		textQuantity.setBounds(450, 71, 84, 21);
		frame.getContentPane().add(textQuantity);
		textQuantity.setColumns(10);

		buttonData = new JButton("\uB4F1\uB85D");
		buttonData.addActionListener(new ActionListener() {

			// 등록/수정 버튼 누를시 발생 

			public void actionPerformed(ActionEvent arg0) {

				try
				{
					EventDAO eventDAO = new EventDAO();
					EventDTO eventDTO = new EventDTO();
					
					if(buttonData.getText().equals("수정")) // 수정
					{
						eventDTO.setEventName(textEventName.getText());
						eventDTO.setStartDate(textStartDate.getText());
						eventDTO.setStartHour(Integer.parseInt(textStartHour.getText()));
						eventDTO.setQuantity(Integer.parseInt(textQuantity.getText()));
						eventDTO.setUid(buttonDataEventUID);

						eventDAO.update(eventDTO);
						
						tableRefresh();
						
						buttonData.setText("등록");

					}
					else // 등록
					{
						
						eventDTO.setEventName(textEventName.getText());
						eventDTO.setStartDate(textStartDate.getText());
						eventDTO.setStartHour(Integer.parseInt(textStartHour.getText()));
						eventDTO.setQuantity(Integer.parseInt(textQuantity.getText()));

						eventDAO.insert(eventDTO); 
						tableRefresh();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

			}
		});
		buttonData.setBounds(546, 39, 97, 51);
		frame.getContentPane().add(buttonData);

		lblNewLabel = new JLabel("* \uC774\uBCA4\uD2B8 \uC2DC\uC791 \uC804\uC5D0\uB9CC  \uC218\uC815\uC0AD\uC81C \uAC00\uB2A5 \uD569\uB2C8\uB2E4!  ");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblNewLabel.setBounds(29, 467, 348, 15);
		frame.getContentPane().add(lblNewLabel);

		label_4 = new JLabel("* \uC774\uBCA4\uD2B8 \uC2DC\uC791 \uD6C4\uC5D0\uB294 \uB2F9\uCCA8\uC790 \uB9AC\uC2A4\uD2B8\uAC00 \uCD9C\uB825\uB429\uB2C8\uB2E4");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_4.setBounds(29, 492, 378, 15);
		frame.getContentPane().add(label_4);

		JButton buttonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				tableRefresh();

			}
		});
		buttonRefresh.setBounds(546, 449, 97, 23);
		frame.getContentPane().add(buttonRefresh);


		///////////////// JTABLE ///////////

		scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 124, 617, 315);
		frame.getContentPane().add(scrollPane);




		// 헤더
		String Column[] = {"코드","이벤트명","시작일", "시작(시)", "전체수량", "남은수량"};
		tableModel = new DefaultTableModel(Column, 0);

		tableEvent = new JTable(tableModel);

		scrollPane.setViewportView(tableEvent);

		tableEvent.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = tableEvent.rowAtPoint(evt.getPoint());
				int col = tableEvent.columnAtPoint(evt.getPoint());
				

				if (row >= 0 && col >= 0) {

					try
					{
						String tableEventUID = tableEvent.getValueAt(row, 0) + "";
						String tableEventName = tableEvent.getValueAt(row, 1) + "";
						
						String tableStartDate = tableEvent.getValueAt(row, 2) + "";
						String tableStartHour = tableEvent.getValueAt(row, 3) + "";
						String tableQuantity = tableEvent.getValueAt(row, 4) + "";
						int eventUID = Integer.parseInt(tableEventUID);

						// DB 시간이 String 이므로 int 로 변환
						int checkStartDate = Integer.parseInt(tableStartDate);
						int checkStartHour = Integer.parseInt(tableStartHour);

						// DB 시간과 비교를 위해 현재시간 가져오기
						String currentStartDate = CommonUtil.getDateTime("yyyyMMdd");
						String currentStartHour = CommonUtil.getDateTime("HH");

						int checkcurrentStartDate = Integer.parseInt(currentStartDate);
						int checkcurrentStartHour = Integer.parseInt(currentStartHour);
						
						// 현재 시간과 DB 시간을 비교
						boolean isEventStart = false;
						if(checkcurrentStartDate == checkStartDate && checkcurrentStartHour > checkStartHour) // 이벤트 시작이라면
							isEventStart = true;
						if(checkcurrentStartDate > checkStartDate)
							isEventStart = true;
						
						if(isEventStart == false) // 이벤트 시작일/시작시 전이라면 수정/삭제 가능
						{
							String[] buttons = {"수정", "삭제", "닫기"};
							int result = JOptionPane.showOptionDialog(null, "이벤트 정보를 수정하시겠습니까?", "정보 수정", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째값");


							EventDAO eventDAO = new EventDAO();
							EventDTO eventDTO = new EventDTO();

							if(result == 0) // 수정
							{
								buttonDataEventUID = Integer.parseInt(tableEventUID);
								textEventName.setText(tableEventName);
								textStartDate.setText(tableStartDate);
								textStartHour.setText(tableStartHour);
								textQuantity.setText(tableQuantity);

								buttonData.setText("수정");
							}
							if(result == 1) // 삭제
							{
								eventDAO.delete(eventUID);
								tableRefresh();
							}
						}
						else // 이벤트 시작일/시작시 후이라면 담청자 보여주기
						{
							AdminEventContents adminEventContents = new AdminEventContents(tableEventName, eventUID);
							adminEventContents.open();
						}

					}catch(Exception e)
					{
						e.printStackTrace();
					}



				}
			}
		});

		tableRefresh();
		////////////////////////////////////

	}

	public void tableRefresh()
	{
		try
		{
			// 값
			ArrayList<EventDTO> dtoList = new ArrayList<EventDTO>();
			EventDAO eventDao = new EventDAO();
			JoinDAO joinDAO = new JoinDAO();
			
			dtoList = eventDao.select();

			tableModel.setNumRows(0);

			for (int i = 0; i < dtoList.size(); i++){

				int uid = dtoList.get(i).getUid();
				String eventName = dtoList.get(i).getEventName();
				String startDate = dtoList.get(i).getStartDate();
				int startHour = dtoList.get(i).getStartHour();
				int quantity = dtoList.get(i).getQuantity();

				// 남은 수량 계산
				int winCount = joinDAO.countWinByEvent(uid);
				int leftQuantity = quantity - winCount;
				
				Object[] data = {uid, eventName, startDate, startHour, quantity, leftQuantity};

				tableModel.addRow(data);	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
