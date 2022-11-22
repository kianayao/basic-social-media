import java.util.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JTree;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JScrollPane;
import java.awt.event.*;

public class Start {

	private JFrame adminFrame;
	private JTextField txtUserId;
	private JTextField txtGroupId;
	private JTextField txtUserId_1;
	private JTextField txtGroupId_1;
	private JTextField txtTreeView;
	private final JScrollPane scrollPane = new JScrollPane();
	private Node currentNode;
	private List<Node> nodes;
	private List<DefaultMutableTreeNode> treeNodes;
	private int currentIndex;
	private DefaultMutableTreeNode top =
	        new DefaultMutableTreeNode("Root");
	private DefaultTreeModel model;
	private static Group rootGroup;
	private int usr_t = 0, grp_t = 0, msg_t = 0, psprcn = 0;

	
	// Launch the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
					window.adminFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the application.	
	public Start() {
		initialize();
	}

	
	// Initialize the contents of the frame
	private void initialize() {
		rootGroup = new Group("Root");
		treeNodes = new ArrayList<DefaultMutableTreeNode>();
		treeNodes.add(top);
		nodes = new ArrayList<Node>();
		nodes.add(rootGroup);
		currentNode = rootGroup;
		
//		User test = new User("123");
//		test.displayProfile();

		addAdminPanel();
		addTreeUI();
		addUserUI();		
		addGroupUI();
		addUserViewBtn();
		addUserTotalBtn();
		addMsgTotalBtn();
		addGroupTotalBtn();
		addPosPercBtn();
	}
	
