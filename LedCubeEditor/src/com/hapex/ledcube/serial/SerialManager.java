package com.hapex.ledcube.serial;

import com.hapex.ledcube.logging.Log;
import gnu.io.CommPortIdentifier;

import java.util.Arrays;
import java.util.Enumeration;

public class SerialManager {

    private CommPortIdentifier portId;
    private SerialConnection connection;
    private Thread connectionThread;

    public SerialManager(String portName) {
        if(!getPortInfo(portName))
            return;

        connection = new SerialConnection(portId);
        connectionThread = new Thread(connection, "SerialThread");
    }

    public void start() {
        connectionThread.start();
    }

    public void finish() {
        connection.finish();
        connectionThread.interrupt();
        connectionThread.stop();

        try {
            connectionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.debug(connectionThread.getName(), String.valueOf(connectionThread.isAlive()));
    }

    public void sendRaw(byte[] data) {
        connection.write(Arrays.copyOf(data,data.length));
    }

    public void sendCube(byte[][] data) {
        byte[] buffer = new byte[200];

        int x,y,i;

        i= 0;

        buffer[i++] = (byte)0xff; // escape
        buffer[i++] = 0x00; // reset to 0,0
        buffer[i++] = 0x00;

        try {
            for (x = 0; x < 8; x++) {
                for (y = 0; y < 8; y++) {
                    buffer[i++] = data[x][y];
                    if (data[x][y] == (byte)0xff) {
                        buffer[i++] = data[x][y];
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Log.error("Index out of range:", e.getMessage());
        }

        connection.write(Arrays.copyOf(buffer, i));
    }


    private boolean getPortInfo(String portName) {
        String defaultPort;
        boolean portFound = false;

        // determine the name of the serial port on several operating systems
        String osname = System.getProperty("os.name","").toLowerCase();
        if ( osname.startsWith("windows") ) {
            // windows
            defaultPort = "COM4";
        } else if (osname.startsWith("linux")) {
            // linux
            defaultPort = "/dev/ttyS0";
        } else if ( osname.startsWith("mac") ) {
            // mac
            defaultPort = "????";
        } else {
            Log.error("Sorry, your operating system is not supported");
            return false;
        }

        if (!portName.isEmpty()) {
            defaultPort = portName;
        }

        Log.debug("Set default port to "+defaultPort);

        // parse ports and if the default port is found, initialized the reader
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(defaultPort)) {
                    Log.info("Found port: "+defaultPort);
                    portFound = true;
                    break;
                }
            }

        }
        if (!portFound) {
            Log.error("Port " + defaultPort + " not found.");
            return false;
        }

        return true;
    }
}
