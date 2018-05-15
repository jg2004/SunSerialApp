package services;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import models.Device;

/**
 * Created by Jerry on 5/4/2018.
 */
public class PollingService extends ScheduledService {

    private Device device;

    public PollingService(Device device) {

        this.device = device;
    }

    @Override
    protected Task createTask() {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                System.out.println("inside polling service thread");
                device.pollNextCommand();
                return null;
            }
        };
        return task;
    }


    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("poilling service succeeded, inside thread: " + Thread.currentThread().getName());
    }

    @Override
    protected void ready() {
        System.out.println("polling service ready called, inside thread: " + Thread.currentThread().getName());

        super.ready();
    }

    @Override
    public void reset() {
        System.out.println("polling service reset called");

        super.reset();
    }

    @Override
    public void restart() {
        super.restart();
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("polling service failed called, inside thread: " + Thread.currentThread().getName());
    }


}