	private void addAdminPanel() {
		adminFrame = new JFrame("Admin Panel");
		adminFrame.setResizable(false);
		adminFrame.getContentPane().setBackground(new Color(255, 221, 238));
		adminFrame.setBounds(300, 300, 680, 385);
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// first try at ensuring the structure of the JTree
//	private DefaultMutableTreeNode addTreeGroup(Group g){
//		DefaultMutableTreeNode g_node = new DefaultMutableTreeNode(g._id);
//		
//		for(Map.Entry<String, Node> set : g.children.entrySet()){
//			Node n = set.getValue();
//			if(n instanceof Group)
//				g_node.add(addTreeGroup((Group) n));
//			else
//				g_node.add(new DefaultMutableTreeNode(n._id, false));
//			System.out.println(n);
//		}
//		return g_node;
//	}
	
	private boolean checkUsrinTree(String userId) {
		for (Node n: nodes) {
			if ((n._id).equals(userId))
				return true;
		}
		return false;
	}

	private void updateTreeUI(){
		model.reload(top);  // called from event dispatcher thread
		System.out.print("printing top: ");
		System.out.println(top);
	}

	private void addTreeUI() {
		scrollPane.setBounds(0, 24, 214, 324);
		adminFrame.getContentPane().add(scrollPane);
		JTree tree = new JTree(top, true);
		model = (DefaultTreeModel) tree.getModel();
		scrollPane.setViewportView(tree);
		tree.setBorder(new LineBorder(new Color(10, 56, 103), 1, true));
		tree.setForeground(new Color(255, 255, 255));
		tree.setBackground(new Color(234, 234, 234));
		tree.setFont(new Font("Tahoma", Font.PLAIN, 15));

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me){
				int row = tree.getRowForLocation(me.getX(), me.getY());
				System.out.println(row);
				currentIndex = row;
				if(row != -1)
					currentNode = nodes.get(row);
				// notify function?
			}
		});
		adminFrame.getContentPane().setLayout(null);
		txtTreeView = new JTextField();
		txtTreeView.setSelectionColor(new Color(255, 221, 238));
		txtTreeView.setOpaque(false);
		txtTreeView.setBounds(0, 0, 212, 25);
		adminFrame.getContentPane().add(txtTreeView);
		txtTreeView.setBackground(new Color(0, 0, 0));
		txtTreeView.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtTreeView.setText("Tree View");
		txtTreeView.setEditable(false);
		txtTreeView.setColumns(10);

	}
	
	private void addNotGrpErrMsg() {
		JInternalFrame addtoGrpErrMsg = new JInternalFrame("Error");
		addtoGrpErrMsg.setClosable(true);
		addtoGrpErrMsg.setBounds(280, 170, 330, 60);
		adminFrame.getContentPane().add(addtoGrpErrMsg);
		addtoGrpErrMsg.getContentPane().setLayout(null);
		
		JTextPane errMsg = new JTextPane();
		errMsg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		errMsg.setEditable(false);
		errMsg.setText("ERROR can't add group or user to non-group");
		errMsg.setBounds(0, 0, 330, 47);
		addtoGrpErrMsg.getContentPane().add(errMsg);
		addtoGrpErrMsg.setVisible(true);
	}
	
	private void addIdExistsErrMsg() {
		JInternalFrame addIdErrMsg = new JInternalFrame("Error");
		addIdErrMsg.setClosable(true);
		addIdErrMsg.setBounds(320, 170, 240, 60);
		adminFrame.getContentPane().add(addIdErrMsg);
		addIdErrMsg.getContentPane().setLayout(null);
		
		JTextPane errMsg = new JTextPane();
		errMsg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		errMsg.setEditable(false);
		errMsg.setText("ERROR this ID is already in use");
		errMsg.setBounds(0, 0, 240, 47);
		addIdErrMsg.getContentPane().add(errMsg);
		addIdErrMsg.setVisible(true);
	}
	
	private void addUserBtn() {
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setBounds(489, 9, 167, 44);
		btnAddUser.setForeground(new Color(255, 255, 255));
		btnAddUser.setBackground(new Color(10, 56, 103));
		btnAddUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User newUser = new User(txtUserId.getText());
				if(currentNode instanceof Group){
					if (!checkUsrinTree(txtUserId.getText())) {
						DefaultMutableTreeNode temp = new DefaultMutableTreeNode(txtUserId.getText(), false);
						(treeNodes.get(currentIndex)).add(temp);
						treeNodes.add(temp);
						((Group)currentNode).addChild(newUser);
						nodes.add(newUser);
						updateTreeUI();
						++usr_t;
					}
					else
						addIdExistsErrMsg();
				}
				else
					addNotGrpErrMsg();
				System.out.println(nodes.get(0));

			}
		});
		adminFrame.getContentPane().add(btnAddUser);
	}
	
	private void addUserUI() {
		txtUserId = new JTextField();
		txtUserId.setBounds(295, 10, 184, 44);
		txtUserId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		adminFrame.getContentPane().add(txtUserId);
		txtUserId.setColumns(10);
		
		txtUserId_1 = new JTextField();
		txtUserId_1.setBounds(224, 10, 255, 44);
		txtUserId_1.setForeground(new Color(255, 255, 255));
		txtUserId_1.setBackground(new Color(255, 176, 216));
		txtUserId_1.setEditable(false);
		txtUserId_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUserId_1.setText("User ID:");
		adminFrame.getContentPane().add(txtUserId_1);
		txtUserId_1.setColumns(10);
		
		addUserBtn();
	}
	
	private void addGroupBtn() {
		JButton btnAddGroup = new JButton("Add Group");
		btnAddGroup.setBounds(489, 64, 167, 43);
		btnAddGroup.setForeground(new Color(255, 255, 255));
		btnAddGroup.setBackground(new Color(10, 56, 103));
		btnAddGroup.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Group newGroup = new Group(txtGroupId.getText());
				if(currentNode instanceof Group){
					if (!checkUsrinTree(txtGroupId.getText())) {
						DefaultMutableTreeNode temp = new DefaultMutableTreeNode(txtGroupId.getText(), true);
						(treeNodes.get(currentIndex)).add(temp);
						treeNodes.add(temp);
						
						((Group)currentNode).addChild(newGroup);
						nodes.add(newGroup);
						updateTreeUI();
						++grp_t;
					}
					else
						addIdExistsErrMsg();
				}
				else
					addNotGrpErrMsg();
				System.out.println(nodes.get(0));
			}
		});
		adminFrame.getContentPane().add(btnAddGroup);
		}
	
	private void addGroupUI() {
		txtGroupId = new JTextField();
		txtGroupId.setBounds(295, 64, 184, 44);
		txtGroupId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtGroupId.setColumns(10);
		adminFrame.getContentPane().add(txtGroupId);
		
		txtGroupId_1 = new JTextField();
		txtGroupId_1.setBounds(223, 64, 256, 44);
		txtGroupId_1.setForeground(new Color(255, 255, 255));
		txtGroupId_1.setBackground(new Color(255, 176, 216));
		txtGroupId_1.setEditable(false);
		txtGroupId_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtGroupId_1.setText("Group ID:");
		adminFrame.getContentPane().add(txtGroupId_1);
		txtGroupId_1.setColumns(10);
		
		addGroupBtn();
	}
	
	private void addUserViewBtn() {
		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.setBounds(224, 118, 432, 44);
		btnOpenUserView.setForeground(new Color(255, 255, 255));
		btnOpenUserView.setBackground(new Color(10, 56, 103));
		btnOpenUserView.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnOpenUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentNode instanceof User)
					((User) currentNode).displayProfile();
			}
		});
		adminFrame.getContentPane().add(btnOpenUserView);
	}
	
	private void addUserTotalBtn() {
		JButton btnShowUserTotal = new JButton("Show User Total");
		btnShowUserTotal.setBounds(224, 240, 213, 44);
		btnShowUserTotal.setForeground(new Color(255, 255, 255));
		btnShowUserTotal.setBackground(new Color(10, 56, 103));
		btnShowUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame intFUserTotal = new JInternalFrame("User Total");
				intFUserTotal.setClosable(true);
				intFUserTotal.setBounds(330, 170, 200, 60);
				adminFrame.getContentPane().add(intFUserTotal);
				intFUserTotal.getContentPane().setLayout(null);
				
				JTextPane txtpnusrnum = new JTextPane();
				txtpnusrnum.setFont(new Font("Tahoma", Font.PLAIN, 15));
				txtpnusrnum.setEditable(false);
				txtpnusrnum.setText("There are " + Integer.toString(usr_t) + " users.");
				txtpnusrnum.setBounds(0, 0, 316, 47);
				intFUserTotal.getContentPane().add(txtpnusrnum);
				intFUserTotal.setVisible(true);
			}
		});
		btnShowUserTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		adminFrame.getContentPane().add(btnShowUserTotal);
	}
	
	private void addMsgTotalBtn() {
		JButton btnShowMessagesTotal = new JButton("Show Messages Total");
		btnShowMessagesTotal.setBounds(224, 294, 213, 44);
		btnShowMessagesTotal.setForeground(new Color(255, 255, 255));
		btnShowMessagesTotal.setBackground(new Color(10, 56, 103));
		btnShowMessagesTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnShowMessagesTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame intFMsgsTotal = new JInternalFrame("Messages Total");
				intFMsgsTotal.setClosable(true);
				intFMsgsTotal.setBounds(330, 170, 200, 60);
				adminFrame.getContentPane().add(intFMsgsTotal);
				intFMsgsTotal.getContentPane().setLayout(null);
				
				JTextPane txtpnmsgs = new JTextPane();
				txtpnmsgs.setFont(new Font("Tahoma", Font.PLAIN, 15));
				txtpnmsgs.setEditable(false);
				txtpnmsgs.setText("There are " + Integer.toString(msg_t) + " messages.");
				txtpnmsgs.setBounds(0, 0, 316, 47);
				intFMsgsTotal.getContentPane().add(txtpnmsgs);
				intFMsgsTotal.setVisible(true);
			}
		});
		adminFrame.getContentPane().add(btnShowMessagesTotal);
	}
	
	private void addGroupTotalBtn() {
		JButton btnShowGroupTotal = new JButton("Show Group Total");
		btnShowGroupTotal.setBounds(443, 240, 213, 44);
		btnShowGroupTotal.setForeground(new Color(255, 255, 255));
		btnShowGroupTotal.setBackground(new Color(10, 56, 103));
		btnShowGroupTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnShowGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame intFGroupTotal = new JInternalFrame("Group Total");
				intFGroupTotal.setClosable(true);
				intFGroupTotal.setBounds(330, 170, 200, 60);
				adminFrame.getContentPane().add(intFGroupTotal);
				intFGroupTotal.getContentPane().setLayout(null);
				
				JTextPane txtpngrpnum = new JTextPane();
				txtpngrpnum.setFont(new Font("Tahoma", Font.PLAIN, 15));
				txtpngrpnum.setEditable(false);
				txtpngrpnum.setText("There are " + Integer.toString(grp_t) + " groups.");
				txtpngrpnum.setBounds(0, 0, 316, 47);
				intFGroupTotal.getContentPane().add(txtpngrpnum);
				intFGroupTotal.setVisible(true);
			}
		});
		adminFrame.getContentPane().add(btnShowGroupTotal);
	}
	
	private void addPosPercBtn() {
		JButton btnShowPosPercent = new JButton("Show Positive \r\nPercentage");
		btnShowPosPercent.setBounds(443, 294, 213, 44);
		btnShowPosPercent.setForeground(new Color(255, 255, 255));
		btnShowPosPercent.setBackground(new Color(10, 56, 103));
		btnShowPosPercent.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnShowPosPercent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame intFPosPerc = new JInternalFrame("Group Total");
				intFPosPerc.setClosable(true);
				intFPosPerc.setBounds(330, 170, 230, 60);
				adminFrame.getContentPane().add(intFPosPerc);
				intFPosPerc.getContentPane().setLayout(null);
				
				JTextPane txtpnpsprc = new JTextPane();
				txtpnpsprc.setFont(new Font("Tahoma", Font.PLAIN, 15));
				txtpnpsprc.setEditable(false);
				txtpnpsprc.setText(Integer.toString(psprcn) + "% of messages are positive.");
				txtpnpsprc.setBounds(0, 0, 316, 47);
				intFPosPerc.getContentPane().add(txtpnpsprc);
				intFPosPerc.setVisible(true);
			}
		});
		adminFrame.getContentPane().add(btnShowPosPercent);
	}
	
	public static Node getNode(String userName) {
		Node n;
		Node temp = new Node();
		for(Map.Entry<String, Node> set : rootGroup.children.entrySet()){
			n = set.getValue();
			if(n._id.equals(userName))
				return n;
			else if (n instanceof Group)
				return getNode(userName,(Group)n);
		}
		return new Group("nodeNotFound");
	}
	
	public static Node getNode(String userName, Group g) {
		Node n;
		Node temp = new Node();
		for(Map.Entry<String, Node> set : g.children.entrySet()){
			n = set.getValue();
			System.out.print("getnode: ");
			System.out.println(n._id);
			if(n._id.equals(userName))
				return n;
			else if (n instanceof Group)
				return getNode(userName,(Group)n);
		}
		return new Group("nodeNotFound");
	}
}
