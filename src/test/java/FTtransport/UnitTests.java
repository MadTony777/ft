package FTtransport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTests extends BaseClass{



    @BeforeEach
    public void executedBeforeEach(TestInfo testInfo) {
        log.info("Starting test: "+ testInfo.getDisplayName());
    }

    @AfterEach
    public void executedAfterEach() {
        log.info("End test\n");
    }

    @Test
    public void ft_putFile_13noKAV_COMPLETED() throws Exception {
        String get = TestFT.put(randomCid, "system-1", "system-3", "no", environment);
        assertEquals("[COMPLETED]", get);
    }

    @Test
    public void ft_putFile_41KAV_COMPLETED() throws Exception {
        String get = TestFT.put(randomCid, "system-4", "system-1", "no", environment);
        assertEquals("[COMPLETED]", get);
    }

    @Test
    public void ft_putFile_21noKAV_COMPLETED() throws Exception {
        String get = TestFT.put(randomCid, "system-2-dmz", "system-1", "no", environment);
        assertEquals("[COMPLETED]", get);
    }

    @Test
    public void ft_putFile_54KAV_COMPLETED() throws Exception {
        String get = TestFT.put(randomCid, "system-5-dmz", "system-4", "no", environment);
        assertEquals("[COMPLETED]", get);
    }

//    @Test
//    public void ft_putFile_41KavVirus_INFECTION() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.put(ciD, "system-4", "system-1", "virus", environment);
//        assertEquals("[kav-agent returns status='INFECTION' with message='null']", get);
//    }
//
//    @Test
//    public void ft_putFile_54KavVirus_INFECTION() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.put(ciD, "system-5-dmz", "system-4", "virus", environment);
//        assertEquals("[kav-agent returns status='INFECTION' with message='null']", get);
//    }

    @Test
    public void ft_getFile_31noKAV_COMPLETED() throws Exception {
        String get = TestFT.get(randomCid, "system-3", "system-1", "no", environment);
        assertEquals("[COMPLETED]", get);
    }

    @Test
    public void ft_getFile_41KAV_COMPLETED() throws Exception {
        String get = TestFT.get(randomCid, "system-4", "system-1", "no", environment);
        assertEquals("[COMPLETED]", get);
    }

    @Test
    public void ft_getFile_21noKAV_COMPLETED() throws Exception {
        String get = TestFT.get(randomCid, "system-2-dmz", "system-1", "no", environment);
        assertEquals("[COMPLETED]", get);
    }


    @Test
    public void ft_getFile_54KAV_COMPLETED() throws Exception {
        String get = TestFT.get(randomCid, "system-5-dmz", "system-4", "no", environment);
        assertEquals("[COMPLETED]", get);
    }

//    @Test
//    public void ft_getFile_41KavVirus_INFECTION() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.get(ciD, "system-4", "system-1", "virus", environment);
//        assertEquals("[kav-agent returns status='INFECTION' with message='null']", get);
//    }
//
//
//    @Test
//    public void ft_getFile_54KavVirus_INFECTION() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.get(ciD, "system-5-dmz", "system-4", "virus", environment);
//        assertEquals("[kav-agent returns status='INFECTION' with message='null']", get);
//    }


    @Test
    public void ft_putToStorage_4noKAVwithStringSource_COMPLETED() throws Exception {
        String get = TestFT.putToStorage(randomCid, "system-4", "with string", "no", environment);
        assertEquals("[COMPLETED]", get);
    }


    @Test
    public void ft_putToStorage_5KAVwithStringSource_COMPLETED() throws Exception {
        int cid = randomCid;
        String get = TestFT.putToStorage(randomCid, "system-5-dmz", "with string", "no", environment);
        assertEquals("[COMPLETED]", get);
    }


    @Test
    public void ft_putToStorage_4noKAVwithoutStringSource_COMPLETED() throws Exception {
        int cid = randomCid;
        String get = TestFT.putToStorage(randomCid, "system-4", "without string", "no", environment);
        assertEquals("[COMPLETED]", get);
    }


    @Test
    public void ft_putToStorage_5KAVwithoutStringSource_COMPLETED() throws Exception {
        String get = TestFT.putToStorage(randomCid, "system-5-dmz", "without string", "no", environment);
        assertEquals("[COMPLETED]", get);
    }

//    @Test
//    public void ft_putToStorage_5KavVirusWithStringSource_INFECTION() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.putToStorage(ciD, "system-5-dmz", "with string", "virus", environment);
//        assertEquals("[kav-agent returns status='INFECTION' with message='null']", get);
//    }
//
//    @Test
//    public void ft_putToStorage_5KavVirusWithoutStringSource_INFECTION() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.putToStorage(ciD, "system-5-dmz", "without string", "virus", environment);
//        assertEquals("[kav-agent returns status='INFECTION' with message='null']", get);
//    }

    @Test
    public void ft_getFromStorage_4withStringSource_COMPLETED() throws Exception {
        String get = TestFT.getFromStorage(randomCid, "with string", "system-4", environment);
        assertEquals("[COMPLETED]", get);
    }

    @Test
    public void ft_getFromStorage_4withoutStringSource_COMPLETED() throws Exception {
        String get = TestFT.getFromStorage(randomCid, "without string", "system-4", environment);
        assertEquals("[COMPLETED]", get);
    }


    @Test
    public void ft_getFromStorage_5withStringSource_COMPLETED() throws Exception {
        String get = TestFT.getFromStorage(randomCid, "with string", "system-5-dmz", environment);
        assertEquals("[COMPLETED]", get);
    }


    @Test
    public void ft_getFromStorage_5withoutStringSource_COMPLETED() throws Exception {
        String get = TestFT.getFromStorage(randomCid, "without string", "system-5-dmz", environment);
        assertEquals("[COMPLETED]", get);
    }


    @Test
    public void ft_put_noFile_ERROR() throws Exception {
        String get = TestFT.putError(randomCid, "nofile", "no", environment);
        assertEquals("[ERROR]", get);
    }
//
//    @Test
//    public void ft_put_wrongFormat_ERROR() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.putError(ciD, "format.gimp", "no", environment);
//        assertEquals("[ERROR]", get);
//    }


//    @Test
//    public void ft_put_formatPNG_COMPLETED() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.putFormat(ciD, "format.png", "no", environment);
//        assertEquals("[COMPLETED]", get);
//    }
//
//    @Test
//    public void ft_put_formatPDF_COMPLETED() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.putFormat(ciD, "format.pdf", "no", environment);
//        assertEquals("[COMPLETED]", get);
//    }
//
//    @Test
//    public void ft_put_formatTXT_COMPLETED() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.putFormat(ciD, "format.txt", "no", environment);
//        assertEquals("[COMPLETED]", get);
//    }


    @Test
    public void ft_put_noFormat_COMPLETED() throws Exception {
        String get = TestFT.putFormat(randomCid, "format", "no", environment);
        assertEquals("[COMPLETED]", get);
    }

    @Test
    public void ft_put_bigFile_ERROR() throws Exception {
        String get = TestFT.putBig(randomCid, "no", environment);
        assertEquals("[ERROR]", get);
    }


//    @Test
//    public void ft_putFile_31_noKavVirus_successfully() throws Exception {
//        int ciD = randomCid;
//        TestFT testft = new TestFT();
//        String get = testft.put(ciD, "system-3", "system-1", "virus", environment);
//        assertEquals("[Target system received file successfully]", get);
//    }
}