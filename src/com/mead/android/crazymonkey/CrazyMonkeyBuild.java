package com.mead.android.crazymonkey;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;

import com.mead.android.crazymonkey.process.Callable;
import com.mead.android.crazymonkey.process.LocalChannel;
import com.mead.android.crazymonkey.util.Utils;

public class CrazyMonkeyBuild {

	private String crazyMonkeyHome;

	private String androidSdkHome;

	private String androidRootHome;

	private int numberOfEmulators;
	
	private int startUpDelay = 2;

	private int startPort;
	
	private int endPort;
	
	private String logPath;
	
	private LocalChannel channel;
	
	private String apkFilePath;
	
	private String testScriptPath;
	
	private String nodeHttpServer;
	
	private StreamTaskListener listener;
	
	private int configPhoneDelay = 10;
	
	private Set<Integer> occupiedPorts = new HashSet<Integer>();
	
	public static final int ADB_CONNECT_TIMEOUT_MS = 60 * 1000;
	
	/** Duration by which emulator booting should normally complete. */
    public static final int BOOT_COMPLETE_TIMEOUT_MS = 360 * 1000;
    
    /** Interval during which killing a process should complete. */
    public static final int KILL_PROCESS_TIMEOUT_MS = 10 * 1000;
	
	public CrazyMonkeyBuild() throws IOException {
		// get the properties from properties file
		File configFile = new File(".", "config.ini");
		
		Map<String, String> config = Utils.parseConfigFile(configFile);
		File crazyMonkeyFile = Utils.getCrazyMonkeyHomeDirectory(".");
		
		this.setCrazyMonkeyHome(crazyMonkeyFile.getAbsolutePath());
		this.setAndroidSdkHome(config.get("android.sdk.home"));
		this.setAndroidRootHome(config.get("android.sdk.root"));
		
		this.setNodeHttpServer(config.get("node.httpserver"));
		
		try {
			this.setStartPort(Integer.parseInt(config.get("emulator.start_port")));
		} catch (NumberFormatException e) {
			
		}
		
		try {
			this.setEndPort(Integer.parseInt(config.get("emulator.end_port")));
		} catch (NumberFormatException e) {
			
		}
		
		try {
			this.setStartUpDelay(Integer.parseInt(config.get("emulator.start_up_delay")));
		} catch (NumberFormatException e) {

		}

		try {
			this.setConfigPhoneDelay(Integer.parseInt(config.get("emulator.config_phone_delay")));
		} catch (NumberFormatException e) {

		}
		
		int numOfEmulator = 15;

		try {
			if (config.get("emulator.max_number") != null) {
				numOfEmulator = Integer.parseInt(config.get("emulator.max_number"));
			}
		} catch (NumberFormatException e) {

		}

		this.setNumberOfEmulators(numOfEmulator);
		
		this.setLogPath(this.crazyMonkeyHome + "//logs");
		this.setApkFilePath(config.get("apk.file_path"));
		this.setTestScriptPath(config.get("apk.test_script_path"));
		
		this.listener = StreamTaskListener.fromStdout();
		this.channel = new LocalChannel(Executors.newCachedThreadPool());
	}

	private static final int MAX_TRIES = 100;
	
	public synchronized int[] getNextPorts() {
		
		int count = 2;
		int start = this.getStartPort();
		int end = this.getEndPort();
		boolean isConsecutive = true;
		
		int[] allocated = new int[count];
		

        boolean allocationFailed = true;
        Random rnd = new Random();

        // Attempt the whole allocation a few times using a brute force approach.
        for (int trynum = 0; (allocationFailed && (trynum < MAX_TRIES)); trynum++) {
            allocationFailed = false;

            // Allocate all of the ports in the range
            for (int offset = 0; offset < count; offset++) {

                final int requestedPort;
                if (!isConsecutive || (offset == 0)) {
                    requestedPort = rnd.nextInt((end - start) - count) + start;
                } else {
                    requestedPort = allocated[0] + offset;
                }
                try {
                    final int i;
                    synchronized (this) {
                        i = allocatePort(requestedPort);
                        if (!this.occupiedPorts.contains(i)) {
                            this.occupiedPorts.add(i);
                        } else {
                        	throw new IOException("The port has been occupied.");
                        }
                    }
                    allocated[offset] = i;
                } catch (IOException ex) {
                	allocationFailed = true;
                    break;
                } catch (InterruptedException e) {
                	allocationFailed = true;
					e.printStackTrace();
				}
            }
        }
        return allocated;
        
	}
	
