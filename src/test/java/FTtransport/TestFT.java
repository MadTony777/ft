package FTtransport;

import java.util.UUID;

import static FTtransport.FTstatusGET.statusMethod;


public class TestFT extends KafkaTest {


    public static String put(int ciD, String source, String target, String virus, String environment) throws Exception {
        String a = String.valueOf(ciD);
        String way = "";
        String transferTarget = "";
        String consuming = "";
        switch (source) {
            case "system-2-dmz":
                way = "dmz";
                transferTarget = "sys2";
                switch (virus) {
                    case "virus":
                        consuming = source;
                        break;
                    case "no":
                        consuming = target;
                }
                break;
            case "system-5-dmz":
                way = "dmz";
                transferTarget = "sys5";
                switch (virus) {
                    case "virus":
                        consuming = source;
                        break;
                    case "no":
                        consuming = target;
                }
                break;
            case "system-1":
                way = "lan";
                transferTarget = "sys1";
                consuming = source;
                break;
            case "system-3":
                way = "lan";
                transferTarget = "sys3";
                consuming = source;
                break;
            case "system-4":
                way = "lan";
                transferTarget = "sys4";
                consuming = source;
        }
        String fname ="test1" + ciD + ".txt";
        transferMethod(ciD, way, transferTarget, fname, virus, environment);
        String fileName = "";
        switch (virus) {
            case "virus":
                fileName = "eicar.com";
                break;
            case "no":
                fileName = fname;
        }
        putMethod(ciD, source, target, fileName, environment);
        pauseMethod(20000);
        String trace = "";
        switch (virus) {
            case "virus":
                trace = consumeMethod(a, consuming, environment);
                break;
            case "no":
                trace = consumeTraceForIncomeMethod(a, consuming, environment);
                break;
        }
        receivedMethod(trace, ciD, source, environment);
        pauseMethod(5000);
        return statusMethod(trace, virus, environment);
    }


    public static String putFormat(int ciD, String name, String virus, String environment) throws Exception {
        String a = String.valueOf(ciD);
        transferMethod(ciD, "lan", "sys4", name, virus, environment);
        putMethod(ciD, "system-4", "system-1", name, environment);
        pauseMethod(20000);
        String trace = consumeTraceForIncomeMethod(a, "system-1", environment);
        receivedMethod(trace, ciD, "system-1", environment);
        pauseMethod(5000);
        return statusMethod(trace, virus, environment);
    }


    public static String putError(int ciD, String name, String virus, String environment) throws Exception {
        String a = String.valueOf(ciD);
        String fname ="test1" + ciD + ".txt";
        transferMethod(ciD, "lan", "sys1", fname, virus, environment);
        putMethod(ciD, "system-1", "system-3", name, environment);
        pauseMethod(20000);
        String result = "";
        String description1 = "[ft-transfer returns error message='java.io.FileNotFoundException: Source filename='/opt/lan/sys1/out/nofile' does not exist']";
        String description2 = "[route 'system-1' -> 'system-3' is unexpected by file ext pattern 'txt|^$|png' for file 'format.gimp']";
        boolean isTypeFound = false;
        while (!isTypeFound) {
            if (consumeDescriptionMethod(ciD, "system-4", environment).equals(description1) || consumeDescriptionMethod(ciD, "system-4", environment).equals(description2)) {
                isTypeFound = true;
                String trace = consumeMethod(a, "system-1", environment);
                receivedMethod(trace, ciD, "system-1", environment);
                pauseMethod(5000);
                result = statusMethod(trace, virus, environment);
            }
        }
        return result;
    }


    public static String putBig(int ciD, String virus, String environment) throws Exception {
        String a = String.valueOf(ciD);
        transferBigMethod(ciD, "lan", "sys1", "big.txt", environment);
        putMethod(ciD, "system-1", "system-3", "big.txt", environment);
        pauseMethod(20000);
        String trace = consumeMethod(a, "system-1", environment);
        receivedMethod(trace, ciD, "system-1", environment);
        pauseMethod(5000);
        return statusMethod(trace, virus, environment);
    }


