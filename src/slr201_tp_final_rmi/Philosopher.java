package slr201_tp_final_rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Philosopher extends UnicastRemoteObject implements RemoteInterface  {
	private static final long serialVersionUID = 1L;
	
	int id;
	Object leftFork;
	Object rightFork;
	
    protected Philosopher(int id, Object leftFork, Object rightFork) throws RemoteException {
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
	    	synchronized (leftFork) {
	    		System.out.println("[" + id + "] Philosopher " + id + " grabbed a fork");
	    		Thread.sleep((int)(Math.random()*128));
	        	synchronized (rightFork) {
	        		System.out.println("[" + id + "] Philosopher " + id + " grabbed other fork");
	        		System.out.println("[" + id + "] Philosopher " + id + " started eating for " + timeToEat + "ms");
			        Thread.sleep(timeToEat);
	        		System.out.println("[" + id + "] Philosopher " + id + " finished eating");
	            }
	    	}
	    }
        catch (Exception e) { e.printStackTrace(); return true;}
    	return false;
    }
}
