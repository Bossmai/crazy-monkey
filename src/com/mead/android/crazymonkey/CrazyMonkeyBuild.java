package com.mead.android.crazymonkey;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;

import com.mead.android.crazymonkey.util.Utils;

public class CrazyMonkeyBuild {

	private String crazyMonkeyHome;

	private String androidSdkHome;

	private String androidRootHome;

	private int numberOfEmulators;
	
	private int startUpDelay = 0;

	private StreamTaskListener listener;
	
	private int startPort;
	
	private int endPort;
	
	//private List<int[]> availablePorts;
	
	private LocalChannel channel;
	
	public static final int ADB_CONNECT_TIMEOUT_MS = 60 * 1000;
	
	/** Duration by which emulator booting should normally complete. */
    public static final int BOOT_COMPLETE_TIMEOUT_MS = 360 * 1000;
    
    /** Interval during which killing a process should complete. */
    public static final int KILL_PROCESS_TIMEOUT_MS = 10 * 1000;
	
	public CrazyMonkeyBuild(String numberOfEmulators) throws IOException {
		//TODO config file path
		File crazyMonkeyHome = Utils.getCrazyMonkeyHomeDirectory("D://projects//private//android//crazy-monkey2");
		// get the properties from properties file
		File configFile = new File(crazyMonkeyHome.getAbsolutePath(), "config.ini");
		Map<String, String> config = Utils.parseConfigFile(configFile);

		this.setAndroidSdkHome(config.get("ANDROID_SDK_HOME"));
		this.setAndroidRootHome(config.get("ANDROID_ROOT_HOME"));
		
		try {
			this.setStartPort(Integer.parseInt(config.get("START_PORT")));
		} catch (NumberFormatException e) {
			
		}
		
		try {
			this.setEndPort(Integer.parseInt(config.get("END_PORT")));
		} catch (NumberFormatException e) {
			
		}
		
		try {
			this.setStartUpDelay(Integer.parseInt(config.get("STARTUP_DELAY")));
		} catch (NumberFormatException e) {
			
		}

		int numOfEmulator = 15;

		try {
			if (config.get("NUMBER_OF_EMULATORS") != null) {
				numOfEmulator = Integer.parseInt(config.get("NUMBER_OF_EMULATORS"));
			}
			if (numberOfEmulators != null) {
				numOfEmulator = Integer.parseInt(numberOfEmulators);
			}
		} catch (NumberFormatException e) {

		}

		this.setNumberOfEmulators(numOfEmulator);
		this.listener = StreamTaskListener.fromStdout();
		this.channel = new LocalChannel(Executors.newCachedThreadPool());
		//this.generateAvailablePorts();
	}

	/*
	public void generateAvailablePorts() {
		int start = this.getStartPort();
		this.availablePorts = new ArrayList<int[]>();

		for (int i = 0; i < numberOfEmulators; i++) {
			int[] ports = new int[3];
			for (int j = 0; j < ports.length; j++) {
				ports[j] = start++;
			}
			this.availablePorts.add(ports);
		}
	}
	*/
	
	private static final int MAX_TRIES = 100;
	
	public synchronized int[] getNextPorts() {
		/*
		if (hasPorts()) {
			int index = new Random(225).nextInt(this.availablePorts.size());
			int[] ports = this.getAvailablePorts().get(index);
			this.getAvailablePorts().remove(index);
			return ports;
		}
		*/
		int count = 2;
		int end = this.getEndPort();
		int start = this.getStartPort();
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
                    }
                    allocated[offset] = i;
                } catch (IOException ex) {
                    break;
                } catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
        
        return allocated;
        
	}
	
	/*
	public synchronized void freePorts(int adbPort, int userPort, int adbServerPort) {
		int[] ports = new int[]{adbPort, userPort, adbServerPort};
		this.availablePorts.add(ports);
	}
	*/
	
	/*
	public boolean hasPorts () {
		return this.availablePorts.size() > 0;
	}
	*/

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
	
	/*
	public List<int[]> getAvailablePorts() {
		return availablePorts;
	}

	public void setAvailablePorts(List<int[]> availablePorts) {
		this.availablePorts = availablePorts;
	}
	*/
	
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
}
