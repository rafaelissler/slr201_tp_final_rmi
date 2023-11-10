package slr201_tp_final_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote{
	boolean eat(int timeToEat) throws RemoteException;
}
