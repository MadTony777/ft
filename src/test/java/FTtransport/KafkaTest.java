package FTtransport;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class KafkaTest extends BaseClass {
    static void putMethod(int cid, String source, String target, String name, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String msg =
                "{\"cid\":\"" + cid + "\"," +
                        "\"operation\":\"PUT_FILE\"," +
                        "\"sourceSystem\":\"" + source + "\"," +
                        "\"targetSystem\":\"" + target + "\"," +
                        "\"originalFilename\":\"" + name + "\"}";
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("ft-agent-request", msg));
        }
        log.info("Message sent:" + msg);
    }

    static void receivedMethod(String trace, int cidR, String source, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String ded = String.valueOf(UUID.randomUUID());
        String msg =
                "{\"cid\":\"" + cidR + "\"," +
                        "\"traceId\":\"" + trace + "\"," +
                        "\"type\":\"FILE_RECEIVED\"," +
                        "\"fileId\":\"" + ded + "\"}";
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("ft-system-notify", 0, String.valueOf(cidR), msg));
        }
        log.info("Recieved sent:" + msg);
    }

    static String consumeMethod(String cidC, String source, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "ft-system-notify";
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        TopicPartition partition2 = new TopicPartition(topic, 2);
        TopicPartition partition3 = new TopicPartition(topic, 3);
        TopicPartition partition4 = new TopicPartition(topic, 4);
        TopicPartition partition5 = new TopicPartition(topic, 5);
        TopicPartition partition6 = new TopicPartition(topic, 6);
        TopicPartition partition7 = new TopicPartition(topic, 7);
        TopicPartition partition8 = new TopicPartition(topic, 8);
        TopicPartition partition9 = new TopicPartition(topic, 9);
        consumer.assign(Arrays.asList(partition0, partition1, partition2, partition3, partition4, partition5, partition6, partition7, partition8, partition9));
        String result = "";
        boolean isCidFound = false;
        long startMs = System.currentTimeMillis();
        while (!isCidFound) {
            if (System.currentTimeMillis() - startMs >= 120000) {
                throw new RuntimeException("Expired; message was not found by the specified parameters.");
            }
            ConsumerRecords<String, String> records = consumer.poll(10000);
            for (ConsumerRecord<String, String> record : records) {
                if (cidC.equals(record.key())) {
                    isCidFound = true;
                    String part = record.value();
                    String full = "[" + part + "]";
                    List<String> cs = getValuesForGivenKey(full, "traceId");
                    String result1 = String.valueOf(cs);
                    result = result1.replace("[", "").replace("]", "");
                    log.info("Kafka response value:" + part);
                    log.info("Value traceId for Recieved:" + result);
                }
            }
        }
        return result;
    }


    static String consumeTraceForIncomeMethod(String cidC, String source, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "ft-system-notify";
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        TopicPartition partition2 = new TopicPartition(topic, 2);
        TopicPartition partition3 = new TopicPartition(topic, 3);
        TopicPartition partition4 = new TopicPartition(topic, 4);
        TopicPartition partition5 = new TopicPartition(topic, 5);
        TopicPartition partition6 = new TopicPartition(topic, 6);
        TopicPartition partition7 = new TopicPartition(topic, 7);
        TopicPartition partition8 = new TopicPartition(topic, 8);
        consumer.assign(Arrays.asList(partition0, partition1, partition2, partition3, partition4, partition5, partition6, partition7, partition8));
        String result = "";
        boolean isCidFound = false;
        long startMs = System.currentTimeMillis();
        while (!isCidFound) {
            if (System.currentTimeMillis() - startMs >= 120000) {
                throw new RuntimeException("Expired; message was not found by the specified parameters.");
            }
            ConsumerRecords<String, String> records = consumer.poll(100000);
            for (ConsumerRecord<String, String> record : records) {
                if (cidC.equals(record.key())) {
                    isCidFound = true;
                    String part = record.value();
                    String full = "[" + part + "]";
                    List<String> trace = getValuesForGivenKey(full, "traceId");
                    String result1 = String.valueOf(trace);
                    List<String> type = getValuesForGivenKey(full, "type");
                    String type1 = String.valueOf(type);
                    String type2 = "[FILE_INCOME]";
                    if (type1.equals(type2)) {
                        result = result1.replace("[", "").replace("]", "");
                        log.info("Kafka response value:" + part);
                        log.info("Value traceId for Recieved:" + result);
                    }
                }
            }
        }
        return result;
    }

    static void transferMethod(int cid, String way, String target, String name, String virus, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getLanURL(environment);
        props.put("bootstrap.servers", service);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String transferFileName;
        if (virus.equals("virus")) {
            transferFileName = "eicar.com";
            name = "eicar.com";
        } else {
            transferFileName = "transfer1.txt";
        }

        String msg =
                "{\"cid\":\"" + cid + "\"," +
                        "\"operation\":" + "\"COPY\"" + "," +
                        "\"sourceFilename\":\"/opt/dmz/sys5/out/" + transferFileName + "\"," +
                        "\"targetFilename\":\"/opt/" + way + "/" + target + "/out/" + name + "\"}";
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("ft-transfer-request", msg));
        }
        log.info("Transfer:" + msg);
    }

    static void transferBigMethod(int cid, String way, String target, String name, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getLanURL(environment);
        props.put("bootstrap.servers", service);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String msg =
                "{\"cid\":\"" + cid + "\"," +
                        "\"isOverrideIfExists\":" + "true" + "," +
                        "\"isRemoveFromSource\":" + "false" + "," +
                        "\"sourceFilename\":\"/opt/dmz/sys2/out/biga.txt\"," +
                        "\"targetFilename\":\"/opt/" + way + "/" + target + "/out/" + name + "\"}";
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("ft-transfer-request", msg));
        }
        log.info("Transfer:" + msg);
    }


    static void getMethod(int cid, String source, String target, String fileid, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String msg =
                "{\"cid\":\"" + cid + "\"," +
                        "\"fileId\":\"" + fileid + "\"," +
                        "\"operation\":\"GET_FILE\"," +
                        "\"sourceSystem\":\"" + source + "\"," +
                        "\"targetSystem\":\"" + target + "\"}";
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("ft-agent-request", msg));
        }
        log.info("Message sent:" + msg);
    }


    static void putforgetMethod(int cid, String source, String target, String name, String traceId, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String msg =
                "{\"cid\":\"" + cid + "\"," +
                        "\"operation\":\"PUT_FILE\"," +
                        "\"traceId\":\"" + traceId + "\"," +
                        "\"sourceSystem\":\"" + source + "\"," +
                        "\"targetSystem\":\"" + target + "\"," +
                        "\"originalFilename\":\"" + name + "\"}";
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("ft-agent-request", msg));
        }
        log.info("Message sent:" + msg);
    }

    static void receivedforGetMethod(String trace, int cidR, String source, String fileId, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String msg =
                "{\"cid\":\"" + cidR + "\"," +
                        "\"traceId\":\"" + trace + "\"," +
                        "\"type\":\"FILE_RECEIVED\"}";
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("ft-system-notify", 0, String.valueOf(cidR), msg));
        }
        log.info("Recieved sent:" + msg);
    }


    static String consumeTypeMethod(int cidC, String source, String environment) {
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "ft-system-notify";
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        TopicPartition partition2 = new TopicPartition(topic, 2);
        TopicPartition partition3 = new TopicPartition(topic, 3);
        TopicPartition partition4 = new TopicPartition(topic, 4);
        TopicPartition partition5 = new TopicPartition(topic, 5);
        TopicPartition partition6 = new TopicPartition(topic, 6);
        TopicPartition partition7 = new TopicPartition(topic, 7);
        TopicPartition partition8 = new TopicPartition(topic, 8);
        consumer.assign(Arrays.asList(partition0, partition1, partition2, partition3, partition4, partition5, partition6, partition7, partition8));
        String result = "";
        String ciDC = String.valueOf(cidC);
        boolean isCidFound = false;
        long startMs = System.currentTimeMillis();
        while (!isCidFound) {
            if (System.currentTimeMillis() - startMs >= 120000) {
                throw new RuntimeException("Expired; message was not found by the specified parameters.");
            }
            ConsumerRecords<String, String> records = consumer.poll(10000);
            for (ConsumerRecord<String, String> record : records) {
                if (ciDC.equals(record.key())) {
                    isCidFound = true;
                    String part = record.value();
                    String full = "[" + part + "]";
                    List<String> cs = getValuesForGivenKey(full, "type");
                    String result1 = String.valueOf(cs);
                    if ((result1.equals("[FILE_INCOME]")) || result1.equals("[FILE_REQUEST]") || result1.equals("[FILE_RECEIVED]")) {
                        result = result1;
                    }
                }
            }
        }
        return result;
    }

    static String consumeTypeMethodStorage(int cidC, String source, String environment) {
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "ft-system-notify";
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        TopicPartition partition2 = new TopicPartition(topic, 2);
        TopicPartition partition3 = new TopicPartition(topic, 3);
        TopicPartition partition4 = new TopicPartition(topic, 4);
        TopicPartition partition5 = new TopicPartition(topic, 5);
        TopicPartition partition6 = new TopicPartition(topic, 6);
        TopicPartition partition7 = new TopicPartition(topic, 7);
        TopicPartition partition8 = new TopicPartition(topic, 8);
        consumer.assign(Arrays.asList(partition0, partition1, partition2, partition3, partition4, partition5, partition6, partition7, partition8));
        String result = "";
        String ciDC = String.valueOf(cidC);
        boolean isCidFound = false;
        long startMs = System.currentTimeMillis();
        while (!isCidFound) {
            if (System.currentTimeMillis() - startMs >= 120000) {
                throw new RuntimeException("Expired; message was not found by the specified parameters.");
            }
            ConsumerRecords<String, String> records = consumer.poll(10000);
            for (ConsumerRecord<String, String> record : records) {
                if (ciDC.equals(record.key())) {
                    isCidFound = true;
                    String part = record.value();
                    String full = "[" + part + "]";
                    List<String> cs = getValuesForGivenKey(full, "type");
                    result = String.valueOf(cs);
                }
            }
        }
        return result;
    }


    static String consumeDescriptionMethod(int cidC, String source, String environment) {
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "ft-system-notify";
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        TopicPartition partition2 = new TopicPartition(topic, 2);
        TopicPartition partition3 = new TopicPartition(topic, 3);
        TopicPartition partition4 = new TopicPartition(topic, 4);
        TopicPartition partition5 = new TopicPartition(topic, 5);
        TopicPartition partition6 = new TopicPartition(topic, 6);
        TopicPartition partition7 = new TopicPartition(topic, 7);
        TopicPartition partition8 = new TopicPartition(topic, 8);
        consumer.assign(Arrays.asList(partition0, partition1, partition2, partition3, partition4, partition5, partition6, partition7, partition8));
        String result = "";
        String ciDC = String.valueOf(cidC);
        boolean isCidFound = false;
        long startMs = System.currentTimeMillis();
        while (!isCidFound) {
            if (System.currentTimeMillis() - startMs >= 120000) {
                throw new RuntimeException("Expired; message was not found by the specified parameters.");
            }
            ConsumerRecords<String, String> records = consumer.poll(10000);
            for (ConsumerRecord<String, String> record : records) {
                if (ciDC.equals(record.key())) {
                    isCidFound = true;
                    String part = record.value();
                    String full = "[" + part + "]";
                    List<String> cs = getValuesForGivenKey(full, "description");
                    result = String.valueOf(cs);
                }
            }
        }
        return result;
    }

    static void putToStorageMethod(int cid, String source, String name, String message, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String msg = "";
        switch (message) {
            case "with string":
                msg =
                        "{\"cid\":\"" + cid + "\"," +
                                "\"operation\":\"PUT_TO_STORAGE\"," +
                                "\"sourceSystem\":\"" + source + "\"," +
                                "\"targetSystem\":\"filestore\"," +
                                "\"originalFilename\":\"" + name + "\"}";
                break;
            case "without string":
                msg =
                        "{\"cid\":\"" + cid + "\"," +
                                "\"operation\":\"PUT_TO_STORAGE\"," +
                                "\"sourceSystem\":\"" + source + "\"," +
                                "\"originalFilename\":\"" + name + "\"}";
        }
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("ft-agent-request", msg));
        }
        log.info("Message sent:" + msg);
    }

    static String consumeFileIdMethod(int cidC, String source, String environment) {
        Properties props = new Properties();
        String service = getURL(environment, source);
        props.put("bootstrap.servers", service);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "ft-system-notify";
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        TopicPartition partition2 = new TopicPartition(topic, 2);
        TopicPartition partition3 = new TopicPartition(topic, 3);
        TopicPartition partition4 = new TopicPartition(topic, 4);
        TopicPartition partition5 = new TopicPartition(topic, 5);
        TopicPartition partition6 = new TopicPartition(topic, 6);
        TopicPartition partition7 = new TopicPartition(topic, 7);
        TopicPartition partition8 = new TopicPartition(topic, 8);
        consumer.assign(Arrays.asList(partition0, partition1, partition2, partition3, partition4, partition5, partition6, partition7, partition8));
        String result = "";
        String ciDC = String.valueOf(cidC);
        boolean isCidFound = false;
        long startMs = System.currentTimeMillis();
        while (!isCidFound) {
            if (System.currentTimeMillis() - startMs >= 120000) {
                throw new RuntimeException("Expired; message was not found by the specified parameters.");
            }
            ConsumerRecords<String, String> records = consumer.poll(10000);
            if (records != null) {
                for (ConsumerRecord<String, String> record : records) {
                    if (ciDC.equals(record.key())) {
                        isCidFound = true;
                        String part = record.value();
                        String full = "[" + part + "]";
                        List<String> cs = getValuesForGivenKey(full, "fileId");
                        result = String.valueOf(cs);
                    }
                }
            }
        }
        return result;
    }


    static void getFromStorageMethod(int cid, String target, String message, String fileid, String environment) {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        Properties props = new Properties();
        String service = getURL(environment, target);
        props.put("bootstrap.servers", service);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String msg = "";
        switch (message) {
            case "with string":
                msg =
                        "{\"cid\":\"" + cid + "\"," +
                                "\"fileId\":\"" + fileid + "\"," +
                                "\"operation\":\"GET_FROM_STORAGE\"," +
                                "\"sourceSystem\":\"filestore\"," +
                                "\"targetSystem\":\"" + target + "\"}";
                break;
            case "without string":
                msg =
                        "{\"cid\":\"" + cid + "\"," +
                                "\"fileId\":\"" + fileid + "\"," +
                                "\"operation\":\"GET_FROM_STORAGE\"," +
                                "\"targetSystem\":\"" + target + "\"}";
        }
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("ft-agent-request", msg));
        }
        log.info("Message sent:" + msg);
    }

}

