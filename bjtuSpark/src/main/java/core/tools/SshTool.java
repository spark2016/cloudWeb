package core.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServlet;
import com.sshtools.logging.LoggerFactory;
import com.sshtools.logging.LoggerLevel;
import com.sshtools.logging.SimpleLogger;
import com.sshtools.net.SocketTransport;
import com.sshtools.publickey.ConsoleKnownHostsKeyVerification;
import com.sshtools.ssh.PasswordAuthentication;
import com.sshtools.ssh.PseudoTerminalModes;
import com.sshtools.ssh.SshAuthentication;
import com.sshtools.ssh.SshClient;
import com.sshtools.ssh.SshConnector;
import com.sshtools.ssh.SshSession;

public class SshTool {
	
//	private String hostname = "172.31.34.36";
//	private String username = "spark";
//	private String password = "123456";
	private String hostname = "9.181.24.218";
	private String username = "root";
	private String password = "passw0rd";
	
	private SshClient ssh;
	
	public void startConnection() {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
		try {
			
			System.out.println("Hostname: " + hostname);
	
			int idx = hostname.indexOf(':');
			int port = 22;
			if (idx > -1) {
				port = Integer.parseInt(hostname.substring(idx + 1));
				hostname = hostname.substring(0, idx);
	
			}
	
			System.out.println("Username [Enter for "
					+ System.getProperty("user.name") + "]: " + username);
	
			if (username == null || username.trim().equals(""))
				username = System.getProperty("user.name");
	
			/**
			 * Create an SshConnector instance
			 */
			SshConnector con = SshConnector.createInstance();
	
			con.getContext().setHostKeyVerification(new ConsoleKnownHostsKeyVerification());
			/**
			 * Connect to the host
			 */
	
			System.out.println("Connecting to " + hostname);
	
			SocketTransport transport = new SocketTransport(hostname, port);
	
			System.out.println("Creating SSH client");
	
			ssh = con.connect(transport, username, true);
	
			/**
			 * Authenticate the user using password authentication
			 */
			PasswordAuthentication pwd = new PasswordAuthentication();
	
			do {
				pwd.setPassword(password);
				System.out.println("Password: " + pwd.toString());
			} while (ssh.authenticate(pwd) != SshAuthentication.COMPLETE
					&& ssh.isConnected());
	
//			ssh.disconnect();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public void stopConnection() {
		ssh.disconnect();
	}
	
	public void sshSession() {
		try {
			/**
			 * Start a session and do basic IO
			 */
			if (ssh.isAuthenticated()) {
	
				// Some old SSH2 servers kill the connection after the first
				// session has closed and there are no other sessions started;
				// so to avoid this we create the first session and dont ever
				// use it
				final SshSession session = ssh.openSessionChannel();
	
				// Use the newly added PseudoTerminalModes class to
				// turn off echo on the remote shell
				PseudoTerminalModes pty = new PseudoTerminalModes(ssh);
				pty.setTerminalMode(PseudoTerminalModes.ECHO, false);
	
				session.requestPseudoTerminal("vt100", 80, 24, 0, 0, pty);
	
				session.startShell();
	
				Thread t = new Thread() {
					public void run() {
						try {
							int read;
							while ((read = session.getInputStream().read()) > -1) {
								System.out.write(read);
								System.out.flush();
							}
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				};
	
				t.start();
				int read;
				// byte[] buf = new byte[4096];
				while ((read = System.in.read()) > -1) {
					session.getOutputStream().write(read);
	
				}
	
				session.close();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SshClient getSsh() {
		return ssh;
	}

	public void setSsh(SshClient ssh) {
		this.ssh = ssh;
	}
	
}
