package com.eventbob.ui.applicant;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.eventbob.dao.applicant.ApplicantDAO;
import com.eventbob.dao.event.EventDAO;
import com.eventbob.dao.join.JoinDAO;
import com.eventbob.dto.applicant.ApplicantDTO;
import com.eventbob.dto.event.EventDTO;
import com.eventbob.dto.join.JoinDTO;
import com.eventbob.ui.admin.AdminEventContents;
import com.eventbob.util.common.CommonUtil;

import javax.swing.JTable;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ApplicantEventMain {

	private JFrame frmEvent;
	private JTable tableEvent;

	DefaultTableModel tableModel = null;

	int applicantUID;
	String applicantName;
	String applicantTel;
	
	public void open()
	{
		try {
			ApplicantEventMain window = new ApplicantEventMain(applicantUID, applicantName, applicantTel);
			window.frmEvent.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ApplicantEventMain(int applicantUID, String applicantName, String applicantTel) {
		
		this.applicantUID = applicantUID;
		this.applicantName = applicantName;
		this.applicantTel = applicantTel;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEvent = new JFrame();
		frmEvent.setTitle("\uC751\uBAA8\uC790 \uC774\uBCA4\uD2B8 \uCC38\uC5EC");
		frmEvent.setBounds(100, 100, 918, 593);
		frmEvent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEvent.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\uC751\uBAA8\uC790 \uC815\uBCF4");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label.setBounds(34, 10, 97, 30);
		frmEvent.getContentPane().add(label);
		
		JList list = new JList();
		list.setBounds(68, 99, 204, -16);
		frmEvent.getContentPane().add(list);
		
		JButton buttonClose = new JButton("\uB2EB\uAE30");
		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				frmEvent.setVisible(false);
				
			}
		});
		buttonClose.setBounds(343, 462, 172, 37);
		frmEvent.getContentPane().add(buttonClose);
		
		
		JButton ButtonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		ButtonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				tableRefresh();
				
			}
		});
		ButtonRefresh.setBounds(705, 16, 97, 23);
		frmEvent.getContentPane().add(ButtonRefresh);
		
		JLabel lableInfo = new JLabel("");
		lableInfo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lableInfo.setBounds(132, 10, 279, 30);
		frmEvent.getContentPane().add(lableInfo);
		
		lableInfo.setText(applicantName + " (" + applicantTel + ")");
		////////////////////////////////////////
		
		// 헤더
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 50, 768, 396);
		frmEvent.getContentPane().add(scrollPane);


		String Column[] = {"이벤트코드", "이벤트명","시작일", "시작시", "전체수량", "남은수량", "상태"};
		tableModel = new DefaultTableModel(Column, 0);

		tableEvent = new JTable(tableModel);
		scrollPane.setViewportView(tableEvent);
		
		// 마우스 클릭 이벤트
		tableEvent.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = tableEvent.rowAtPoint(evt.getPoint());
				int col = tableEvent.columnAtPoint(evt.getPoint());
				
				if (row >= 0 && col >= 0) {
					
					try
					{
						
						String tableEventUID = tableEvent.getValueAt(row, 0) + "";
						String tableLeftQuantity = tableEvent.getValueAt(row, 5) + "";
						String tableStatus = tableEvent.getValueAt(row, 6) + "";

						int eventUID = Integer.parseInt(tableEventUID);
						
						if(tableStatus.equals("당첨") == true)
						{
							JOptionPane.showMessageDialog(null, "이미 당첨되었습니다.");
						}
						if(tableStatus.equals("꽝") == true)
						{
							JOptionPane.showMessageDialog(null, "이미 응모하셨군요. 꽝입니다!");
						}
						if(tableStatus.equals("신규") == true)
						{
							if(tableLeftQuantity.equals("0") == true)
							{
								JOptionPane.showMessageDialog(null, "이벤트 수량이 소진되었습니다.");
							}
							else
							{
								int random = CommonUtil.getRandom();

								int isWin = 0;
								if(random == 1) // 당첨
								{
									isWin = 1;
									JOptionPane.showMessageDialog(null, "당첨되었습니다. 축하합니다");
								}
								else
								{
									JOptionPane.showMessageDialog(null, "꽝입니다. 다음 기회를 노리세요");
								}
								JoinDAO joinDAO = new JoinDAO();
								joinDAO.insert(eventUID, applicantUID, isWin);
							}
							
							
							tableRefresh();
							
							
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
			EventDAO eventDAO = new EventDAO();	
			JoinDAO joinDAO = new JoinDAO();	
			
			ArrayList<EventDTO> dtoList = new ArrayList<EventDTO>();
			ArrayList<JoinDTO> joinDTOList = new ArrayList<JoinDTO>();
			
			tableModel.setNumRows(0);

			int eventUID;
			String eventName;
			String startDate;
			int startHour;
			int quantity;
			int leftQuantity;
			String status;
			
			dtoList = eventDAO.select();
			
			for (int i = 0; i < dtoList.size(); i++){

				eventUID = dtoList.get(i).getUid();
				eventName = dtoList.get(i).getEventName();
				startDate = dtoList.get(i).getStartDate();
				startHour = dtoList.get(i).getStartHour();
				quantity = dtoList.get(i).getQuantity();

				int checkStartDate = Integer.parseInt(startDate);
				
				// DB 시간과 비교를 위해 현재시간 가져오기
				String currentStartDate = CommonUtil.getDateTime("yyyyMMdd");
				String currentStartHour = CommonUtil.getDateTime("HH");

				int checkcurrentStartDate = Integer.parseInt(currentStartDate);
				int checkcurrentStartHour = Integer.parseInt(currentStartHour);

				// 현재 시간과 DB 시간을 비교
				boolean isEventStart = false;
				if(checkcurrentStartDate == checkStartDate && checkcurrentStartHour > startHour) // 이벤트 시작이라면
					isEventStart = true;
				if(checkcurrentStartDate > checkStartDate)
					isEventStart = true;
				
				if(isEventStart == false)
					continue;
						
						
				// 신규 / 당첨 / 꽝 여부 판별
				joinDTOList = joinDAO.selectByEventAndApplicant(eventUID, applicantUID);
				if(joinDTOList.size() == 0)
					status = "신규";
				else
				{
					if(joinDTOList.get(0).getIsWin() == 1)
						status = "당첨";
					else
						status = "꽝";
				}
				
				// 남은 수량 계산
				int winCount = joinDAO.countWinByEvent(eventUID);
				leftQuantity = quantity - winCount;
				
				Object[] data = {eventUID, eventName, startDate, startHour, quantity, leftQuantity, status};

				tableModel.addRow(data);	
				
			}
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
	}
}
