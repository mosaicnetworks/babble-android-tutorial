package io.mosaicnetworks.myfirstapp;

import java.util.ArrayList;
import java.util.List;

import io.mosaicnetworks.babble.discovery.Peer;
import io.mosaicnetworks.babble.node.BabbleConfig;
import io.mosaicnetworks.babble.node.BabbleNode;
import io.mosaicnetworks.babble.node.KeyPair;
import io.mosaicnetworks.babble.node.LeaveResponseListener;

public class MessagingService {


    private static MessagingService instance;
    private List<MessageObserver> mObservers = new ArrayList<>();
    private BabbleNode mBabbleNode;
    private BabbleState mBabbleState;
    private KeyPair mKeyPair = new KeyPair();
    private static final int BABBLING_PORT = 6666;

    public static MessagingService getInstance() {
        if (instance == null) {
            instance = new MessagingService();
        }
        return instance;
    }

    public void configure(List<Peer> peers, String moniker, String inetAddress) {

        mBabbleState = new BabbleState(new StateObserver() {
            public void onStateChanged(Message message) {
                notifyObservers(message);
            }
        });
        // This assumes that the peer list supplied is empty, and we just need to add ourselves to the
        // peers list.
        peers.add(new Peer(mKeyPair.publicKey, inetAddress + ":" + BABBLING_PORT, moniker));


        try {
            mBabbleNode = BabbleNode.createWithConfig(peers, mKeyPair.privateKey, inetAddress,
                    BABBLING_PORT, moniker, mBabbleState,
                    new BabbleConfig.Builder().logLevel(BabbleConfig.LogLevel.DEBUG).build());
        } catch (IllegalArgumentException ex) {
            //The reassignment of mState and mBabbleNode has failed, so leave them as before
            //TODO: need to catch port in use exception (IOException) and throw others
            throw new RuntimeException(ex);
        }
    }

    public void start() {
        mBabbleNode.run();
    }

    public void stop() {
        mBabbleNode.leave(new LeaveResponseListener() {
            @Override
            public void onSuccess() {
                mBabbleNode = null;
            }
        });
    }

    public void submitMessage(Message message) {
        mBabbleNode.submitTx(message.toBabbleTx().toBytes());
    }


    public void registerObserver(MessageObserver messageObserver) {
        if (!mObservers.contains(messageObserver)) {
            mObservers.add(messageObserver);
        }
    }

    public void removeObserver(MessageObserver messageObserver) {
        mObservers.remove(messageObserver);
    }

    private void notifyObservers(Message message) {
        for (MessageObserver observer : mObservers) {
            observer.onMessageReceived(message);
        }
    }
}

