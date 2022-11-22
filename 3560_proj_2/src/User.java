import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class User extends Node{
	private JFrame _userFrame;
	private JTextField _txtUsrFollow;
	private JTextField _txtUserId;
	private JTextField _txtFollowing;
	private JTextField _txtMsgEntry;
	private JTextField _txtMessage;
	private JTextField _txtNewsFeed;
	private DefaultListModel<String> _feedModel = new DefaultListModel<String>();
	private JList<String> _lstNewsFeed = new JList<String>(_feedModel);
	private DefaultListModel<String> _followingModel = new DefaultListModel<String>();
	private JList<String> _followingList = new JList<String>(_followingModel);
	private List<String>  _following = new ArrayList<String>();
	private List<User>  _followers = new ArrayList<User>();
	private List<Post> _feed = new ArrayList<Post>();

	public User(String id){
		super._id = id;
	}
	
	
	public void displayProfile() {
		displayBase();
		followUserUI();
		lstFollowing();
		msgPost();
		newsFeed();
		System.out.println(_followers.size());
	}
	
	private void displayBase() {
		_userFrame = new JFrame(super._id);
		_userFrame.getContentPane().setBackground(new Color(255, 225, 240));
		_userFrame.setBounds(100, 100, 382, 470);
		_userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_userFrame.setVisible(true);
		_userFrame.getContentPane().setLayout(null);
	}
	
	private void followUserUI() {
		_txtUsrFollow = new JTextField();
		_txtUsrFollow.setBounds(75, 10, 150, 44);
		_txtUsrFollow.setFont(new Font("Tahoma", Font.PLAIN, 15));
		_userFrame.getContentPane().add(_txtUsrFollow);
		_txtUsrFollow.setColumns(10);
		_txtUsrFollow.setForeground(new Color(0, 0, 0));
		_txtUsrFollow.setBackground(new Color(255, 255, 255));
		
		_txtUserId = new JTextField();
		_txtUserId.setBounds(10, 10, 215, 44);
		_txtUserId.setText("User ID:");
		_txtUserId.setEditable(false);
		_txtUserId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		_txtUserId.setColumns(10);
		_txtUserId.setForeground(new Color(255, 255, 255));
		_txtUserId.setBackground(new Color(255, 176, 216));
		_userFrame.getContentPane().add(_txtUserId);
		
		followUsrBtn();
	}
	private void followErr() {
		JFrame followErrMsg = new JFrame("Error");
		followErrMsg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		followErrMsg.setBounds(280, 170, 310, 80);
		followErrMsg.getContentPane().setBackground(new Color(255, 255, 255));
		followErrMsg.getContentPane().setLayout(null);
		
		JTextPane errMsg = new JTextPane();
		errMsg.setFont(new Font("Tahoma", Font.BOLD, 12));
		errMsg.setEditable(false);				    
		errMsg.setText("ERROR can't follow Group, nonexistant user, or yourself");
		errMsg.setBounds(0, 0, 310, 80);
		errMsg.setBackground(new Color(255, 255, 255));
		errMsg.setForeground(new Color(255, 176, 216));
		followErrMsg.getContentPane().add(errMsg);
		followErrMsg.setFocusable(true);
		followErrMsg.setVisible(true);
	}
	
	private void followUsrBtn() {
		User tempThis = this;
		JButton btnNewButton = new JButton("Follow User");
		btnNewButton.setBounds(235, 10, 123, 44);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(10, 56, 103));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Node user = Start.getNode(_txtUsrFollow.getText());
				System.out.println("follow id: ");
				System.out.println(user._id);
				if (user instanceof User && !(_txtUsrFollow.getText().equals(tempThis._id))) {
					if (_following.isEmpty() || !_following.contains(_txtUsrFollow.getText())) {
						_following.add(_txtUsrFollow.getText());
						_followingModel.addElement(_txtUsrFollow.getText());
						((User)user).addFollower(tempThis);
					}
				}
				else
					followErr();
			}
		});
		_userFrame.getContentPane().add(btnNewButton);
	}
	
	private void lstFollowing() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 348, 155);
		_userFrame.getContentPane().add(scrollPane);
		
		_txtFollowing = new JTextField();
		_txtFollowing.setBackground(new Color(255, 255, 255));
		_txtFollowing.setFont(new Font("Tahoma", Font.PLAIN, 15));
		_txtFollowing.setText("Following");
		scrollPane.setColumnHeaderView(_txtFollowing);
		_txtFollowing.setEditable(false);
		_txtFollowing.setColumns(10);
		
		scrollPane.setViewportView(_followingList);
	}
	
	private void msgPost() {
		_txtMsgEntry = new JTextField();
		_txtMsgEntry.setForeground(Color.BLACK);
		_txtMsgEntry.setFont(new Font("Tahoma", Font.PLAIN, 15));
		_txtMsgEntry.setColumns(10);
		_txtMsgEntry.setBackground(Color.WHITE);
		_txtMsgEntry.setBounds(85, 229, 186, 44);
		_userFrame.getContentPane().add(_txtMsgEntry);
		
		_txtMessage = new JTextField();
		_txtMessage.setText("Message:");
		_txtMessage.setForeground(Color.WHITE);
		_txtMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		_txtMessage.setEditable(false);
		_txtMessage.setColumns(10);
		_txtMessage.setBackground(new Color(255, 176, 216));
		_txtMessage.setBounds(10, 229, 261, 44);
		_userFrame.getContentPane().add(_txtMessage);
		
		msgPostBtn();
	}
	
	private void msgPostBtn() {
		JButton btnPost = new JButton("Post");
		btnPost.setForeground(new Color(255, 255, 255));
		btnPost.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPost.setBackground(new Color(10, 56, 103));
		btnPost.setBounds(281, 229, 77, 44);
		btnPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Post userPost = new Post(_id, _txtMsgEntry.getText());
				_feedModel.addElement(userPost.toString());
				updateFollowerFeed(userPost);
			}
		});
		_userFrame.getContentPane().add(btnPost);
	}
	
	private void newsFeed() {
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 283, 348, 140);
		_userFrame.getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(_lstNewsFeed);
		
		_txtNewsFeed = new JTextField();
		_txtNewsFeed.setFont(new Font("Tahoma", Font.PLAIN, 15));
		_txtNewsFeed.setText("News Feed");
		_txtNewsFeed.setEditable(false);
		_txtNewsFeed.setBackground(new Color(255, 255, 255));
		scrollPane_1.setColumnHeaderView(_txtNewsFeed);
		_txtNewsFeed.setColumns(10);
	}
	
	private void addFollower(User follower) {
		_followers.add(follower);
	}
	
	private void addToFeed(Post p) {
		_feedModel.addElement(p.toString());
	}
	
	private void updateFollowerFeed(Post p) {
		for (User u: _followers)
			u.addToFeed(p);
	}
}
