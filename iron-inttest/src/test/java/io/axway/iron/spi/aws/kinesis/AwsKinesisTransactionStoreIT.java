package io.axway.iron.spi.aws.kinesis;

import org.testng.annotations.Test;
import io.axway.iron.core.spi.file.FileStoreFactory;
import io.axway.iron.spi.SpiTest;
import io.axway.iron.spi.aws.BaseInttest;
import io.axway.iron.spi.jackson.JacksonSerializer;

/**
 * Test KinesisTransactionStore and FileSnapshotStore
 */
public class AwsKinesisTransactionStoreIT extends BaseInttest {

    @Test(enabled = false)
    public void shouldCreateCompanySequenceBeRight() throws Exception {
        String randomStoreName = createRandomStoreName();
        createStreamAndWaitActivation(randomStoreName);
        FileStoreFactory fileStoreFactory = buildFileStoreFactoryNoLimitedSize();
        AwsKinesisTransactionStoreFactory awsKinesisTransactionStoreFactory = new AwsKinesisTransactionStoreFactory(m_configuration);

        try {
            JacksonSerializer jacksonSerializer = new JacksonSerializer();
            SpiTest.checkThatCreateCompanySequenceIsRight(awsKinesisTransactionStoreFactory, jacksonSerializer, fileStoreFactory, jacksonSerializer,
                                                          randomStoreName);
        } finally {
            deleteKinesisStream(randomStoreName);
        }
    }

    @Test(enabled = false)
    public void shouldRetrieveCommandsFromSnapshotFileStoreAndNotFromTransactionFileStore() throws Exception {
        String randomStoreName = createRandomStoreName();
        FileStoreFactory fileStoreFactory = buildFileStoreFactoryNoLimitedSize();

        JacksonSerializer jacksonSerializer = new JacksonSerializer();
        SpiTest.checkThatCommandIsExecutedFromSnapshotStoreNotFromTransactionStore(fileStoreFactory, jacksonSerializer, fileStoreFactory, jacksonSerializer,
                                                                                   randomStoreName);
    }

    @Test(enabled = false)
    public void shouldRetrieveCommandsFromSnapshotFileStoreAndNotFromTransactionKinesisStoreSample() throws Exception {
        String randomStoreName = createRandomStoreName();
        createStreamAndWaitActivation(randomStoreName);
        FileStoreFactory fileStoreFactory = buildFileStoreFactoryNoLimitedSize();
        AwsKinesisTransactionStoreFactory awsKinesisTransactionStoreFactory = new AwsKinesisTransactionStoreFactory(m_configuration);

        try {
            JacksonSerializer jacksonSerializer = new JacksonSerializer();
            SpiTest.checkThatCommandIsExecutedFromSnapshotStoreNotFromTransactionStore(awsKinesisTransactionStoreFactory, jacksonSerializer, fileStoreFactory,
                                                                                       jacksonSerializer, randomStoreName);
        } finally {
            deleteKinesisStream(randomStoreName);
        }
    }
}