    public static String get(int ciD, String source, String target, String virus, String environment) throws Exception {
        String a = String.valueOf(ciD);
        String fileid = String.valueOf(UUID.randomUUID());
        String way = "";
        String transferTarget = "";
        switch (source) {
            case "system-2-dmz":
                way = "dmz";
                transferTarget = "sys2";
                break;
            case "system-5-dmz":
                way = "dmz";
                transferTarget = "sys5";
                break;
            case "system-1":
                way = "lan";
                transferTarget = "sys1";
                break;
            case "system-3":
                way = "lan";
                transferTarget = "sys3";
                break;
            case "system-4":
                way = "lan";
                transferTarget = "sys4";
                break;
        }
        String fileName = "";
        switch (virus) {
            case "virus":
                fileName = "eicar.com";
                break;
            case "no":
                fileName = "test1" + ciD + ".txt";
                break;
        }
        transferMethod(ciD, way, transferTarget, fileName, virus, environment);
        getMethod(ciD, source, target, fileid, environment);
        String trace = consumeMethod(a, source, environment);
        putforgetMethod(ciD, source, target, fileName, trace, environment);
        String result = "";
        String typE = "[FILE_INCOME]";
        String description = "[kav-agent returns status='INFECTION' with message='null']";
        boolean isTypeFound = false;
        while (!isTypeFound) {
            switch (virus) {
                case "virus":
                    if (consumeDescriptionMethod(ciD, "system-4", environment).equals(description)) {
                        isTypeFound = true;
                        pauseMethod(20000);
                        result = statusMethod(trace, virus, environment);
                    }
                    break;
                case "no":
                    if (consumeTypeMethod(ciD, target, environment).equals(typE)) {
                        isTypeFound = true;
                        receivedforGetMethod(trace, ciD, source, fileid, environment);
                        pauseMethod(5000);
                        result = statusMethod(trace, virus, environment);
                    }
            }
        }
        return result;
    }


    public static String putToStorage(int ciD, String source, String systemstring, String virus, String environment) throws Exception {
        String a = String.valueOf(ciD);
        String way = "";
        String transferTarget = "";
        switch (source) {
            case "system-2-dmz":
                way = "dmz";
                transferTarget = "sys2";
                break;
            case "system-5-dmz":
                way = "dmz";
                transferTarget = "sys5";
                break;
            case "system-1":
                way = "lan";
                transferTarget = "sys1";
                break;
            case "system-3":
                way = "lan";
                transferTarget = "sys3";
                break;
            case "system-4":
                way = "lan";
                transferTarget = "sys4";
                break;
        }
        String fileName = "";
        String typE = "";
        String typE1 = "[FILE_RECEIVED]";
        String consumeSystem = "";
        switch (virus) {
            case "virus":
                fileName = "eicar.com";
                consumeSystem = source;
                typE = "[ERROR]";
                break;
            case "no":
                fileName = "test1" + ciD + ".txt";
                consumeSystem = "system-1";
                typE = "[FILE_INCOME]";
                break;
        }
        transferMethod(ciD, way, transferTarget, fileName, virus, environment);
        putToStorageMethod(ciD, source, fileName, systemstring, environment);
        String result = "";
        boolean isTypeFound = false;
        while (!isTypeFound) {
            if (consumeTypeMethod(ciD, consumeSystem, environment).equals(typE)|| consumeTypeMethodStorage(ciD, consumeSystem, environment).equals(typE1)) {
                isTypeFound = true;
                String trace = consumeMethod(a, consumeSystem, environment);
                pauseMethod(5000);
                result = statusMethod(trace, virus, environment);
            }
        }
        return result;
    }


    public static String getFromStorage(int ciD, String systemstring, String target, String environment) throws Exception {
        String virus = "no";
        String a = String.valueOf(ciD);
        String way = "";
        String transferTarget = "";
        switch (target) {
            case "system-2-dmz":
                way = "dmz";
                transferTarget = "sys2";
                break;
            case "system-5-dmz":
                way = "dmz";
                transferTarget = "sys5";
                break;
            case "system-1":
                way = "lan";
                transferTarget = "sys1";
                break;
            case "system-3":
                way = "lan";
                transferTarget = "sys3";
                break;
            case "system-4":
                way = "lan";
                transferTarget = "sys4";
        }
        String fname = "test12" + ciD + ".txt";
        transferMethod(ciD + 1, way, transferTarget, fname, virus, environment);
        putToStorageMethod(ciD + 1, target, fname, systemstring, environment);
        String fileid = "";
        String result = "";
        boolean isTypeReceived = false;
        while (!isTypeReceived) {
            if (consumeTypeMethod(ciD + 1, target, environment).equals("[FILE_RECEIVED]")) {
                isTypeReceived = true;
                String fileid1 = consumeFileIdMethod(ciD + 1, target, environment);
                fileid = fileid1.replace("[", "").replace("]", "");
                getFromStorageMethod(ciD, target, systemstring, fileid, environment);
                boolean isTypeIncome = false;
                while (!isTypeIncome) {
                    if (consumeTypeMethod(ciD, target, environment).equals("[FILE_INCOME]")) {
                        isTypeIncome = true;
                        String trace = consumeTraceForIncomeMethod(a, target, environment);
                        receivedforGetMethod(trace, ciD, target, fileid, environment);
                        pauseMethod(5000);
                        result = statusMethod(trace, virus, environment);
                    }
                }
            }
        }
        return result;
    }
}

