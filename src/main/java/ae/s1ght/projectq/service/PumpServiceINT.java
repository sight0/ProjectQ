package ae.s1ght.projectq.service;

import ae.s1ght.projectq.model.Lane;
import ae.s1ght.projectq.model.Pump;
import ae.s1ght.projectq.model.Vehicle;

import java.util.List;
import java.util.Map;


public interface PumpServiceINT {
    Pump getPumpById(Long id);
    List<Pump> getAllPumps();
    Map<String, String> getReservers();
    Pump savePump(Pump pump);
    void startFueling(Long id, double initialFuel);
    void deletePump(Long id);
    int calculateAvailablePumps(List<Pump> pumps);
    double getAmountPayable(Pump pump);
    void assignVehicleToPump(Vehicle vehicle, Pump pump);
    Pump findAvailablePump(Lane lane);
    Lane resetPump(Pump pump);
    double getPercentage(Pump pump);
    double calculateApproxTTC(Pump pump);
    List<Pump> getPumpsByLaneId(Long laneId);
}
