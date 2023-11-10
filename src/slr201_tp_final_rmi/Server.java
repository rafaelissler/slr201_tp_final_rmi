package slr201_tp_final_rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
	private ReentrantLock[] forks;
	private Philosopher[] phi;
	private int numPhi;
	private int port;
	
	public Server(String[] args) {
        System.out.println("[INIT] Server started");
		numPhi = Integer.parseInt(args[0]);
		port = Integer.parseInt(args[1]);

		// Initialize forks
		forks = new ReentrantLock[numPhi];
		for (int i = 0; i < numPhi; i++) {
			forks[i] = new ReentrantLock();
		}
		
		// Initialize philosophers separately
		phi = new Philosopher[numPhi];
		for (int i = 0; i < (numPhi-1); i++) {
			try {
				phi[i] = new Philosopher(i, forks[i], forks[i+1]);
			}
			catch (RemoteException e) {e.printStackTrace();}
		}
		
		// Initialize last philosopher
		try {
			phi[numPhi-1] = new Philosopher(numPhi-1, forks[0], forks[numPhi-1]);
		}
		catch (RemoteException e) {e.printStackTrace();}
		
        System.out.println("[INIT] Forks and philosophers initialized");
		
		// Start RMI registry
		try {
			Registry registry = LocateRegistry.createRegistry(port);
			// Bind the objects
			for (int i = 0; i < numPhi; i++) {
				registry.rebind("Philosopher" + i, phi[i]);
			}
		}
		catch (RemoteException e) {e.printStackTrace();}
        System.out.println("[INIT] Registry initialized");
	}
	public static void main(String[] args) {
		new Server(args);
	}
}
