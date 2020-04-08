package com.hapex.ledcube.serial;

import com.hapex.ledcube.logging.Log;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.TooManyListenersException;

public class SerialConnection implements Runnable, SerialPortEventListener {

    private static final int BAUD_RATE = 115200;

    private InputStream inputStream;
    private OutputStream outputStream;
    private SerialPort serialPort;

    private volatile byte[] sendBuffer;
    private volatile boolean hasSendData = false;
    private volatile boolean done = false;

    SerialConnection(CommPortIdentifier portId) {
// initalize serial port
        try {
            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
        } catch (PortInUseException e) {
            Log.error("Port", portId.getName(), "already in use!");
        }

        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            Log.error("IO Exception", e.getMessage());
        }

        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            Log.error("Too many listeners");
        }

        // activate the DATA_AVAILABLE notifier
        serialPort.notifyOnDataAvailable(true);

        try {
            // set port parameters
            serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
            Log.error("Could not set serial params", e.getMessage());
        }
    }

    static final String HEXES = "0123456789ABCDEF";
    public static String getHex( byte [] raw ) {
        if ( raw == null ) {
            return null;
        }
        final StringBuilder hex = new StringBuilder( 3 * raw.length );
        for ( final byte b : raw ) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F))).append(' ');
        }
        return hex.toString();
    }

    private void sendBuffer() {
        //Log.info("Writing", String.valueOf(sendBuffer.length), "bytes of data to "+serialPort.getName(), ": ", getHex(sendBuffer));
        try {
            // write string to serial port
            outputStream.write(sendBuffer);
            synchronized (this) {
                hasSendData = false;
            }

        } catch (IOException e) {
            Log.error("IO Error during serial writing:", e.getMessage());
        }
    }

    public void write(byte[] data) {
        while (hasSendData);
        synchronized (this) {
            sendBuffer = data;
            hasSendData = true;
        }
    }

    void finish() {
            done = true;
            serialPort.removeEventListener();

    }

    @Override
    public void run() {
        initPortOutput();

        while (!done) {
            if(hasSendData)
                sendBuffer();
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
                Log.debug("....EVENT: ", "BI");
                break;
            case SerialPortEvent.OE:
                Log.debug("....EVENT: ", "OE");
                break;
            case SerialPortEvent.FE:
                Log.debug("....EVENT: ", "FE");
                break;
            case SerialPortEvent.PE:
                Log.debug("....EVENT: ", "PE");
                break;
            case SerialPortEvent.CD:
                Log.debug("....EVENT: ", "CD");
                break;
            case SerialPortEvent.CTS:
                Log.debug("....EVENT: ", "CTS");
                break;
            case SerialPortEvent.DSR:
                Log.debug("....EVENT: ", "DSR");
                break;
            case SerialPortEvent.RI:
                Log.debug("....EVENT: ", "RI");
                break;
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                Log.debug("....EVENT: ", "OUTPUT_BUFFER_EMPTY");
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                // we get here if data has been received
                byte[] readBuffer = new byte[20];
                try {
                    // read data
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                    }
                    // print data
                    String result  = new String(readBuffer);
                    Log.info("Read: " + result);
                } catch (IOException e) {
                    Log.error("IO Error during serial read:", e.getMessage());
                }

                break;
        }
    }

    private void initPortOutput() {
        // initwritetoport() assumes that the port has already been opened and
        //    initialized by constructor

        try {
            // get the outputstream
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
            Log.error("IO Exception:", e.getMessage());
        }

        try {
            // activate the OUTPUT_BUFFER_EMPTY notifier
            serialPort.notifyOnOutputEmpty(true);
        } catch (Exception e) {
            Log.error("Error setting event notification", e.getMessage());
            System.exit(-1);
        }

    }
}
