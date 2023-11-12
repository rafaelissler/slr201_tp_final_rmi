package slr201_tp_final_rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client extends Thread{
	int id;
	RemoteInterface rmiObj;
	
	public Client(int id, RemoteInterface rmiObj) {
		this.id = id;
		this.rmiObj = rmiObj;
	}

	public static void main(String[] args) {
        System.out.println("[INIT] Client started");
		int numPhi = Integer.parseInt(args[0]);
		int port = Integer.parseInt(args[1]);
		String ipName = args[2];
		Client[] clients = new Client[numPhi];
		
        try {
            // Get a reference to the remote object from the registry and create clients
            Registry registry = LocateRegistry.getRegistry(ipName, port);
            for (int i = 0; i < numPhi; i++) {
                clients[i] = new Client(i, (RemoteInterface) (registry.lookup("Philosopher" + i)));
            }
            
            // Start clients separately from the previous loop
            for (int i = 0; i < numPhi; i++) {
                clients[i].start();
            }
            
            // Wait for clients to end
            for (int i = 0; i < numPhi; i++) {
                clients[i].join();
            }
        }
        catch (Exception e) { e.printStackTrace(); return;}
        System.out.println("[INIT] Client connected with port " + port + " and IP " + ipName);
	}
	
	public void run() {
        System.out.println("[" + id + "] Client created");
        try {
	        while (true) {
				int timeToThink = (int) (Math.random()*256);
				System.out.println("[" + id + "] Philosopher " + id + " is thinking for " + timeToThink + "ms");
		        Thread.sleep(timeToThink);
		    	System.out.println("[" + id + "] Philosopher " + id + " finished thinking");
		    	
				int timeToEat = (int) (Math.random()*256);
				System.out.println("[" + id + "] Philosopher " + id + " wants to eat for " + timeToEat + "ms");
				while (rmiObj.eat(timeToEat));
		    	System.out.println("[" + id + "] Philosopher " + id + " finished eating");
			}
	    }
        catch (Exception e) {  e.printStackTrace(); return;}
	}
}