	public String getCrazyMonkeyHome() {
		return crazyMonkeyHome;
	}

	public void setCrazyMonkeyHome(String crazyMonkeyHome) {
		this.crazyMonkeyHome = crazyMonkeyHome;
	}

	public String getAndroidSdkHome() {
		return androidSdkHome;
	}

	public void setAndroidSdkHome(String androidSdkHome) {
		this.androidSdkHome = androidSdkHome;
	}

	public String getAndroidRootHome() {
		return androidRootHome;
	}

	public void setAndroidRootHome(String androidRootHome) {
		this.androidRootHome = androidRootHome;
	}

	public int getNumberOfEmulators() {
		return numberOfEmulators;
	}

	public void setNumberOfEmulators(int numberOfEmulators) {
		this.numberOfEmulators = numberOfEmulators;
	}

	public StreamTaskListener getListener() {
		return listener;
	}

	public void setListener(StreamTaskListener listener) {
		this.listener = listener;
	}
	
	public int getStartUpDelay() {
		return startUpDelay;
	}

	public void setStartUpDelay(int startUpDelay) {
		this.startUpDelay = startUpDelay;
	}

	public int getStartPort() {
		return startPort;
	}

	public void setStartPort(int startPort) {
		this.startPort = startPort;
	}

	public int getEndPort() {
		return endPort;
	}

	public void setEndPort(int endPort) {
		this.endPort = endPort;
	}
	
	public LocalChannel getChannel() {
		return channel;
	}

	public void setChannel(LocalChannel channel) {
		this.channel = channel;
	}
	
	
	private int allocatePort(final int port) throws InterruptedException, IOException {
        return this.getChannel().call(new AllocateTask(port));
    }
	
	private static final class AllocateTask implements Callable<Integer,IOException> {
        private final int port;

        public AllocateTask(int port) {
            this.port = port;
        }

        public Integer call() throws IOException {
            ServerSocket server;
            try {
                server = new ServerSocket(port);
            } catch (IOException e) {
                throw e;
            }
            int localPort = server.getLocalPort();
            server.close();
            return localPort;
        }

        private static final long serialVersionUID = 1L;
    }

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getApkFilePath() {
		return apkFilePath;
	}

	public void setApkFilePath(String apkFilePath) {
		this.apkFilePath = apkFilePath;
	}

	public String getTestScriptPath() {
		return testScriptPath;
	}

	public void setTestScriptPath(String testScriptPath) {
		this.testScriptPath = testScriptPath;
	}

	public Set<Integer> getOccupiedPorts() {
		return occupiedPorts;
	}

	public void setOccupiedPorts(Set<Integer> occupiedPorts) {
		this.occupiedPorts = occupiedPorts;
	}
	
	public synchronized void freePorts (int[] ports) {
		if (ports != null) {
			for (int i = 0; i < ports.length; i++) {
				if (this.occupiedPorts.contains(ports[i])) {
					this.occupiedPorts.remove(ports[i]);
				}
			}
		}
	}

	public String getNodeHttpServer() {
		return nodeHttpServer;
	}

	public void setNodeHttpServer(String nodeHttpServer) {
		this.nodeHttpServer = nodeHttpServer;
	}

	public int getConfigPhoneDelay() {
		return configPhoneDelay;
	}

	public void setConfigPhoneDelay(int configPhoneDelay) {
		this.configPhoneDelay = configPhoneDelay;
	}
}
