package com.github.mob41.osumer.ui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.github.mob41.osumer.Config;
import com.github.mob41.osumer.exceptions.DebugDump;
import com.github.mob41.osumer.exceptions.DebuggableException;
import com.github.mob41.osumer.exceptions.DumpManager;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class AccountSettingsPanel extends JPanel {
	private JTextField usrFld;
	private JPasswordField pwdFld;

	private Config config;
	
	/**
	 * Create the panel.
	 */
	public AccountSettingsPanel(Config conf) {
		this.config = conf;
		
		String user = config.getUser();
		String pass = config.getPass();
		
		JLabel lblUsername = new JLabel("Username:");
		
		usrFld = new JTextField();
		usrFld.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		
		pwdFld = new JPasswordField();
		
		if (user != null){
			usrFld.setText(user);
		}
		
		if (pass != null){
			pwdFld.setText(pass);
		}
		
		JButton btnSaveConfigurationFor = new JButton("Save configuration for osumerExpress");
		btnSaveConfigurationFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				config.setUser(usrFld.getText());
				config.setPass(new String(pwdFld.getPassword()));
				try {
					config.write();
				} catch (IOException e) {
					DumpManager.getInstance()
						.addDump(new DebugDump(
							null,
							"Set password via setPass() in configuration",
							"Writing configuration to file",
							"Show Error Dialog",
							"Error occurred on writing configuration",
							false,
							e));
					JOptionPane.showMessageDialog(null, "Error occurred on writing configuration:\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton btnRemoveConfiguration = new JButton("Remove configuration / Reset");
		btnRemoveConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usrFld.setText("");
				pwdFld.setText("");
				config.removeUser();
				config.removePass();
				try {
					config.write();
				} catch (IOException e1) {
					DumpManager.getInstance()
						.addDump(new DebugDump(
							null,
							"Reset / Remove password via removePass() in configuration",
							"Writing configuration to file",
							"Show Error Dialog",
							"Error occurred on writing configuration",
							false,
							e1));
					JOptionPane.showMessageDialog(null, "Error occurred on writing configuration:\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblUsername, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(pwdFld, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
								.addComponent(usrFld, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)))
						.addComponent(btnRemoveConfiguration, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
						.addComponent(btnSaveConfigurationFor, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(usrFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(pwdFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
					.addComponent(btnSaveConfigurationFor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRemoveConfiguration)
					.addContainerGap())
		);
		setLayout(groupLayout);

	}

}
