package core.tools;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

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
	
	private String hostname = "172.31.34.36";
	private String username = "spark";
	private String password = "123456";
//	private String hostname = "9.181.24.218";
//	private String username = "root";
//	private String password = "passw0rd";
	
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
	
	public String sshSession(String userId) {
		String resultStr = "";
		try {
			/**
			 * Start a session and do basic IO
			 */
			if (ssh.isAuthenticated()) {

				// Some old SSH2 servers (Solaris) kill the connection after the
				// first
				// session has closed and there are no other sessions started;
				// so to avoid this we create the first session and dont ever
				// use it
				SshSession s = ssh.openSessionChannel();
				s.startShell();

				ThreadPool pool = new ThreadPool();

				System.out.println("Executing session ");
				System.out.println(ssh.getChannelCount()
						+ " channels currently open");
				SshSession session = null;
				try {
					// 执行sh脚本
					session = ssh.openSessionChannel();

					if (session.requestPseudoTerminal("vt100", 80,
							24, 0, 0)) {

						session.executeCommand("/home/spark/project/test.sh " + userId);
						InputStream in = session.getInputStream();

						ByteArrayOutputStream out = new ByteArrayOutputStream();
						int read;
						while ((read = in.read()) > -1) {
							if (read > 0)
								out.write(read);
						}

						synchronized (System.out) {
							System.out.write(out.toByteArray());
						}
					} else
						System.out
								.println("Failed to allocate pseudo terminal");

					// 读取dat文件
					session.close();
					session = ssh.openSessionChannel();
					if (session.requestPseudoTerminal("vt100", 80,
							24, 0, 0)) {

						session.executeCommand("cat /home/spark/project/result.dat");
						InputStream in = session.getInputStream();

						ByteArrayOutputStream out = new ByteArrayOutputStream();
						int read;
						while ((read = in.read()) > -1) {
							if (read > 0) {
								out.write(read);
							}
						}

						synchronized (System.out) {
							System.out.write(out.toByteArray());
							resultStr = new String(out.toByteArray());
						}
					} else
						System.out
								.println("Failed to allocate pseudo terminal");
				} catch (Throwable t1) {
					t1.printStackTrace();
				} finally {
					if (session != null)
						session.close();
					System.out.println("Completed session ");
				}

			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return resultStr;
	}
	
	class ThreadPool {

		Thread t[] = new Thread[5];

		public synchronized void addOperation(Runnable r) {

			int nextThread;
			System.out.println("Adding new operation");
			while ((nextThread = getNextThread()) == -1) {
				try {
					wait();
				} catch (InterruptedException ex) {
				}
			}

			start(r, nextThread);

		}

		public int getNextThread() {
			synchronized (t) {
				for (int i = 0; i < t.length; i++) {
					if (t[i] == null) {
						return i;
					}
				}
			}
			return -1;

		}

		public synchronized void release() {
			notify();
		}

		public synchronized void start(final Runnable r, final int i) {
			t[i] = new Thread() {
				public void run() {

					try {
						r.run();
					} catch (Exception ex) {
					}

					synchronized (t) {
						t[i] = null;
					}

					release();
				}

			};

			t[i].start();
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
