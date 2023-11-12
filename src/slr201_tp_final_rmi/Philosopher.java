package slr201_tp_final_rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends UnicastRemoteObject implements RemoteInterface  {
	private static final long serialVersionUID = 1L;
	
	int id;
	ReentrantLock leftFork;
	ReentrantLock rightFork;
	
    protected Philosopher(int id, ReentrantLock leftFork, ReentrantLock rightFork) throws RemoteException {
        super();
        System.out.println("[" + id + "] Philosopher " + id + " created");
        this.id = id;
    	this.leftFork = leftFork;
    	this.rightFork = rightFork;
    }

    @Override
    public boolean eat(int timeToEat) throws RemoteException {
    	System.out.println("[" + id + "] Philosopher " + id + " wants to eat");

        try {
	    	leftFork.lock();
    		System.out.println("[" + id + "] Philosopher " + id + " grabbed a fork");
    		Thread.sleep((int)(Math.random()*128));
    		
    		rightFork.lock();
    		System.out.println("[" + id + "] Philosopher " + id + " grabbed other fork");
    		
    		System.out.println("[" + id + "] Philosopher " + id + " started eating for " + timeToEat + "ms");
	        Thread.sleep(timeToEat);
    		System.out.println("[" + id + "] Philosopher " + id + " finished eating");
	    }
        catch (Exception e) { e.printStackTrace(); }
        finally { leftFork.unlock(); rightFork.unlock(); }
        
    	return false;
    }
}
